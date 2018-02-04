package com.timbuchalka.top10downloader.api.crud;

import android.os.AsyncTask;

import com.timbuchalka.top10downloader.api.crud.fetcher.DeleteFetcher;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.FetcherAbstract;
import com.timbuchalka.top10downloader.models.ModelInterface;

public class DeleteData<T extends ModelInterface>
        extends AsyncTask<String, Void, T>
        implements FetcherAbstract.OnDownloadComplete {
    private Long id;

    private Class<T> genericClass;
    private final OnDataAvailable<T> mCallBack;

    public interface OnDataAvailable<T>{
        void onDataAvailable(T data, DownloadStatus status);
    }

    public DeleteData(Class<T> genericClass, OnDataAvailable<T> callBack, Long id) {
        mCallBack = callBack;
        this.genericClass = genericClass;
        this.id = id;
    }

    @Override
    protected void onPostExecute(T Logins) {
        if(mCallBack != null) {
            mCallBack.onDataAvailable(null, DownloadStatus.OK);
        }
    }

    @Override
    protected T doInBackground(String... params) {
        FetcherAbstract RawDataFetcher = new DeleteFetcher(genericClass,this, id);
        RawDataFetcher.execute();
        return null;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if(status == DownloadStatus.OK) {
            mCallBack.onDataAvailable(null, status);
        }
    }
}
