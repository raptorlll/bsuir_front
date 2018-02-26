package com.leonov.bsuir.models;

import java.util.Collection;

public class Conversation implements ModelInterface {
    private Long id;
    private Byte active;
    private int messagesCount;
    private ConsultantGroupUser consultantGroupUser;
    private CustomerInformation customerInformation;
    private Collection<ConversationMessage> conversationMessages;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<ConversationMessage> getConversationMessages() {
        return conversationMessages;
    }

    public void setConversationMessages(Collection<ConversationMessage> conversationMessages) {
        this.conversationMessages = conversationMessages;
    }

    public int getMessagesCount() {
        return messagesCount;
    }

    public void setMessagesCount(int messagesCount) {
        this.messagesCount = messagesCount;
    }

    public Byte getActive() {
        return active;
    }

    public void setActive(Byte active) {
        this.active = active;
    }

    public ConsultantGroupUser getConsultantGroupUser() {
        return consultantGroupUser;
    }

    public void setConsultantGroupUser(ConsultantGroupUser consultantGroupUser) {
        this.consultantGroupUser = consultantGroupUser;
    }

    public CustomerInformation getCustomerInformation() {
        return customerInformation;
    }

    public void setCustomerInformation(CustomerInformation customerInformation) {
        this.customerInformation = customerInformation;
    }

    public double getVideoMoney() {
        double tarifVideo = getTarifVideo();
        double minutes = 0;

        for (ConversationMessage cm : conversationMessages){
            if(cm.getVideoDuration()!=null){
                minutes += cm.getVideoDuration().getMinutes() + cm.getVideoDuration().getSeconds() / 60;
            }
        }

        return Math.ceil(minutes * tarifVideo);
    }

    public double getSpentMoney() {
        double tarif = getTarif();

        return this.getMessagesCount() * tarif + getVideoMoney();
    }

    public double getTarif() {
        return this.getConsultantGroupUser().getConversationTarif() == null
                    ? this.getConsultantGroupUser().getConsultantGroup().getConversationTarif()
                    : this.getConsultantGroupUser().getConversationTarif();
    }
    public double getTarifVideo() {
        return this.getConsultantGroupUser().getVideoTarif() == null
                    ? this.getConsultantGroupUser().getConsultantGroup().getVideoTarif()
                    : this.getConsultantGroupUser().getVideoTarif();
    }

    public boolean canPost(Collection<CustomerPayment> data){
        return  needAtLeast(data) < 0;
    }

    public double needAtLeast(Collection<CustomerPayment> data){
        int paymentsTotal = 0;
        double tarif = getTarif();
        double tarifVideo = getTarifVideo();

        for (CustomerPayment customerPayment : data) {
            paymentsTotal += customerPayment.getAmount();
        }

        return  (getSpentMoney() + Math.max(tarif, tarifVideo)) - paymentsTotal;
    }
}
