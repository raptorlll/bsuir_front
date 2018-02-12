package com.leonov.bsuir;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by timbuchalka on 10/08/2016.
 */

public class BaseAuthActivity extends BaseActivity {
    private static final String TAG = "BaseGuestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Check if user loggedin */
        if (!this.isUserLogeddin()) {
            /* Redirect */
            Intent i = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(i);
            finish();
        } else {
            Log.d(TAG, "onCreate: Checked auths");
        }
    }
}
