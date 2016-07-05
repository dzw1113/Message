package com.icip.framework.message.adapter;

import java.util.Map;

import com.icip.framework.message.send.Sender;
import com.icip.framework.message.send.WxSender;
import com.icip.utils.AppUtil;

/**
 * 短信适配器
 * 
 * @author lenovo
 * 
 */
public class WxSenderAdapter implements Sender {

	@Override
	public void sender(String title, String content, String[] to,Map<String,String> params)
			throws Exception {
		WxSender smsSender = (WxSender) AppUtil.getBean(WxSender.class);
		smsSender.sendWxMessage(null, to, title, content);
	}

	@Override
	public void sender(String id, String[] to, Map<String, String> params)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
