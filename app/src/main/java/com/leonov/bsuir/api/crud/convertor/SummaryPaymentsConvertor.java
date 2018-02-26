package com.leonov.bsuir.api.crud.convertor;

import com.google.gson.reflect.TypeToken;
import com.leonov.bsuir.models.Conversation;
import com.leonov.bsuir.models.SummaryPayments;

import java.lang.reflect.Type;
import java.util.Collection;

public class SummaryPaymentsConvertor<T>
        extends CrudConvertorAbstract<SummaryPayments> {
    @Override
    public String getUrl() {
        return "/summary_payments/reports";
    }

    @Override
    public Type getTypeCollection() {
        return new TypeToken<Collection<SummaryPayments>>() {}.getType();
    }

    @Override
    public Type getTypeElement() {
        return new TypeToken<SummaryPayments>() {}.getType();
    }
}
