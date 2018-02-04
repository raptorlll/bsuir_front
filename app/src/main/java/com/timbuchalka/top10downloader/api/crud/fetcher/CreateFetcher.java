package com.timbuchalka.top10downloader.api.crud.fetcher;

import android.net.Uri;

import com.timbuchalka.top10downloader.api.FetcherAbstract;
import com.timbuchalka.top10downloader.api.crud.ApiCrudFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CreateFetcher extends FetcherAbstract {
    private String customerInformation;
    private Class genericClass;

    public CreateFetcher(Class genericClass,OnDownloadComplete callback, String customerInformation) {
        super(callback);
        this.customerInformation = customerInformation;
        this.genericClass = genericClass;
    }

    @Override
    public String createUri() {
        return Uri.parse(getUrlConcat()).buildUpon().build().toString();
    }

    @Override
    public String getUrl() {
        return ApiCrudFactory.getUrl(genericClass);
    }


    public HttpURLConnection executeRequest(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        attemptAssignToken(connection);

        connection.setRequestProperty("Content-Type", "application/json");
        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(customerInformation);
        writer.flush();
        writer.close();
        os.close();

        connection.connect();
        return connection;
    }
}

