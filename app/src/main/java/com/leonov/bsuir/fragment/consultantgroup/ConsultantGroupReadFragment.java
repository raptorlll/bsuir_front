package com.leonov.bsuir.fragment.consultantgroup;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.leonov.bsuir.R;
import com.leonov.bsuir.fragment.crud.ReadFragment;
import com.leonov.bsuir.models.ConsultantGroup;

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
