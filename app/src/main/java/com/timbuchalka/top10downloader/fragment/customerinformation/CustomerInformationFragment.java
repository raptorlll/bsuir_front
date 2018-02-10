package com.timbuchalka.top10downloader.fragment.customerinformation;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.adapters.CrudInformationAdapter;
import com.timbuchalka.top10downloader.fragment.crud.CrudFragment;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.CustomerInformation;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class CustomerInformationFragment
        extends CrudFragment<CustomerInformation> {

    @Override
    protected int getLayoutList(){
        return R.layout.list_row_customer_information;
    }

    @Override
    protected int getLayoutView(){
        return R.layout.list_row_customer_information_read;
    }

    @Override
    protected int getLayoutCreate(){
        return R.layout.list_row_customer_information_update;
    }

    @Override
    protected int getLayoutUpdate(){
        return R.layout.list_row_customer_information_update;
    }

    public CustomerInformationFragment() {
        super(CustomerInformation.class);
    }


    @SuppressLint("ValidFragment")
    public CustomerInformationFragment(Class<CustomerInformation> genericClass){
        super(genericClass);
    }


    @NonNull
    @Override
    public UpdateFragment<CustomerInformation> getCreateFragment() {
        return new CustomerInformationUpdateFragment(genericClass, getLayoutCreate());
    }

    @Override
    public UpdateFragment<CustomerInformation> getUpdateFragment(CustomerInformation activeElement) {
        return new CustomerInformationUpdateFragment(genericClass, activeElement, getLayoutUpdate());
    }

    @Override
    public ReadFragment getReadFragment(CustomerInformation activeElement) {
        return new CustomerInformationReadFragment(activeElement, getLayoutView());
    }

    public static class ViewHolderImplementation extends CrudInformationAdapter.ViewHolder<CustomerInformation> {
        @Override
        public void fillData(View v) {
            this.birthData = (TextView) v.findViewById(R.id.birthData);
            this.additionalInformation = (TextView) v.findViewById(R.id.additionalInformation);
            this.primary = (TextView) v.findViewById(R.id.primary);
        }
        private TextView birthData;
        private TextView additionalInformation;
        private TextView primary;

        @Override
        public void setText(CustomerInformation currentElement) {
            birthData.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(currentElement.getBirthData()).concat(" birth date"));
            additionalInformation.setText(currentElement.getAdditionalInformation());
            primary.setText(currentElement.getPrimary() == 0 ? "Secondary" : "Primary");
        }
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, CustomerInformation currentElement)  {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();
        return viewHolder;
    }
}
