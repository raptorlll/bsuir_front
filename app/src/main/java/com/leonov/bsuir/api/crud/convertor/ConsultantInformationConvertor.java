package com.leonov.bsuir.api.crud.convertor;

import com.google.gson.reflect.TypeToken;
import com.leonov.bsuir.models.ConsultantInformation;

import java.lang.reflect.Type;
import java.util.Collection;

public class ConsultantInformationConvertor<T>
        extends CrudConvertorAbstract<ConsultantInformation> {
    @Override
    public String getUrl() {
        return "/consultant_information";
    }

    @Override
    public Type getTypeCollection() {
        return new TypeToken<Collection<ConsultantInformation>>() {}.getType();
    }

    @Override
    public Type getTypeElement() {
        return new TypeToken<ConsultantInformation>() {}.getType();
    }
}
