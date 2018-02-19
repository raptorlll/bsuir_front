package com.leonov.bsuir.api.crud.fetcher;

import android.net.Uri;

import com.leonov.bsuir.api.FetcherAbstract;
import com.leonov.bsuir.api.crud.ApiCrudFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListFetcher extends FetcherAbstract {
    private Class genericClass;
    private Class queryClass;

    public ListFetcher(OnDownloadComplete callback) {
        super(callback);
    }

    public ListFetcher(Class genericClass, OnDownloadComplete callback) {
        super(callback);
        this.genericClass = genericClass;
    }

    public ListFetcher(Class genericClass, OnDownloadComplete callback, Class queryClass) {
        super(callback);
        this.genericClass = genericClass;
        this.queryClass = queryClass;
    }

    @Override
    public String createUri() {
        return Uri.parse(getUrlConcat()).buildUpon().build().toString();
    }


    @Override
    public String getUrl() {
        return ApiCrudFactory.getUrl(queryClass == null ?  genericClass : queryClass);
    }


    public HttpURLConnection executeRequest(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        attemptAssignToken(connection);

        connection.connect();
        return connection;
    }
}
