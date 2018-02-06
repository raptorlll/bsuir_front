package com.timbuchalka.top10downloader.api.crud.convertor;

import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import com.timbuchalka.top10downloader.models.CustomerPayment;

import java.lang.reflect.Type;
import java.util.Collection;

public class CustomerPaymentConvertor<T>
        extends CrudConvertorAbstract<CustomerPayment> {
    @Override
    public String getUrl() {
        return "/customer_payment";
    }

    @Override
    public Type getTypeCollection() {
        return new TypeToken<Collection<CustomerPayment>>() {}.getType();
    }

    @Override
    public Type getTypeElement() {
        return new TypeToken<CustomerPayment>() {}.getType();
    }
}
