package com.timbuchalka.top10downloader.api.crud.convertor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.crud.ApiCrudFactory;
import com.timbuchalka.top10downloader.models.CustomerInformation;
import com.timbuchalka.top10downloader.models.ModelInterface;

import java.lang.reflect.Type;
import java.util.Collection;

public abstract class CrudConvertorAbstract<T extends ModelInterface> implements CrudConvertorInterface<T> {
    @Override
    public Collection<T> convertCollection(String data) {
        Gson gson = new Gson();
        Type type = type = getTypeCollection();

        Collection<T> userJson = gson.fromJson(data, type);

        return  userJson;
    }

    @Override
    public T convertElement(String data) {
        Gson gson = new Gson();
        Type type = getTypeElement();

        T userJson = gson.fromJson(data, type);

        return  userJson;
    }

    @Override
    public String convertElement(T data) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Type type = getTypeElement();
        String json = gson.toJson(data, type);

        return json;
    }
}
