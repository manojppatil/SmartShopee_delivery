package com.global.technolabs.smartshopee_delivery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.global.technolabs.smartshopee_delivery.Adapter.OrderItemsAdapter;
import com.global.technolabs.smartshopee_delivery.Helper.DialogBox;
import com.global.technolabs.smartshopee_delivery.Helper.L;
import com.global.technolabs.smartshopee_delivery.Helper.Routes;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class ViewOrderActivity extends AppCompatActivity {

    TextView orderno, name, phone, area, address, total;
    private RotateLoading rotateLoading;
    int order_id;
    ArrayList arr = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        setTitle("View Order");
        Intent intent = getIntent();
        order_id = intent.getIntExtra("order_id", -1);

        orderno = findViewById(R.id.showorderno);
        name = findViewById(R.id.showname);
        phone = findViewById(R.id.showphone);
        area = findViewById(R.id.showarea);
        address = findViewById(R.id.showaddress);
        total = findViewById(R.id.total);
        rotateLoading = (RotateLoading) findViewById(R.id.rotateloading);

        RequestParams requestParams = new RequestParams();
        requestParams.put("id", order_id);
        requestParams.put("tbname", "orders");

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(Routes.selectOne, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                rotateLoading.setVisibility(View.VISIBLE);
                rotateLoading.setLoadingColor(Color.BLUE);
                rotateLoading.start();

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                rotateLoading.stop();
                rotateLoading.setVisibility(View.GONE);
                String string = new String(responseBody);
                L.L(string);
                JSONArray jr = null;
                JSONObject jo = null;
                try {
                    if (statusCode == 200) {
                        try {
                            jr = new JSONArray(string);
                            jo = jr.getJSONObject(0);
                            orderno.setText(jo.get("id").toString());
                            name.setText(jo.get("fname").toString());
                            phone.setText(jo.get("mobile").toString());
                            address.setText(jo.get("address").toString());
                            area.setText(jo.get("area").toString());
                            total.setText("Total : " + jo.get("total"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        getOrderItems();
    }

    public void getOrderItems() {
        RequestParams requestParams = new RequestParams();
        requestParams.put("id", order_id);
        requestParams.put("column", "orderid");
        requestParams.put("tbname", "orderslist");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(Routes.selectOneByColumn, requestParams, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                rotateLoading.setVisibility(View.VISIBLE);
                rotateLoading.setLoadingColor(Color.BLUE);
                rotateLoading.start();
            }

            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                rotateLoading.stop();
                rotateLoading.setVisibility(View.GONE);
                String str = new String(responseBody);
                JSONArray jr = null;
                JSONObject jo = null;
                L.L(str);
                try {
                    if (statusCode == 200) {
                        try {
                            jr = new JSONArray(str);
                            for (int i = 0; i < jr.length(); i++) {
                                jo = jr.getJSONObject(i);
                                final HashMap hashMap = new HashMap();
                                if (jo != null) {
                                    hashMap.put("id", jo.get("id").toString());
                                    hashMap.put("itemname", jo.get("itemname").toString());
                                    hashMap.put("itemquantity", jo.get("itemquantity").toString());
                                    hashMap.put("itemprice", jo.get("itemprice").toString());
                                    hashMap.put("itemtotal", jo.get("itemtotal").toString());
                                }
                                arr.add(hashMap);
                            }

                            OrderItemsAdapter orderItemsAdapter = new OrderItemsAdapter(arr, ViewOrderActivity.this);
                            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.item_recycler);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(ViewOrderActivity.this));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(orderItemsAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        L.L(statusCode + str);
                        new DialogBox(ViewOrderActivity.this, str).asyncDialogBox();
                    }
                } catch (Exception ex) {
                    L.L(ex.toString());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                    error) {
                new DialogBox(ViewOrderActivity.this, responseBody.toString());
            }

            @Override
            public void onRetry(int retryNo) {

            }

        });
    }
}
