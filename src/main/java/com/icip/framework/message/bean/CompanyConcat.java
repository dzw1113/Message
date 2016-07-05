package com.icip.framework.message.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CompanyConcat implements Serializable{

	private static final long serialVersionUID = 1L;

	private String companyID;
	
	private String bmtCode;
	
	private Map<String,Object> params;
	
	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public List<String> getMailList() {
		return mailList;
	}

	public void setMailList(List<String> mailList) {
		this.mailList = mailList;
	}

	public List<String> getSmsList() {
		return smsList;
	}

	public void setSmsList(List<String> smsList) {
		this.smsList = smsList;
	}

	public List<String> getPushList() {
		return pushList;
	}

	public void setPushList(List<String> pushList) {
		this.pushList = pushList;
	}

	public List<String> getWxList() {
		return wxList;
	}

	public void setWxList(List<String> wxList) {
		this.wxList = wxList;
	}

	public String getBmtCode() {
		return bmtCode;
	}

	public void setBmtCode(String bmtCode) {
		this.bmtCode = bmtCode;
	}

	public Map<String,Object> getParams() {
		return params;
	}

	public void setParams(Map<String,Object> params) {
		this.params = params;
	}

	private List<String> mailList;
	private List<String> smsList;
	private List<String> pushList;
	private List<String> wxList;

}
