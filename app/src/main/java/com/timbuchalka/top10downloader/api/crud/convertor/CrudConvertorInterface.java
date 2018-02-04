package com.timbuchalka.top10downloader.api.crud.convertor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.models.CustomerInformation;
import com.timbuchalka.top10downloader.models.ModelInterface;

import java.lang.reflect.Type;
import java.util.Collection;

public interface CrudConvertorInterface<T extends ModelInterface> {
    String getUrl();

    public Type getTypeCollection();

    Collection<T> convertCollection(String data);

    T convertElement(String data);

    String convertElement(T data);

    public Type getTypeElement();
}
