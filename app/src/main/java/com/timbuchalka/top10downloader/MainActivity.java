package com.timbuchalka.top10downloader;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.timbuchalka.top10downloader.fragment.consultantgroup.ConsultantGroupFragment;
import com.timbuchalka.top10downloader.fragment.consultantgroupuser.ConsultantGroupUserFragment;
import com.timbuchalka.top10downloader.fragment.consultantinformation.ConsultantInformationFragment;
import com.timbuchalka.top10downloader.fragment.conversation.ConversationFragment;
import com.timbuchalka.top10downloader.fragment.conversationmessage.ConversationMessageFragment;
import com.timbuchalka.top10downloader.fragment.customerinformation.CustomerInformationFragment;
import com.timbuchalka.top10downloader.models.Role;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;

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
    private static final int MENU_LOGOUT = Menu.FIRST + 100;
//    private static final int MENU_CLIENT_INFO = Menu.FIRST + 1;



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

        if(isAdmin()){
            menu.add(0, MENU_CLIENT_INFO, Menu.NONE, R.string.client_info);
            menu.add(0, MENU_CONSULTANT_GROUP, Menu.NONE, "Consultant group");
            menu.add(0, MENU_CONSULTANT_GROUP_USER, Menu.NONE, "Consultant group user");
            menu.add(0, MENU_CONSULTANT_INFORMATION, Menu.NONE, "Consultant information");
            menu.add(0, MENU_CONVERSATION, Menu.NONE, "Conversations");
            menu.add(0, MENU_CONVERSATION_MESSAGE, Menu.NONE, "Conversation message");
        }

        menu.add(0, MENU_LOGOUT, Menu.NONE, "Logout");

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        FragmentTransaction ft;
        ft = getSupportFragmentManager().beginTransaction();

        switch (id) {
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
                ft.replace(R.id.fragmentMain, new ConversationFragment());
                ft.commit();
                break;

            case MENU_CONVERSATION_MESSAGE:
                ft.replace(R.id.fragmentMain, new ConversationMessageFragment());
                ft.commit();
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

            case R.id.mnuFree:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topfreeapplications/limit=%d/xml";
                break;
            case R.id.mnuPaid:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/toppaidapplications/limit=%d/xml";
                break;
            case R.id.mnuSongs:
                feedUrl = "http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=%d/xml";
                break;
            case R.id.mnu10:
            case R.id.mnu25:
                if (!item.isChecked()) {
                    item.setChecked(true);
                    feedLimit = 35 - feedLimit;
                    Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() + " setting feedLimit to " + feedLimit);
                } else {
                    Log.d(TAG, "onOptionsItemSelected: " + item.getTitle() + " feedLimit unchanged");
                }
                break;
            case R.id.mnuRefresh:
                feedCachedUrl = "INVALIDATED";
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
//        downloadUrl(String.format(feedUrl, feedLimit));
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
//            DownloadData downloadData = new DownloadData();
//            downloadData.execute(feedUrl);
            feedCachedUrl = feedUrl;
            Log.d(TAG, "downloadUrl: done");
        } else {
            Log.d(TAG, "downloadUrl: URL not changed");
        }
    }

    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
//            Log.d(TAG, "onPostExecute: parameter is " + s);
            ParseApplications parseApplications = new ParseApplications();
            parseApplications.parse(s);

//            ArrayAdapter<CustomerInformationEntry> arrayAdapter = new ArrayAdapter<CustomerInformationEntry>(
//                    MainActivity.this, R.layout.list_item, parseApplications.getApplications());
//            listApps.setAdapter(arrayAdapter);
//            FeedAdapter<FeedEntry> feedAdapter = new FeedAdapter<>(MainActivity.this, R.layout.list_record,
//                    parseApplications.getApplications());
//            listApps.setAdapter(feedAdapter);

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
//                InputStream inputStream = connection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader reader = new BufferedReader(inputStreamReader);
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


















