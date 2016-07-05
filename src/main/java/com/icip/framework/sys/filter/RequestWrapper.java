package com.icip.framework.sys.filter;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 针对get解码用到的，后续加入防止XSS攻击
 * @author lenovo
 *
 */
public class RequestWrapper extends HttpServletRequestWrapper {

	public RequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getParameter(String name) {
		return decode(super.getParameter(name));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String[]> getParameterMap() {
		return super.getParameterMap();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enumeration<String> getParameterNames() {
		return super.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String name) {
		return super.getParameterValues(name);// TODO 未实现
	}

	// 对get方式提交的数据进行解码
	private String decode(String str) {
		if (null == str)
			return null;

		if ("get".equalsIgnoreCase(super.getMethod())) {
			try {
				return URLDecoder.decode(str, "utf-8");
			} catch (UnsupportedEncodingException e) {
				return str;
			}
		}
		return str;
	}
}
