package com.leonov.bsuir;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.GetLoginData;
import com.leonov.bsuir.models.Token;

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
//this.contex
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


















