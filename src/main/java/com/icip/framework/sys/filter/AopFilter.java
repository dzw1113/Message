package com.icip.framework.sys.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.icip.utils.ContextUtil;
import com.icip.utils.http.RequestUtil;

/**
 * 用于拦截请求以获取HttpSevletRequest对象，以供后续Aop类使用,以获取当前用户请求的IP等信息。 用于相同线程间共享Request对象 。
 * 
 * @author csx
 * 
 */
public class AopFilter extends OncePerRequestFilter  implements Filter {

	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		ContextUtil.clearAll();
		try {
			RequestUtil.setHttpServletRequest((HttpServletRequest) request);
			RequestUtil.setHttpServletResponse((HttpServletResponse) response);

			chain.doFilter(request, response);
		} finally {
			ContextUtil.clearAll();
		}
	}

}
