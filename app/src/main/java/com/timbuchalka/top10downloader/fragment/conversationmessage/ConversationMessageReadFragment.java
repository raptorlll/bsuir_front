package com.timbuchalka.top10downloader.fragment.conversationmessage;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.models.ConversationMessage;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ConversationMessageReadFragment
        extends ReadFragment<ConversationMessage> {

    public ConversationMessageReadFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public ConversationMessageReadFragment(ConversationMessage activeElement, int layout) {
        super(activeElement, layout);
    }

    private TextView message;
    private TextView attachedFile;

    @Override
    public void createSetVars(View v) {
        message = (TextView) v.findViewById(R.id.message);
        attachedFile = (TextView) v.findViewById(R.id.attachedFile);

        message.setText(activeElement.getMessage());
        attachedFile.setText(activeElement.getAttachedFile().toString());
    }
}
