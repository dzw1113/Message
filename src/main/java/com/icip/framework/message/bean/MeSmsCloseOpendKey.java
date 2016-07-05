package com.icip.framework.message.bean;

public class MeSmsCloseOpendKey {
    private String pcid;

    private String cid;

    private String bmtCode;

    public String getPcid() {
        return pcid;
    }

    public void setPcid(String pcid) {
        this.pcid = pcid == null ? null : pcid.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public String getBmtCode() {
        return bmtCode;
    }

    public void setBmtCode(String bmtCode) {
        this.bmtCode = bmtCode == null ? null : bmtCode.trim();
    }
}