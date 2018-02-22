package com.leonov.bsuir.api.crud.fetcher;

import android.net.Uri;

import com.leonov.bsuir.api.FetcherAbstract;
import com.leonov.bsuir.api.crud.ApiCrudFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetFetcher extends FetcherAbstract {
    private String url;

    public GetFetcher(OnDownloadComplete callback) {
        super(callback);
    }

    public GetFetcher(String url, OnDownloadComplete callback) {
        super(callback);
        this.url = url;
    }

    @Override
    public String createUri() {
        return Uri.parse(getUrlConcat()).buildUpon().build().toString();
    }


    @Override
    public String getUrl() {
        return url;
    }


    public HttpURLConnection executeRequest(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        attemptAssignToken(connection);

        connection.connect();
        return connection;
    }
}
