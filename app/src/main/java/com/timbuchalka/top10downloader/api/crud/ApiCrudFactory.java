package com.timbuchalka.top10downloader.api.crud;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.crud.convertor.CrudConvertorInterface;
import com.timbuchalka.top10downloader.api.crud.convertor.CustomerInformationConvertor;
import com.timbuchalka.top10downloader.models.CustomerInformation;
import com.timbuchalka.top10downloader.models.ModelInterface;

import java.lang.reflect.Type;
import java.util.Collection;

public class ApiCrudFactory {
    ApiCrudFactory(){

    }

    public static <T extends ModelInterface> CrudConvertorInterface<T> getConvertor(Class genericClass){
        if(CustomerInformation.class == genericClass) {
            return new CustomerInformationConvertor();
        }

        return null;
    }

    public static String getUrl(Class genericClass){
        return ApiCrudFactory.getConvertor(genericClass).getUrl();
    }

    public static <T extends ModelInterface> Collection<T> convertCollection(Class<T> genericClass, String data) {
        return (Collection<T>) ApiCrudFactory.getConvertor(genericClass).convertCollection(data);
    }

    public static <T extends ModelInterface> T convertElement(Class<T> genericClass, String data) {
        return (T) ApiCrudFactory.getConvertor(genericClass).convertElement(data);
    }
    public static <T extends ModelInterface> String convertElement(Class<T> genericClass, T data) {
        return (String) ApiCrudFactory.getConvertor(genericClass).convertElement(data);
    }
}


/**package com.timbuchalka.top10downloader.api.crud;

 import com.google.gson.Gson;
 import com.google.gson.reflect.TypeToken;
 import com.timbuchalka.top10downloader.api.crud.convertor.CrudConvertorInterface;
 import com.timbuchalka.top10downloader.api.crud.convertor.CustomerInformationConvertor;
 import com.timbuchalka.top10downloader.models.CustomerInformation;
 import com.timbuchalka.top10downloader.models.ModelInterface;

 import java.lang.reflect.Type;
 import java.util.Collection;

 public class ApiCrudFactory<T extends ModelInterface> {
 ApiCrudFactory(){

 }

 public CrudConvertorInterface<T> getConvertor(Class genericClass){
 if(CustomerInformation.class == genericClass) {
 return new CustomerInformationConvertor();
 }

 return null;
 }

 public String getUrl(Class genericClass){
 return getConvertor(genericClass).getUrl();
 }

 public Collection<T> convertCollection(Class<T> genericClass, String data) {
 return getConvertor(genericClass).convertCollection(data);
 }

 public T convertElement(Class<T> genericClass, String data) {
 return getConvertor(genericClass).convertElement(data);
 }
 public String convertElement(Class<T> genericClass, T data) {
 return getConvertor(genericClass).convertElement(data);
 }
 }
*/
