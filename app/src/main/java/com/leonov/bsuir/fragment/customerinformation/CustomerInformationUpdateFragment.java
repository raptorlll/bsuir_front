package com.leonov.bsuir.fragment.customerinformation;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.leonov.bsuir.R;
import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.crud.ListData;
import com.leonov.bsuir.enums.RolesEnum;
import com.leonov.bsuir.fragment.crud.UpdateFragment;
import com.leonov.bsuir.models.ConsultantGroupUser;
import com.leonov.bsuir.models.CustomerInformation;
import com.leonov.bsuir.models.Role;
import com.leonov.bsuir.models.User;
import com.leonov.bsuir.statical.RolesChecker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class CustomerInformationUpdateFragment
        extends UpdateFragment<CustomerInformation>
        implements ListData.OnDataAvailable<User> {
    TextView birthData;
    TextView additionalInformation;
    CheckBox primary;
    Spinner user;

    Date date;
    private ArrayList<User> userData;

    @SuppressLint("ValidFragment")
    CustomerInformationUpdateFragment(){
        super();
    }

    @SuppressLint("ValidFragment")
    CustomerInformationUpdateFragment(Class<CustomerInformation> genericClass, CustomerInformation activeElement, int layout) {
        super(genericClass, activeElement, layout);

        if(RolesChecker.getInstance().isAdmin()){
            (new ListData<User>(User.class, this)).execute();
        }
    }


    @Override
    public void onDataAvailable(Collection<User> data, DownloadStatus status) {
        System.out.println("");
        ArrayList<String> list = new ArrayList<String>();
        Collection<User> filteredUsers = new HashSet<>();
        userData = new ArrayList<User>();

        for (User u : data){
            for (Role r: u.getRoles()){
                if(r.getDescription().equals(RolesEnum.CUSTOMER.toString())){
                    filteredUsers.add(u);
                    userData.add(u);
                }
            }
        }

        for (User u : filteredUsers){
            list.add(u.getFirstName().concat(" ").concat(u.getEmail()));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);

        user.setAdapter(arrayAdapter);

        if (activeElement.getUser() == null && !filteredUsers.isEmpty()) {
            user.setSelection(0);
        } else {
            int i = 0;
            for (User u : filteredUsers){
                if(activeElement.getUser().getId() == u.getId()){
                    user.setSelection(i);
                }

                i++;
            }
        }

        user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activeElement.setUser(((ArrayList<User>)userData).get(i));
                System.out.println("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @SuppressLint("ValidFragment")
    CustomerInformationUpdateFragment(Class<CustomerInformation> genericClass, int layout) {
        super(genericClass, layout);

        if(RolesChecker.getInstance().isAdmin()){
            (new ListData<User>(User.class, this)).execute();
        }
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

        if(!RolesChecker.getInstance().isAdmin()){
            user.setVisibility(View.INVISIBLE);
        }
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
        user = (Spinner) view.findViewById(R.id.user);

        if(!RolesChecker.getInstance().isAdmin()){
            user.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public Fragment getListView() {
        return new CustomerInformationFragment();
    }
}
