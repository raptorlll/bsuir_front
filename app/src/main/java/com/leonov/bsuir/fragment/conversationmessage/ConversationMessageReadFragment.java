package com.leonov.bsuir.fragment.conversationmessage;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.leonov.bsuir.R;
import com.leonov.bsuir.fragment.crud.ReadFragment;
import com.leonov.bsuir.models.ConversationMessage;

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
