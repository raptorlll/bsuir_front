package com.leonov.bsuir.fragment.customerayment;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.leonov.bsuir.R;
import com.leonov.bsuir.adapters.CrudInformationAdapter;
import com.leonov.bsuir.fragment.crud.CrudFragment;
import com.leonov.bsuir.fragment.crud.ReadFragment;
import com.leonov.bsuir.fragment.crud.UpdateFragment;
import com.leonov.bsuir.models.CustomerPayment;

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
            this.dataTime = (TextView) v.findViewById(R.id.dataTime);
            this.conversation = (TextView) v.findViewById(R.id.conversation);
            this.amount = (TextView) v.findViewById(R.id.amount);
        }

        private TextView dataTime;
        private TextView conversation;
        private TextView amount;

        @Override
        public void setText(CustomerPayment currentElement) {
            dataTime.setText(new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.US).format(currentElement.getDataTime()));
            conversation.setText(currentElement.getConversation().getCustomerInformation().getAdditionalInformation());
            amount.setText(currentElement.getAmount().toString());
        }
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, CustomerPayment currentElement)  {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();
        return viewHolder;
    }
}
