package com.icip.framework.message.bean;

public class SysSendLog extends SysSendLogKey {
    private String bmtCode;

    private Integer msgCount;

    private String updateTime;
    
    private String systemId;

    public String getBmtCode() {
        return bmtCode;
    }

    public void setBmtCode(String bmtCode) {
        this.bmtCode = bmtCode;
    }

    public Integer getMsgCount() {
        return msgCount;
    }

    public void setMsgCount(Integer msgCount) {
        this.msgCount = msgCount;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

}