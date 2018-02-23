package com.leonov.bsuir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.crud.ListData;
import com.leonov.bsuir.fragment.customerayment.CustomerPaymentUpdateFragment;
import com.leonov.bsuir.models.Conversation;
import com.leonov.bsuir.models.CustomerPayment;
import com.leonov.bsuir.statical.RolesChecker;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;

@SuppressLint("ValidFragment")
public class ConversationPaymentsFragment extends Fragment{
    private ListView listView;
    private Button addButton;
    private EditText textField;
    private Conversation conversation;
    private Button pay;
    private TextView sum;
    private Button payment;
    private Button back;

    @SuppressLint("ValidFragment")
    public ConversationPaymentsFragment(Conversation conversation) {
        this.conversation = conversation;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.conversation_payment_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.list_of_messages);
        addButton = (Button) view.findViewById(R.id.fab);
        textField = (EditText) view.findViewById(R.id.input);
        payment = (Button) view.findViewById(R.id.payments);
        back = (Button) view.findViewById(R.id.back);

        pay = (Button) view.findViewById(R.id.payments);
        sum = (TextView) view.findViewById(R.id.sum);


        if(RolesChecker.getInstance().isConsultant()){
            pay.setEnabled(false);
        }

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentMain, new CustomerPaymentUpdateFragment(
                    CustomerPayment.class,
                    R.layout.list_row_customer_payment_update,
                    conversation
                ));
                fragmentTransaction.commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentMain, new ConversationMessageFragment(conversation));
                fragmentTransaction.commit();
            }
        });
        updateView();
    }

    private void updateView() {
        (new ListData<CustomerPayment>(CustomerPayment.class, new ListData.OnDataAvailable<CustomerPayment>() {
            @Override
            public void onDataAvailable(Collection<CustomerPayment> data, DownloadStatus status) {
                MySimpleArrayAdapter feedAdapter = new MySimpleArrayAdapter(getContext(), data.toArray());
                listView.setAdapter(feedAdapter);
                feedAdapter.notifyDataSetChanged();

                int value = 0;

                for (CustomerPayment customerPayment : data) {
                    value += customerPayment.getAmount();
                }

                double spendCents = conversation.getSpentMoney();

                sum.setText("Summury payments : "+ value + " USD cents. You have spend " + Math.ceil(spendCents) + " USD cents");
            }
        }, "/customer_payment/conversation/" + conversation.getId().toString())).execute();
    }

    public class MySimpleArrayAdapter extends ArrayAdapter<CustomerPayment> {
        private final Context context;
        private final Object[] values;

        public MySimpleArrayAdapter(Context context, Object[] values) {
            super(context, R.layout.conversation_message);
            this.context = context;
            this.values = values;
        }

        @Override
        public int getCount() {
            return values.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.list_row_customer_payment_read, parent, false);

            CustomerPayment conversationMessage = (CustomerPayment) values[position];

            TextView dataTime;
            TextView conversation;
            TextView amount;

            dataTime = (TextView) rowView.findViewById(R.id.dataTime);
            conversation = (TextView) rowView.findViewById(R.id.conversation);
            amount = (TextView) rowView.findViewById(R.id.amount);

            dataTime.setText("Time : " + new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).format(conversationMessage.getDataTime()));
            conversation.setText("Conversation : " + conversationMessage.getConversation().getId().toString().concat(" ") +
                    conversationMessage.getConversation().getConsultantGroupUser().getUser().getFirstName().concat(" ") +
                    conversationMessage.getConversation().getConsultantGroupUser().getConsultantGroup().getName());
            amount.setText("Amount : " + conversationMessage.getAmount().toString() + "USD cents");

            return rowView;
        }
    }
}


















