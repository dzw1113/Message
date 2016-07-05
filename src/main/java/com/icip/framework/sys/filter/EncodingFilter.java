package com.icip.framework.sys.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * 编码过滤器
 * @author lenovo
 *
 */
public class EncodingFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws ServletException, IOException {
		String encoding = "UTF-8";
		req.setCharacterEncoding(encoding);
		resp.setCharacterEncoding(encoding);
		HttpServletRequest request;

		request = (HttpServletRequest) req;

		chain.doFilter(new RequestWrapper(request), resp);
	}

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
		// no thing
	}
}
