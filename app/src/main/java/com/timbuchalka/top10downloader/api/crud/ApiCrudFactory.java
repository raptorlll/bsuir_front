package com.timbuchalka.top10downloader.api.crud;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.crud.convertor.*;
import com.timbuchalka.top10downloader.models.*;

import java.com.timbuchalka.top10downloader.api.crud.convertor.ConversationConvertor;
import java.com.timbuchalka.top10downloader.models.Conversation;
import java.lang.reflect.Type;
import java.util.Collection;

public class ApiCrudFactory {
    ApiCrudFactory() {

    }

    public static <T extends ModelInterface> CrudConvertorInterface<T> getConvertor(Class genericClass) {
        if (CustomerInformation.class == genericClass) {
            return new CustomerInformationConvertor();
        } else if (ConsultantGroup.class == genericClass) {
            return new ConsultantGroupConvertor();
        } else if (ConsultantGroupUser.class == genericClass) {
            return new ConsultantGroupUserConvertor();
        } else if (User.class == genericClass) {
            return new UserConvertor();
        } else if (ConsultantInformation.class == genericClass) {
            return new ConsultantInformationConvertor();
        } else if (Conversation.class == genericClass) {
            return new ConversationConvertor();
        } else if (CustomerPayment.class == genericClass) {
            return new CustomerPaymentConvertor();
        } else if (ConversationMessage.class == genericClass) {
            return new ConversationMessageConvertor();
        }

        return null;
    }

    public static String getUrl(Class genericClass) {
        return ApiCrudFactory.getConvertor(genericClass).getUrl();
    }

    static <T extends ModelInterface> Collection<T> convertCollection(Class<T> genericClass, String data) {
        return (Collection<T>) ApiCrudFactory.getConvertor(genericClass).convertCollection(data);
    }

    static <T extends ModelInterface> T convertElement(Class<T> genericClass, String data) {
        return (T) ApiCrudFactory.getConvertor(genericClass).convertElement(data);
    }

    static <T extends ModelInterface> String convertElement(Class<T> genericClass, T data) {
        return (String) ApiCrudFactory.getConvertor(genericClass).convertElement(data);
    }
}
