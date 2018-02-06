package com.timbuchalka.top10downloader.models;

import java.sql.Timestamp;
import java.com.timbuchalka.top10downloader.models.Conversation;

public class CustomerPayment implements ModelInterface {
    private Long id;
    private Timestamp dataTime;
    private Conversation conversation;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDataTime() {
        return dataTime;
    }

    public void setDataTime(Timestamp dataTime) {
        this.dataTime = dataTime;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
