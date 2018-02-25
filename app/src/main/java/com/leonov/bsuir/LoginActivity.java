package com.leonov.bsuir;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
public class LoginActivity extends BaseGuestActivity {
    private static final String TAG = "LoginVideoActivity";
    private FragmentTransaction ft;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.guest, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_register:
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_placeholder, new RegistrationFragment());
                ft.commit();

                return true;
            case R.id.menu_login:
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_placeholder, new LoginFragment());
                ft.commit();

                return true;
            case R.id.menu_license:
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_placeholder, new LicenseFragment());
                ft.commit();

                return true;
            case R.id.menu_company_news:
                ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_placeholder, new NewsFragment());
                ft.commit();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new RegistrationFragment());
        ft.commit();
        setContentView(R.layout.login);
//
//        Toolbar toolbar = findViewById(R.layout.menu_main).findViewById(R.id.toolbar);
////        toolbar.inflateMenu(R.menu.menu_main);
//        setSupportActionBar(toolbar);
//        setHasOptionsMenu(true);
    }


}


















