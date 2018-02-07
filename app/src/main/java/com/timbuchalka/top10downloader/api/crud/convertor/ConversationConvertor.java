package com.timbuchalka.top10downloader.api.crud.convertor;

import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.api.crud.convertor.CrudConvertorAbstract;
import com.timbuchalka.top10downloader.models.ConsultantGroup;

import com.timbuchalka.top10downloader.models.Conversation;
import java.lang.reflect.Type;
import java.util.Collection;

public class ConversationConvertor<T>
        extends CrudConvertorAbstract<Conversation> {
    @Override
    public String getUrl() {
        return "/conversation";
    }

    @Override
    public Type getTypeCollection() {
        return new TypeToken<Collection<Conversation>>() {}.getType();
    }

    @Override
    public Type getTypeElement() {
        return new TypeToken<Conversation>() {}.getType();
    }
}
