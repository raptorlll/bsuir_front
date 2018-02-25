package com.leonov.bsuir;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.leonov.bsuir.fragment.consultantgroup.ConsultantGroupFragment;
import com.leonov.bsuir.fragment.consultantgroupuser.ConsultantGroupUserFragment;
import com.leonov.bsuir.fragment.consultantinformation.ConsultantInformationFragment;
import com.leonov.bsuir.fragment.conversation.ConversationFragment;
import com.leonov.bsuir.fragment.conversationmessage.ConversationMessageFragment;
import com.leonov.bsuir.fragment.customerayment.CustomerPaymentFragment;
import com.leonov.bsuir.fragment.customerinformation.CustomerInformationFragment;
import com.leonov.bsuir.models.Role;
import com.leonov.bsuir.video.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.MODIFY_AUDIO_SETTINGS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;

public class MainActivity extends BaseAuthActivity {
    private static final String TAG = "MainActivity";
    private ListView listApps;
    private String feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
    private int feedLimit = 10;
    private String feedCachedUrl = "INVALIDATED";
    public static final String STATE_URL = "feedUrl";
    public static final String STATE_LIMIT = "feedLimit";
    public Set<Role> rolesSet;

    private static final int MENU_ADD = Menu.FIRST;
    private static final int MENU_CLIENT_INFO = Menu.FIRST + 1;
    private static final int MENU_CONSULTANT_GROUP = Menu.FIRST + 2;
    private static final int MENU_CONSULTANT_GROUP_USER = Menu.FIRST + 3;
    private static final int MENU_CONSULTANT_INFORMATION = Menu.FIRST + 4;
    private static final int MENU_CONVERSATION = Menu.FIRST + 5;
    private static final int MENU_CONVERSATION_MESSAGE = Menu.FIRST + 6;
    private static final int MENU_CUSTOMER_PAYMENT = Menu.FIRST + 7;
    private static final int MENU_MAIN = Menu.FIRST + 8;
    private static final int MENU_CUSTOMER_PAYMENT_REPORTS = Menu.FIRST + 9;
    private static final int MENU_VIDEO_LOGIN = Menu.FIRST + 10;
    private static final int MENU_LOGOUT = Menu.FIRST + 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listApps = (ListView) findViewById(R.id.xmlListView);

        if (savedInstanceState != null) {
            feedUrl = savedInstanceState.getString(STATE_URL);
            feedLimit = savedInstanceState.getInt(STATE_LIMIT);
        }

        downloadUrl(String.format(feedUrl, feedLimit));

