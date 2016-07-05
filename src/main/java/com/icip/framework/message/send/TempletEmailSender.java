package com.icip.framework.message.send;

import java.util.Map;

import com.icip.framework.message.bean.Mail;

/**
 * 邮件发送器
 * 
 */
public interface TempletEmailSender {

	/**
	 * @param from
	 *            发件人邮箱
	 * @param to
	 *            收件人邮箱
	 * @param subject
	 *            邮件主题
	 * @param templet
	 *            模板
	 * @param paramMap
	 *            模板参数信息
	 * @throws Exception
	 */
	public void sendEmailByTemplet(String from, String to, String subject,
			String templet, Map<String, Object> paramMap) throws Exception;

	/**
	 * @param from
	 *            发件人邮箱
	 * @param to
	 *            收件人邮箱(多个)
	 * @param subject
	 *            邮件主题
	 * @param templet
	 *            模板
	 * @param paramMap
	 *            模板参数信息
	 * @throws Exception
	 */
	public void sendEmailByTemplet(String from, String[] to, String subject,
			String templet, Map<String, Object> paramMap) throws Exception;

	/**
	 * @param mail
	 *            邮件对象
	 * @param templet
	 *            模板
	 * @param paramMap
	 *            模板参数信息
	 * @throws Exception
	 */
	public void sendEmailByTemplet(Mail mail, String templet,
			Map<String, Object> paramMap) throws Exception;

}
