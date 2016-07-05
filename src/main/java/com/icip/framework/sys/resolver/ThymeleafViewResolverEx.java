package com.icip.framework.sys.resolver;

import java.io.File;
import java.util.Locale;

import org.springframework.web.servlet.View;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

/**
 * 视图解析器没找到HTML找JSP
 * 
 * @author lenovo
 * 
 */
public class ThymeleafViewResolverEx extends ThymeleafViewResolver {

	@Override
	public View resolveViewName(String viewName, Locale locale)
			throws Exception {

		ServletContextTemplateResolver servletContextTemplateResolver = (ServletContextTemplateResolver) this
				.getWebApplicationContext().getBean("templateResolver");
		servletContextTemplateResolver.initialize();
		String prefix = servletContextTemplateResolver.getPrefix().substring(1);
		String suffix = servletContextTemplateResolver.getSuffix();

		File file = new File(this.getServletContext().getRealPath("/") + prefix
				+ viewName + suffix);
		if (!file.exists()) {
			return null;
		}
		return super.resolveViewName(viewName, locale);
	}
}
