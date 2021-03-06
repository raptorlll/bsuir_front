package com.leonov.bsuir.api.crud;

import android.os.AsyncTask;

//import com.leonov.bsuir.convertors.CustomerInformationConvertor;
import com.leonov.bsuir.api.crud.fetcher.ListFetcher;
import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.FetcherAbstract;
import com.leonov.bsuir.models.ModelInterface;

import java.util.Collection;

public class ListData<T extends ModelInterface>
        extends AsyncTask<String, Void, Collection<T>>
        implements FetcherAbstract.OnDownloadComplete {
    private Collection<T> mLogin;
    private Class<T> genericClass;
    private Class queryClass;
    private String urlParameter;

    private final OnDataAvailable<T> mCallBack;

    public interface OnDataAvailable<T> {
        void onDataAvailable(Collection<T> data, DownloadStatus status);
    }

    public ListData(Class<T> genericClass, OnDataAvailable callBack) {
        mCallBack = callBack;
        this.genericClass = genericClass;
    }

    /** here can override url */
    public ListData(Class<T> genericClass, OnDataAvailable callBack, Class queryClass) {
        mCallBack = callBack;
        this.queryClass = queryClass;
        this.genericClass = genericClass;
    }

    /** here can override url */
    public ListData(Class<T> genericClass, OnDataAvailable callBack, String urlParameter) {
        mCallBack = callBack;
        this.genericClass = genericClass;
        this.urlParameter = urlParameter;
    }

    @Override
    protected void onPostExecute(Collection<T> Logins) {

    }

    @Override
    protected Collection<T> doInBackground(String... params) {
        FetcherAbstract RawDataFetcher = urlParameter!=null ?
                new ListFetcher(genericClass, this, urlParameter):
                new ListFetcher(genericClass, this, queryClass);
        RawDataFetcher.execute();
        return mLogin;
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
       if(status == DownloadStatus.OK) {
            try {
                Collection<T> userJson = ApiCrudFactory.convertCollection(genericClass, data);
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





















