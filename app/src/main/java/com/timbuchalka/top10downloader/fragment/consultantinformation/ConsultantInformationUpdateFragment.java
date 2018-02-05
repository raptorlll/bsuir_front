package com.timbuchalka.top10downloader.fragment.consultantinformation;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.Utils;
import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.crud.ListData;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import com.timbuchalka.top10downloader.models.ConsultantGroupUser;
import com.timbuchalka.top10downloader.models.ConsultantInformation;
import com.timbuchalka.top10downloader.models.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

//https://stackoverflow.com/questions/34276466/simple-httpurlconnection-post-file-multipart-form-data-from-android-to-google-bl
public class ConsultantInformationUpdateFragment
        extends UpdateFragment<ConsultantInformation>
        implements ListData.OnDataAvailable<ConsultantGroupUser> {
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


    @SuppressLint("ValidFragment")
    ConsultantInformationUpdateFragment() {
        super();
    }

    @Override
    public void onDataAvailable(Collection<ConsultantGroupUser> data, DownloadStatus status) {
        System.out.println("");

        consultantGroupUserData = (ArrayList<ConsultantGroupUser>) data;
        ArrayList<String> list = new ArrayList<String>();

        for (ConsultantGroupUser u : data) {
            list.add(u.getUser().getFirstName().concat(" ").concat(u.getUser().getEmail()));
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
    }

    @SuppressLint("ValidFragment")
    ConsultantInformationUpdateFragment(Class<ConsultantInformation> genericClass, int layout) {
        super(genericClass, layout);
        // This always works


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

                MultipartUtility multipart = null;
                try {
                    multipart = new MultipartUtility("http://10.0.2.2:8080/consultant_information/save", "UTF-8");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                multipart.addFormField("data", "{\n" +
                        "\t\"education\": \"MIT\",\n" +
                        "\t\"degree\": \"PhD\",\n" +
                        "\t\"licenseNumber\": \"License by NY Government\",\n" +
                        "\t\"licenseFile\": \"file.png\",\n" +
                        "\t\"licenseUntil\": \"2017-12-30\",\n" +
                        "\t\"availableFrom\": \"09:00:00\",\n" +
                        "\t\"availableUntil\": \"16:00:00\",\n" +
                        "\t\"consultantGroupUser\": 2\n" +
                        "}");

                try {
                    multipart.addFilePart("file",fileChoosed);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<String> response = null;
                try {
                    response = multipart.finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
//
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

    public class MultipartUtility {
        private final String boundary;
        private static final String LINE_FEED = "\r\n";
        private HttpURLConnection httpConn;
        private String charset;
        private OutputStream outputStream;
        private PrintWriter writer;

        /**
         * This constructor initializes a new HTTP POST request with content type
         * is set to multipart/form-data
         *
         * @param requestURL
         * @param charset
         * @throws IOException
         */
        public MultipartUtility(String requestURL, String charset)
                throws IOException {
            this.charset = charset;

            // creates a unique boundary based on time stamp
            boundary = "===" + System.currentTimeMillis() + "===";
            URL url = new URL(requestURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);    // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            outputStream = httpConn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                    true);
        }

        /**
         * Adds a form field to the request
         *
         * @param name  field name
         * @param value field value
         */
        public void addFormField(String name, String value) {
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + name + "\"")
                    .append(LINE_FEED);
            writer.append("Content-Type: text/plain; charset=" + charset).append(
                    LINE_FEED);
            writer.append(LINE_FEED);
            writer.append(value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a upload file section to the request
         *
         * @param fieldName  name attribute in <input type="file" name="..." />
         * @param uploadFile a File to be uploaded
         * @throws IOException
         */
        public void addFilePart(String fieldName, File uploadFile)
                throws IOException {
            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append(
                    "Content-Disposition: form-data; name=\"" + fieldName
                            + "\"; filename=\"" + fileName + "\"")
                    .append(LINE_FEED);
            writer.append(
                    "Content-Type: "
                            + URLConnection.guessContentTypeFromName(fileName))
                    .append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            writer.append(LINE_FEED);
            writer.flush();
        }

        /**
         * Adds a header field to the request.
         *
         * @param name  - name of the header field
         * @param value - value of the header field
         */
        public void addHeaderField(String name, String value) {
            writer.append(name + ": " + value).append(LINE_FEED);
            writer.flush();
        }

        /**
         * Completes the request and receives response from the server.
         *
         * @return a list of Strings as response in case the server returned
         * status OK, otherwise an exception is thrown.
         * @throws IOException
         */
        public List<String> finish() throws IOException {
            List<String> response = new ArrayList<String>();
            writer.append(LINE_FEED).flush();
            writer.append("--" + boundary + "--").append(LINE_FEED);
            writer.close();

            // checks server's status code first
            int status = httpConn.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        httpConn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    response.add(line);
                }
                reader.close();
                httpConn.disconnect();
            } else {
                throw new IOException("Server returned non-OK status: " + status);
            }
            return response;
        }
    }
}
