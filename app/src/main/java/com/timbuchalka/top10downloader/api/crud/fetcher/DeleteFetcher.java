package com.timbuchalka.top10downloader.api.crud.fetcher;

import android.net.Uri;

import com.timbuchalka.top10downloader.api.FetcherAbstract;
import com.timbuchalka.top10downloader.api.crud.ApiCrudFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DeleteFetcher extends FetcherAbstract {
    private Long id;
    private Class genericClass;

    public DeleteFetcher(Class genericClass,OnDownloadComplete callback, Long id) {
        super(callback);
        this.id = id;
        this.genericClass = genericClass;
    }

    @Override
    public String createUri() {
        return Uri.parse(getUrlConcat()).buildUpon().build().toString();
    }

    @Override
    public String getUrl() {
        return ApiCrudFactory.getUrl(genericClass).concat("/").concat(id.toString());
    }

    public HttpURLConnection executeRequest(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");

        attemptAssignToken(connection);

        connection.connect();
        return connection;
    }
}

