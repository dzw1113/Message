package com.icip.framework.message.send;

/**
 * 功能: 短信发送服务
 * 
 */
public interface SmsSender {

	public void sendSms(String mobilePhone, String message,String sign) throws Exception;

	public void sendSms(String[] mobilePhones, String message,String sign) throws Exception;

}
