package com.icip.framework.message.bean;

/**
 * 邮件附件类。
 * 
 */
public class MailAttachment {
	// 文件名
	protected String fileName;
	// 文件路径
	protected String filePath;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}