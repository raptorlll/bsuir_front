package com.timbuchalka.top10downloader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.GetLoginData;
import com.timbuchalka.top10downloader.api.GetRolesData;
import com.timbuchalka.top10downloader.models.Token;

public class RegistrationFragment extends Fragment {


    EditText username, password, name, lastname, email;
    Button button;
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        // Defines the xml file for the fragment

        View view = inflater.inflate(R.layout.registration_fragment, parent, false);
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);
        name = (EditText) view.findViewById(R.id.name);
        lastname = (EditText) view.findViewById(R.id.lastname);
        email = (EditText) view.findViewById(R.id.email);
        button = (Button) view.findViewById(R.id.button);

        sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerCheck();
            }
        });

        return view;
        // Defines the xml file for the fragment
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);
    }


    private void registerProcess(String username, String password) {
        PostRegisterData loginData = new PostRegisterData(this);
        loginData.execute(username, password);
    }

    void registerCheck() {
        //check username and password are correct and then add them to SharedPreferences
        if (username.getText().toString().length() > 1 && password.getText().toString().length() > 1) {
            registerProcess(username.getText().toString(), password.getText().toString());
//            sendAuthRequest(username.getText().toString(), password.getText().toString());
        } else {
            Toast.makeText(getActivity(), "Fill all fields correctly", Toast.LENGTH_LONG).show();
        }
    }
}


















