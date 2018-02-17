package com.leonov.bsuir.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.leonov.bsuir.api.crud.ApiCrudFactory;
import com.leonov.bsuir.api.crud.fetcher.UpdateFetcher;
import com.leonov.bsuir.global.GlobalClass;
import com.leonov.bsuir.models.ModelInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class PutTokenData
        extends AsyncTask<String, Void, String> {
    private final OnDataAvailable mCallBack;
    private final String token;

    public interface OnDataAvailable{
        void onDataAvailable(String data, DownloadStatus status);
    }

    public PutTokenData(OnDataAvailable callBack, String token) {
        mCallBack = callBack;
        this.token = token;
    }

    @Override
    protected void onPostExecute(String token) {
        if(mCallBack != null) {
            mCallBack.onDataAvailable(token, DownloadStatus.OK);
        }
    }

    protected void attemptAssignToken(HttpURLConnection connection){
        SharedPreferences sp = GlobalClass.getContext().getSharedPreferences("login", Context.MODE_PRIVATE);

        if (sp.contains("token")) {
            String token = sp.getString("token", "");
            connection.addRequestProperty("Authorization", "Bearer " + token);
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            URL url = new URL("http://10.0.2.2:8080/user/token");
            HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
            httpCon.setDoOutput(true);
            httpCon.setRequestMethod("POST");

            httpCon.setRequestProperty("Content-Type", "application/json");

            attemptAssignToken(httpCon);

            OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream(), "UTF-8");
            out.write(token);
            out.close();
            httpCon.getInputStream();

            int code = httpCon.getResponseCode();

            return "Ok";
        }catch (IOException e){
            System.out.println("ee");
        }

        return "";
    }
}
