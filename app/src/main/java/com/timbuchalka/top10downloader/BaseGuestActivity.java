package com.timbuchalka.top10downloader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class BaseGuestActivity extends BaseActivity {
    private static final String TAG = "BaseGuestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Check if user loggedin */
        if (this.isUserLogeddin()) {
            /* Redirect */
            Intent i = new Intent(getBaseContext(), MainActivity.class);
            startActivity(i);
            finish();
        } else {
            Log.d(TAG, "onCreate: Checked auths");
        }
    }
}
