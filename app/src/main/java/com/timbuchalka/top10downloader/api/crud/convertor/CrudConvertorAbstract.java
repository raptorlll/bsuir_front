package com.timbuchalka.top10downloader.api.crud.convertor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.crud.ApiCrudFactory;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import com.timbuchalka.top10downloader.models.CustomerInformation;
import com.timbuchalka.top10downloader.models.ModelInterface;
import com.timbuchalka.top10downloader.models.User;
import com.timbuchalka.top10downloader.models.UserJson;

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
        GsonBuilder gsonBuilder = new GsonBuilder();

        attacheSerializers(gsonBuilder);

        Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd").create();
        Type type = getTypeElement();
        String json = gson.toJson(data, type);

        return json;
    }

    private void attacheSerializers(GsonBuilder gsonBuilder){
        if(getTypeElement() != UserJson.class){
            gsonBuilder.registerTypeAdapter(UserJson.class, new ModelInterfaceSerializer());
        }
        if(getTypeElement() != ConsultantGroup.class){
            gsonBuilder.registerTypeAdapter(ConsultantGroup.class, new ModelInterfaceSerializer());
        }
        if(getTypeElement() != User.class){
            gsonBuilder.registerTypeAdapter(User.class, new ModelInterfaceSerializer());
        }
    }

    private class ModelInterfaceSerializer implements JsonSerializer<T> {
        public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getId());
        }
    }
}
