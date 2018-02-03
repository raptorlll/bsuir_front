package com.timbuchalka.top10downloader.api.crud;

import android.os.AsyncTask;

//import com.timbuchalka.top10downloader.convertors.CustomerInformationConvertor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.crud.fetcher.ListFetcher;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.FetcherAbstract;

import java.lang.reflect.Type;
import java.util.Collection;

public class ListData<T>
        extends AsyncTask<String, Void, Collection<T>>
        implements FetcherAbstract.OnDownloadComplete {
    private Collection<T> mLogin;

    private final OnDataAvailable<T> mCallBack;

    public interface OnDataAvailable<T> {
        void onDataAvailable(Collection<T> data, DownloadStatus status);
    }

    public ListData(OnDataAvailable callBack) {
        mCallBack = callBack;
    }

    @Override
    protected void onPostExecute(Collection<T> Logins) {
        if(mCallBack != null) {
            mCallBack.onDataAvailable(mLogin, DownloadStatus.OK);
        }
    }

    @Override
    protected Collection<T> doInBackground(String... params) {
        FetcherAbstract RawDataFetcher = new ListFetcher(this);
        RawDataFetcher.runInSameThread();
        return mLogin;
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if(status == DownloadStatus.OK) {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<Collection<T>>() {}.getType();
                Collection<T> userJson = gson.fromJson(data, type);
                mLogin = userJson;
            } catch(Exception jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
}





















