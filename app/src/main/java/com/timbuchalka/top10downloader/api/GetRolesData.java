package com.timbuchalka.top10downloader.api;

import android.os.AsyncTask;
import android.util.Log;

import com.timbuchalka.top10downloader.convertors.RolesConvertor;
import com.timbuchalka.top10downloader.convertors.TokenConvertor;
import com.timbuchalka.top10downloader.models.Role;
import com.timbuchalka.top10downloader.models.Token;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashSet;
import java.util.Set;

public class GetRolesData
        extends AsyncTask<String, Void, Set<Role>>
        implements FetcherAbstract.OnDownloadComplete {

    private static final String TAG = "GetLoginData";

    private Set<Role> mLogin;
    private String mBaseURL;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    public interface OnDataAvailable {
        void onDataAvailable(Set<Role> data, DownloadStatus status);
    }

    public GetRolesData(OnDataAvailable callBack) {
        Log.d(TAG, "GetLoginData called");
        mCallBack = callBack;
    }

    @Override
    protected void onPostExecute(Set<Role> Logins) {
        Log.d(TAG, "onPostExecute starts");

        if(mCallBack != null) {
            mCallBack.onDataAvailable(mLogin, DownloadStatus.OK);
        }
        Log.d(TAG, "onPostExecute ends");
    }

    @Override
    protected Set<Role> doInBackground(String... params) {
        FetcherAbstract RawDataFetcher = new RolesFetcher(this, params[0]);
        RawDataFetcher.execute();
        Log.d(TAG, "doInBackground ends");
        return mLogin;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);
        Set<Role> token;
        if(status == DownloadStatus.OK) {
            try {
                JSONArray jsonData = new JSONArray(data);
                Set<String> roles = new HashSet<String>();
                for (int i = 0; i < jsonData.length(); i++){
                    roles.add(jsonData.getString(i));
                }

                Set<Role> setRoles = new HashSet<Role>();

                for (String s: roles){
                    setRoles.add(new Role(s, s));
                }
//
                mLogin = setRoles;
                Log.d(TAG, "onDownloadComplete " + setRoles.toString());
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





















