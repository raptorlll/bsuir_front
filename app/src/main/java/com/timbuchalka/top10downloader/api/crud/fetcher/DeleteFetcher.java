package com.timbuchalka.top10downloader.api.crud.fetcher;

import android.net.Uri;

import com.timbuchalka.top10downloader.api.FetcherAbstract;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteFetcher extends FetcherAbstract {
    private Long id;

    public DeleteFetcher(OnDownloadComplete callback, Long id) {
        super(callback);
        this.id = id;
    }

    @Override
    public String createUri() {
        return Uri.parse(getUrlConcat()).buildUpon().build().toString();
    }

    @Override
    public String getUrl() {
        return "/customer_information".concat("/").concat(id.toString());
    }

    public HttpURLConnection executeRequest(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        attemptAssignToken(connection);

        connection.connect();
        return connection;
    }
}