        /* Set default fragment */
        FragmentTransaction ft;
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragmentMain, new MainFragment());
        ft.commit();


        if (!checkPermission()) {
            requestPermission();
        }
    }

    @Override
    protected void onServiceConnected() {
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        //if SharedPreferences contains username and password then redirect to Home activity
        if (sp.contains("login")) {
            if (!getSinchServiceInterface().isStarted()) {
                getSinchServiceInterface().startClient(sp.getString("login", ""));
                System.out.println("");
//                showSpinner();
            } else {
//                openPlaceCallActivity();
                System.out.println("");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.feeds_menu, menu);
        if (feedLimit == 10) {
            menu.findItem(R.id.mnu10).setChecked(true);
        } else {
            menu.findItem(R.id.mnu25).setChecked(true);
        }
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();

        menu.add(0, MENU_MAIN, Menu.NONE, "Main page");

        if (isAdmin()) {
            menu.add(0, MENU_CLIENT_INFO, Menu.NONE, R.string.client_info);
            menu.add(0, MENU_CONSULTANT_GROUP, Menu.NONE, "Consultant group");
            menu.add(0, MENU_CONSULTANT_GROUP_USER, Menu.NONE, "Consultant group user");
            menu.add(0, MENU_CONSULTANT_INFORMATION, Menu.NONE, "Consultant information");
            menu.add(0, MENU_CONVERSATION, Menu.NONE, "Conversations");
            menu.add(0, MENU_CONVERSATION_MESSAGE, Menu.NONE, "Conversation message");
            menu.add(0, MENU_CUSTOMER_PAYMENT, Menu.NONE, "Customer payment");
        }

        if (isCustomer()) {
            menu.add(0, MENU_CLIENT_INFO, Menu.NONE, "My information accounts");
            menu.add(0, MENU_CONVERSATION, Menu.NONE, "Conversations");
            menu.add(0, MENU_CUSTOMER_PAYMENT, Menu.NONE, "Payments");
        }

        if (isConsultant()) {
            menu.add(0, MENU_CONSULTANT_INFORMATION, Menu.NONE, "My information");
            menu.add(0, MENU_CONVERSATION, Menu.NONE, "Conversations");
            menu.add(0, MENU_CUSTOMER_PAYMENT, Menu.NONE, "Payments list");
            menu.add(0, MENU_CUSTOMER_PAYMENT_REPORTS, Menu.NONE, "Payment reports");
        }

//        menu.add(0, MENU_VIDEO_LOGIN, Menu.NONE, "Video login");
        menu.add(0, MENU_LOGOUT, Menu.NONE, "Logout");

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentTransaction ft;
        ft = getSupportFragmentManager().beginTransaction();

        switch (id) {
            case MENU_MAIN:
                ft.replace(R.id.fragmentMain, new MainFragment());
                ft.commit();
                break;

            case MENU_CLIENT_INFO:
                ft.replace(R.id.fragmentMain, new CustomerInformationFragment());
                ft.commit();
                break;

            case MENU_CONSULTANT_GROUP:
                ft.replace(R.id.fragmentMain, new ConsultantGroupFragment());
                ft.commit();
                break;

            case MENU_CONSULTANT_GROUP_USER:
                ft.replace(R.id.fragmentMain, new ConsultantGroupUserFragment());
                ft.commit();
                break;

            case MENU_CONSULTANT_INFORMATION:
                ft.replace(R.id.fragmentMain, new ConsultantInformationFragment());
                ft.commit();
                break;

            case MENU_CONVERSATION:
                ConversationFragment fragment3 = new ConversationFragment();

                if (isConsultant()) {
                    fragment3.setReadOnly(true);
                }

                ft.replace(R.id.fragmentMain, fragment3);
                ft.commit();
                break;

            case MENU_CONVERSATION_MESSAGE:
                ft.replace(R.id.fragmentMain, new ConversationMessageFragment());
                ft.commit();
                break;

            case MENU_CUSTOMER_PAYMENT:
                CustomerPaymentFragment fragment5 = new CustomerPaymentFragment();

                if (isConsultant()) {
                    fragment5.setReadOnly(true);
                }

                ft.replace(R.id.fragmentMain, fragment5);
                ft.commit();
                break;

            case MENU_CUSTOMER_PAYMENT_REPORTS:
                ft.replace(R.id.fragmentMain, new CustomerPaymentFragment());
                ft.commit();
                break;

            case MENU_VIDEO_LOGIN:

                Intent mainActivity = new Intent(this, LoginVideoActivity.class);
                startActivity(mainActivity);
                break;

            case MENU_LOGOUT:
                SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.clear();
                e.commit();
                /* Reload activity */
                finish();
                startActivity(getIntent());
                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_URL, feedUrl);
        outState.putInt(STATE_LIMIT, feedLimit);
        super.onSaveInstanceState(outState);
    }

    private void downloadUrl(String feedUrl) {
        if (!feedUrl.equalsIgnoreCase(feedCachedUrl)) {
            Log.d(TAG, "downloadUrl: starting Asynctask");
            feedCachedUrl = feedUrl;
            Log.d(TAG, "downloadUrl: done");
        } else {
            Log.d(TAG, "downloadUrl: URL not changed");
        }
    }


    public static final int RequestPermissionCode = 1;

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                INTERNET,
                WRITE_EXTERNAL_STORAGE,
                RECORD_AUDIO,
                MODIFY_AUDIO_SETTINGS,
                READ_PHONE_STATE,
                ACCESS_NETWORK_STATE,
                CAMERA
        }, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean internet = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean write_external_storage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean record_audio = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean modify_audio_settings = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean read_phone_state = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean access_network_state = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean camera = grantResults[6] == PackageManager.PERMISSION_GRANTED;

                    if (internet &&
                            write_external_storage &&
                            record_audio &&
                            modify_audio_settings &&
                            read_phone_state &&
                            access_network_state &&
                            camera) {
                        Toast.makeText(getBaseContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Permission", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        boolean internet = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET) == PackageManager.PERMISSION_GRANTED;
        boolean write_external_storage = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean record_audio = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        boolean modify_audio_settings = ContextCompat.checkSelfPermission(getApplicationContext(), MODIFY_AUDIO_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        boolean read_phone_state = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
        boolean access_network_state = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED;
        boolean camera = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA) == PackageManager.PERMISSION_GRANTED;

        return internet &&
                write_external_storage &&
                record_audio &&
                modify_audio_settings &&
                read_phone_state &&
                access_network_state &&
                camera;
    }


    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ParseApplications parseApplications = new ParseApplications();
            parseApplications.parse(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.d(TAG, "doInBackground: starts with " + strings[0]);
            String rssFeed = downloadXML(strings[0]);
            if (rssFeed == null) {
                Log.e(TAG, "doInBackground: Error downloading");
            }
            return rssFeed;
        }

        private String downloadXML(String urlPath) {
            StringBuilder xmlResult = new StringBuilder();

            try {
                URL url = new URL(urlPath);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "downloadXML: The response code was " + response);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                int charsRead;
                char[] inputBuffer = new char[500];
                while (true) {
                    charsRead = reader.read(inputBuffer);
                    if (charsRead < 0) {
                        break;
                    }
                    if (charsRead > 0) {
                        xmlResult.append(String.copyValueOf(inputBuffer, 0, charsRead));
                    }
                }
                reader.close();

                return xmlResult.toString();
            } catch (MalformedURLException e) {
                Log.e(TAG, "downloadXML: Invalid URL " + e.getMessage());
            } catch (IOException e) {
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            } catch (SecurityException e) {
                Log.e(TAG, "downloadXML: Security Exception.  Needs permisson? " + e.getMessage());
//                e.printStackTrace();
            }

            return null;
        }
    }
}


















