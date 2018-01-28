package com.timbuchalka.top10downloader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.GetLoginData;
import com.timbuchalka.top10downloader.api.GetRolesData;
import com.timbuchalka.top10downloader.models.Role;
import com.timbuchalka.top10downloader.models.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

public class RegistrationActivity
        extends BaseGuestActivity
        implements GetLoginData.OnDataAvailable {
    private static final String TAG = "LoginActivity";

    @Override
    public void onDataAvailable(Token data, DownloadStatus status) {

    }

    EditText username, password;
    Button button;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.button);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        //if SharedPreferences contains username and password then redirect to Home activity
        if (sp.contains("token")) {
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCheck();
            }
        });
    }

    private void authProcess(String username, String password) {
        GetLoginData loginData = new GetLoginData(this);
        loginData.execute(username, password);
    }


    void loginCheck() {
        //check username and password are correct and then add them to SharedPreferences
        if (username.getText().toString().length() > 1 && password.getText().toString().length() > 1) {
            authProcess(username.getText().toString(), password.getText().toString());
        } else {
            Toast.makeText(getBaseContext(), "Fill all fields correctly", Toast.LENGTH_LONG).show();
        }
    }

}


















