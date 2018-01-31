package com.timbuchalka.top10downloader.api.CustomerInformation;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.CustomerInformation.fetcher.CustomerInformationCreateFetcher;
import com.timbuchalka.top10downloader.api.CustomerInformation.fetcher.CustomerInformationFetcher;
import com.timbuchalka.top10downloader.api.CustomerInformation.fetcher.CustomerInformationUpdateFetcher;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.FetcherAbstract;
import com.timbuchalka.top10downloader.models.CustomerInformation;

import java.lang.reflect.Type;

//import com.timbuchalka.top10downloader.convertors.CustomerInformationConvertor;

public class GetCustomerInformationDataCreate
        extends AsyncTask<String, Void, CustomerInformation>
        implements FetcherAbstract.OnDownloadComplete {

    private static final String TAG = "GetLoginData";
    private CustomerInformation model;

    private CustomerInformation mLogin;
    private String mBaseURL;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    public interface OnDataAvailable {
        void onDataAvailable(CustomerInformation data, DownloadStatus status);
    }

    public GetCustomerInformationDataCreate(OnDataAvailable callBack) {
        Log.d(TAG, "GetLoginData called");
        mCallBack = callBack;
    }


    public GetCustomerInformationDataCreate(OnDataAvailable callBack, CustomerInformation model) {
        Log.d(TAG, "GetLoginData called");
        mCallBack = callBack;
        this.model = model;
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
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd").create();
        Type type = new TypeToken<CustomerInformation>() {}.getType();
        String json = gson.toJson(model, type);

        FetcherAbstract RawDataFetcher = new CustomerInformationCreateFetcher(this, json);
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
                Type type = new TypeToken<CustomerInformation>() {}.getType();
                CustomerInformation userJson = gson.fromJson(data, type);
                mLogin = userJson;//new ArrayList<>();
//                Log.d(TAG, "onDownloadComplete " + CustomerInformation.toString());
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





















