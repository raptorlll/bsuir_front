package com.timbuchalka.top10downloader.api;

import android.net.Uri;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

public class LoginFetcher extends FetcherAbstract{
    private String password;
    private String username;

    public LoginFetcher(OnDownloadComplete callback, String name, String password) {
        super(callback);
        this.username = name;
        this.password = password;
    }

    public LoginFetcher(OnDownloadComplete callback) {
        super(callback);
    }

    @Override
    public String createUri() {
        return Uri.parse(getUrlConcat()).buildUpon()
                .build().toString();
    }


    @Override
    String getUrl() {
        return "/oauth/token";
    }


    public HttpURLConnection executeRequest(URL url) throws IOException {
        Authenticator.setDefault(new Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("testjwtclientid", "XY7kmzoNzl100".toCharArray());
        }});

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        Uri.Builder builder = new Uri.Builder()
                .appendQueryParameter("username", username)
                .appendQueryParameter("password", password)
                .appendQueryParameter("grant_type", "password");

        String query = builder.build().getEncodedQuery();

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(query);
        writer.flush();
        writer.close();
        os.close();

        connection.connect();
        return connection;
    }
}
