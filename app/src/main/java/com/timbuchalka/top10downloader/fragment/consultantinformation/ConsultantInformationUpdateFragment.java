package com.timbuchalka.top10downloader.fragment.consultantinformation;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.crud.ListData;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import com.timbuchalka.top10downloader.models.ConsultantInformation;
import com.timbuchalka.top10downloader.models.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class ConsultantInformationUpdateFragment
        extends UpdateFragment<ConsultantInformation>
{
    CheckBox status;
    TextView videoTarif;
    TextView conversationTarif;
    Spinner user;
    Spinner consultantGroup;
    ArrayList<User> userData = null;
    ArrayList<ConsultantGroup> consultantGroupData = null;

    @SuppressLint("ValidFragment")
    ConsultantInformationUpdateFragment(){
        super();
    }

    @SuppressLint("ValidFragment")
    ConsultantInformationUpdateFragment(Class<ConsultantInformation> genericClass, ConsultantInformation activeElement2, int layout) {
        super(genericClass, activeElement2, layout);
    }

    @SuppressLint("ValidFragment")
    ConsultantInformationUpdateFragment(Class<ConsultantInformation> genericClass, int layout) {
        super(genericClass, layout);
    }


    private DatePickerDialog datePicker;

    private SimpleDateFormat dateFormatter;

    @Override
    public void convertForView(ConsultantInformation activeElement) {
        status.setChecked(activeElement.getStatus() == 1);
        videoTarif.setText(activeElement.getVideoTarif() != null ? activeElement.getVideoTarif().toString() : "");
        conversationTarif.setText(activeElement.getConversationTarif() != null ? activeElement.getConversationTarif().toString() : "");
    }

    @Override
    public void convertForSubmit(ConsultantInformation activeElement) {
        activeElement.setStatus(new Byte(status.isChecked() ? "1" : "0"));

        try{
            activeElement.setConversationTarif(Integer.parseInt(conversationTarif.getText().toString()));
            activeElement.setVideoTarif(Integer.parseInt(videoTarif.getText().toString()));
        } catch (NumberFormatException e) {
            System.out.println("Conversion error");
        }
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
        return new ConsultantInformationFragment();
    }
}
