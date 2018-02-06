package com.timbuchalka.top10downloader.fragment.conversation;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.models.Conversation;

import java.text.SimpleDateFormat;

public class ConversationReadFragment
        extends ReadFragment<Conversation> {

    public ConversationReadFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public ConversationReadFragment(Conversation activeElement, int layout) {
        super(activeElement, layout);
    }

    TextView birthData;
    TextView additionalInformation;
    TextView primary;

    @Override
    public void createSetVars(View view) {
        birthData = (TextView) view.findViewById(R.id.birthData);
        additionalInformation = (TextView) view.findViewById(R.id.additionalInformation);
        primary = (TextView) view.findViewById(R.id.primary);
        birthData.setText(new SimpleDateFormat("Y-m-d").format(activeElement.getBirthData()).concat(" birth date"));
        additionalInformation.setText(activeElement.getAdditionalInformation());
        primary.setText(activeElement.getPrimary() == 0 ? "Secondary" : "Primary");
    }
}
