package com.timbuchalka.top10downloader.fragment.consultantinformation;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.adapters.CrudInformationAdapter;
import com.timbuchalka.top10downloader.fragment.crud.CrudFragment;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.ConsultantInformation;


public class ConsultantInformationFragment
        extends CrudFragment<ConsultantInformation> {
    @Override
    protected int getLayoutList() {
        return R.layout.list_row_consultant_information;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.list_row_consultant_information_read;
    }

    @Override
    protected int getLayoutCreate() {
        return R.layout.list_row_consultant_information_update;
    }

    @Override
    protected int getLayoutUpdate() {
        return R.layout.list_row_consultant_information_update;
    }

    public ConsultantInformationFragment() {
        super(ConsultantInformation.class);
    }

    @SuppressLint("ValidFragment")
    public ConsultantInformationFragment(Class<ConsultantInformation> genericClass) {
        super(genericClass);
    }

    @NonNull
    @Override
    public UpdateFragment<ConsultantInformation> getCreateFragment() {
        return new ConsultantInformationUpdateFragment(genericClass, getLayoutCreate());
    }

    @Override
    public UpdateFragment<ConsultantInformation> getUpdateFragment(ConsultantInformation activeElement) {
        return new ConsultantInformationUpdateFragment(genericClass, activeElement, getLayoutUpdate());
    }

    @Override
    public ReadFragment getReadFragment(ConsultantInformation activeElement) {
        return new ConsultantInformationReadFragment(activeElement, getLayoutView());
    }

    public static class ViewHolderImplementation extends CrudInformationAdapter.ViewHolder<ConsultantInformation> {
        @Override
        public void fillData(View v) {
            education = (TextView) v.findViewById(R.id.education);
            degree = (TextView) v.findViewById(R.id.degree);
            licenseNumber = (TextView) v.findViewById(R.id.licenseNumber);
            licenseFile = (TextView) v.findViewById(R.id.licenseFile);
            licenseUntil = (TextView) v.findViewById(R.id.licenseUntil);
            availableFrom = (TextView) v.findViewById(R.id.availableFrom);
            availableUntil = (TextView) v.findViewById(R.id.availableUntil);
            consultantGroupUser = (TextView) v.findViewById(R.id.consultantGroupUser);
        }

        private TextView education;
        private TextView degree;
        private TextView licenseNumber;
        private TextView licenseFile;
        private TextView licenseUntil;
        private TextView availableFrom;
        private TextView availableUntil;
        private TextView consultantGroupUser;

        @Override
        public void setText(ConsultantInformation currentElement) {
//            status.setText(currentElement.getStatus()==0 ? "Active" : "Inactive");
//            videoTarif.setText(currentElement.getVideoTarif() != null ? currentElement.getVideoTarif().toString() : "");
//            conversationTarif.setText(currentElement.getConversationTarif() != null ? currentElement.getConversationTarif().toString() : "");
//            user.setText(currentElement.getUser().getFirstName());
//            consultantGroup.setText(currentElement.getConsultantGroup().getName());
            education.setText(currentElement.getEducation().toString());
            degree.setText(currentElement.getDegree().toString());
            licenseNumber.setText(currentElement.getLicenseNumber().toString());
            licenseFile.setText(currentElement.getLicenseFile().toString());
            licenseUntil.setText(currentElement.getLicenseUntil().toString());
            availableFrom.setText(currentElement.getAvailableFrom().toString());
            availableUntil.setText(currentElement.getAvailableUntil().toString());
            consultantGroupUser.setText(currentElement.getConsultantGroupUser().toString());

        }
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, ConsultantInformation currentElement) {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();

        return viewHolder;
    }
}
