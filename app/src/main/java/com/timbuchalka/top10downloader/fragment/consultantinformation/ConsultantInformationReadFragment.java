package com.timbuchalka.top10downloader.fragment.consultantinformation;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.models.ConsultantInformation;

public class ConsultantInformationReadFragment
        extends ReadFragment<ConsultantInformation> {

    public ConsultantInformationReadFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public ConsultantInformationReadFragment(ConsultantInformation activeElement, int layout) {
        super(activeElement, layout);
    }

    @Override
    public void createSetVars(View view) {
        ((TextView) view.findViewById(R.id.status)).setText(activeElement.getStatus() == 1 ? "Active" : "Inactive");
        ((TextView) view.findViewById(R.id.videoTarif)).setText(activeElement.getVideoTarif() != null ? activeElement.getVideoTarif().toString() : "");
        ((TextView) view.findViewById(R.id.conversationTarif)).setText(activeElement.getConversationTarif() != null ? activeElement.getConversationTarif().toString() : "");
        ((TextView) view.findViewById(R.id.user)).setText(activeElement.getUser().getFirstName());
        ((TextView) view.findViewById(R.id.consultantGroup)).setText(activeElement.getConsultantGroup().getName());
    }
}
