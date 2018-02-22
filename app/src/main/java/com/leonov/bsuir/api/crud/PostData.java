package com.leonov.bsuir.api.crud;

import android.os.AsyncTask;

import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.FetcherAbstract;
import com.leonov.bsuir.api.crud.fetcher.CreateFetcher;
import com.leonov.bsuir.api.crud.fetcher.PostFetcher;
import com.leonov.bsuir.models.ModelInterface;

public class PostData
        extends AsyncTask<String, Void, String>
        implements FetcherAbstract.OnDownloadComplete {
    private String data;
    private String url;
    private OnDataAvailable mCallBack;

    public interface OnDataAvailable {
        void onDataAvailable(String data, DownloadStatus status);
    }

    public PostData(OnDataAvailable callBack, String url) {
        mCallBack = callBack;
        this.url = url;
    }

    public PostData(OnDataAvailable callBack, String url, String data) {
        mCallBack = callBack;
        this.url = url;
        this.data = data;
    }

    @Override
    protected void onPostExecute(String Logins) {
    }

    @Override
    protected String doInBackground(String... params) {
        FetcherAbstract RawDataFetcher = new PostFetcher(url, this, data==null ? "" : data);
        RawDataFetcher.execute();

        return "";
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if(status == DownloadStatus.OK) {
            try {
                if(mCallBack != null) {
                    mCallBack.onDataAvailable(data, DownloadStatus.OK);
                    return;
                }
            } catch(Exception jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        mCallBack.onDataAvailable(data, DownloadStatus.OK);
    }
}





















