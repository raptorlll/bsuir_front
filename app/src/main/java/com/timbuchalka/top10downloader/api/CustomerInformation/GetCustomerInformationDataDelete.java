package com.timbuchalka.top10downloader.api.CustomerInformation;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.CustomerInformation.fetcher.CustomerInformationDeleteFetcher;
import com.timbuchalka.top10downloader.api.CustomerInformation.fetcher.CustomerInformationUpdateFetcher;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.FetcherAbstract;
import com.timbuchalka.top10downloader.models.CustomerInformation;

import java.lang.reflect.Type;

//import com.timbuchalka.top10downloader.convertors.CustomerInformationConvertor;

public class GetCustomerInformationDataDelete
        extends AsyncTask<String, Void, CustomerInformation>
        implements FetcherAbstract.OnDownloadComplete {

    private static final String TAG = "GetLoginData";
    private Long id;

    private CustomerInformation mLogin;
    private String mBaseURL;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    public interface OnDataAvailable{
        void onDataAvailable(CustomerInformation data, DownloadStatus status);
    }

    public GetCustomerInformationDataDelete(OnDataAvailable callBack, Long id) {
        Log.d(TAG, "GetLoginData called");
        mCallBack = callBack;
        this.id = id;
    }

    @Override
    protected void onPostExecute(CustomerInformation Logins) {
        Log.d(TAG, "onPostExecute starts");

        if(mCallBack != null) {
            mCallBack.onDataAvailable(mLogin, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute ends");
    }

    @Override
    protected CustomerInformation doInBackground(String... params) {
        FetcherAbstract RawDataFetcher = new CustomerInformationDeleteFetcher(this, id);
        RawDataFetcher.runInSameThread();
        Log.d(TAG, "doInBackground ends");
        return mLogin;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);

        if(status == DownloadStatus.OK) {
            mCallBack.onDataAvailable(mLogin, status);
        }

        Log.d(TAG, "onDownloadComplete ends");
    }
}





















