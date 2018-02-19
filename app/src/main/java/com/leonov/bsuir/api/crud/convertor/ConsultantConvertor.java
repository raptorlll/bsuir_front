package com.leonov.bsuir.api.crud.convertor;

public class ConsultantConvertor<T>
        extends UserConvertor {
    @Override
    public String getUrl() {
        return "/user/consultant";
    }
}
