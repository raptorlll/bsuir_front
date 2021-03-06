package com.leonov.bsuir.api.crud;

import android.os.AsyncTask;

import com.leonov.bsuir.api.crud.fetcher.UpdateFetcher;
import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.FetcherAbstract;
import com.leonov.bsuir.models.ModelInterface;

public class UpdateData<T extends ModelInterface>
        extends AsyncTask<String, Void, T>
        implements FetcherAbstract.OnDownloadComplete {
    private Class<T> genericClass;
    private T model;

    private T mLogin;

    private final OnDataAvailable mCallBack;

    public interface OnDataAvailable<T>{
        void onDataAvailable(T data, DownloadStatus status);
    }

    public UpdateData(Class<T> genericClass, OnDataAvailable callBack, T model) {
        this.genericClass = genericClass;
        mCallBack = callBack;
        this.model = model;
    }

    @Override
    protected void onPostExecute(T Logins) {
        if(mCallBack != null) {
            mCallBack.onDataAvailable(mLogin, DownloadStatus.OK);
        }
    }

    @Override
    protected T doInBackground(String... params) {
        String json = ApiCrudFactory.convertElement(genericClass, model);

        FetcherAbstract RawDataFetcher = new UpdateFetcher(genericClass, this, json, model.getId().toString());
        RawDataFetcher.execute();

        return mLogin;
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if(status == DownloadStatus.OK) {
            try {
                T userJson = ApiCrudFactory.convertElement(genericClass, data);
                mLogin = userJson;

                if(mCallBack != null) {
                    mCallBack.onDataAvailable(mLogin, DownloadStatus.OK);
                }
            } catch(Exception jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
}
