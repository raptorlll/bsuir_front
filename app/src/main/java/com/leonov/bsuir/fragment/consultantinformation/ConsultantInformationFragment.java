package com.leonov.bsuir.fragment.consultantinformation;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.leonov.bsuir.R;
import com.leonov.bsuir.adapters.CrudInformationAdapter;
import com.leonov.bsuir.fragment.crud.CrudFragment;
import com.leonov.bsuir.fragment.crud.ReadFragment;
import com.leonov.bsuir.fragment.crud.UpdateFragment;
import com.leonov.bsuir.models.ConsultantInformation;


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
            education.setText("Education : "+ (currentElement.getEducation() != null ? currentElement.getEducation().toString() : ""));
            degree.setText("Degree : "+ (currentElement.getDegree() != null ? currentElement.getDegree().toString() : ""));
            licenseNumber.setText("License Number : "+ (currentElement.getLicenseNumber() != null ? currentElement.getLicenseNumber().toString() : ""));
            licenseFile.setText("License File : "+ (currentElement.getLicenseFile() != null ? currentElement.getLicenseFile().toString() : ""));
            licenseUntil.setText("License Until : "+ (currentElement.getLicenseUntil().toString()));
            availableFrom.setText("Available From : "+ (currentElement.getAvailableFrom().toString()));
            availableUntil.setText("Available Until : "+ (currentElement.getAvailableUntil().toString()));
            consultantGroupUser.setText("User : "+ (currentElement.getConsultantGroupUser().getUser().getFirstName()
                    .concat(" ").concat(currentElement.getConsultantGroupUser().getConsultantGroup().getName())));
        }
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, ConsultantInformation currentElement) {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();

        return viewHolder;
    }
}
