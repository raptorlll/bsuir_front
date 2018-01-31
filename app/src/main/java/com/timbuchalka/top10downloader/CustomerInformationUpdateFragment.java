package com.timbuchalka.top10downloader;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.CustomerInformation.GetCustomerInformationDataCreate;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.CustomerInformation.GetCustomerInformationDataUpdate;
import com.timbuchalka.top10downloader.models.CustomerInformation;
import com.timbuchalka.top10downloader.models.Role;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;


public class CustomerInformationUpdateFragment
        extends Fragment
        implements View.OnClickListener,
            GetCustomerInformationDataUpdate.OnDataAvailable,
            GetCustomerInformationDataCreate.OnDataAvailable
{
    private static final String TAG = "Update info";
    private int layout;
    private CustomerInformation activeElement;
    private CustomerInformation element;
    TextView birthData;
    TextView additionalInformation;
    CheckBox primary;
    Button buttonSubmit;
    Date date;

    FragmentTransaction ft;

    @Override
    public void onDataAvailable(CustomerInformation data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: ");
        //redirect back
        ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentMain, new CustomerInformationFragment());
        ft.commit();
    }

    private DatePickerDialog datePicker;

    private SimpleDateFormat dateFormatter;

    public CustomerInformationUpdateFragment() {
    }

    private void findViewsById(View view) {
        birthData = (TextView) view.findViewById(R.id.birthData);
        additionalInformation = (TextView) view.findViewById(R.id.additionalInformation);
        primary = (CheckBox) view.findViewById(R.id.primary);
        buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);
    }

    @SuppressLint("ValidFragment")
    public CustomerInformationUpdateFragment(CustomerInformation activeElement, int layout) {
        super();
        this.activeElement = activeElement;
        this.layout = layout;
    }

    @SuppressLint("ValidFragment")
    public CustomerInformationUpdateFragment(int layout) {
        super();
        this.activeElement = new CustomerInformation();
        this.layout = layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(layout, parent, false);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById(view);

        setValues();

        setListeners();

        return view;
    }

    private void setValues() {
        if(activeElement.getId()==null){
            return;
        }

        birthData.setText(dateFormatter.format(activeElement.getBirthData()));
        date = activeElement.getBirthData();
        additionalInformation.setText(activeElement.getAdditionalInformation());
        primary.setChecked(activeElement.getPrimary() == 1);
    }

    private void submit(){
        activeElement.setAdditionalInformation(additionalInformation.getText().toString());
        activeElement.setPrimary(new Byte(primary.isChecked() ? "1" : "0"));
        activeElement.setBirthData(date);

        if(activeElement.getId() == null) {
            GetCustomerInformationDataCreate getCustomerInformationData = new GetCustomerInformationDataCreate(this, activeElement);
            getCustomerInformationData.execute();
        } else {
            GetCustomerInformationDataUpdate getCustomerInformationData = new GetCustomerInformationDataUpdate(this, activeElement);
            getCustomerInformationData.execute();
        }
    }


    @Override
    public void onClick(View view) {
        if(view == birthData) {
            datePicker.show();
        } else if (view == buttonSubmit) {
            submit();
        }
    }

    private void setListeners() {
        birthData.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                birthData.setText(dateFormatter.format(newDate.getTime()));
                date = newDate.getTime();
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        buttonSubmit.setOnClickListener(this);
    }
}


















