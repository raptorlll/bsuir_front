package com.timbuchalka.top10downloader.adapters;

import android.content.Context;
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

import com.timbuchalka.top10downloader.fragment.crud.CrudFragment;
import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.models.ModelInterface;

import java.util.List;

public class CrudInformationAdapter<T extends ModelInterface>
        extends ArrayAdapter {
    private static final String TAG = "CustomerInformation";
    private final int layoutResource;
    private final LayoutInflater layoutInflater;
    private final FragmentActivity contextInner;
    private CrudFragment<T> crudFragment;
    private List<T> elementsList;

    FragmentTransaction ft;
    public CrudInformationAdapter(CrudFragment<T> crudFragment, FragmentActivity context, int resource, List<T> applications) {
        super(context, resource);
        this.layoutResource = resource;
        this.contextInner = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.elementsList = applications;
        this.crudFragment = crudFragment;
        ft = context.getSupportFragmentManager().beginTransaction();
    }

    @Override
    public int getCount() {
        return elementsList.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder<T> viewHolder;

        T currentElement = elementsList.get(position);

        if (convertView == null) {
            Log.d(TAG, "getView: called ");
            convertView = layoutInflater.inflate(layoutResource, parent, false);

            viewHolder = crudFragment.getViewHolder(convertView, currentElement);
            viewHolder.fillData(convertView);
            viewHolder.getCrudButons(convertView);
            convertView.setTag(viewHolder);
        } else {
            Log.d(TAG, "getView: provided a convertView");
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.setText(currentElement);

        try {
            viewHolder.crudButtons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context contextAll = getContext().getApplicationContext();
                    PopupMenu popup = new PopupMenu(contextAll, v);
                    popup.getMenuInflater().inflate(R.menu.crud_popup, popup.getMenu());
                    popup.show();

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            String positionStr  = String.valueOf(position);
                            final Context contextAll = getContext().getApplicationContext();
                            T activeElement = elementsList.get(position);

                            switch (item.getItemId()) {
                                case R.id.crudRead:
                                    crudFragment.onViewClick(activeElement);
                                    break;

                                case R.id.crudUpdate:
                                    crudFragment.onUpdateClick(activeElement);
                                    break;

                                case R.id.crudDelete:
                                    crudFragment.onDeleteClick(activeElement);
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

    abstract public static class ViewHolder<T>{
        protected TextView crudButtons;
        abstract public void fillData(View v);
        abstract public void setText(T v);
        void getCrudButons(View v) {
            this.crudButtons = (TextView) v.findViewById(R.id.crudButtons);
        }
    }
}





















