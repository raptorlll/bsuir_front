package com.leonov.bsuir.enums;

public enum RolesEnum {
    ADMIN("ADMIN_USER"),
    CONSULTANT("CONSULTANT"),
    CUSTOMER("CUSTOMER");

    private String value;

    RolesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
