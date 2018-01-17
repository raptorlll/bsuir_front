package com.timbuchalka.top10downloader.models;

public class Role implements ModelInterface {
    private String value;
    private String description;

    public Role(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Role{" +
                "value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
