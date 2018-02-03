package com.timbuchalka.top10downloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.timbuchalka.top10downloader.adapters.CustomerInformationAdapter;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.CustomerInformation.GetCustomerInformationData;
import com.timbuchalka.top10downloader.models.CustomerInformation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CustomerInformationFragment
        extends Fragment
        implements GetCustomerInformationData.OnDataAvailable,
            CustomerInformationAdapter.DataReload {
    private ListView listApps;
    private static final String TAG = "CustomerrmationFragment";
    FragmentTransaction ft;
    Button addButtom;
    @Override
    public void onDataAvailable(Collection<CustomerInformation> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: ");

        List<CustomerInformation> list = new ArrayList<>();
        list.addAll(data);


        CustomerInformationAdapter<CustomerInformation> feedAdapter =
                new CustomerInformationAdapter<>(getActivity(), R.layout.list_row_customer_information, list);
        feedAdapter.registerReloadListener(this);
        listApps.setAdapter(feedAdapter);
    }

    @Override
    public void reloadData() {

        GetCustomerInformationData getCustomerInformationData = new GetCustomerInformationData(this);
        getCustomerInformationData.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        GetCustomerInformationData getCustomerInformationData = new GetCustomerInformationData(this);
        getCustomerInformationData.execute();

        ft = getActivity().getSupportFragmentManager().beginTransaction();
        View view = inflater.inflate(R.layout.cutomer_information, parent, false);
        listApps = (ListView) view.findViewById(R.id.xmlListView);

        addButtom = (Button) view.findViewById(R.id.crudCreate);
        addButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerInformationUpdateFragment faa = new CustomerInformationUpdateFragment(R.layout.list_row_customer_information_update);
                ft.replace(R.id.fragmentMain, faa);
                ft.commit();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
    }
}


















