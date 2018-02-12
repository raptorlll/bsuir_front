package com.leonov.bsuir.api;

import android.os.AsyncTask;
import android.util.Log;
import com.leonov.bsuir.api.crud.ApiCrudFactory;
import com.leonov.bsuir.models.ConsultantInformation;
import com.leonov.bsuir.api.crud.convertor.ConsultantInformationConvertor;
import java.io.File;
import java.util.Set;

public class GetPostFilesData
        extends AsyncTask<String, Void, ConsultantInformation>
        implements FetcherAbstract.OnDownloadComplete {

    private static final String TAG = "GetPostFilesData";
    private ConsultantInformation consultantInformation;
    private File file;

    private ConsultantInformation mLogin;
    private String mBaseURL;

    private final OnDataAvailable mCallBack;
    private boolean runningOnSameThread = false;

    public interface OnDataAvailable <T>{
        void onDataAvailable(T data, DownloadStatus status);
    }

    public GetPostFilesData(OnDataAvailable callBack, File file, ConsultantInformation consultantInformation) {
        Log.d(TAG, "GetLoginData called");
        this.file = file;
        this.consultantInformation = consultantInformation;
        mCallBack = callBack;
    }

    @Override
    protected ConsultantInformation doInBackground(String... params) {
        ConsultantInformationConvertor<ConsultantInformation> convertor =
                new ConsultantInformationConvertor<ConsultantInformation>();
        String json = convertor.convertElement(consultantInformation);

        FetcherAbstract RawDataFetcher = new MultipartFileFetcher(this, file, json, "/consultant_information/save");
        RawDataFetcher.execute();


        Log.d(TAG, "doInBackground ends");
        return mLogin;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);
        Set<ConsultantInformation> token;
        if(status == DownloadStatus.OK) {
            try {
                ConsultantInformation convertedData = ApiCrudFactory.convertElement(ConsultantInformation.class, data);
                mLogin = convertedData;
                mCallBack.onDataAvailable(mLogin, status);
            } catch(Exception jsone) {
                jsone.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error processing Json data " + jsone.getMessage());
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        }

        mCallBack.onDataAvailable(mLogin, status);
    }
}





















