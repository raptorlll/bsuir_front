package com.timbuchalka.top10downloader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.GetLoginData;
import com.timbuchalka.top10downloader.api.GetRegisterData;
import com.timbuchalka.top10downloader.api.GetRolesData;
import com.timbuchalka.top10downloader.models.Role;
import com.timbuchalka.top10downloader.models.Token;
import com.timbuchalka.top10downloader.models.UserJson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RegistrationFragment extends Fragment
    implements GetRegisterData.OnDataAvailable
{
    private static final String TAG = "RegistrationFragment";

    @Override
    public void onDataAvailable(UserJson data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: ");

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new LoginFragment());
        ft.commit();
    }

    EditText username, password, name, lastname, email;
    Button button;
    private SharedPreferences sp;
    private CheckBox checkboxAdmin, checkboxClient, checkboxConsultant;

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
        checkboxAdmin = (CheckBox) view.findViewById(R.id.checkbox_admin);
        checkboxClient = (CheckBox) view.findViewById(R.id.checkbox_client);
        checkboxConsultant = (CheckBox) view.findViewById(R.id.checkbox_consultant);



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


    private void registerProcess(String userString) {
        GetRegisterData registerData = new GetRegisterData(this);
        registerData.execute();
    }

    void registerCheck() {
        //check username and password are correct and then add them to SharedPreferences
        if (username.getText().toString().length() > 1 && password.getText().toString().length() > 1) {
            List<String> roles = new ArrayList<String>();

            if (checkboxAdmin.isChecked()) {
                roles.add("ADMIN_USER");
            }

            if (checkboxConsultant.isChecked()) {
                roles.add("CONSULTANT");
            }

            if (checkboxClient.isChecked()) {
                roles.add("CUSTOMER");
            }


            UserJson userJson = new UserJson();
            userJson.setEmail(email.getText().toString());
            userJson.setFirstName(name.getText().toString());
            userJson.setLastName(lastname.getText().toString());
            userJson.setPassword(password.getText().toString());
            userJson.setUsername(username.getText().toString());
            userJson.setRoles(roles);

            Gson gson = new Gson();
            Type type = new TypeToken<UserJson>() {}.getType();
            String json = gson.toJson(userJson, type);

            registerProcess(json);
//            sendAuthRequest(username.getText().toString(), password.getText().toString());
        } else {
            Toast.makeText(getActivity(), "Fill all fields correctly", Toast.LENGTH_LONG).show();
        }
    }
}


















