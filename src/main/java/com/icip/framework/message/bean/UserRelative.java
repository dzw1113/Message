package com.icip.framework.message.bean;

public class UserRelative extends UserRelativeKey {
    private String mobile;

    private String email;

    private String qqNo;

    private String wechatNo;

    private String weiBoNo;

    private String auroraNo;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getQqNo() {
        return qqNo;
    }

    public void setQqNo(String qqNo) {
        this.qqNo = qqNo == null ? null : qqNo.trim();
    }

    public String getWechatNo() {
        return wechatNo;
    }

    public void setWechatNo(String wechatNo) {
        this.wechatNo = wechatNo == null ? null : wechatNo.trim();
    }

    public String getWeiBoNo() {
        return weiBoNo;
    }

    public void setWeiBoNo(String weiBoNo) {
        this.weiBoNo = weiBoNo == null ? null : weiBoNo.trim();
    }

    public String getAuroraNo() {
        return auroraNo;
    }

    public void setAuroraNo(String auroraNo) {
        this.auroraNo = auroraNo == null ? null : auroraNo.trim();
    }
}