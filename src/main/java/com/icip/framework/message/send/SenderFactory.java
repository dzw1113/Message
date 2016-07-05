package com.icip.framework.message.send;

import com.icip.framework.message.BasePropertyID;
import com.icip.framework.message.adapter.MailSenderAdapter;
import com.icip.framework.message.adapter.PushSenderAdapter;
import com.icip.framework.message.adapter.SmsSenderAdapter;
import com.icip.framework.message.adapter.VoiceSenderAdapter;
import com.icip.framework.message.adapter.WxSenderAdapter;

/**
 * 功能: 根据不同的消息类型，取得适应的消息发送器
 * 
 */
public class SenderFactory {

	public Sender getSender(String type) {
		Sender sender = null;
		if (BasePropertyID.TYPE_MAIL.equalsIgnoreCase(type)) {
			// 邮件
			sender = new MailSenderAdapter();
		} else if (BasePropertyID.TYPE_SMS.equalsIgnoreCase(type)) {
			// 短信
			sender = new SmsSenderAdapter();
		} else if (BasePropertyID.TYPE_PUSH.equalsIgnoreCase(type)) {
			// 推送
			sender = new PushSenderAdapter();
		} else if (BasePropertyID.TYPE_WX.equalsIgnoreCase(type)) {
			// 微信
			sender = new WxSenderAdapter();
		} else if (BasePropertyID.TYPE_VOICE.equalsIgnoreCase(type)) {
			// 语音
			sender = new VoiceSenderAdapter();
		}
		return sender;
	}
}
