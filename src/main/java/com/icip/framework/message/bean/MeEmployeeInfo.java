package com.icip.framework.message.bean;

public class MeEmployeeInfo extends MeEmployeeInfoKey {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}