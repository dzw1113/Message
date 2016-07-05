package com.icip.framework.sys.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

import com.icip.utils.AppUtil;

/**
 * 重写ContextLoaderListener，获取ServletContext
 * 
 * @author lenovo
 * 
 */
public class StartupListner extends ContextLoaderListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		AppUtil.init(event.getServletContext());
	}

}
