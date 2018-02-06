package com.timbuchalka.top10downloader.fragment.conversationmessage;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.GetPostFilesMessageData;
import com.timbuchalka.top10downloader.api.crud.ListData;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.ConsultantGroupUser;
import com.timbuchalka.top10downloader.models.ConversationMessage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;
import java.com.timbuchalka.top10downloader.models.Conversation;

public class ConversationMessageUpdateFragment
        extends UpdateFragment<ConversationMessage>
        implements GetPostFilesMessageData.OnDataAvailable{
    private EditText message;
    private EditText attachedFile;
    private ArrayList<Conversation> conversationData;

    @Override
    public void onDataAvailable(ConversationMessage data, DownloadStatus status) {
        /**Todo: Implement this. Redirect */
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
    public void convertForSubmit(ConversationMessage activeElement) {
        /* All in spinner */
        activeElement.setMessage(message.getText());

        GetPostFilesMessageData postFilesData = new GetPostFilesMessageData(this, fileChoosed, activeElement);
        postFilesData.execute();

//        activeElement.setMessage(message.getText());
    }

    @Override
    public void onClickListeners(View view) {
        if (view == licenseFile) {
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
