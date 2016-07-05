package com.icip.framework.message.impl;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.icip.framework.common.exception.AppException;
import com.icip.framework.message.BasePropertyID;
import com.icip.framework.message.ConfigService;
import com.icip.framework.message.bean.Mail;
import com.icip.framework.message.bean.MailAttachment;
import com.icip.framework.message.send.EmailSender;

/**
 * JAVA MAIL实现
 */
public class EmailSenderImpl implements EmailSender, InitializingBean {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(EmailSenderImpl.class);

	private ConfigService configService;

	private JavaMailSenderImpl sender; // 实际的发送实现

	public JavaMailSenderImpl getSender() {
		return sender;
	}

	public void setSender(JavaMailSenderImpl sender) {
		this.sender = sender;
	}

	@Override
	public void sendEmail(String from, String to, String subject,
			String mailBody) throws Exception {
		sendEmail(from, new String[] { to }, subject, mailBody);
	}

	@Override
	public void sendEmail(String from, String[] to, String subject,
			String mailBody) throws Exception {
		// 构造MAIL对象
		Mail mail = new Mail();
		mail.setFrom(from);
		mail.setTo(to);
		mail.setSubject(subject);
		mail.setContent(mailBody);
		sendEmail(mail);
	}

	@Override
	public void sendEmail(Mail mail) throws Exception {
		// 检查必要参数
		if (mail == null) {
			logger.error("邮件不能为空！");
			throw new AppException("", "邮件不能为空！");
		}
		if (ArrayUtils.isEmpty(mail.getTo())) {
			logger.error("收件人不能为空！");
			throw new AppException("", "收件人不能为空!");
		}
		MimeMessageHelper helper = null;
		try {
			helper = new MimeMessageHelper(sender.createMimeMessage(), true,
					"UTF-8");
			// 发件人
			if (mail.getFrom() != null) {
				if (mail.getFromName() == null) {
					helper.setFrom(mail.getFrom());
				} else {
					helper.setFrom(mail.getFrom(), mail.getFromName());
				}

			}
			// 收件人
			helper.setTo(mail.getTo());

			// 抄送人
			if (mail.getCc() != null) {
				helper.setCc(mail.getCc());
			}

			// 密送人
			if (mail.getBcc() != null) {
				helper.setBcc(mail.getBcc());
			}

			// 邮件主题
			helper.setSubject(mail.getSubject());

			// 邮件内容
			helper.setText(mail.getContent(), mail.isHtmlFormat());

			// 附件
			if (mail.getAttachments() != null) {
				for (MailAttachment attachment : mail.getAttachments()) {
					helper.addAttachment(attachment.getFileName(), new File(
							attachment.getFilePath()));
				}
			}

			// 发送时间
			helper.setSentDate(new Date());
		} catch (Exception e) {
			logger.error("未知错误",e);
			throw new AppException("", "未知出错！");
		}
		// 发送
		try {
			sender.send(helper.getMimeMessage());
		} catch (MailException e) {
			logger.error("发送失败",e);
			throw new AppException("", "发送失败！");
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		sender = new JavaMailSenderImpl();

		// configService读出参数
		Properties pros = new Properties();

		pros.setProperty("mail.smtp.user",
				configService.getConfig(BasePropertyID.MAIL_SMTP_USER));
		pros.setProperty("mail.smtp.host",
				configService.getConfig(BasePropertyID.MAIL_SMTP_HOST));
		pros.setProperty("mail.smtp.port",
				configService.getConfig(BasePropertyID.MAIL_SMTP_PORT));
		pros.setProperty("mail.smtp.connectiontimeout", configService
				.getConfig(BasePropertyID.MAIL_SMTP_CONNECTIONTIMEOUT));
		pros.setProperty("mail.smtp.timeout",
				configService.getConfig(BasePropertyID.MAIL_SMTP_TIMEOUT));
		pros.setProperty("mail.smtp.from",
				configService.getConfig(BasePropertyID.MAIL_SMTP_FROM));
		pros.setProperty("mail.smtp.auth",
				configService.getConfig(BasePropertyID.MAIL_SMTP_AUTH));

		sender.setJavaMailProperties(pros);
		sender.setPassword(configService
				.getConfig(BasePropertyID.MAIL_SMTP_PASSWORD));

	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

}