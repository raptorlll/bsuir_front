package com.timbuchalka.top10downloader.fragment.consultantgroupuser;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.crud.ListData;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.ConsultantGroupUser;
import com.timbuchalka.top10downloader.models.User;

import java.text.SimpleDateFormat;
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

    @Override
    public void onDataAvailable(Collection<User> data, DownloadStatus status) {
        System.out.println("User");
    }

    @SuppressLint("ValidFragment")
    ConsultantGroupUserUpdateFragment(){
        super();
    }

    @SuppressLint("ValidFragment")
    ConsultantGroupUserUpdateFragment(Class<ConsultantGroupUser> genericClass, ConsultantGroupUser activeElement, int layout) {
        super(genericClass, activeElement, layout);

//        new ListData(User.class, this).execute();
        new ListData(User.class, this).execute();
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
