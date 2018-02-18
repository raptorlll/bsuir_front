package com.leonov.bsuir;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        return inflater.inflate(R.layout.main_fragment, parent, false);
    }


    private ViewPager viewPager;
    TabsPagerAdapter myAdapter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ViewPager vpPager = (ViewPager) view.findViewById(R.id.viewpager);
        myAdapter = new TabsPagerAdapter(getChildFragmentManager());
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
}


















