package com.leonov.bsuir;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.crud.ListData;
import com.leonov.bsuir.api.crud.PostData;
import com.leonov.bsuir.models.SummaryPayments;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Locale;

public class SummaryPaymentsFragment extends Fragment {
    private ListView listView;
    private Button sendButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.summary_payments_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.xmlListView);
        sendButton = (Button) view.findViewById(R.id.send);

        (new ListData<SummaryPayments>(SummaryPayments.class, new ListData.OnDataAvailable<SummaryPayments>() {
            @Override
            public void onDataAvailable(Collection<SummaryPayments> data, DownloadStatus status) {
                MySimpleArrayAdapter feedAdapter = new MySimpleArrayAdapter(getContext(), data.toArray());
                listView.setAdapter(feedAdapter);
            }
        }, "/summary_payments/reports")).execute();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostData postData = new PostData(new PostData.OnDataAvailable() {
                    @Override
                    public void onDataAvailable(String data, DownloadStatus status) {
                        if (data.equals("error")) {
                            Toast.makeText(getContext(),
                                    "Error!",
                                    Toast.LENGTH_LONG
                            ).show();
                        } else {
                            Toast.makeText(getContext(),
                                    "You successfuly sended!",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }
                }, "/summary_payments/send");
                postData.execute();
            }
        });
    }

    public class MySimpleArrayAdapter extends ArrayAdapter<SummaryPayments> {
        private final Context context;
        private final Object[] values;

        public MySimpleArrayAdapter(Context context, Object[] values) {
            super(context, R.layout.payment_item);
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
            View rowView = inflater.inflate(R.layout.payment_item, parent, false);
            TextView sum = (TextView) rowView.findViewById(R.id.sum);
            TextView date = (TextView) rowView.findViewById(R.id.date);
            TextView paymentsCount = (TextView) rowView.findViewById(R.id.paymentsCount);
            TextView messageCount = (TextView) rowView.findViewById(R.id.messageCount);

            SummaryPayments summaryPayments = (SummaryPayments) values[position];

            sum.setText("Sum : " + Math.ceil(summaryPayments.getSum()) + " USD cents");
            date.setText("Date : ".concat( new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(summaryPayments.getDate())));
            paymentsCount.setText("Payments count : " + summaryPayments.getPaymentsCount());
            messageCount.setText("Message count : "+ summaryPayments.getMessageCount());

            return rowView;
        }
    }
}


















