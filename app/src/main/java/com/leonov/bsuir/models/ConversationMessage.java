package com.leonov.bsuir.models;

import java.security.Timestamp;
import java.sql.Time;

public class ConversationMessage implements ModelInterface {
    private Long id;
    private String message;
    private Byte isConsultantMessage;
    private Time dateTime;
    private String attachedFile;
    private Conversation conversation;
    private Time videoDuration;
    private String videoExternalLink;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Byte getIsConsultantMessage() {
        return isConsultantMessage;
    }

    public void setIsConsultantMessage(Byte isConsultantMessage) {
        this.isConsultantMessage = isConsultantMessage;
    }

    public Time getDateTime() {
        return dateTime;
    }

    public void setDateTime(Time dateTime) {
        this.dateTime = dateTime;
    }

    public String getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(String attachedFile) {
        this.attachedFile = attachedFile;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public Time getVideoDuration() {
        return videoDuration;
    }

    public void setVideoDuration(Time videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getVideoExternalLink() {
        return videoExternalLink;
    }

    public void setVideoExternalLink(String videoExternalLink) {
        this.videoExternalLink = videoExternalLink;
    }
}
