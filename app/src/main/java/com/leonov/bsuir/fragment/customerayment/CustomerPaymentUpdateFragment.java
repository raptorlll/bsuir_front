package com.leonov.bsuir.fragment.customerayment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.leonov.bsuir.R;
import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.crud.ListData;
import com.leonov.bsuir.fragment.crud.UpdateFragment;
import com.leonov.bsuir.models.CustomerPayment;

import java.util.ArrayList;
import java.util.Collection;

import com.leonov.bsuir.models.Conversation;

public class CustomerPaymentUpdateFragment
        extends UpdateFragment<CustomerPayment> {
    private TextView amount;
    private Spinner conversation;
    private ArrayList<Conversation> conversationData;

    @SuppressLint("ValidFragment")
    CustomerPaymentUpdateFragment(){
        super();
    }

    @SuppressLint("ValidFragment")
    public CustomerPaymentUpdateFragment(Class<CustomerPayment> genericClass, CustomerPayment activeElement, int layout) {
        super(genericClass, activeElement, layout);
        executeDropDowns();
    }

    @SuppressLint("ValidFragment")
    public CustomerPaymentUpdateFragment(Class<CustomerPayment> genericClass, int layout) {
        super(genericClass, layout);
        executeDropDowns();
    }

    private void executeDropDowns() {
        (new ListData<Conversation>(Conversation.class, new ListData.OnDataAvailable<Conversation>() {
            @Override
            public void onDataAvailable(Collection<Conversation> data, DownloadStatus status) {
                System.out.println("");
                conversationData = (ArrayList<Conversation>) data;
                ArrayList<String> list = new ArrayList<String>();

                for (Conversation u : data){
                    list.add(u.getCustomerInformation().getAdditionalInformation());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);

                conversation.setAdapter(arrayAdapter);

                if (activeElement.getConversation() == null && !data.isEmpty()) {
                    conversation.setSelection(0);
                } else {
                    int i = 0;
                    for (Conversation u : data){
                        if(activeElement.getConversation().getId() == u.getId()){
                            conversation.setSelection(i);
                        }

                        i++;
                    }
                }

                conversation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        activeElement.setConversation(conversationData.get(i));
                        System.out.println("");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        })).execute();
    }

    @Override
    public void convertForView(CustomerPayment activeElement) {
        amount.setText(activeElement.getAmount().toString());
    }

    @Override
    public void convertForSubmit(CustomerPayment activeElement) {
        activeElement.setAmount(Long.parseLong(amount.getText().toString()));
        /* All in spinner */
    }

    @Override
    public void onClickListeners(View view) {
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void findViewsById(View v) {
        amount = (TextView) v.findViewById(R.id.amount);
        conversation = (Spinner) v.findViewById(R.id.conversation);
    }

    @Override
    public Fragment getListView() {
        return new CustomerPaymentFragment();
    }
}
