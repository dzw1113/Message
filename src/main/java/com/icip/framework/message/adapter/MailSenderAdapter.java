package com.icip.framework.message.adapter;

import java.util.Map;

import com.icip.framework.message.BasePropertyID;
import com.icip.framework.message.ConfigService;
import com.icip.framework.message.bean.Mail;
import com.icip.framework.message.send.EmailSender;
import com.icip.framework.message.send.Sender;
import com.icip.utils.AppUtil;
/**
 * 邮件适配器
 * 
 * @author lenovo
 * 
 */
public class MailSenderAdapter implements Sender {

	@Override
	public void sender(String title, String content, String[] to,Map<String,String> params)
			throws Exception {
		EmailSender emailSender = (EmailSender) AppUtil.getBean("emailSender");
		ConfigService configService = (ConfigService) AppUtil
				.getBean("configService");
		// 从数据库取发件人，及发件人姓名
		String from = configService.getConfig(BasePropertyID.MAIL_SMTP_FROM);
		String fromName = configService
				.getConfig(BasePropertyID.MAIL_SMTP_FROMNAME);

		Mail mail = new Mail();
		mail.setFrom(from);
		mail.setFromName(fromName);
		mail.setTo(to);
		mail.setSubject(title);
		mail.setContent(content);

		emailSender.sendEmail(mail);

	}

	@Override
	public void sender(String id, String[] to, Map<String, String> params)
			throws Exception {
		
	}

}
