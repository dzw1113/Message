package com.icip.framework.sys.exception;

/**
 * 业务异常
 * 
 * @author lenovo
 * 
 */
public class BusinessCheckException extends BaseException {

	private static final long serialVersionUID = -8366705779104000511L;

	public BusinessCheckException(String code) {
		super(code);
	}

	public BusinessCheckException(String msg, String viewName) {
		super(msg, viewName);
	}

	public BusinessCheckException(String code, String msg, String viewName) {
		super(code, msg, viewName);
	}
}
