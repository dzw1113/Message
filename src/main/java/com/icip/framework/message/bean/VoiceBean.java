package com.icip.framework.message.bean;

/**
* @Description: 阿里云信息发送实体
* @author  肖伟
* @date 2016年6月24日 下午4:00:21
* @update 2016年6月24日 下午4:00:21
 */
public class VoiceBean {
	public VoiceBean(){
		
	}
	public VoiceBean(String alyUrl,String alyAppKey,String alyAppSecret,String alyVoicePhone) {
		this.alyUrl=alyUrl;
		this.alyAppKey=alyAppKey;;
		this.alyAppSecret=alyAppSecret;
		this.alyVoicePhone=alyVoicePhone;
	}
	
	
	
	//语音发送URL
	private String alyUrl;
	//应用APP证书key
	private String alyAppKey;
	//应用APP证书Secret
	private String alyAppSecret;
	//应用语音呼叫号码
	private String alyVoicePhone;
	public String getAlyUrl() {
		return alyUrl;
	}
	public void setAlyUrl(String alyUrl) {
		this.alyUrl = alyUrl;
	}
	public String getAlyAppKey() {
		return alyAppKey;
	}
	public void setAlyAppKey(String alyAppKey) {
		this.alyAppKey = alyAppKey;
	}
	public String getAlyAppSecret() {
		return alyAppSecret;
	}
	public void setAlyAppSecret(String alyAppSecret) {
		this.alyAppSecret = alyAppSecret;
	}
	public String getAlyVoicePhone() {
		return alyVoicePhone;
	}
	public void setAlyVoicePhone(String alyVoicePhone) {
		this.alyVoicePhone = alyVoicePhone;
	}
	
	
}
