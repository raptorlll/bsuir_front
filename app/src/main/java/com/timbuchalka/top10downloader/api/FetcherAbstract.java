package com.timbuchalka.top10downloader.api;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.timbuchalka.top10downloader.BaseActivity;
import com.timbuchalka.top10downloader.MainActivity;
import com.timbuchalka.top10downloader.global.GlobalClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

abstract public class FetcherAbstract extends AsyncTask<String, Void, String> {
    abstract protected String getUrl();

    private static final String TAG = "FetcherAbstract";
    String host = "http://10.0.2.2:8080";

    private DownloadStatus mDownloadStatus;
    private final OnDownloadComplete mCallback;

    public interface OnDownloadComplete {
        void onDownloadComplete(String data, DownloadStatus status);
    }

    public FetcherAbstract(OnDownloadComplete callback) {
        this.mDownloadStatus = DownloadStatus.IDLE;
        mCallback = callback;
    }


    protected String getUrlConcat() {
        return host.concat(getUrl());
    }

    abstract public String createUri();

    public void runInSameThread() {
        String s = createUri();
        this.execute(s);
//        if(mCallback != null) {
//            mCallback.onDownloadComplete(doInBackground(s), mDownloadStatus);
//        }

        Log.d(TAG, "runInSameThread ends");
    }

    void runInSameThread(String s) {
        Log.d(TAG, "runInSameThread starts");

        if(mCallback != null) {
            mCallback.onDownloadComplete(doInBackground(s), mDownloadStatus);
        }

        Log.d(TAG, "runInSameThread ends");
    }

    @Override
    protected void onPostExecute(String s) {
        if(mCallback != null) {
            mCallback.onDownloadComplete(s, mDownloadStatus);
        }

        Log.d(TAG, "onPostExecute: ends");
    }

    abstract public HttpURLConnection executeRequest(URL url) throws IOException;

    protected void attemptAssignToken(HttpURLConnection connection){
        SharedPreferences sp = GlobalClass.getContext().getSharedPreferences("login", Context.MODE_PRIVATE);

        if (sp.contains("token")) {
            String token = sp.getString("token", "");
            connection.addRequestProperty("Authorization", "Bearer " + token);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if(strings == null) {
            mDownloadStatus = DownloadStatus.NOT_INITIALISED;
            return null;
        }

        try {
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings.length == 0 || strings == null ? createUri() : strings[0]);

            connection = executeRequest(url);

            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code was " + response);

            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                result.append(line).append("\n");
            }

            mDownloadStatus = DownloadStatus.OK;
            return result.toString();
        } catch(MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage() );
        } catch(IOException e) {
            Log.e(TAG, "doInBackground: IO Exception reading data: " + e.getMessage() );
        } catch(SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exception. Needs permission? " + e.getMessage());
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            if(reader != null) {
                try {
                    reader.close();
                } catch(IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream " + e.getMessage() );
                }
            }
        }

        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }

}

























