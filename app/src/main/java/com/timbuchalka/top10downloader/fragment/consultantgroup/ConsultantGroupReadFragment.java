package com.timbuchalka.top10downloader.fragment.consultantgroup;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.models.ConsultantGroup;

import java.text.SimpleDateFormat;

public class ConsultantGroupReadFragment
        extends ReadFragment<ConsultantGroup> {

    public ConsultantGroupReadFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public ConsultantGroupReadFragment(ConsultantGroup activeElement, int layout) {
        super(activeElement, layout);
    }

    TextView name;
    TextView description;
    TextView videoTarif;
    TextView conversationTarif;

    @Override
    public void createSetVars(View view) {
        name = (TextView) view.findViewById(R.id.name);
        description = (TextView) view.findViewById(R.id.description);
        videoTarif = (TextView) view.findViewById(R.id.videoTarif);
        conversationTarif = (TextView) view.findViewById(R.id.conversationTarif);

        name.setText(activeElement.getName());
        description.setText(activeElement.getDescription());
        videoTarif.setText(activeElement.getVideoTarif().toString());
        conversationTarif.setText(activeElement.getConversationTarif().toString());
    }
}
