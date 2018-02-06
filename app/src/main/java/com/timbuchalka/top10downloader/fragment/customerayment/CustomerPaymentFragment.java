package com.timbuchalka.top10downloader.fragment.customerayment;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.timbuchalka.top10downloader.R;
import com.timbuchalka.top10downloader.adapters.CrudInformationAdapter;
import com.timbuchalka.top10downloader.fragment.crud.CrudFragment;
import com.timbuchalka.top10downloader.fragment.crud.ReadFragment;
import com.timbuchalka.top10downloader.fragment.crud.UpdateFragment;
import com.timbuchalka.top10downloader.models.CustomerPayment;
import com.timbuchalka.top10downloader.models.CustomerInformation;

import java.text.SimpleDateFormat;
import java.util.Locale;


public class CustomerPaymentFragment
        extends CrudFragment<CustomerPayment> {
    @Override
    protected int getLayoutList(){
        return R.layout.list_row_customer_payment;
    }

    @Override
    protected int getLayoutView(){
        return R.layout.list_row_customer_payment_read;
    }

    @Override
    protected int getLayoutCreate(){
        return R.layout.list_row_customer_payment_update;
    }

    @Override
    protected int getLayoutUpdate(){
        return R.layout.list_row_customer_payment_update;
    }

    public CustomerPaymentFragment() {
        super(CustomerPayment.class);
    }

    @SuppressLint("ValidFragment")
    public CustomerPaymentFragment(Class<CustomerPayment> genericClass){
        super(genericClass);
    }


    @NonNull
    @Override
    public UpdateFragment<CustomerPayment> getCreateFragment() {
        return new CustomerPaymentUpdateFragment(genericClass, getLayoutCreate());
    }

    @Override
    public UpdateFragment<CustomerPayment> getUpdateFragment(CustomerPayment activeElement) {
        return new CustomerPaymentUpdateFragment(genericClass, activeElement, getLayoutUpdate());
    }

    @Override
    public ReadFragment getReadFragment(CustomerPayment activeElement) {
        return new CustomerPaymentReadFragment(activeElement, getLayoutView());
    }

    public static class ViewHolderImplementation extends CrudInformationAdapter.ViewHolder<CustomerPayment> {
        @Override
        public void fillData(View v) {
            this.dataTime = (TextView) v.findViewById(R.id.name);
            this.conversation = (TextView) v.findViewById(R.id.description);
        }

        private TextView dataTime;
        private TextView conversation;

        @Override
        public void setText(CustomerPayment currentElement) {
            dataTime.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).format(currentElement.getDataTime()));
            conversation.setText(currentElement.getConversation().getCustomerInformation().getAdditionalInformation());
        }
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, CustomerPayment currentElement)  {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();
        return viewHolder;
    }
}
