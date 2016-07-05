package com.icip.framework.message.bean;

public class MeSmsCountInfo extends MeSmsCountInfoKey {
    private String useCount;

    public String getUseCount() {
        return useCount;
    }

    public void setUseCount(String useCount) {
        this.useCount = useCount == null ? null : useCount.trim();
    }
}