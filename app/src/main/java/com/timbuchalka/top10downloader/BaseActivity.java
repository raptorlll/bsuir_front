package com.timbuchalka.top10downloader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Created by timbuchalka on 10/08/2016.
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    static final String FLICKR_QUERY = "FLICKR_QUERY";
    static final String PHOTO_TRANSFER = "PHOTO_TRANSFER";
    SharedPreferences sp;

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


}
