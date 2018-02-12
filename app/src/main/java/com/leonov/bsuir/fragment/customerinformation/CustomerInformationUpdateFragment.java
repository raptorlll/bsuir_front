package com.leonov.bsuir.fragment.customerinformation;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import com.leonov.bsuir.R;
import com.leonov.bsuir.fragment.crud.UpdateFragment;
import com.leonov.bsuir.models.CustomerInformation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomerInformationUpdateFragment
        extends UpdateFragment<CustomerInformation> {
    TextView birthData;
    TextView additionalInformation;
    CheckBox primary;
    Date date;

    @SuppressLint("ValidFragment")
    CustomerInformationUpdateFragment(){
        super();
    }

    @SuppressLint("ValidFragment")
    CustomerInformationUpdateFragment(Class<CustomerInformation> genericClass, CustomerInformation activeElement, int layout) {
        super(genericClass, activeElement, layout);
    }

    @SuppressLint("ValidFragment")
    CustomerInformationUpdateFragment(Class<CustomerInformation> genericClass, int layout) {
        super(genericClass, layout);
    }

    private DatePickerDialog datePicker;

    private SimpleDateFormat dateFormatter;

    @Override
    public void convertForView(CustomerInformation activeElement) {
        birthData.setText(dateFormatter.format(activeElement.getBirthData()));
        date = activeElement.getBirthData();
        additionalInformation.setText(activeElement.getAdditionalInformation());
        primary.setChecked(activeElement.getPrimary() == 1);
    }

    @Override
    public void convertForSubmit(CustomerInformation activeElement) {
        activeElement.setAdditionalInformation(additionalInformation.getText().toString());
        activeElement.setPrimary(new Byte(primary.isChecked() ? "1" : "0"));
        activeElement.setBirthData(date);
    }

    @Override
    public void onClickListeners(View view) {
        if(view == birthData) {
            datePicker.show();
        }
    }

    @Override
    public void setListeners() {
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
    }

    @Override
    public void findViewsById(View view) {
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        birthData = (TextView) view.findViewById(R.id.birthData);
        additionalInformation = (TextView) view.findViewById(R.id.additionalInformation);
        primary = (CheckBox) view.findViewById(R.id.primary);
    }

    @Override
    public Fragment getListView() {
        return new CustomerInformationFragment();
    }
}
