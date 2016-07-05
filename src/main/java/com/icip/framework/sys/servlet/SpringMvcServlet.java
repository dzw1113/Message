package com.icip.framework.sys.servlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.icip.utils.AppUtil;

/**
 * 注入ApplicationContext
 */
public class SpringMvcServlet extends DispatcherServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void destroy() {
		super.destroy();
	}


	@Override
	protected void onRefresh(ApplicationContext context) {
		initStrategies(context);
		// 把spring mvc容器缓存起来
		AppUtil.setContext(context);
	}

}
