package com.icip.sys;

import java.text.MessageFormat;

public class AppException extends Exception {
	private static final long serialVersionUID = -3448940577256191837L;
	private String exceptionCode;
	private Throwable e;

	public AppException(String exceptionID) {
		this.exceptionCode = exceptionID;
	}

	public AppException(String exceptionID, String message) {
		super(message);
		this.exceptionCode = exceptionID;
	}

	public AppException(String exceptionID, Throwable cause) {
		super(cause);
		this.exceptionCode = exceptionID;
		this.e = cause;
	}

	public AppException(String exceptionID, String message, Throwable cause) {
		super(message, cause);
		this.exceptionCode = exceptionID;
		this.e = cause;
	}

	public AppException(String exceptionID, String message, Object[] param) {
		super(format(message, param));
		this.exceptionCode = exceptionID;
	}

	public AppException(String exceptionID, String message, Object[] param,
			Throwable cause) {
		super(format(message, param), cause);
		this.exceptionCode = exceptionID;
		this.e = cause;
	}

	public void setExceptionCode(String code) {
		this.exceptionCode = code;
	}

	public String getExceptionCode() {
		return this.exceptionCode;
	}

	public Throwable getThrowable() {
		return this.e;
	}

	static String format(String message, Object[] params) {
		if ((message != null) && (message.trim().length() > 0)
				&& (params != null) && (params.length > 0)) {
			return new MessageFormat(message).format(params);
		}

		return message;
	}
}