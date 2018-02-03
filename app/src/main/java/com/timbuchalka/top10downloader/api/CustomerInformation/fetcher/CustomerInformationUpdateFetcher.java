package com.timbuchalka.top10downloader.api.CustomerInformation.fetcher;

import android.net.Uri;

import com.timbuchalka.top10downloader.api.FetcherAbstract;
import com.timbuchalka.top10downloader.models.CustomerInformation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomerInformationUpdateFetcher extends FetcherAbstract {

    private String guid;
    private String customerInformation;

    public CustomerInformationUpdateFetcher(OnDownloadComplete callback) {
        super(callback);
    }

    public CustomerInformationUpdateFetcher(OnDownloadComplete callback, String customerInformation, String guid) {
        super(callback);
        this.customerInformation = customerInformation;
        this.guid = guid;
    }

    @Override
    public String createUri() {
        return Uri.parse(getUrlConcat()).buildUpon().build().toString();
    }


    @Override
    public String getUrl() {
        return "/customer_information".concat("/").concat(guid);
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
        writer.write(customerInformation);
        writer.flush();
        writer.close();
        os.close();

        connection.connect();
        return connection;
    }
}

