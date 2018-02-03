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

import com.timbuchalka.top10downloader.api.crud.CreateData;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.crud.UpdateData;
import com.timbuchalka.top10downloader.models.CustomerInformation;
import com.timbuchalka.top10downloader.models.ModelInterface;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


abstract public class UpdateFragment<T extends ModelInterface>
        extends Fragment
        implements View.OnClickListener,
            UpdateData.OnDataAvailable<T>,
            CreateData.OnDataAvailable<T>
{
    private static final String TAG = "Update info";
    private Class<T> genericClass;
    private int layout;
    private T activeElement;
    private T element;
    TextView birthData;
    TextView additionalInformation;
    CheckBox primary;
    Button buttonSubmit;
    Date date;

    FragmentTransaction ft;

    @Override
    public void onDataAvailable(T data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: ");
        //redirect back
        ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentMain, new CustomerInformationFragment());
        ft.commit();
    }

    private DatePickerDialog datePicker;

    private SimpleDateFormat dateFormatter;

    public UpdateFragment() {
    }

    private void findViewsById(View view) {
        birthData = (TextView) view.findViewById(R.id.birthData);
        additionalInformation = (TextView) view.findViewById(R.id.additionalInformation);
        primary = (CheckBox) view.findViewById(R.id.primary);
        buttonSubmit = (Button) view.findViewById(R.id.buttonSubmit);
    }

    @SuppressLint("ValidFragment")
    public UpdateFragment(Class<T> genericClass,T activeElement, int layout) {
        super();
        this.activeElement = activeElement;
        this.layout = layout;
        this.genericClass = genericClass;
    }

    @SuppressLint("ValidFragment")
    public UpdateFragment(Class<T> genericClass, int layout) {
        super();

        this.genericClass = genericClass;

        try {
            this.activeElement = genericClass.newInstance();
        } catch (IllegalAccessException e){
            System.out.println("");
        }catch (java.lang.InstantiationException e){
            System.out.println("");
        }

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

        convertForView();
    }
    private void submit(){
        convertForSubmit();

        if(activeElement.getId() == null) {
            CreateData<T> getCustomerInformationData = new CreateData<T>(this, activeElement);
            getCustomerInformationData.execute();
        } else {
            UpdateData<T> getCustomerInformationData = new UpdateData<T>(this, activeElement);
            getCustomerInformationData.execute();
        }
    }
    abstract public void convertForView();

    /** Override */
    abstract public void convertForSubmit();

    /** Override */
    public void onClickListeners(View view) {}

    /** Override */
    public void setListeners() {}

//    abstract public void convertForView() {
//        birthData.setText(dateFormatter.format(activeElement.getBirthData()));
//        date = activeElement.getBirthData();
//        additionalInformation.setText(activeElement.getAdditionalInformation());
//        primary.setChecked(activeElement.getPrimary() == 1);
//    }
//
//    /** Override */
//    public void convertForSubmit() {
//        activeElement.setAdditionalInformation(additionalInformation.getText().toString());
//        activeElement.setPrimary(new Byte(primary.isChecked() ? "1" : "0"));
//        activeElement.setBirthData(date);
//    }
//
//    /** Override */
//    public void onClickListeners(View view) {
//        if(view == birthData) {
//            datePicker.show();
//        }
//    }
//
//    /** Override */
//    public void setListeners() {
//        birthData.setOnClickListener(this);
//
//        Calendar newCalendar = Calendar.getInstance();
//        datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//                birthData.setText(dateFormatter.format(newDate.getTime()));
//                date = newDate.getTime();
//            }
//        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
//
//        buttonSubmit.setOnClickListener(this);
//    }


    @Override
    public void onClick(View view) {
        onClickListeners(view);

        if (view == buttonSubmit) {
            submit();
        }
    }

}


















