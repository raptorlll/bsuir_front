package com.leonov.bsuir.models;

import java.util.Collection;

public class Conversation implements ModelInterface {
    private Long id;
    private Byte active;
    private int messagesCount;
    private ConsultantGroupUser consultantGroupUser;
    private CustomerInformation customerInformation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public double getSpentMoney() {
        double tarif = getTarif();
        return this.getMessagesCount() * tarif;
    }

    public double getTarif() {
        return this.getConsultantGroupUser().getConversationTarif() == null
                    ? this.getConsultantGroupUser().getConsultantGroup().getConversationTarif()
                    : this.getConsultantGroupUser().getConversationTarif();
    }

    public boolean canPost(Collection<CustomerPayment> data){
        return  needAtLeast(data) < 0;
    }

    public double needAtLeast(Collection<CustomerPayment> data){
        int paymentsTotal = 0;
        double tarif = getTarif();

        for (CustomerPayment customerPayment : data) {
            paymentsTotal += customerPayment.getAmount();
        }

        return  (getSpentMoney() + tarif) - paymentsTotal;
    }
}
