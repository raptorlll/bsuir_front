package com.leonov.bsuir.api;

import android.net.Uri;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class RolesFetcher extends FetcherAbstract{
    private String token;

    public RolesFetcher(OnDownloadComplete callback, String token) {
        super(callback);
        this.token = token;
    }

    public RolesFetcher(OnDownloadComplete callback) {
        super(callback);
    }

    @Override
    public String createUri() {
        return Uri.parse(getUrlConcat()).buildUpon()
                .build().toString();
    }


    @Override
    protected String getUrl() {
        return "/springjwt/getuserroles";
    }


    public HttpURLConnection executeRequest(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        connection.addRequestProperty("Authorization", "Bearer " + token);

        connection.connect();
        return connection;
    }
}
