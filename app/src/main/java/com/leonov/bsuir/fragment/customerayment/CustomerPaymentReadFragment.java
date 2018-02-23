package com.leonov.bsuir.fragment.customerayment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.leonov.bsuir.R;
import com.leonov.bsuir.fragment.crud.ReadFragment;
import com.leonov.bsuir.models.CustomerPayment;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class CustomerPaymentReadFragment
        extends ReadFragment<CustomerPayment> {

    public CustomerPaymentReadFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public CustomerPaymentReadFragment(CustomerPayment activeElement, int layout) {
        super(activeElement, layout);
    }


    private TextView dataTime;
    private TextView conversation;
    private TextView amount;

    @Override
    public void createSetVars(View v) {
        dataTime = (TextView) v.findViewById(R.id.dataTime);
        conversation = (TextView) v.findViewById(R.id.conversation);
        amount = (TextView) v.findViewById(R.id.amount);

        dataTime.setText("Time : " + new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).format(activeElement.getDataTime()));
        conversation.setText("Conversation : " + activeElement.getConversation().getId().toString().concat(" ") +
                activeElement.getConversation().getConsultantGroupUser().getUser().getFirstName().concat(" ") +
                activeElement.getConversation().getConsultantGroupUser().getConsultantGroup().getName());
        amount.setText("Amount : " + activeElement.getAmount().toString() + "USD cents");
    }
}
