package com.leonov.bsuir.api;

import android.net.Uri;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterFetcher extends FetcherAbstract{
    private String userJson;


    public RegisterFetcher(OnDownloadComplete callback, String userJson) {
        super(callback);
        this.userJson = userJson;
    }

    public RegisterFetcher(OnDownloadComplete callback) {
        super(callback);
    }

    @Override
    public String createUri() {
        return Uri.parse(getUrlConcat()).buildUpon()
                .build().toString();
    }


    @Override
    protected String getUrl() {
        return "/user/registration";
    }


    public HttpURLConnection executeRequest(URL url) throws IOException {

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoInput(true);
        connection.setDoOutput(true);


        connection.setRequestProperty("Content-Type", "application/json");
//
//        Uri.Builder builder = new Uri.Builder()
//                .appendQueryParameter("username", username)
//                .appendQueryParameter("password", password)
//                .appendQueryParameter("grant_type", "password");
//
//        String query = builder.build().getEncodedQuery();

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(userJson);
        writer.flush();
        writer.close();
        os.close();

        connection.connect();
        return connection;
    }
}
