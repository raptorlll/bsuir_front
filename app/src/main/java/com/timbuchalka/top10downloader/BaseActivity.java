package com.timbuchalka.top10downloader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.models.Role;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by timbuchalka on 10/08/2016.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    SharedPreferences sp;
    private Set<Role> roles = new HashSet<>();
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Check if user loggedin */

    }

    void activateToolbar(boolean enableHome) {
//        Log.d(TAG, "activateToolbar: starts");
//        ActionBar actionBar = getSupportActionBar();
//        if(actionBar == null) {
//            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//            if(toolbar != null) {
//                setSupportActionBar(toolbar);
//                actionBar = getSupportActionBar();
//            }
//        }
//
//        if(actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(enableHome);
//        }
    }


    boolean isUserLogeddin() {
        sp = getSharedPreferences("login", MODE_PRIVATE);
        //if SharedPreferences contains username and password then redirect to Home activity
        return  sp.contains("token");
    }

    Set<Role> getRoles(){
        if (roles.size() == 0) {
            parserRoles();
        }

        return roles;
    }

    private void parserRoles() {
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        String rolesString = sp.getString("roles", "");

        Gson gson = new Gson();
        Type type = new TypeToken<Set<Role>>() {}.getType();
        Set<Role> rolesSet = gson.fromJson(rolesString.length() == 0 ? "[]" : rolesString, type);

        /** If there are no roles yet - set default = guest */
        if(rolesSet.size() == 0){
            rolesSet.add(new Role("GUEST", "guest"));
        }
        
        roles = rolesSet;
    }


}
