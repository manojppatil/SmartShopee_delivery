package com.global.technolabs.smartshopee_delivery;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.global.technolabs.smartshopee_delivery.Helper.DialogBox;
import com.global.technolabs.smartshopee_delivery.Helper.L;
import com.global.technolabs.smartshopee_delivery.Helper.NetworkConnection;
import com.global.technolabs.smartshopee_delivery.Helper.Routes;
import com.global.technolabs.smartshopee_delivery.Helper.SharedPreferencesWork;
import com.google.android.material.textfield.TextInputLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    EditText mobile, password;
    Button login;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;
    String strmobile, strpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        new SharedPreferencesWork(LoginActivity.this).checkForLogin();
        mobile = (EditText) findViewById(R.id.mobile);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        passError = (TextInputLayout) findViewById(R.id.passError);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gologin();
            }
        });

    }

    private void gologin() {
        strmobile = mobile.getText().toString();
        strpass = password.getText().toString();
        if (NetworkConnection.checkNetworkConnection(this)) {
            ArrayList validationMessage = new ArrayList();
            if (strmobile.length() > 0) {
            } else
                validationMessage.add("Mobile number is not valid");

            if (password.getText().toString().length() > 0) {
            } else
                validationMessage.add("password field is empty");

            if (validationMessage.size() > 0) {
                String allmessage = "";
                for (int i = 0; i < validationMessage.size(); i++) {
                    allmessage += "\n" + validationMessage.get(i);
                    new DialogBox(this, allmessage).asyncDialogBox();
                    validationMessage.clear();
                }
            } else {
                RequestParams params = new RequestParams();
                params.add("id", strmobile);
                params.add("column", "contact");
                params.add("tbname", "delivery_boy");
                AsyncHttpClient client = new AsyncHttpClient();
                client.post(Routes.selectOneByColumn, params, new AsyncHttpResponseHandler() {
                    ProgressDialog dialog;

                    @Override
                    public void onStart() {
                        dialog = ProgressDialog.show(LoginActivity.this, "Working",
                                "Signing in...", true);
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        dialog.dismiss();
                        String str = new String(responseBody);
                        L.L(str);
                        JSONArray jr = null;
                        JSONObject jo = null;
                        if (statusCode == 200) {
                            try {
                                jr = new JSONArray(str);
                                jo = jr.getJSONObject(0);
                                String userid = jo.getString("id");
                                String area = jo.getString("area");
//                                    deviceToken = FirebaseInstanceId.getInstance().getToken();
//                                    UsersRef.child("Users").child(userid).child("mobile").setValue(mobile);
//                                    UsersRef.child("Users").child(userid).child("device_token").setValue(deviceToken);

                                HashMap hm = new HashMap();
                                hm.put("userid", strmobile);
                                hm.put("password", strpass);
                                hm.put("area", area);
                                hm.put("id", userid);

                                new SharedPreferencesWork(LoginActivity.this).insertOrReplace(hm, Routes.sharedPrefForLogin);
                                Intent admin = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(admin);
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            L.L(statusCode + str);
                            Toast.makeText(LoginActivity.this, "Enter right credentials", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(LoginActivity.this, "Connection Timed out", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        dialog = ProgressDialog.show(LoginActivity.this, "Logging in...",
                                "Saving.Please wait...", true);
                    }
                });
            }
        } else {
            new DialogBox(this, "Check your network connnection").asyncDialogBox();
        }
    }
}
