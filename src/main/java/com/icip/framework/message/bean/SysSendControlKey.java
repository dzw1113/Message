package com.icip.framework.message.bean;

public class SysSendControlKey {
	
    private String companyId;

    private String bmtType;

    public SysSendControlKey(String companyId,String bmtType){
		this.companyId = companyId;
		this.bmtType = bmtType;
	}
    
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getBmtType() {
        return bmtType;
    }

    public void setBmtType(String bmtType) {
        this.bmtType = bmtType == null ? null : bmtType.trim();
    }
}