package com.leonov.bsuir.models;

public class ConsultantGroupUser implements ModelInterface {
    private Long id;
    private Byte status;
    private Integer videoTarif;
    private Integer conversationTarif;
    private User user;
    private ConsultantGroup consultantGroup;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ConsultantGroup getConsultantGroup() {
        return consultantGroup;
    }

    public void setConsultantGroup(ConsultantGroup consultantGroup) {
        this.consultantGroup = consultantGroup;
    }
}
