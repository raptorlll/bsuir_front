package com.timbuchalka.top10downloader;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.timbuchalka.top10downloader.adapters.CustomerInformationAdapter;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.GetCustomerInformationData;
import com.timbuchalka.top10downloader.models.CustomerInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CustomerInformationCreateFragment extends Fragment{
    private static final String TAG = "Create info";
    private int layout;
    private CustomerInformation activeElement;
    private CustomerInformation element;
    TextView birthData;
    TextView additionalInformation;
    TextView primary;
    public CustomerInformationCreateFragment() {
    }

    @SuppressLint("ValidFragment")
    public CustomerInformationCreateFragment(CustomerInformation activeElement, int layout) {
        super();
        this.activeElement = activeElement;
        this.layout = layout;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
//        GetCustomerInformationData getCustomerInformationData = new GetCustomerInformationData(this);
//        getCustomerInformationData.execute();
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


















