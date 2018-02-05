package com.timbuchalka.top10downloader.models;

import java.sql.Time;
import java.util.Date;

public class ConsultantInformation implements ModelInterface {
    private Long id;
    private String education;
    private String degree;
    private String licenseNumber;
    private String licenseFile;
    private Date licenseUntil;
    private Time availableFrom;
    private Time availableUntil;
    private ConsultantGroupUser consultantGroupUser;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseFile() {
        return licenseFile;
    }

    public void setLicenseFile(String licenseFile) {
        this.licenseFile = licenseFile;
    }

    public Date getLicenseUntil() {
        return licenseUntil;
    }

    public void setLicenseUntil(Date licenseUntil) {
        this.licenseUntil = licenseUntil;
    }

    public Time getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Time availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Time getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(Time availableUntil) {
        this.availableUntil = availableUntil;
    }

    public ConsultantGroupUser getConsultantGroupUser() {
        return consultantGroupUser;
    }

    public void setConsultantGroupUser(ConsultantGroupUser consultantGroupUser) {
        this.consultantGroupUser = consultantGroupUser;
    }
}
