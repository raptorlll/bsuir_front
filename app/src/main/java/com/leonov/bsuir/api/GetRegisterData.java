package com.leonov.bsuir.api;

import android.os.AsyncTask;
import android.util.Log;

import com.leonov.bsuir.convertors.UserJsonConvertor;
import com.leonov.bsuir.models.UserJson;

import org.json.JSONException;
import org.json.JSONObject;

public class GetRegisterData
        extends AsyncTask<String, Void, UserJson>
        implements FetcherAbstract.OnDownloadComplete {

    private static final String TAG = "GetRegisterData";
    private String userJson;

    private UserJson mLogin;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    public interface OnDataAvailable {
        void onDataAvailable(UserJson data, DownloadStatus status);
    }

    public GetRegisterData(OnDataAvailable callBack, String userJson) {
        Log.d(TAG, "GetLoginData called");
        mCallBack = callBack;
        this.userJson = userJson;
    }

    @Override
    protected UserJson doInBackground(String... params) {
        FetcherAbstract RawDataFetcher = new RegisterFetcher(this, userJson);
        RawDataFetcher.execute();
        Log.d(TAG, "doInBackground ends");
        return mLogin;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);
        UserJson userJson;
        if (status == DownloadStatus.OK) {
            try {
                JSONObject jsonData = new JSONObject(data);
                UserJsonConvertor convertor = new UserJsonConvertor();
                userJson = convertor.convert(jsonData);

                mLogin = userJson;

                mCallBack.onDataAvailable(mLogin, DownloadStatus.OK);
            } catch (JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data " + jsone.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        mCallBack.onDataAvailable(mLogin, DownloadStatus.OK);
    }
}





















