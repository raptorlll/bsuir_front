package com.timbuchalka.top10downloader.api.CustomerInformation.fetcher;

import android.net.Uri;

import com.timbuchalka.top10downloader.api.FetcherAbstract;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

public class CustomerInformationFetcher extends FetcherAbstract {

    public CustomerInformationFetcher(OnDownloadComplete callback) {
        super(callback);
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
        connection.setRequestMethod("GET");

//        connection.addRequestProperty("Authorization", "Bearer " + token);

        connection.connect();
        return connection;
    }
}
