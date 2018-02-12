package com.leonov.bsuir.api.crud.convertor;

import com.google.gson.reflect.TypeToken;
import com.leonov.bsuir.models.ConsultantGroup;

import java.lang.reflect.Type;
import java.util.Collection;

public class ConsultantGroupConvertor<T>
        extends CrudConvertorAbstract<ConsultantGroup> {
    @Override
    public String getUrl() {
        return "/consultant_group";
    }

    @Override
    public Type getTypeCollection() {
        return new TypeToken<Collection<ConsultantGroup>>() {}.getType();
    }

    @Override
    public Type getTypeElement() {
        return new TypeToken<ConsultantGroup>() {}.getType();
    }
}
