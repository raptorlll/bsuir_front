package com.timbuchalka.top10downloader.global;

import android.app.Application;
import android.content.Context;

public class GlobalClass extends Application {
    public static Context getContext() {
        return mInstance.getApplicationContext();
    }

    private static GlobalClass mInstance;

    public static synchronized GlobalClass getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }
}