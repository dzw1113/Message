package com.icip.sys;

public class SysErrorMsg {
	private String tipId;
	private String tipType;
	private String tipContent;
	private String showContent;
	private String lastUpdate;

	public String getTipId() {
		return this.tipId;
	}

	public void setTipId(String tipId) {
		this.tipId = ((tipId == null) ? null : tipId.trim());
	}

	public String getTipType() {
		return this.tipType;
	}

	public void setTipType(String tipType) {
		this.tipType = ((tipType == null) ? null : tipType.trim());
	}

	public String getTipContent() {
		return this.tipContent;
	}

	public void setTipContent(String tipContent) {
		this.tipContent = ((tipContent == null) ? null : tipContent.trim());
	}

	public String getShowContent() {
		return this.showContent;
	}

	public void setShowContent(String showContent) {
		this.showContent = ((showContent == null) ? null : showContent.trim());
	}

	public String getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = ((lastUpdate == null) ? null : lastUpdate.trim());
	}
}