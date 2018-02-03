package com.timbuchalka.top10downloader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.timbuchalka.top10downloader.adapters.CrudInformationAdapter;
import com.timbuchalka.top10downloader.api.crud.ListData;
import com.timbuchalka.top10downloader.api.crud.DeleteData;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.models.ModelInterface;

import java.text.SimpleDateFormat;
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

    CrudFragment(Class cl){

    }

    abstract protected int getLayoutList();

    abstract protected int getLayoutView();

    abstract protected int getLayoutCreate();

    abstract protected int getLayoutUpdate();

    @Override
    public void onDataAvailable(Collection<T> data, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: ");

        List<T> list = new ArrayList<>();
        list.addAll(data);

        CrudInformationAdapter<T> feedAdapter =
                new CrudInformationAdapter<T>(this, getActivity(), getLayoutList(), list);
        listView.setAdapter(feedAdapter);
    }

    public void loadData(){
        ListData listData = new ListData(this);
        listData.execute();
    }

    public void reloadData() {
        loadData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        loadData();

        ft = getActivity().getSupportFragmentManager().beginTransaction();
        View view = inflater.inflate(getLayoutList(), parent, false);
        listView = (ListView) view.findViewById(R.id.xmlListView);
        addButtom = (Button) view.findViewById(R.id.crudCreate);
        addButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateFragment<T> faa = new UpdateFragment<T>(genericClass, getLayoutCreate());
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
        DeleteData<T> deleteData = new DeleteData<T>(
                new DeleteData.OnDataAvailable<T>(){
                    @Override
                    public void onDataAvailable(T data, DownloadStatus status) {
                        Toast.makeText(getContext(), "You deleted row", Toast.LENGTH_LONG).show();
                        System.out.println("reload data");

                        reloadData();
                    }
                }, activeElement.getId());
        deleteData.execute();
    }

    public void onUpdateClick(T activeElement) {
        UpdateFragment<T> faa1 = new UpdateFragment<T>(genericClass, activeElement, getLayoutUpdate());
        fragmentReplace(faa1);
        Toast.makeText(getContext(), "Add to Wish List Clicked at position ", Toast.LENGTH_LONG).show();

    }

    public void onViewClick(T activeElement) {
        CustomerInformationReadFragment faa =
                new CustomerInformationReadFragment(activeElement, getLayoutView());
        fragmentReplace(faa);
        Toast.makeText(getContext(), "Read at position ", Toast.LENGTH_LONG).show();
    }


    private void inflateListInformation(ViewHolderImplementation viewHolder, T currentElement) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("Y-m-d");
        viewHolder.birthData.setText(new SimpleDateFormat("Y-m-d").format(currentElement.getBirthData()).concat(" birth date"));
        viewHolder.additionalInformation.setText(currentElement.getAdditionalInformation());
        viewHolder.primary.setText(currentElement.getPrimary() == 0 ? "Secondary" : "Primary");
    }

    public ViewHolder getViewHolder(View convertView) {
        ViewHolder viewHolder = new ViewHolderImplementation();

        return viewHolder;
    }

    private static class ViewHolderImplementation extends CrudInformationAdapter.ViewHolder {
        @Override
        public void fillData(View v) {
            this.birthData = (TextView) v.findViewById(R.id.birthData);
            this.additionalInformation = (TextView) v.findViewById(R.id.additionalInformation);
            this.primary = (TextView) v.findViewById(R.id.primary);
        }
        private TextView birthData;
        private TextView additionalInformation;
        private TextView primary;
    }
}


















