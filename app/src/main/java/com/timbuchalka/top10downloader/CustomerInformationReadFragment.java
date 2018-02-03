package com.timbuchalka.top10downloader;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timbuchalka.top10downloader.models.CustomerInformation;

import java.text.SimpleDateFormat;


public class CustomerInformationReadFragment extends Fragment {
    private static final String TAG = "Create info";
    private int layout;
    private CustomerInformation activeElement;
    private CustomerInformation element;
    TextView birthData;
    TextView additionalInformation;
    TextView primary;

    public CustomerInformationReadFragment() {
    }

    @SuppressLint("ValidFragment")
    public CustomerInformationReadFragment(CustomerInformation activeElement, int layout) {
        super();
        this.activeElement = activeElement;
        this.layout = layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(layout, parent, false);
        birthData = (TextView) view.findViewById(R.id.birthData);
        additionalInformation = (TextView) view.findViewById(R.id.additionalInformation);
        primary = (TextView) view.findViewById(R.id.primary);
        birthData.setText(new SimpleDateFormat("Y-m-d").format(activeElement.getBirthData()).concat(" birth date"));
        additionalInformation.setText(activeElement.getAdditionalInformation());
        primary.setText(activeElement.getPrimary() == 0 ? "Secondary" : "Primary");
        return view;
    }
}


















