package com.global.technolabs.smartshopee_delivery.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.global.technolabs.smartshopee_delivery.R;
import com.global.technolabs.smartshopee_delivery.ViewOrderActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DeliveredorderAdapter extends RecyclerView.Adapter<DeliveredorderAdapter.ViewHolder> {
    private ArrayList items;
    private Context context;
    HashMap hm;
    private int inserted_id;

    public DeliveredorderAdapter(ArrayList items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deliverorderlist_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        hm = (HashMap) items.get(position);
        final int id = Integer.parseInt(hm.get("id").toString());

        String fname = hm.get("fname").toString();
        holder.username.setText(fname);
        final String mobile = hm.get("mobile").toString();

        String address = hm.get("address").toString();
        holder.usseraddress.setText(address);
        String status = hm.get("status").toString();
        holder.orderstatus.setText(status);
        DateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a");
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
                String phone = hm.get("mobile").toString();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
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
