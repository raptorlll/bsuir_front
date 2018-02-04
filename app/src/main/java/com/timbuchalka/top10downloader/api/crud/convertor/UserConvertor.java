package com.timbuchalka.top10downloader.api.crud.convertor;

import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import com.timbuchalka.top10downloader.models.User;

import java.lang.reflect.Type;
import java.util.Collection;

public class UserConvertor<T>
        extends CrudConvertorAbstract<User> {
    @Override
    public String getUrl() {
        return "/user";
    }

    @Override
    public Type getTypeCollection() {
        return new TypeToken<Collection<User>>() {}.getType();
    }

    @Override
    public Type getTypeElement() {
        return new TypeToken<User>() {}.getType();
    }
}
