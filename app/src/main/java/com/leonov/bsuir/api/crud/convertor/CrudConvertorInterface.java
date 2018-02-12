package com.leonov.bsuir.api.crud.convertor;

import com.leonov.bsuir.models.ModelInterface;

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
