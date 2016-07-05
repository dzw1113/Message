package com.icip.controller;

import java.util.List;
import java.util.Map;

public class SendMsg implements java.io.Serializable{

	private static final long serialVersionUID = -7826482402805696112L;
	
	//发送小区（多个）
	private List<String> cids;
	//产品
	private String productId;
	//客户号（推送用到）
	private List<String> custNos;
	//公司编号（多个）
	private List<String> pcids;
	//参数
	private Map<String,String> params;
	//手机号
	private List<String> mobileNos;
	//模板编号（多个）
	private List<String> templetIds;
	//模版编号
	private String templetId;
	//产品编号（多个）
	private List<String> productIds;
	
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public List<String> getMobileNos() {
		return mobileNos;
	}
	public void setMobileNos(List<String> mobileNos) {
		this.mobileNos = mobileNos;
	}
	public List<String> getCustNos() {
		return custNos;
	}
	public void setCustNos(List<String> custNos) {
		this.custNos = custNos;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public List<String> getCids() {
		return cids;
	}
	public void setCids(List<String> cids) {
		this.cids = cids;
	}
	public List<String> getTempletIds() {
		return templetIds;
	}
	public void setTempletIds(List<String> templetIds) {
		this.templetIds = templetIds;
	}
	public String getTempletId() {
		return templetId;
	}
	public void setTempletId(String templetId) {
		this.templetId = templetId;
	}
	public List<String> getProductIds() {
		return productIds;
	}
	public void setProductIds(List<String> productIds) {
		this.productIds = productIds;
	}
	public List<String> getPcids() {
		return pcids;
	}
	public void setPcids(List<String> pcids) {
		this.pcids = pcids;
	}
	
}
