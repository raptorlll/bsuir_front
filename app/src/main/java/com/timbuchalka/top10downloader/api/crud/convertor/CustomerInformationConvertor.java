package com.timbuchalka.top10downloader.api.crud.convertor;

import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.models.CustomerInformation;

import java.lang.reflect.Type;
import java.util.Collection;

public class CustomerInformationConvertor<T>
        extends CrudConvertorAbstract<CustomerInformation> {
    @Override
    public String getUrl() {
        return "/customer_information";
    }

    @Override
    public Type getTypeCollection() {
        return new TypeToken<Collection<CustomerInformation>>() {}.getType();
    }

    @Override
    public Type getTypeElement() {
        return new TypeToken<CustomerInformation>() {}.getType();
    }
}
