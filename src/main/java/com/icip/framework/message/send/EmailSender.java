package com.icip.framework.message.send;

import com.icip.framework.message.bean.Mail;

/**
 * 邮件发送器
 * 
 */
public interface EmailSender {

	/**
	 * 发送邮件
	 * 
	 * @param from
	 *            发件人
	 * @param to
	 *            收件人
	 * @param subject
	 *            邮件主题
	 * @param mailBody
	 *            邮件内容
	 * @throws Exception
	 *             参数校验失败或发送邮件失败时，抛出此异常
	 */
	void sendEmail(String from, String to, String subject, String mailBody)
			throws Exception;

	/**
	 * 发送邮件
	 * 
	 * @param from
	 *            发件人
	 * @param to
	 *            多个收件人
	 * @param subject
	 *            邮件主题
	 * @param mailBody
	 *            邮件内容
	 * @throws Exception
	 *             参数校验失败或发送邮件失败时，抛出此异常
	 */
	void sendEmail(String from, String[] to, String subject, String mailBody)
			throws Exception;

	/**
	 * 发送邮件
	 * 
	 * @param mail
	 *            邮件
	 * @throws Exception
	 *             参数校验失败或发送邮件失败时，抛出此异常
	 */
	void sendEmail(Mail mail) throws Exception;

}
