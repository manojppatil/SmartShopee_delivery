package com.global.technolabs.smartshopee_delivery.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.global.technolabs.smartshopee_delivery.Helper.L;
import com.global.technolabs.smartshopee_delivery.Helper.Routes;
import com.global.technolabs.smartshopee_delivery.MainActivity;
import com.global.technolabs.smartshopee_delivery.R;
import com.global.technolabs.smartshopee_delivery.ViewOrderActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class NeworderAdapter extends RecyclerView.Adapter<NeworderAdapter.ViewHolder> {
    private ArrayList items;
    private Context context;
    HashMap hm;
    private int inserted_id;
    AlertDialog alertDialog1;
    CharSequence[] values = {" Confirmed ", " Delivered ", " Cancelled "};

    public NeworderAdapter(ArrayList items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.neworderlist_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        hm = (HashMap) items.get(position);
        final int id = Integer.parseInt(hm.get("id").toString());

        String fname = hm.get("fname").toString();
        holder.username.setText(fname);
        final String mobile = hm.get("mobile").toString();

        String address = hm.get("address").toString();
        holder.usseraddress.setText(address);
        String status = hm.get("status").toString();
        holder.orderstatus.setText(status);
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        String orderdate = hm.get("date").toString();
        Date date = null;
        try {
            date = inputFormat.parse(orderdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStart = outputFormat.format(date);
        holder.orderdate.setText(outputDateStart);
        Double total = Double.valueOf(hm.get("total").toString());
        holder.orderprice.setText("Rs. " + total);

        holder.callnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mobile, null));
                context.startActivity(intent);
            }
        });

        holder.vieworder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ViewOrderActivity.class);
                intent.putExtra("order_id", id);
                context.startActivity(intent);
            }
        });

        holder.orderstatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("Update Order Status");

                builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {

                        switch (item) {
                            case 0:
                                RequestParams requestParams = new RequestParams();
                                requestParams.put("id", id);
                                requestParams.put("tbname", "orders");
                                requestParams.put("status", "Confirmed");

                                AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
                                asyncHttpClient.post(Routes.update, requestParams, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        String str = new String(responseBody);
                                        L.L(str);
                                        JSONArray jr = null;
                                        JSONObject jo = null;
                                        try {
                                            if (statusCode == 200) {
                                                try {
                                                    jo = new JSONObject(str);
                                                    if (jo.getString("status").equals("success")) {
                                                        inserted_id = Integer.parseInt(jo.getString("recentinsertedid"));
                                                        Toast.makeText(context, "Order Confirmed", Toast.LENGTH_LONG).show();
                                                        holder.orderstatus.setText("Confirmed");
                                                        context.startActivity(new Intent(context, MainActivity.class));
                                                    }

                                                } catch (JSONException ex) {
                                                    ex.printStackTrace();
                                                }
                                            } else {
                                                Toast.makeText(context, "Status not Updated, Please try again...", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        Toast.makeText(context, "Status not Updated, Please try again...", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                break;
                            case 1:
                                RequestParams requestParams1 = new RequestParams();
                                requestParams1.put("id", id);
                                requestParams1.put("tbname", "orders");
                                requestParams1.put("status", "Delivered");

                                AsyncHttpClient asyncHttpClient1 = new AsyncHttpClient();
                                asyncHttpClient1.post(Routes.update, requestParams1, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        String str = new String(responseBody);
                                        L.L(str);
                                        JSONArray jr = null;
                                        JSONObject jo = null;
                                        try {
                                            if (statusCode == 200) {
                                                try {
                                                    jo = new JSONObject(str);
                                                    if (jo.getString("status").equals("success")) {
                                                        inserted_id = Integer.parseInt(jo.getString("recentinsertedid"));
                                                        Toast.makeText(context, "Order Delivered", Toast.LENGTH_LONG).show();
                                                        holder.orderstatus.setText("Delivered");
                                                        context.startActivity(new Intent(context, MainActivity.class));
                                                    }

                                                } catch (JSONException ex) {
                                                    ex.printStackTrace();
                                                }
                                            } else {
                                                Toast.makeText(context, "Status not Updated, Please try again...", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        Toast.makeText(context, "Status not Updated, Please try again...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            case 2:
                                RequestParams requestParams2 = new RequestParams();
                                requestParams2.put("id", id);
                                requestParams2.put("tbname", "orders");
                                requestParams2.put("status", "Cancelled");

                                AsyncHttpClient asyncHttpClient2 = new AsyncHttpClient();
                                asyncHttpClient2.post(Routes.update, requestParams2, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        String str = new String(responseBody);
                                        L.L(str);
                                        JSONArray jr = null;
                                        JSONObject jo = null;
                                        try {
                                            if (statusCode == 200) {
                                                try {
                                                    jo = new JSONObject(str);
                                                    if (jo.getString("status").equals("success")) {
                                                        inserted_id = Integer.parseInt(jo.getString("recentinsertedid"));
                                                        Toast.makeText(context, "Order Cancelled", Toast.LENGTH_LONG).show();
                                                        holder.orderstatus.setText("Cancelled");
                                                        notifyDataSetChanged();
                                                        context.startActivity(new Intent(context, MainActivity.class));
                                                    }

                                                } catch (JSONException ex) {
                                                    ex.printStackTrace();
                                                }
                                            } else {
                                                Toast.makeText(context, "Status not Updated, Please try again...", Toast.LENGTH_SHORT).show();
                                            }
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        Toast.makeText(context, "Status not Updated, Please try again...", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                        }
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = builder.create();
                alertDialog1.show();


            }
        });

        holder.itemView.setTag(hm);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, usseraddress, orderdate, orderprice, orderstatus;
        TextView callnow, vieworder;

        public ViewHolder(View v) {
            super(v);

            username = itemView.findViewById(R.id.username);
            usseraddress = itemView.findViewById(R.id.useraddress);
            orderdate = itemView.findViewById(R.id.orderdate);
            orderprice = itemView.findViewById(R.id.orderprice);
            orderstatus = itemView.findViewById(R.id.orderstatus);
            callnow = itemView.findViewById(R.id.calluser);
            vieworder = itemView.findViewById(R.id.vieworder);
        }
    }
}
