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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CustomerInformationFragment
        extends Fragment
        implements GetCustomerInformationData.OnDataAvailable{
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
        listApps.setAdapter(feedAdapter);
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



    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Log.d(TAG, "onPostExecute: parameter is " + s);
            ParseApplications parseApplications = new ParseApplications();
            parseApplications.parse(s);

//            ArrayAdapter<CustomerInformationEntry> arrayAdapter = new ArrayAdapter<CustomerInformationEntry>(
//                    MainActivity.this, R.layout.list_item, parseApplications.getApplications());
//            listApps.setAdapter(arrayAdapter);
            FeedAdapter<FeedEntry> feedAdapter = new FeedAdapter<>(
                    getContext(),
                    R.layout.list_record,
                    parseApplications.getApplications()
            );

            listApps.setAdapter(feedAdapter);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with " + strings[0]);
            String rssFeed = downloadXML(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }
            return rssFeed;
        }

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was " + response);
//                InputStream inputStream = connection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader reader = new BufferedReader(inputStreamReader);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char[] inputBuffer = new char[500];
                while (true) {
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) {
                        break;
                    }
                    if (charsRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();

                return xmlResult.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            } catch (SecurityException e) {
                Log.e(TAG, "downloadXML: Security Exception.  Needs permisson? " + e.getMessage());
//                e.printStackTrace();
            }

            return null;
        }
    }
}


















