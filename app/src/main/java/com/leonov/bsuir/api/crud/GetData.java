package com.leonov.bsuir.api.crud;

import android.os.AsyncTask;

import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.FetcherAbstract;
import com.leonov.bsuir.api.crud.fetcher.GetFetcher;
import com.leonov.bsuir.api.crud.fetcher.ListFetcher;
import com.leonov.bsuir.models.ModelInterface;

import java.util.Collection;

//import com.leonov.bsuir.convertors.CustomerInformationConvertor;

public class GetData
        extends AsyncTask<String, Void, String>
        implements FetcherAbstract.OnDownloadComplete {

    private final OnDataAvailable mCallBack;
    private String url;

    public interface OnDataAvailable {
        void onDataAvailable(String data, DownloadStatus status);
    }

    public GetData(OnDataAvailable callBack, String url) {
        mCallBack = callBack;
        this.url = url;
    }

    @Override
    protected void onPostExecute(String Logins) {

    }

    @Override
    protected String doInBackground(String... params) {
        FetcherAbstract RawDataFetcher = new GetFetcher(url, this);
        RawDataFetcher.execute();

        return "";
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
       if(status == DownloadStatus.OK) {
            try {
                if(mCallBack != null) {
                    mCallBack.onDataAvailable(data, DownloadStatus.OK);
                }
            } catch(Exception jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
}





















