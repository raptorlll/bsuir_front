package com.leonov.bsuir.fragment.consultantinformation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.Utils;
import com.leonov.bsuir.R;
import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.crud.ListData;
import com.leonov.bsuir.fragment.crud.UpdateFragment;
import com.leonov.bsuir.models.ConsultantGroupUser;
import com.leonov.bsuir.models.ConsultantInformation;

import java.io.File;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import com.leonov.bsuir.api.GetPostFilesData;

//https://stackoverflow.com/questions/34276466/simple-httpurlconnection-post-file-multipart-form-data-from-android-to-google-bl
public class ConsultantInformationUpdateFragment
        extends UpdateFragment<ConsultantInformation>
        implements ListData.OnDataAvailable<ConsultantGroupUser>,
            GetPostFilesData.OnDataAvailable<ConsultantInformation> {
    private TextView education;
    private TextView degree;
    private TextView licenseNumber;
    private TextView licenseFile;
    private TextView licenseUntil;
    private TextView availableFrom;
    private TextView availableUntil;
    private Spinner consultantGroupUser;
    private DatePickerDialog datePicker;
    private ArrayList<ConsultantGroupUser> consultantGroupUserData;

    @Override
    public boolean customSubmit(ConsultantInformation activeElement) {
        GetPostFilesData postFilesData = new GetPostFilesData(this, fileChoosed, activeElement);
        postFilesData.execute();

        return true;
    }

    @SuppressLint("ValidFragment")
    ConsultantInformationUpdateFragment() {
        super();
    }

    @Override
    public void onDataAvailable(ConsultantInformation data, DownloadStatus status) {
        System.out.println("Data available");
        changeToListPage();
    }

    @Override
    public void onDataAvailable(Collection<ConsultantGroupUser> data, DownloadStatus status) {
        System.out.println("");

        consultantGroupUserData = (ArrayList<ConsultantGroupUser>) data;
        ArrayList<String> list = new ArrayList<String>();

        for (ConsultantGroupUser u : data) {
            list.add(u.getUser().getFirstName().concat(" ").concat(u.getConsultantGroup().getName()));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);

        consultantGroupUser.setAdapter(arrayAdapter);

        if (activeElement.getConsultantGroupUser() == null && !data.isEmpty()) {
            consultantGroupUser.setSelection(0);
        } else {
            int i = 0;
            for (ConsultantGroupUser u : data) {
                if (activeElement.getConsultantGroupUser().getId() == u.getId()) {
                    consultantGroupUser.setSelection(i);
                }

                i++;
            }
        }

        consultantGroupUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                activeElement.setConsultantGroupUser(((ArrayList<ConsultantGroupUser>) consultantGroupUserData).get(i));
                System.out.println("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @SuppressLint("ValidFragment")
    ConsultantInformationUpdateFragment(Class<ConsultantInformation> genericClass, ConsultantInformation activeElement2, int layout) {
        super(genericClass, activeElement2, layout);
        (new ListData<ConsultantGroupUser>(ConsultantGroupUser.class, this)).execute();
    }

    @SuppressLint("ValidFragment")
    ConsultantInformationUpdateFragment(Class<ConsultantInformation> genericClass, int layout) {
        super(genericClass, layout);
        (new ListData<ConsultantGroupUser>(ConsultantGroupUser.class, this)).execute();
    }

    private File fileChoosed = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Use the provided utility method to parse the result
            List<Uri> files = Utils.getSelectedFilesFromResult(intent);
            for (Uri uri : files) {
                File file = Utils.getFileForUri(uri);
                fileChoosed = Utils.getFileForUri(uri);
            }
        }
    }

    @Override
    public void convertForView(ConsultantInformation activeElement) {
        education.setText(activeElement.getEducation().toString());
        degree.setText(activeElement.getDegree().toString());
        licenseNumber.setText(activeElement.getLicenseNumber().toString());
        licenseFile.setText(activeElement.getLicenseFile().toString());
        licenseUntil.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(activeElement.getLicenseUntil()));
        availableFrom.setText((new SimpleDateFormat("HH:mm", Locale.US)).format(activeElement.getAvailableFrom()));
        availableUntil.setText((new SimpleDateFormat("HH:mm", Locale.US)).format(activeElement.getAvailableUntil()));
//        consultantGroupUser.setText(activeElement.getConsultantGroupUser().toString());
    }

    @Override
    public void convertForSubmit(ConsultantInformation activeElement) {
        activeElement.setEducation(education.getText().toString());
        activeElement.setDegree(degree.getText().toString());
        activeElement.setLicenseNumber(licenseNumber.getText().toString());
    }

    @Override
    public void onClickListeners(View view) {
        if (view == licenseUntil) {
            datePicker.show();
        } else if (view == licenseFile) {
            initFilePicker();
        }
    }

    @Override
    public void setListeners() {
        licenseFile.setOnClickListener(this);

        // perform click event listener on edit text
        availableFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();

                int hour = activeElement.getAvailableFrom() != null ?
                        Integer.parseInt((new SimpleDateFormat("H", Locale.US)).format(activeElement.getAvailableFrom())) :
                        mcurrentTime.get(Calendar.HOUR_OF_DAY);

                int minute = activeElement.getAvailableFrom() != null ?
                        Integer.parseInt((new SimpleDateFormat("m", Locale.US)).format(activeElement.getAvailableFrom())) :
                        mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Time time = new Time(selectedHour, selectedMinute, 0);
                        availableFrom.setText((new SimpleDateFormat("HH:mm", Locale.US)).format(time));
                        activeElement.setAvailableFrom(time);
                    }
                }, hour, minute, true);

                mTimePicker.show();
            }
        });

        // perform click event listener on edit text
        availableUntil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();

                int hour = activeElement.getAvailableUntil() != null ?
                        Integer.parseInt((new SimpleDateFormat("H", Locale.US)).format(activeElement.getAvailableUntil())) :
                        mcurrentTime.get(Calendar.HOUR_OF_DAY);

                int minute = activeElement.getAvailableUntil() != null ?
                        Integer.parseInt((new SimpleDateFormat("m", Locale.US)).format(activeElement.getAvailableUntil())) :
                        mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Time time = new Time(selectedHour, selectedMinute, 0);
                        availableUntil.setText((new SimpleDateFormat("HH:mm", Locale.US)).format(time));
                        activeElement.setAvailableUntil(time);
                    }
                }, hour, minute, true);

                mTimePicker.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();
        licenseUntil.setOnClickListener(this);
        datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                licenseUntil.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(newDate.getTime()));
                activeElement.setLicenseUntil(newDate.getTime());
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void findViewsById(View v) {
        education = (TextView) v.findViewById(R.id.education);
        degree = (TextView) v.findViewById(R.id.degree);
        licenseNumber = (TextView) v.findViewById(R.id.licenseNumber);
        licenseFile = (TextView) v.findViewById(R.id.licenseFile);
        licenseUntil = (TextView) v.findViewById(R.id.licenseUntil);
        availableFrom = (TextView) v.findViewById(R.id.availableFrom);
        availableUntil = (TextView) v.findViewById(R.id.availableUntil);
        consultantGroupUser = (Spinner) v.findViewById(R.id.consultantGroupUser);
    }

    private void initFilePicker() {
        Intent i = new Intent(getContext(), FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(i, 1);
    }

    @Override
    public Fragment getListView() {
        return new ConsultantInformationFragment();
    }
}
