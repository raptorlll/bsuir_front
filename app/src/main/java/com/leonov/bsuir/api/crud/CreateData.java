package com.leonov.bsuir.api.crud;

import android.os.AsyncTask;

import com.leonov.bsuir.api.crud.fetcher.CreateFetcher;
import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.FetcherAbstract;
import com.leonov.bsuir.models.ModelInterface;

public class CreateData<T extends ModelInterface>
        extends AsyncTask<String, Void, T>
        implements FetcherAbstract.OnDownloadComplete {
    private Class<T> genericClass;
    private T model;
    private T modelOutput;
    private OnDataAvailable<T> mCallBack;

    public interface OnDataAvailable<T> {
        void onDataAvailable(T data, DownloadStatus status);
    }

    public CreateData(OnDataAvailable<T> callBack, T model) {
        mCallBack = callBack;
        this.model = model;
    }

    public CreateData(Class<T> genericClass, OnDataAvailable<T> callBack, T model) {
        mCallBack = callBack;
        this.model = model;
        this.genericClass = genericClass;
    }

    @Override
    protected void onPostExecute(T Logins) {
    }

    @Override
    protected T doInBackground(String... params) {
        String json = ApiCrudFactory.convertElement(genericClass, model);

        FetcherAbstract RawDataFetcher = new CreateFetcher(genericClass, this, json);
        RawDataFetcher.execute();

        return modelOutput;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if(status == DownloadStatus.OK) {
            try {
                T userJson = ApiCrudFactory.convertElement(genericClass, data);
                modelOutput = userJson;

                if(mCallBack != null) {
                    mCallBack.onDataAvailable(modelOutput, DownloadStatus.OK);
                    return;
                }
            } catch(Exception jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        mCallBack.onDataAvailable(modelOutput, DownloadStatus.OK);
    }
}





















