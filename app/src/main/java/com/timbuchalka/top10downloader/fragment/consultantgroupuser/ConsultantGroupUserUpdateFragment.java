package com.timbuchalka.top10downloader.fragment.consultantgroupuser;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.crud.ListData;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import com.timbuchalka.top10downloader.models.ConsultantGroupUser;
import com.timbuchalka.top10downloader.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class ConsultantGroupUserUpdateFragment
        extends UpdateFragment<ConsultantGroupUser>
        implements ListData.OnDataAvailable<User>
{
    CheckBox status;
    TextView videoTarif;
    TextView conversationTarif;
    Spinner user;
    Spinner consultantGroup;
    ArrayList<User> userData = null;
    ArrayList<ConsultantGroup> consultantGroupData = null;

    @Override
    public void onDataAvailable(Collection<User> data, DownloadStatus status) {
        System.out.println("");
        userData = (ArrayList<User>)data;
        ArrayList<String> list = new ArrayList<String>();

        for (User u : data){
            list.add(u.getFirstName().concat(" ").concat(u.getEmail()));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);

        user.setAdapter(arrayAdapter);

        int i = 0;
        for (User u : data){
            if(activeElement.getUser().getId() == u.getId()){
                user.setSelection(i);
            }

            i++;
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
    ConsultantGroupUserUpdateFragment(){
        super();
    }

    @SuppressLint("ValidFragment")
    ConsultantGroupUserUpdateFragment(Class<ConsultantGroupUser> genericClass, ConsultantGroupUser activeElement2, int layout) {
        super(genericClass, activeElement2, layout);
        (new ListData<User>(User.class, this)).execute();
        (new ListData<ConsultantGroup>(ConsultantGroup.class, new ListData.OnDataAvailable<ConsultantGroup>() {
            @Override
            public void onDataAvailable(Collection<ConsultantGroup> data, DownloadStatus status) {
                System.out.println("");
                consultantGroupData = (ArrayList<ConsultantGroup>) data;
                ArrayList<String> list = new ArrayList<String>();

                for (ConsultantGroup u : data){
                    list.add(u.getName().concat(" ").concat(u.getDescription()));
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);

                consultantGroup.setAdapter(arrayAdapter);

                int i = 0;
                for (ConsultantGroup u : data){
                    if(activeElement.getConsultantGroup().getId() == u.getId()){
                        user.setSelection(i);
                    }

                    i++;
                }

                consultantGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        activeElement.setConsultantGroup(consultantGroupData.get(i));
                        System.out.println("");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        })).execute();
//        new ListData(User.class, this).execute();
    }

    @SuppressLint("ValidFragment")
    ConsultantGroupUserUpdateFragment(Class<ConsultantGroupUser> genericClass, int layout) {
        super(genericClass, layout);
    }

    private DatePickerDialog datePicker;

    private SimpleDateFormat dateFormatter;

    @Override
    public void convertForView(ConsultantGroupUser activeElement) {
        status.setChecked(activeElement.getStatus() == 1);
        videoTarif.setText(activeElement.getVideoTarif() != null ? activeElement.getVideoTarif().toString() : "");
        conversationTarif.setText(activeElement.getConversationTarif() != null ? activeElement.getConversationTarif().toString() : "");
//        user.setText(activeElement.getConversationTarif().toString());
//        consultantGroup.setText(activeElement.getConversationTarif().toString());
    }

    @Override
    public void convertForSubmit(ConsultantGroupUser activeElement) {
//        activeElement.setName(name.getText().toString());
//        activeElement.setDescription(description.getText().toString());
//        try{
//            activeElement.setConversationTarif(Integer.parseInt(conversationTarif.getText().toString()));
//            activeElement.setVideoTarif(Integer.parseInt(videoTarif.getText().toString()));
//        } catch (NumberFormatException e) {
//            System.out.println("Conversion error");
//        }
    }

    @Override
    public void onClickListeners(View view) {
    }

    @Override
    public void setListeners() {

    }

    @Override
    public void findViewsById(View view) {
        status = (CheckBox) view.findViewById(R.id.status);
        videoTarif = (TextView) view.findViewById(R.id.videoTarif);
        conversationTarif = (TextView) view.findViewById(R.id.conversationTarif);
        user = (Spinner) view.findViewById(R.id.user);
        consultantGroup = (Spinner) view.findViewById(R.id.consultantGroup);
    }

    @Override
    public Fragment getListView() {
        return new ConsultantGroupUserFragment();
    }
}
