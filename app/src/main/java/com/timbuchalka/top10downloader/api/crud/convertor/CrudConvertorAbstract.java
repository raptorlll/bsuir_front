package com.timbuchalka.top10downloader.api.crud.convertor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.crud.ApiCrudFactory;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import com.timbuchalka.top10downloader.models.ConsultantGroupUser;
import com.timbuchalka.top10downloader.models.CustomerInformation;
import com.timbuchalka.top10downloader.models.ModelInterface;
import com.timbuchalka.top10downloader.models.User;
import com.timbuchalka.top10downloader.models.UserJson;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public abstract class CrudConvertorAbstract<T extends ModelInterface> implements CrudConvertorInterface<T> {
    private static final String TIME_FORMAT = "HH:mm:ss";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    @Override
    public Collection<T> convertCollection(String data) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        attacheDeserializers(gsonBuilder);

        Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd").create();
        Type type = getTypeCollection();
        Collection<T> userJson = gson.fromJson(data, type);

        return userJson;
    }

    @Override
    public T convertElement(String data) {
        GsonBuilder gsonBuilder = new GsonBuilder();

        attacheDeserializers(gsonBuilder);

        Gson gson = gsonBuilder.setDateFormat("yyyy-MM-dd").create();
        Type type = getTypeElement();
        T userJson = gson.fromJson(data, type);

        return userJson;
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

    private void attacheSerializers(GsonBuilder gsonBuilder) {
        if (getTypeElement() != UserJson.class) {
            gsonBuilder.registerTypeAdapter(UserJson.class, new ModelInterfaceSerializer());
        }

        if (getTypeElement() != ConsultantGroup.class) {
            gsonBuilder.registerTypeAdapter(ConsultantGroup.class, new ModelInterfaceSerializer());
        }

        if (getTypeElement() != User.class) {
            gsonBuilder.registerTypeAdapter(User.class, new ModelInterfaceSerializer());
        }

        if (getTypeElement() != ConsultantGroupUser.class) {
            gsonBuilder.registerTypeAdapter(ConsultantGroupUser.class, new ModelInterfaceSerializer());
        }

        /* Custom time serialization */
        gsonBuilder.registerTypeAdapter(Time.class, new TimeSerializer());
    }

    private void attacheDeserializers(GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapter(Time.class, new TimeDeserializer());
    }


    private class DateDeserializer implements JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOF,
                                JsonDeserializationContext context) throws JsonParseException {
            try {
                return new SimpleDateFormat(DATE_FORMAT, Locale.US).parse(jsonElement.getAsString());
            } catch (ParseException e) {
            }

            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                    + "\". Supported formats: " + DATE_FORMAT);
        }
    }


    private class TimeDeserializer implements JsonDeserializer<Time> {
        @Override
        public Time deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context)
                throws JsonParseException {
            try {
                String s = jsonElement.getAsString();
                SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.US);
                sdf.parse(s);
                long ms = sdf.parse(s).getTime();
                Time t = new Time(ms);

                return t;
            } catch (ParseException e) {
            }
            throw new JsonParseException("Unparseable time: \"" + jsonElement.getAsString()
                    + "\". Supported formats: " + TIME_FORMAT);
        }
    }

    private class TimeSerializer implements JsonSerializer<Time> {
        @Override
        public JsonElement serialize(Time src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive((new SimpleDateFormat("HH:mm:00", Locale.US)).format(src));
        }
    }

    private class ModelInterfaceSerializer implements JsonSerializer<T> {
        public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getId());
        }
    }
}
