package com.timbuchalka.top10downloader.fragment.customerayment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.models.CustomerPayment;

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

    @Override
    public void createSetVars(View v) {
        dataTime = (TextView) v.findViewById(R.id.name);
        conversation = (TextView) v.findViewById(R.id.description);

        dataTime.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).format(activeElement.getDataTime()));
        conversation.setText(activeElement.getConversation().getCustomerInformation().getAdditionalInformation());
    }
}
