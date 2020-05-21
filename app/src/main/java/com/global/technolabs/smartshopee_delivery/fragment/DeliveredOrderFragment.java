package com.global.technolabs.smartshopee_delivery.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.global.technolabs.smartshopee_delivery.Adapter.DeliveredorderAdapter;
import com.global.technolabs.smartshopee_delivery.Adapter.NeworderAdapter;
import com.global.technolabs.smartshopee_delivery.Helper.DialogBox;
import com.global.technolabs.smartshopee_delivery.Helper.L;
import com.global.technolabs.smartshopee_delivery.Helper.Routes;
import com.global.technolabs.smartshopee_delivery.Helper.SharedPreferencesWork;
import com.global.technolabs.smartshopee_delivery.MainActivity;
import com.global.technolabs.smartshopee_delivery.R;
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

public class DeliveredOrderFragment extends Fragment {
    Context context;
    ArrayList arr = new ArrayList();
    private RotateLoading rotateLoading;
    HashMap hashMap;
    String id;

    public DeliveredOrderFragment(Context context) {
        this.context = context;
        SharedPreferencesWork sharedPreferencesWork = new SharedPreferencesWork(context);
        hashMap = sharedPreferencesWork.checkAndReturn(Routes.sharedPrefForLogin, "id");
        id = hashMap.get("id").toString();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_deliveredorder, container, false);
        rotateLoading = (RotateLoading) view.findViewById(R.id.rotateloading);
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.post(Routes.deliveredorder, new AsyncHttpResponseHandler() {
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
                                    hashMap.put("fname", jo.get("fname").toString());
                                    hashMap.put("mobile", jo.get("mobile").toString());
                                    hashMap.put("area", jo.get("area").toString());
                                    hashMap.put("address", jo.get("address").toString());
                                    hashMap.put("status", jo.get("status").toString());
                                    hashMap.put("date", jo.get("date").toString());
                                    hashMap.put("total", jo.get("total").toString());
                                }
                                arr.add(hashMap);
                            }

                            DeliveredorderAdapter deliveredorderAdapter = new DeliveredorderAdapter(arr, context);
                            RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.deliveredorder_recycler);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(context));
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                            recyclerView.setAdapter(deliveredorderAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        L.L(statusCode + str);
                        Toast.makeText(context, "Data not Updated, Please try again", Toast.LENGTH_SHORT).show();                    }
                } catch (Exception ex) {
                    L.L(ex.toString());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
                    error) {
                Toast.makeText(context, "Data not Updated, Please try again", Toast.LENGTH_SHORT).show();            }

            @Override
            public void onRetry(int retryNo) {

            }

        });
        return view;
    }
}
