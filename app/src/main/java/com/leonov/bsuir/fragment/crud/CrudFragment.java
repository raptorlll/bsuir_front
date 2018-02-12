package com.leonov.bsuir.fragment.crud;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.leonov.bsuir.R;
import com.leonov.bsuir.adapters.CrudInformationAdapter;
import com.leonov.bsuir.api.crud.ListData;
import com.leonov.bsuir.api.crud.DeleteData;
import com.leonov.bsuir.api.DownloadStatus;
import com.leonov.bsuir.models.ModelInterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

abstract public class CrudFragment<T extends ModelInterface>
        extends Fragment
        implements ListData.OnDataAvailable<T> {
    private ListView listView;
    private static final String TAG = "Crud Fragment";
    FragmentTransaction ft;
    Button addButtom;
    protected Class genericClass;
    public CrudFragment(){}
    public CrudFragment(Class<T> genericClass){
        this();
        this.genericClass = genericClass;
    }

    @Override
    public void onDataAvailable(Collection<T> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: ");

        List<T> list = new ArrayList<>();
        list.addAll(data);

        CrudInformationAdapter<T> feedAdapter = new CrudInformationAdapter<T>(this, getActivity(), getLayoutList(), list);
        listView.setAdapter(feedAdapter);
    }

    public void loadData(){
        ListData<T> listData = new ListData<T>(genericClass,this);
        listData.execute();
    }

    public void reloadData() {
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        loadData();

        ft = getActivity().getSupportFragmentManager().beginTransaction();
        View view = inflater.inflate(getLayoutMain(), parent, false);
        listView = (ListView) view.findViewById(R.id.xmlListView);
        addButtom = (Button) view.findViewById(R.id.crudCreate);
        addButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateFragment<T> faa = getCreateFragment();
                ft.replace(R.id.fragmentMain, faa);
                ft.commit();
            }
        });

        return view;
    }

    private void fragmentReplace(Fragment fa){
        ft.replace(R.id.fragmentMain, fa);
        ft.commit();
    }

    public void onDeleteClick(T activeElement) {
        DeleteData<T> deleteData = new DeleteData<T>(genericClass,
                new DeleteData.OnDataAvailable<T>(){
                    @Override
                    public void onDataAvailable(T data, DownloadStatus status) {
                        System.out.println("reload data");

                        reloadData();
                    }
                }, activeElement.getId());
        deleteData.execute();
    }

    public void onUpdateClick(T activeElement) {
        UpdateFragment<T> faa1 = getUpdateFragment(activeElement);
        fragmentReplace(faa1);
        Toast.makeText(getContext(), "Add to Wish List Clicked at position ", Toast.LENGTH_LONG).show();

    }

    public void onViewClick(T activeElement) {
        ReadFragment faa = getReadFragment(activeElement);
        fragmentReplace(faa);
        Toast.makeText(getContext(), "Read at position ", Toast.LENGTH_LONG).show();
    }

    protected int getLayoutMain() {
        return R.layout.cutomer_information;
    }

    abstract protected int getLayoutList();

    abstract protected int getLayoutView();

    abstract protected int getLayoutCreate();

    abstract protected int getLayoutUpdate();

    public abstract ReadFragment getReadFragment(T activeElement);

    public abstract UpdateFragment<T> getUpdateFragment(T activeElement);

    public abstract UpdateFragment<T> getCreateFragment();

//    public abstract static class ViewHolderAbsctract extends CrudInformationAdapter.ViewHolder {}

    public abstract CrudInformationAdapter.ViewHolder getViewHolder(View convertView, T currentElement);
}
