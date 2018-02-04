package com.timbuchalka.top10downloader.api.crud;

import android.os.AsyncTask;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.crud.fetcher.CreateFetcher;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.FetcherAbstract;
import com.timbuchalka.top10downloader.models.ModelInterface;

import java.lang.reflect.Type;

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
        if(mCallBack != null) {
            mCallBack.onDataAvailable(modelOutput, DownloadStatus.OK);
        }
    }

    @Override
    protected T doInBackground(String... params) {
        String json = ApiCrudFactory.convertElement(genericClass, model);

        FetcherAbstract RawDataFetcher = new CreateFetcher(genericClass, this, json);
        RawDataFetcher.runInSameThread();

        return modelOutput;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if(status == DownloadStatus.OK) {
            try {
                T userJson = ApiCrudFactory.convertElement(genericClass, data);
                modelOutput = userJson;
            } catch(Exception jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
}





















