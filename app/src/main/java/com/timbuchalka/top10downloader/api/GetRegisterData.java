package com.timbuchalka.top10downloader.api;

import android.os.AsyncTask;
import android.util.Log;

import com.timbuchalka.top10downloader.convertors.TokenConvertor;
import com.timbuchalka.top10downloader.convertors.UserJsonConvertor;
import com.timbuchalka.top10downloader.models.Token;
import com.timbuchalka.top10downloader.models.UserJson;

import org.json.JSONException;
import org.json.JSONObject;

public class GetRegisterData
        extends AsyncTask<String, Void, UserJson>
        implements FetcherAbstract.OnDownloadComplete {

    private static final String TAG = "GetRegisterData";

    private UserJson mLogin;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    public interface OnDataAvailable {
        void onDataAvailable(UserJson data, DownloadStatus status);
    }

    public GetRegisterData(OnDataAvailable callBack) {
        Log.d(TAG, "GetLoginData called");
        mCallBack = callBack;
    }

    @Override
    protected void onPostExecute(UserJson Logins) {
        Log.d(TAG, "onPostExecute starts");

        if (mCallBack != null) {
            mCallBack.onDataAvailable(mLogin, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute ends");
    }

    @Override
    protected UserJson doInBackground(String... params) {
        FetcherAbstract RawDataFetcher = new RegisterFetcher(this, params[0]);
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
//                TokenConvertor convertor = new TokenConvertor();
//                token = convertor.convert(jsonData);

                mLogin = userJson;
            } catch (JSONException jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data " + jsone.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        if (runningOnSameThread && mCallBack != null) {
            mCallBack.onDataAvailable(mLogin, status);
        }

        Log.d(TAG, "onDownloadComplete ends");
    }
}





















