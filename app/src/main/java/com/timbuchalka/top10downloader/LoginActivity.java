package com.timbuchalka.top10downloader;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.*;

import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.GetLoginData;
import com.timbuchalka.top10downloader.models.Login;
import com.timbuchalka.top10downloader.models.Token;

public class LoginActivity extends AppCompatActivity implements GetLoginData.OnDataAvailable {
    private static final String TAG = "LoginActivity";

    @Override
    public void onDataAvailable(Token data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");
        if(status != DownloadStatus.OK ) {
            Log.e(TAG, "onDataAvailable failed with status " + status);
            Toast.makeText(getBaseContext(), "Login Erorr - server error", Toast.LENGTH_LONG).show();
            return ;
        }

        if (data == null){
            // download or processing failed
            Log.e(TAG, "onDataAvailable failed with status " + status);
            Toast.makeText(getBaseContext(), "Login error", Toast.LENGTH_LONG).show();
            return ;
        }

        SharedPreferences.Editor e= sp.edit();
        e.putString("token", data.getAccess_token());
        e.putString("expires_in", data.getExpires_in());
        e.putString("jti", data.getJti());
        e.putString("scope", data.getScope());
        e.putString("token_type", data.getToken_type());
        e.commit();

        Toast.makeText(getBaseContext(),"Login Successful", Toast.LENGTH_LONG).show();
        startActivity(new Intent(getBaseContext(), MainActivity.class));

        finish();



        Log.d(TAG, "onDataAvailable: ends");
    }

    //
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login);
//
//        Log.d(TAG, "!!!!!!!!!!!!!!!!!!!!!!1");
//    }
    EditText username, password;
    Button button;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.button);

        sp = getSharedPreferences("login", MODE_PRIVATE);

        //if SharedPreferences contains username and password then redirect to Home activity
        if (sp.contains("token")) {
            startActivity(new Intent(getBaseContext(), MainActivity.class));
            finish();   //finish current activity
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCheck();
            }
        });
    }

    private void authProcess(String username, String password) {
        GetLoginData loginData = new GetLoginData(this);
        loginData.execute(username, password);
    }


    void loginCheck() {
        //check username and password are correct and then add them to SharedPreferences
        if (username.getText().toString().length() > 1 && password.getText().toString().length() > 1) {
            authProcess(username.getText().toString(), password.getText().toString());

//            sendAuthRequest(username.getText().toString(), password.getText().toString());
        } else {
            Toast.makeText(getBaseContext(), "Fill all fields correctly", Toast.LENGTH_LONG).show();
        }
    }

    private void sendAuthRequest(String name, String password) {
        Log.d(TAG, "sendAuthRequest: ");

        DownloadData downloadData = new DownloadData();
        downloadData.execute("http://10.0.2.2:8080/health");
    }



    private class DownloadData extends AsyncTask<String, Void, String> {
        private static final String TAG = "DownloadData";

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.d(TAG, "onPostExecute: parameter is " + s);
//            ParseApplications parseApplications = new ParseApplications();
//            parseApplications.parse(s);
//
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
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            } catch (Exception e) {
                Log.e(TAG, "downloadXML: IO Exception reading data: " + e.getMessage());
            }

            return null;
        }
    }
}


















