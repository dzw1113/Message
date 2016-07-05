package com.icip.framework.message.adapter;

import java.util.Map;

import com.icip.framework.message.send.Sender;
import com.icip.framework.message.send.SmsSender;
import com.icip.utils.AppUtil;

/**
 * 短信适配器
 * 
 * @author lenovo
 * 
 */
public class SmsSenderAdapter implements Sender {

	@Override
	public void sender(String title, String content, String[] to,Map<String,String> params)
			throws Exception {
		String sign = ""+params.get("sign");
		SmsSender smsSender = (SmsSender) AppUtil.getBean(SmsSender.class);
		smsSender.sendSms(to, content,sign);
	}

	@Override
	public void sender(String bmtCode, String[] to, Map<String, String> params)
			throws Exception {
		// TODO Auto-generated method stub
		
	}



}
