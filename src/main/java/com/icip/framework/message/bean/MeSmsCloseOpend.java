package com.icip.framework.message.bean;

public class MeSmsCloseOpend extends MeSmsCloseOpendKey {
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}