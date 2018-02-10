package com.timbuchalka.top10downloader.fragment.consultantinformation;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.api.FetcherAbstract;
import com.timbuchalka.top10downloader.api.GetFileByUrlData;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.models.ConsultantInformation;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

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
        ((TextView) view.findViewById(R.id.education)).setText(activeElement.getEducation().toString());
        ((TextView) view.findViewById(R.id.degree)).setText(activeElement.getDegree().toString());
        ((TextView) view.findViewById(R.id.licenseNumber)).setText(activeElement.getLicenseNumber().toString());
        ((TextView) view.findViewById(R.id.licenseFile)).setText(activeElement.getLicenseFile().toString());
        ((TextView) view.findViewById(R.id.licenseUntil)).setText(activeElement.getLicenseUntil().toString());
        ((TextView) view.findViewById(R.id.availableFrom)).setText(activeElement.getAvailableFrom().toString());
        ((TextView) view.findViewById(R.id.availableUntil)).setText(activeElement.getAvailableUntil().toString());
        ((TextView) view.findViewById(R.id.consultantGroupUser)).setText(activeElement.getConsultantGroupUser().toString());
        ImageView viewImage = ((ImageView) view.findViewById(R.id.licenseFileView));

        new GetFileByUrlData(viewImage).execute(FetcherAbstract.API_HOST + "/assets/"+activeElement.getLicenseFile());
    }
}
