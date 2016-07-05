package com.icip.framework.sys.exception;

/**
 * 异常基类
 * 
 * @author lenovo
 * 
 */
public class BaseException extends Exception {
	private static final long serialVersionUID = -8366705779104000511L;

	protected String code;

	protected String msg;

	protected String viewName;

	public BaseException() {
		super();
	};

	public BaseException(String code) {
		super();
		this.code = code;
	}

	public BaseException(String msg, String viewName) {
		super(msg);
		this.viewName = viewName;
		this.msg = msg;
	}

	public BaseException(String code, String msg, String viewName) {
		super(msg);
		this.code = code;
		this.msg = msg;
		this.viewName = viewName;
	}

	@Override
	public String getMessage() {
		return msg;
	}

	public String getCode() {
		return code;
	}

	public String getViewName() {
		return viewName;
	}

	@Override
	public String toString() {
		return msg;
	}

}
