package com.icip.framework.sys.exception;

/**
 * 系统异常
 * 
 * @author lenovo
 * 
 */
public class SystemUncheckException extends BaseException {

	private static final long serialVersionUID = 248598440500716871L;

	public SystemUncheckException(String code) {
		super(code);
	}

	public SystemUncheckException(String msg, String viewName) {
		super(msg, viewName);
	}

	public SystemUncheckException(String code, String msg, String viewName) {
		super(code, msg, viewName);
	}

}
