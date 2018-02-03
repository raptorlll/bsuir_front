package com.timbuchalka.top10downloader;

import com.timbuchalka.top10downloader.api.crud.ListData;
import com.timbuchalka.top10downloader.models.CustomerInformation;


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
}


















