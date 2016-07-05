package com.icip.framework.message.bean;


/**
 * 功能: 封装邮件对象
 * 
 */
public class Mail {

	/**
	 * 发件人
	 */
	private String from;

	/**
	 * 发件人(显示)
	 */
	private String fromName;
	/**
	 * 收件人
	 */
	private String[] to;
	/**
	 * 抄送
	 */
	private String[] cc;
	/**
	 * 秘密抄送
	 */
	private String[] bcc;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 邮件内容
	 */
	private String content;
	/**
	 * 附件
	 */
	private MailAttachment[] attachments;

	/**
	 * 是否以HTML格式发送
	 */
	boolean isHtmlFormat = true;
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isHtmlFormat() {
		return isHtmlFormat;
	}

	public void setHtmlFormat(boolean isHtmlFormat) {
		this.isHtmlFormat = isHtmlFormat;
	}

	public MailAttachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(MailAttachment[] attachments) {
		this.attachments = attachments;
	}

	

}