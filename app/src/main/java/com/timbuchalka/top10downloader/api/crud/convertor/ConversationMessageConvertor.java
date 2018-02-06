package com.timbuchalka.top10downloader.api.crud.convertor;

import com.google.gson.reflect.TypeToken;
import com.timbuchalka.top10downloader.models.ConsultantGroup;
import com.timbuchalka.top10downloader.models.ConversationMessage;

import java.lang.reflect.Type;
import java.util.Collection;

public class ConversationMessageConvertor<T>
        extends CrudConvertorAbstract<ConversationMessage> {
    @Override
    public String getUrl() {
        return "/conversation_message";
    }

    @Override
    public Type getTypeCollection() {
        return new TypeToken<Collection<ConversationMessage>>() {}.getType();
    }

    @Override
    public Type getTypeElement() {
        return new TypeToken<ConversationMessage>() {}.getType();
    }
}
