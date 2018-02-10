package com.timbuchalka.top10downloader.models;

import java.sql.Date;
import java.sql.Timestamp;
import com.timbuchalka.top10downloader.models.Conversation;

public class CustomerPayment implements ModelInterface {
    private Long id;
    private Date dataTime;
    private Conversation conversation;
    private Long amount;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
