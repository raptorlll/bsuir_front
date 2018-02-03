package com.timbuchalka.top10downloader.api.crud;

import com.timbuchalka.top10downloader.models.CustomerInformation;

public class ApiCrudFactory {
    ApiCrudFactory(){

    }

    static String getRoutesByClass(Class cl){
        if(CustomerInformation.class == cl) {
            return "customer_information";
        }

        return  null;
    }

}
