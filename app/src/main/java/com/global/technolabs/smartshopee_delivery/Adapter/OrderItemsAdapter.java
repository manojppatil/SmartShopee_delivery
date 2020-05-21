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

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ViewHolder> {
    private ArrayList items;
    private Context context;
    HashMap hm;
    private int inserted_id;

    public OrderItemsAdapter(ArrayList items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemsAdapter.ViewHolder holder, int position) {
        hm = (HashMap) items.get(position);
        final int id = Integer.parseInt(hm.get("id").toString());

        String itemname = hm.get("itemname").toString();
        holder.itemname.setText(itemname);
        final String itemquantity = hm.get("itemquantity").toString();
        holder.quantity.setText(itemquantity);
        String itemprice = hm.get("itemprice").toString();
        holder.price.setText(itemprice);
        String itemtotal = hm.get("itemtotal").toString();
        holder.total.setText(itemtotal);

        holder.itemView.setTag(hm);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemname, quantity, price, total;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemname);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.itemprice);
            total = itemView.findViewById(R.id.itemtotal);
        }
    }
}
