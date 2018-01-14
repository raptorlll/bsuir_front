package com.timbuchalka.top10downloader.api;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.timbuchalka.top10downloader.convertors.ContertorInterface;
import com.timbuchalka.top10downloader.convertors.LoginConvertor;
import com.timbuchalka.top10downloader.convertors.TokenConvertor;
import com.timbuchalka.top10downloader.models.Login;
import com.timbuchalka.top10downloader.models.Token;

import org.json.JSONException;
import org.json.JSONObject;

public class GetLoginData
        extends AsyncTask<String, Void, Token>
        implements FetcherAbstract.OnDownloadComplete {
    
    private static final String TAG = "GetLoginData";

    private Token mLogin;
    private String mBaseURL;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    public interface OnDataAvailable {
        void onDataAvailable(Token data, DownloadStatus status);
    }

    public GetLoginData(OnDataAvailable callBack) {
        Log.d(TAG, "GetLoginData called");
        mCallBack = callBack;
    }

//    void executeOnSameThread(String searchCriteria) {
//        Log.d(TAG, "executeOnSameThread starts");
//        runningOnSameThread = true;
//
//        FetcherAbstract RawDataFetcher = new LoginFetcher(this);
//        RawDataFetcher.execute(destinationUri);
//        Log.d(TAG, "executeOnSameThread ends");
//    }

    @Override
    protected void onPostExecute(Token Logins) {
        Log.d(TAG, "onPostExecute starts");

        if(mCallBack != null) {
            mCallBack.onDataAvailable(mLogin, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute ends");
    }

    @Override
    protected Token doInBackground(String... params) {
        FetcherAbstract RawDataFetcher = new LoginFetcher(this, params[0], params[1]);
        RawDataFetcher.runInSameThread();
        Log.d(TAG, "doInBackground ends");
        return mLogin;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);
        Token token;
        if(status == DownloadStatus.OK) {
            try {
                JSONObject jsonData = new JSONObject(data);

//                LoginConvertor convertor = new LoginConvertor();
//                login = convertor.convert(jsonData);
                TokenConvertor convertor = new TokenConvertor();
                token = convertor.convert(jsonData);
                mLogin = token;
                Log.d(TAG, "onDownloadComplete " + token.toString());
            } catch(JSONException jsone) {
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





















