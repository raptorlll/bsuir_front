package com.leonov.bsuir;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.leonov.bsuir.video.SinchService;

/**
 * Created by timbuchalka on 10/08/2016.
 */

public class BaseAuthActivity extends BaseActivity implements ServiceConnection {
    private static final String TAG = "BaseGuestActivity";

    private SinchService.SinchServiceInterface mSinchServiceInterface;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getApplicationContext().bindService(new Intent(this, SinchService.class), this, BIND_AUTO_CREATE);
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

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        if (SinchService.class.getName().equals(componentName.getClassName())) {
            mSinchServiceInterface = (SinchService.SinchServiceInterface) iBinder;
            onServiceConnected();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        if (SinchService.class.getName().equals(componentName.getClassName())) {
            mSinchServiceInterface = null;
            onServiceDisconnected();
        }
    }

    protected void onServiceConnected() {
        // for subclasses
    }

    protected void onServiceDisconnected() {
        // for subclasses
    }

    protected SinchService.SinchServiceInterface getSinchServiceInterface() {
        return mSinchServiceInterface;
    }

}
