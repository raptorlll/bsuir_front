package com.timbuchalka.top10downloader.api.CustomerInformation;

import android.os.AsyncTask;
import android.util.Log;

//import com.timbuchalka.top10downloader.convertors.CustomerInformationConvertor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.CustomerInformation.fetcher.CustomerInformationFetcher;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.FetcherAbstract;
import com.timbuchalka.top10downloader.models.CustomerInformation;

import java.lang.reflect.Type;
import java.util.Collection;

public class GetCustomerInformationData
        extends AsyncTask<String, Void, Collection<CustomerInformation>>
        implements FetcherAbstract.OnDownloadComplete {

    private static final String TAG = "GetLoginData";

    private Collection<CustomerInformation> mLogin;
    private String mBaseURL;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    public interface OnDataAvailable {
        void onDataAvailable(Collection<CustomerInformation> data, DownloadStatus status);
    }

    public GetCustomerInformationData(OnDataAvailable callBack) {
        Log.d(TAG, "GetLoginData called");
        mCallBack = callBack;
    }

    @Override
    protected void onPostExecute(Collection<CustomerInformation> Logins) {
        Log.d(TAG, "onPostExecute starts");

        if(mCallBack != null) {
            mCallBack.onDataAvailable(mLogin, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute ends");
    }

    @Override
    protected Collection<CustomerInformation> doInBackground(String... params) {
        FetcherAbstract RawDataFetcher = new CustomerInformationFetcher(this);
        RawDataFetcher.runInSameThread();
        Log.d(TAG, "doInBackground ends");
        return mLogin;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);
        CustomerInformation CustomerInformation;
        if(status == DownloadStatus.OK) {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<Collection<CustomerInformation>>() {}.getType();
                Collection<CustomerInformation> userJson = gson.fromJson(data, type);
                mLogin = userJson;
            } catch(Exception jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data " + jsone.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        if(runningOnSameThread && mCallBack != null) {
            mCallBack.onDataAvailable(mLogin, status);
        }

        Log.d(TAG, "onDownloadComplete ends");
    }
}





















