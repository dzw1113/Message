package com.icip.framework.message.bean;

public class SysSendControl extends SysSendControlKey {
	
	public SysSendControl(String companyId, String bmtType) {
		super(companyId, bmtType);
	}

	private String sendCycle;

	private Integer sendNumber;

	private String beginTime;

	private String endTime;

	private String status;

	private String companyId;

	private String bmtType;

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

	public String getSendCycle() {
		return sendCycle;
	}

	public void setSendCycle(String sendCycle) {
		this.sendCycle = sendCycle == null ? null : sendCycle.trim();
	}

	public Integer getSendNumber() {
		return sendNumber;
	}

	public void setSendNumber(Integer sendNumber) {
		this.sendNumber = sendNumber;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime == null ? null : beginTime.trim();
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime == null ? null : endTime.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}
}