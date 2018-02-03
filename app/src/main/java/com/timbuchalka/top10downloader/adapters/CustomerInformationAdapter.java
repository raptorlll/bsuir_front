package com.timbuchalka.top10downloader.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.timbuchalka.top10downloader.CustomerInformationFragment;
import com.timbuchalka.top10downloader.CustomerInformationReadFragment;
import com.timbuchalka.top10downloader.CustomerInformationUpdateFragment;
import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.api.CustomerInformation.GetCustomerInformationDataDelete;
import com.timbuchalka.top10downloader.api.DownloadStatus;
import com.timbuchalka.top10downloader.api.FetcherAbstract;
import com.timbuchalka.top10downloader.models.CustomerInformation;

import java.text.SimpleDateFormat;
import java.util.List;

public class CustomerInformationAdapter<T extends CustomerInformation>
        extends ArrayAdapter {
    private static final String TAG = "CustomerInformation";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private final FragmentActivity contextInner;
    private List<T> elementsList;

    private DataReload reloadDataEvent;

    public interface DataReload{
        public void reloadData();
    }

    public void registerReloadListener(DataReload event){
        reloadDataEvent = event;
    }

    FragmentTransaction ft;
    public CustomerInformationAdapter(FragmentActivity context, int resource, List<T> applications) {
        super(context, resource);
        this.layoutResource = resource;
        this.contextInner = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.elementsList = applications;

        ft = context.getSupportFragmentManager().beginTransaction();
    }

    @Override
    public int getCount() {
        return elementsList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            Log.d(TAG, "getView: called ");
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            Log.d(TAG, "getView: provided a convertView");
            viewHolder = (ViewHolder) convertView.getTag();
        }

        T currentElement = elementsList.get(position);
        SimpleDateFormat dateFormatter = new SimpleDateFormat("Y-m-d");

        viewHolder.birthData.setText(new SimpleDateFormat("Y-m-d").format(currentElement.getBirthData()).concat(" birth date"));
        viewHolder.additionalInformation.setText(currentElement.getAdditionalInformation());
        viewHolder.primary.setText(currentElement.getPrimary() == 0 ? "Secondary" : "Primary");

        try {
            viewHolder.crudButtons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context contextAll = getContext().getApplicationContext();
                    PopupMenu popup = new PopupMenu(contextAll, v);
                    popup.getMenuInflater().inflate(R.menu.crud_popup, popup.getMenu());
                    popup.show();

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        private void fragmentReplace(Fragment fa){
                            ft.replace(R.id.fragmentMain, fa);
                            ft.commit();
                        }

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            String positionStr  = String.valueOf(position);
                            final Context contextAll = getContext().getApplicationContext();
                            T activeElement = elementsList.get(position);

                            switch (item.getItemId()) {
                                case R.id.crudRead:
                                    CustomerInformationReadFragment faa =
                                            new CustomerInformationReadFragment(activeElement, R.layout.list_row_customer_information_read);
                                    fragmentReplace(faa);
                                    Toast.makeText(contextAll, "Read at position " + positionStr , Toast.LENGTH_LONG).show();
                                    break;

                                case R.id.crudUpdate:
                                    CustomerInformationUpdateFragment faa1 =
                                            new CustomerInformationUpdateFragment(activeElement, R.layout.list_row_customer_information_update);
                                    fragmentReplace(faa1);
                                    Toast.makeText(contextAll, "Add to Wish List Clicked at position " + position, Toast.LENGTH_LONG).show();
                                    break;

                                case R.id.crudDelete:
                                    GetCustomerInformationDataDelete deleteData = new GetCustomerInformationDataDelete(
                                            new GetCustomerInformationDataDelete.OnDataAvailable(){
                                            @Override
                                            public void onDataAvailable(CustomerInformation data, DownloadStatus status) {
//                                                Toast.makeText(contextAll, "You deleted row", Toast.LENGTH_LONG).show();
                                                System.out.println("reload data");

                                                if(reloadDataEvent!=null)
                                                    reloadDataEvent.reloadData();
                                            }
                                        }, activeElement.getId());
                                    deleteData.execute();
                                    break;

                                default:
                                    break;
                            }

                            return true;
                        }
                    });
                }
            });

        } catch (Exception e) {

            e.printStackTrace();
        }


        return convertView;
    }

    private class ViewHolder {
        private TextView birthData;
        private TextView additionalInformation;
        private TextView primary;
        private TextView crudButtons;

        ViewHolder(View v) {
            this.birthData = (TextView) v.findViewById(R.id.birthData);
            this.additionalInformation = (TextView) v.findViewById(R.id.additionalInformation);
            this.primary = (TextView) v.findViewById(R.id.primary);
            this.crudButtons = (TextView) v.findViewById(R.id.crudButtons);
        }

    }
}





















