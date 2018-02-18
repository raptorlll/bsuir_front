package com.leonov.bsuir.fragment.customerinformation;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.leonov.bsuir.R;
import com.leonov.bsuir.adapters.CrudInformationAdapter;
import com.leonov.bsuir.fragment.crud.CrudFragment;
import com.leonov.bsuir.fragment.crud.ReadFragment;
import com.leonov.bsuir.fragment.crud.UpdateFragment;
import com.leonov.bsuir.models.CustomerInformation;
import com.leonov.bsuir.statical.RolesChecker;

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
            this.user = (TextView) v.findViewById(R.id.user);
        }

        private TextView birthData;
        private TextView additionalInformation;
        private TextView primary;
        private TextView user;

        @Override
        public void setText(CustomerInformation currentElement) {
            birthData.setText("Birth : ".concat(new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(currentElement.getBirthData())));
            additionalInformation.setText("Information : ".concat(currentElement.getAdditionalInformation()));
            primary.setText(currentElement.getPrimary() == 0 ? "Secondary" : "Primary");

            if(!RolesChecker.getInstance().isAdmin()){
                user.setVisibility(View.INVISIBLE);
            }else{
                user.setText("User : ".concat(currentElement.getUser().getFirstName().concat(" ").concat(currentElement.getUser().getLastName())));
            }
        }
    }

    @Override
    public CrudInformationAdapter.ViewHolder getViewHolder(View convertView, CustomerInformation currentElement)  {
        ViewHolderImplementation viewHolder = new ViewHolderImplementation();
        return viewHolder;
    }
}
