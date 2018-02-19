package com.leonov.bsuir;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leonov.bsuir.fragment.consultantgroup.ConsultantGroupFragment;
import com.leonov.bsuir.fragment.customerinformation.CustomerInformationFragment;
import com.leonov.bsuir.fragment.customerinformation.CustomerInformationReadFragment;
import com.leonov.bsuir.models.ConsultantGroup;
import com.leonov.bsuir.models.CustomerInformation;
import com.leonov.bsuir.statical.RolesChecker;

public class MainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.main_fragment, parent, false);
    }

    private ViewPager viewPager;
    FragmentPagerAdapter myAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ViewPager vpPager = (ViewPager) view.findViewById(R.id.viewpager);

        if(RolesChecker.getInstance().isAdmin()){
            myAdapter = new TabsPagerAdapter(getChildFragmentManager());
        }else if(RolesChecker.getInstance().isCustomer()){
            myAdapter = new TabsPagerAdapter(getChildFragmentManager());
        }else if(RolesChecker.getInstance().isConsultant()){
            myAdapter = new TabsPagerAdapterConsultant(getChildFragmentManager());
        }
        vpPager.setAdapter(myAdapter);
    }


    public class TabsPagerAdapter extends FragmentPagerAdapter {
        private int NUM_ITEMS = 1;
        private String[] titles= new String[]{
                "Users list",
                "News list"
        };

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return titles.length;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new UsersListFragment();
                case 1:
                    return new NewsFragment();
//                case 2:
//                    return new ThirdFragment();
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return  titles[position];
        }

    }


    public class TabsPagerAdapterConsultant extends FragmentPagerAdapter {
        private int NUM_ITEMS = 1;
        private String[] titles= new String[]{
                "Users list",
                "News list",
                "All groups"
        };

        public TabsPagerAdapterConsultant(FragmentManager fm) {
            super(fm);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return titles.length;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new UsersListFragment();
                case 1:
                    return new NewsFragment();
                case 2:
                    ConsultantGroupFragment consultantGroupFragment = new ConsultantGroupFragment(ConsultantGroup.class);
                    consultantGroupFragment.setReadOnly(true);
                    return consultantGroupFragment;
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return  titles[position];
        }

    }
}


















