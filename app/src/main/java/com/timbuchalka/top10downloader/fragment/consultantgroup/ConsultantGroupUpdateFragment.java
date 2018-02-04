package com.timbuchalka.top10downloader.fragment.consultantgroup;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import java.text.SimpleDateFormat;

public class ConsultantGroupUpdateFragment
        extends UpdateFragment<ConsultantGroup> {
    TextView name;
    TextView description;
    TextView videoTarif;
    TextView conversationTarif;

    @SuppressLint("ValidFragment")
    ConsultantGroupUpdateFragment(){
        super();
    }

    @SuppressLint("ValidFragment")
    ConsultantGroupUpdateFragment(Class<ConsultantGroup> genericClass, ConsultantGroup activeElement, int layout) {
        super(genericClass, activeElement, layout);
    }

    @SuppressLint("ValidFragment")
    ConsultantGroupUpdateFragment(Class<ConsultantGroup> genericClass, int layout) {
        super(genericClass, layout);
    }

    private DatePickerDialog datePicker;

    private SimpleDateFormat dateFormatter;

    @Override
    public void convertForView(ConsultantGroup activeElement) {
        name.setText(activeElement.getName());
        description.setText(activeElement.getDescription());
        videoTarif.setText(activeElement.getVideoTarif().toString());
        conversationTarif.setText(activeElement.getConversationTarif().toString());
    }

    @Override
    public void convertForSubmit(ConsultantGroup activeElement) {
        activeElement.setName(name.getText().toString());
        activeElement.setDescription(description.getText().toString());
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
        name = (TextView) view.findViewById(R.id.name);
        description = (TextView) view.findViewById(R.id.description);
        videoTarif = (TextView) view.findViewById(R.id.videoTarif);
        conversationTarif = (TextView) view.findViewById(R.id.conversationTarif);
    }

    @Override
    public Fragment getListView() {
        return new ConsultantGroupFragment();
    }
}
