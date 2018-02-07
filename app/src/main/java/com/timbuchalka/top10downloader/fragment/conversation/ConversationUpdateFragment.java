package com.timbuchalka.top10downloader.fragment.conversation;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.crud.ListData;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import com.timbuchalka.top10downloader.models.ConsultantGroupUser;
import com.timbuchalka.top10downloader.models.Conversation;
import com.timbuchalka.top10downloader.models.CustomerInformation;
import com.timbuchalka.top10downloader.models.User;

import com.timbuchalka.top10downloader.models.Conversation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class ConversationUpdateFragment
        extends UpdateFragment<Conversation> {
    Spinner customerInformation;
    Spinner consultantGroupUser;
    CheckBox active;

    ArrayList<ConsultantGroupUser> consultantGroupUserData;
    ArrayList<CustomerInformation> customerInformationData;

    @SuppressLint("ValidFragment")
    ConversationUpdateFragment(){
        super();
    }

    @SuppressLint("ValidFragment")
    ConversationUpdateFragment(Class<Conversation> genericClass, Conversation activeElement, int layout) {
        super(genericClass, activeElement, layout);
        executeDropDowns();
    }

    @SuppressLint("ValidFragment")
    ConversationUpdateFragment(Class<Conversation> genericClass, int layout) {
        super(genericClass, layout);
        executeDropDowns();
    }


    private void executeDropDowns() {
        (new ListData<CustomerInformation>(CustomerInformation.class, new ListData.OnDataAvailable<CustomerInformation>() {
            @Override
            public void onDataAvailable(Collection<CustomerInformation> data, DownloadStatus status) {
                System.out.println("");
                customerInformationData = (ArrayList<CustomerInformation>) data;
                ArrayList<String> list = new ArrayList<String>();

                for (CustomerInformation u : data){
                    list.add(u.getAdditionalInformation());
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);

                customerInformation.setAdapter(arrayAdapter);

                if (activeElement.getCustomerInformation() == null && !data.isEmpty()) {
                    customerInformation.setSelection(0);
                } else {
                    int i = 0;
                    for (CustomerInformation u : data){
                        if(activeElement.getCustomerInformation().getId() == u.getId()){
                            customerInformation.setSelection(i);
                        }

                        i++;
                    }
                }

                customerInformation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        activeElement.setCustomerInformation(customerInformationData.get(i));
                        System.out.println("");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        })).execute();

        (new ListData<ConsultantGroupUser>(ConsultantGroupUser.class, new ListData.OnDataAvailable<ConsultantGroupUser>() {
            @Override
            public void onDataAvailable(Collection<ConsultantGroupUser> data, DownloadStatus status) {
                System.out.println("");
                consultantGroupUserData = (ArrayList<ConsultantGroupUser>) data;
                ArrayList<String> list = new ArrayList<String>();

                for (ConsultantGroupUser u : data){
                    list.add(u.getUser().getUsername().concat(" ").concat(u.getUser().getLastName()));
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);

                consultantGroupUser.setAdapter(arrayAdapter);

                if (activeElement.getConsultantGroupUser() == null && !data.isEmpty()) {
                    consultantGroupUser.setSelection(0);
                } else {
                    int i = 0;
                    for (ConsultantGroupUser u : data){
                        if(activeElement.getConsultantGroupUser().getId() == u.getId()){
                            consultantGroupUser.setSelection(i);
                        }

                        i++;
                    }
                }

                consultantGroupUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        activeElement.setConsultantGroupUser(consultantGroupUserData.get(i));
                        System.out.println("");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        })).execute();
    }
    @Override
    public void convertForView(Conversation activeElement) {
        /* All other in spinners */
        active.setChecked(activeElement.getActive() == 1);
    }

    @Override
    public void convertForSubmit(Conversation activeElement) {
        /* All other in  spinners */
        activeElement.setActive(new Byte(active.isChecked() ? "1" : "0"));
    }

    @Override
    public void onClickListeners(View view) {
    }

    @Override
    public void setListeners() {
    }

    @Override
    public void findViewsById(View view) {
        customerInformation = (Spinner) view.findViewById(R.id.customerInformation);
        consultantGroupUser = (Spinner) view.findViewById(R.id.consultantGroupUser);
        active = (CheckBox) view.findViewById(R.id.active);
    }

    @Override
    public Fragment getListView() {
        return new ConversationFragment();
    }
}
