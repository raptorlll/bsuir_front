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

    TextView active;
    TextView consultantGroupUser;
    TextView customerInformation;

    @Override
    public void createSetVars(View view) {
        active = (TextView) view.findViewById(R.id.active);
        consultantGroupUser = (TextView) view.findViewById(R.id.consultantGroupUser);
        customerInformation = (TextView) view.findViewById(R.id.customerInformation);

        active.setText(activeElement.getActive() == 1 ? "Active" : "Inactive");
        consultantGroupUser.setText(activeElement.getConsultantGroupUser().getUser().getFirstName().concat("")
                .concat(activeElement.getConsultantGroupUser().getUser().getLastName()));
        customerInformation.setText(activeElement.getCustomerInformation().getAdditionalInformation());
    }
}
