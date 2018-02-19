package com.leonov.bsuir.fragment.consultantgroupuser;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.leonov.bsuir.R;
import com.leonov.bsuir.fragment.crud.ReadFragment;
import com.leonov.bsuir.models.ConsultantGroupUser;

public class ConsultantGroupUserReadFragment
        extends ReadFragment<ConsultantGroupUser> {

    public ConsultantGroupUserReadFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public ConsultantGroupUserReadFragment(ConsultantGroupUser activeElement, int layout) {
        super(activeElement, layout);
    }

    @Override
    public void createSetVars(View view) {
        ((TextView) view.findViewById(R.id.status)).setText("Status : " + (activeElement.getStatus() == 1 ? "Active" : "Inactive"));
        ((TextView) view.findViewById(R.id.videoTarif)).setText("Video tarif : " + (activeElement.getVideoTarif() != null ? activeElement.getVideoTarif().toString() : ""));
        ((TextView) view.findViewById(R.id.conversationTarif)).setText("Conversation tarif : " + (activeElement.getConversationTarif() != null ? activeElement.getConversationTarif().toString() : ""));
        ((TextView) view.findViewById(R.id.user)).setText("Consultant : " + activeElement.getUser().getFirstName());
        ((TextView) view.findViewById(R.id.consultantGroup)).setText("Group : " + activeElement.getConsultantGroup().getName());
    }
}
