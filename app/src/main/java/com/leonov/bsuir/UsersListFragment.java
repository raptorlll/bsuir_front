package com.leonov.bsuir;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.leonov.bsuir.adapters.CrudInformationAdapter;
import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.api.crud.ListData;
import com.leonov.bsuir.models.Role;
import com.leonov.bsuir.models.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UsersListFragment extends Fragment {
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.users_list_fragment, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        listView = (ListView) view.findViewById(R.id.xmlListView);

        (new ListData<User>(User.class, new ListData.OnDataAvailable<User>() {
            @Override
            public void onDataAvailable(Collection<User> data, DownloadStatus status) {

                MySimpleArrayAdapter feedAdapter = new MySimpleArrayAdapter(getContext(), data.toArray());
                listView.setAdapter(feedAdapter);
            }
        })).execute();

    }

    public class MySimpleArrayAdapter extends ArrayAdapter<User> {
        private final Context context;
        private final Object[] values;

        public MySimpleArrayAdapter(Context context, Object[] values) {
            super(context, R.layout.user_item);
            this.context = context;
            this.values = values;
        }

        @Override
        public int getCount() {
            return values.length;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.user_item, parent, false);
            TextView name = (TextView) rowView.findViewById(R.id.username);
            TextView lastname = (TextView) rowView.findViewById(R.id.userlastname);
            TextView role = (TextView) rowView.findViewById(R.id.userrole);
            TextView email = (TextView) rowView.findViewById(R.id.useremail);

            User user = (User) values/*.toArray()*/[position];

            StringBuffer roleStr = new StringBuffer("");
            for (Role roleObj : user.getRoles()) {
                roleStr.append(roleObj.getDescription()).append(" ");
            }

            name.setText("Name : ".concat(user.getFirstName()));
            lastname.setText("Lastname : ".concat(user.getLastName()));
            role.setText("Role : ".concat(roleStr.toString()));
            email.setText("Email : ".concat(user.getEmail()));

            return rowView;
        }
    }
}


















