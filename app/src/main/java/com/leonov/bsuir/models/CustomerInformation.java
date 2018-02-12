package com.leonov.bsuir.models;

import java.util.Date;

public class CustomerInformation implements ModelInterface {
    private Long id;
    private Date birthData;
    private String additionalInformation;
    private Byte primary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getBirthData() {
        return birthData;
    }

    public void setBirthData(Date birthData) {
        this.birthData = birthData;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public Byte getPrimary() {
        return primary;
    }

    public void setPrimary(Byte primary) {
        this.primary = primary;
    }
}
