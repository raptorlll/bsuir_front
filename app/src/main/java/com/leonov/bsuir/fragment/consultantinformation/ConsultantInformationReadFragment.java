package com.leonov.bsuir.fragment.consultantinformation;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leonov.bsuir.R;
import com.leonov.bsuir.api.FetcherAbstract;
import com.leonov.bsuir.api.GetFileByUrlData;
import com.leonov.bsuir.fragment.crud.ReadFragment;
import com.leonov.bsuir.models.ConsultantInformation;

public class ConsultantInformationReadFragment
        extends ReadFragment<ConsultantInformation> {

    public ConsultantInformationReadFragment() {
        super();
    }

    @SuppressLint("ValidFragment")
    public ConsultantInformationReadFragment(ConsultantInformation activeElement, int layout) {
        super(activeElement, layout);
    }

    @Override
    public void createSetVars(View view) {
        ((TextView) view.findViewById(R.id.education)).setText("Education : "+ activeElement.getEducation().toString());
        ((TextView) view.findViewById(R.id.degree)).setText("Degree : "+ activeElement.getDegree().toString());
        ((TextView) view.findViewById(R.id.licenseNumber)).setText("License Number : "+ activeElement.getLicenseNumber().toString());
        ((TextView) view.findViewById(R.id.licenseFile)).setText("License File : "+activeElement.getLicenseFile().toString());
        ((TextView) view.findViewById(R.id.licenseUntil)).setText("License Until : "+ activeElement.getLicenseUntil().toString());
        ((TextView) view.findViewById(R.id.availableFrom)).setText("Available From : "+ activeElement.getAvailableFrom().toString());
        ((TextView) view.findViewById(R.id.availableUntil)).setText("Available Until : "+ activeElement.getAvailableUntil().toString());
        ((TextView) view.findViewById(R.id.consultantGroupUser)).setText("User : "+ activeElement.getConsultantGroupUser().getUser().getFirstName()
            .concat(" ").concat(activeElement.getConsultantGroupUser().getConsultantGroup().getName()));
        ImageView viewImage = ((ImageView) view.findViewById(R.id.licenseFileView));

        new GetFileByUrlData(viewImage).execute(FetcherAbstract.API_HOST + "/assets/"+activeElement.getLicenseFile());
    }
}
