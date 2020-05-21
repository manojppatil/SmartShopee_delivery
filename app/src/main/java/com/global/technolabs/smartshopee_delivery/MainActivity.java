package com.global.technolabs.smartshopee_delivery;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.global.technolabs.smartshopee_delivery.Adapter.SectionsPageAdapter;
import com.global.technolabs.smartshopee_delivery.Helper.Routes;
import com.global.technolabs.smartshopee_delivery.Helper.SharedPreferencesWork;
import com.global.technolabs.smartshopee_delivery.fragment.DeliveredOrderFragment;
import com.global.technolabs.smartshopee_delivery.fragment.NewOrderFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    HashMap hashMap;
    String id;
    TabLayout tabLayout;
    ViewPager viewPager;
    private SectionsPageAdapter mSectionsPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Orders");

        SharedPreferencesWork sharedPreferencesWork = new SharedPreferencesWork(MainActivity.this);
        hashMap = sharedPreferencesWork.checkAndReturn(Routes.sharedPrefForLogin,"id");
        id = hashMap.get("id").toString();
        viewPager = findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setText("New Orders");
        tabLayout.getTabAt(1).setText("Delivered Orders");
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new NewOrderFragment(MainActivity.this));
        adapter.addFragment(new DeliveredOrderFragment(MainActivity.this));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.logout, menu);

        MenuItem menuItem = menu.findItem(R.id.mySwitch);
        menuItem.setActionView(R.layout.use_logout);
        Button logout = menu.findItem(R.id.mySwitch).getActionView().findViewById(R.id.action_switch);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesWork sharedPreferencesWork = new SharedPreferencesWork(MainActivity.this);
                sharedPreferencesWork.eraseData(Routes.sharedPrefForLogin);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        MenuItem menuItem1 = menu.findItem(R.id.myCall);
        menuItem1.setActionView(R.layout.use_call);
        Button call = menu.findItem(R.id.myCall).getActionView().findViewById(R.id.action_call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = "7738790221";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", mobile, null));
                startActivity(intent);
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        finish();
    }
}
