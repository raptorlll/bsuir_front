package com.leonov.bsuir.api;

import android.os.AsyncTask;
import android.util.Log;
import com.leonov.bsuir.api.crud.ApiCrudFactory;
import com.leonov.bsuir.api.crud.convertor.ConversationMessageConvertor;
import com.leonov.bsuir.models.ConversationMessage;
import java.io.File;
import java.util.Set;

public class GetPostFilesMessageData
        extends AsyncTask<String, Void, ConversationMessage>
        implements FetcherAbstract.OnDownloadComplete {

    private static final String TAG = "GetPostFilesData";
    private ConversationMessage conversationMessage;
    private File file;
    private ConversationMessage mLogin;
    private final OnDataAvailable mCallBack;

    public interface OnDataAvailable {
        void onDataAvailable(ConversationMessage data, DownloadStatus status);
    }

    public GetPostFilesMessageData(OnDataAvailable callBack, File file, ConversationMessage conversationMessage) {
        Log.d(TAG, "GetLoginData called");
        this.file = file;
        this.conversationMessage = conversationMessage;
        mCallBack = callBack;
    }

    @Override
    protected ConversationMessage doInBackground(String... params) {
        ConversationMessageConvertor<ConversationMessage> convertor = new ConversationMessageConvertor<ConversationMessage>();
        String json = convertor.convertElement(conversationMessage);

        FetcherAbstract RawDataFetcher = new MultipartFileFetcher(this, file, json, "/conversation_message/save");
        RawDataFetcher.execute();

        Log.d(TAG, "doInBackground ends");
        return mLogin;
    }


    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete starts. Status = " + status);
        Set<ConversationMessage> token;
        if(status == DownloadStatus.OK) {
            try {
                ConversationMessage convertedData = ApiCrudFactory.convertElement(ConversationMessage.class, data);
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





















