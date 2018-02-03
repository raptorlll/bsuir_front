package com.timbuchalka.top10downloader.api.crud.fetcher;

import android.net.Uri;

import com.timbuchalka.top10downloader.api.FetcherAbstract;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ListFetcher extends FetcherAbstract {

    public ListFetcher(OnDownloadComplete callback) {
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

        attemptAssignToken(connection);

        connection.connect();
        return connection;
    }
}
