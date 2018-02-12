package com.leonov.bsuir.models;

public class ConsultantGroup implements ModelInterface {
    private Long id;
    private String name;
    private String description;
    private Integer videoTarif;
    private Integer conversationTarif;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVideoTarif() {
        return videoTarif;
    }

    public void setVideoTarif(Integer videoTarif) {
        this.videoTarif = videoTarif;
    }

    public Integer getConversationTarif() {
        return conversationTarif;
    }

    public void setConversationTarif(Integer conversationTarif) {
        this.conversationTarif = conversationTarif;
    }
}
