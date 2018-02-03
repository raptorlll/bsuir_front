package com.timbuchalka.top10downloader.api.CustomerInformation.fetcher;

import android.net.Uri;

import com.timbuchalka.top10downloader.api.FetcherAbstract;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CustomerInformationDeleteFetcher extends FetcherAbstract {
    private String guid;
    private Long id;

    public CustomerInformationDeleteFetcher(OnDownloadComplete callback) {
        super(callback);
    }

    public CustomerInformationDeleteFetcher(OnDownloadComplete callback, Long id) {
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

