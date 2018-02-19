package com.leonov.bsuir.fragment.conversation;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.leonov.bsuir.R;
import com.leonov.bsuir.fragment.crud.ReadFragment;
import com.leonov.bsuir.models.Conversation;

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

        active.setText("Status : " + (activeElement.getActive() == 1 ? "Active" : "Inactive"));
        consultantGroupUser.setText("Consultant : " + activeElement.getConsultantGroupUser().getUser().getFirstName().concat(" ")
                .concat(activeElement.getConsultantGroupUser().getConsultantGroup().getName()));
        customerInformation.setText("Customer : " + activeElement.getCustomerInformation().getAdditionalInformation());
    }
}
