package com.timbuchalka.top10downloader.api.crud;

import android.os.AsyncTask;

import com.timbuchalka.top10downloader.api.crud.fetcher.DeleteFetcher;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.FetcherAbstract;

public class DeleteData<T>
        extends AsyncTask<String, Void, T>
        implements FetcherAbstract.OnDownloadComplete {
    private Long id;

    private final OnDataAvailable<T> mCallBack;

    public interface OnDataAvailable<T>{
        void onDataAvailable(T data, DownloadStatus status);
    }

    public DeleteData(OnDataAvailable<T> callBack, Long id) {
        mCallBack = callBack;
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
        FetcherAbstract RawDataFetcher = new DeleteFetcher(this, id);
        RawDataFetcher.runInSameThread();
        return null;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if(status == DownloadStatus.OK) {
            mCallBack.onDataAvailable(null, status);
        }
    }
}
