package com.leonov.bsuir.api.crud.convertor;

import com.google.gson.reflect.TypeToken;
import com.leonov.bsuir.models.User;

import java.lang.reflect.Type;
import java.util.Collection;

public class ClientConvertor<T>
        extends UserConvertor {
    @Override
    public String getUrl() {
        return "/user/client";
    }
}
