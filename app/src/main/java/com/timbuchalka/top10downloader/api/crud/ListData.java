package com.timbuchalka.top10downloader.api.crud;

import android.os.AsyncTask;

//import com.timbuchalka.top10downloader.convertors.CustomerInformationConvertor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.crud.fetcher.ListFetcher;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.FetcherAbstract;
import com.timbuchalka.top10downloader.models.ModelInterface;

import java.lang.reflect.Type;
import java.util.Collection;

import static com.timbuchalka.top10downloader.api.crud.ApiCrudFactory.convertCollection;

public class ListData<T extends ModelInterface>
        extends AsyncTask<String, Void, Collection<T>>
        implements FetcherAbstract.OnDownloadComplete {
    private Collection<T> mLogin;
    private Class<T> genericClass;

    private final OnDataAvailable<T> mCallBack;

    public interface OnDataAvailable<T> {
        void onDataAvailable(Collection<T> data, DownloadStatus status);
    }

    public ListData(Class<T> genericClass, OnDataAvailable callBack) {
        mCallBack = callBack;
        this.genericClass = genericClass;
    }

    @Override
    protected void onPostExecute(Collection<T> Logins) {
        if(mCallBack != null) {
            mCallBack.onDataAvailable(mLogin, DownloadStatus.OK);
        }
    }

    @Override
    protected Collection<T> doInBackground(String... params) {
        FetcherAbstract RawDataFetcher = new ListFetcher(genericClass, this);
        RawDataFetcher.runInSameThread();
        return mLogin;
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if(status == DownloadStatus.OK) {
            try {
                Collection<T> userJson = ApiCrudFactory.convertCollection(genericClass, data);
                mLogin = userJson;
            } catch(Exception jsone) {
                jsone.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }
    }
}





















