package com.icip.framework.message.bean;

public class MeSmsCanuse {
    private String pcid;

    private String canuseCount;

    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid == null ? null : pcid.trim();
    }

    public String getCanuseCount() {
        return canuseCount;
    }

    public void setCanuseCount(String canuseCount) {
        this.canuseCount = canuseCount == null ? null : canuseCount.trim();
    }
}