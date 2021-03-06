package com.leonov.bsuir.api.crud.fetcher;

import android.net.Uri;

import com.leonov.bsuir.api.FetcherAbstract;
import com.leonov.bsuir.api.crud.ApiCrudFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateFetcher extends FetcherAbstract {
    private Class genericClass;
    private String id;
    private String model;
    UpdateFetcher(OnDownloadComplete callback, String model, String id) {
        super(callback);
        this.model = model;
        this.id = id;
    }

    public UpdateFetcher(Class genericClass, OnDownloadComplete callback, String model, String id) {
        this(callback, model, id);
        this.genericClass = genericClass;
    }

    @Override
    public String createUri() {
        return Uri.parse(getUrlConcat()).buildUpon().build().toString();
    }

    @Override
    public String getUrl() {
        return ApiCrudFactory.getUrl(genericClass).concat("/").concat(id);
    }

    public HttpURLConnection executeRequest(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        connection.setRequestProperty("Content-Type", "application/json");

        attemptAssignToken(connection);

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(model);
        writer.flush();
        writer.close();
        os.close();

        connection.connect();
        return connection;
    }
}

