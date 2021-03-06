package com.leonov.bsuir.fragment.conversationmessage;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.EditText;

import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.Utils;
import com.leonov.bsuir.R;
import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.GetPostFilesMessageData;
import com.leonov.bsuir.fragment.crud.UpdateFragment;
import com.leonov.bsuir.models.ConversationMessage;

import java.io.File;
import java.util.*;
import com.leonov.bsuir.models.Conversation;

public class ConversationMessageUpdateFragment
        extends UpdateFragment<ConversationMessage>
        implements GetPostFilesMessageData.OnDataAvailable
 {
    private EditText message;
    private EditText attachedFile;
    private ArrayList<Conversation> conversationData;

    @Override
    public void onDataAvailable(ConversationMessage data, DownloadStatus status) {
        System.out.println("Data available");
        changeToListPage();
    }

    @SuppressLint("ValidFragment")
    ConversationMessageUpdateFragment(){
        super();
    }

    @SuppressLint("ValidFragment")
    public ConversationMessageUpdateFragment(Class<ConversationMessage> genericClass, ConversationMessage activeElement, int layout) {
        super(genericClass, activeElement, layout);
        executeDropDowns();
    }

    @SuppressLint("ValidFragment")
    public ConversationMessageUpdateFragment(Class<ConversationMessage> genericClass, int layout) {
        super(genericClass, layout);
        executeDropDowns();
    }

    private void executeDropDowns() {
    }

    @Override
    public void convertForView(ConversationMessage activeElement) {
        message.setText(activeElement.getMessage());
        attachedFile.setText(activeElement.getAttachedFile().toString());
    }

    @Override
    public boolean customSubmit(ConversationMessage activeElement) {
        GetPostFilesMessageData postFilesData = new GetPostFilesMessageData(this, fileChoosed, activeElement);
        postFilesData.execute();

        return true;
    }

    @Override
    public void convertForSubmit(ConversationMessage activeElement) {
        /* All in spinner */
        activeElement.setMessage(message.getText().toString());


//        activeElement.setMessage(message.getText());
    }

    @Override
    public void onClickListeners(View view) {
        if (view == attachedFile) {
            initFilePicker();
        }
    }

    @Override
    public void setListeners() {
        attachedFile.setOnClickListener(this);
    }

    private void initFilePicker() {
        Intent i = new Intent(getContext(), FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(i, 1);
    }

    private File fileChoosed = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Use the provided utility method to parse the result
            List<Uri> files = Utils.getSelectedFilesFromResult(intent);
            for (Uri uri : files) {
                File file = Utils.getFileForUri(uri);
                fileChoosed = Utils.getFileForUri(uri);
            }
        }
    }

    @Override
    public void findViewsById(View v) {
        message = (EditText) v.findViewById(R.id.message);
        attachedFile = (EditText) v.findViewById(R.id.attachedFile);
    }

    @Override
    public Fragment getListView() {
        return new ConversationMessageFragment();
    }
}
