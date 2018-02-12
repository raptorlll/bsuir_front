package com.leonov.bsuir;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.GetLoginData;
import com.leonov.bsuir.api.GetRolesData;
import com.leonov.bsuir.models.Role;
import com.leonov.bsuir.models.Token;

import java.lang.reflect.Type;
import java.util.Set;

public class LoginFragment
        extends Fragment
        implements GetLoginData.OnDataAvailable, GetRolesData.OnDataAvailable  {
    private static final String TAG = "Login fragment";

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup parent,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.login_fragment, parent, false);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        button = (Button) view.findViewById(R.id.button);

        sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        //if SharedPreferences contains username and password then redirect to Home activity
        if (sp.contains("token")) {
            startActivity(new Intent(getActivity(), MainActivity.class));
            getActivity().finish();   //finish current activity
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCheck();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }




    @Override
    public void onDataAvailable(Set<Role> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: ");
        Gson gson = new Gson();
        Type type = new TypeToken<Set<Role>>() {
        }.getType();
        String json = gson.toJson(data, type);

        SharedPreferences.Editor e = sp.edit();
        e.putString("roles", json);
        e.commit();
    }

    @Override
    public void onDataAvailable(Token data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if (status != DownloadStatus.OK) {
            Log.e(TAG, "onDataAvailable failed with status " + status);
            Toast.makeText(getActivity(), "Login Erorr - server error", Toast.LENGTH_LONG).show();
            return;
        }

        if (data == null) {
            // download or processing failed
            Log.e(TAG, "onDataAvailable failed with status " + status);
            Toast.makeText(getActivity(), "Login error", Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences.Editor e = sp.edit();
        e.putString("token", data.getAccess_token());
        e.putString("expires_in", data.getExpires_in());
        e.putString("jti", data.getJti());
        e.putString("scope", data.getScope());
        e.putString("token_type", data.getToken_type());
        e.commit();


        sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        getRoles(sp.getString("token", ""));

        Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getActivity(), MainActivity.class));

        getActivity().finish();

        Log.d(TAG, "onDataAvailable: ends");
    }

    EditText username, password;
    Button button;
    SharedPreferences sp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void authProcess(String username, String password) {
        GetLoginData loginData = new GetLoginData(this);
        loginData.execute(username, password);
    }

    private void getRoles(String token) {
        GetRolesData loginData = new GetRolesData(this);
        loginData.execute(token);
    }


    void loginCheck() {
        //check username and password are correct and then add them to SharedPreferences
        if (username.getText().toString().length() > 1 && password.getText().toString().length() > 1) {
            authProcess(username.getText().toString(), password.getText().toString());
        } else {
            Toast.makeText(getActivity(), "Fill all fields correctly", Toast.LENGTH_LONG).show();
        }
    }
}


















