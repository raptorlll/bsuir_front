package com.timbuchalka.top10downloader.api.crud.convertor;

import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import com.timbuchalka.top10downloader.models.ConsultantGroupUser;

import java.lang.reflect.Type;
import java.util.Collection;

public class ConsultantGroupUserConvertor<T>
        extends CrudConvertorAbstract<ConsultantGroupUser> {
    @Override
    public String getUrl() {
        return "/consultant_group_user";
    }

    @Override
    public Type getTypeCollection() {
        return new TypeToken<Collection<ConsultantGroupUser>>() {}.getType();
    }

    @Override
    public Type getTypeElement() {
        return new TypeToken<ConsultantGroupUser>() {}.getType();
    }
}
