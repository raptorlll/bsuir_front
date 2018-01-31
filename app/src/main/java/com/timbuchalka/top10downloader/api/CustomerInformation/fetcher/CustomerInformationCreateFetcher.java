package com.timbuchalka.top10downloader.api.CustomerInformation.fetcher;

import android.net.Uri;

import com.timbuchalka.top10downloader.api.FetcherAbstract;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomerInformationCreateFetcher extends FetcherAbstract {

    private String guid;
    private String customerInformation;

    public  CustomerInformationCreateFetcher(OnDownloadComplete callback) {
        super(callback);
    }

    public CustomerInformationCreateFetcher(OnDownloadComplete callback, String customerInformation) {
        super(callback);
        this.customerInformation = customerInformation;
    }

    @Override
    public String createUri() {
        return Uri.parse(getUrlConcat()).buildUpon().build().toString();
    }


    @Override
    public String getUrl() {
        return "/customer_information";
    }


    public HttpURLConnection executeRequest(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setReadTimeout(10000);
        connection.setConnectTimeout(15000);
        connection.setDoInput(true);
        connection.setDoOutput(true);


        connection.setRequestProperty("Content-Type", "application/json");

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(os, "UTF-8"));
        writer.write(customerInformation);
        writer.flush();
        writer.close();
        os.close();

        connection.connect();
        return connection;
    }
}

