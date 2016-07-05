package com.icip.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.icip.framework.common.model.SysErrorMsg;
import com.icip.framework.sys.exception.ExceptionConstants;

/**
 * 错误总处理 1、针对BaseException下的两个子异常，抛出时需指定code、message以及viewName（异步情况不需要） 1.1 *
 * BusinessCheckException 1.2 SystemUncheckException
 * 2、针对AppException(平台里的TCP请求)，则直接区code返回加密后的字符串,已封装 3、其他的Exception则走系统默认
 * 
 * @author lenovo
 * 
 */
@EnableWebMvc
@ControllerAdvice
public class CoreExceptionControllerAdvice {

	/**
	 * 默认信息key
	 */
	public final static String SYSTEM_SYSERRORMSG = "sysErrorMsg";

	/**
	 * 异常处理 该异常处理主要处理HTTP请求异常。 异步异常
	 * 
	 * @param ex
	 * @param reponse
	 * @return Object
	 */
	@ExceptionHandler(Exception.class)
	public Object handleException(Exception ex, HttpServletResponse reponse) {
		return getSysEMsg(ex, reponse);
	}

	/**
	 * 根据错误做出相应的处理
	 * 
	 * @param ex
	 * @param reponse
	 * @return ModelAndView
	 */
	private ModelAndView getSysEMsg(Exception ex, HttpServletResponse reponse) {
		ModelAndView mav = new ModelAndView();
		SysErrorMsg sem = new SysErrorMsg();
		sem.setTipId(ExceptionConstants.SYSTEM_ERROR);
		sem.setShowContent(ex.getMessage());
		mav.addObject(SYSTEM_SYSERRORMSG, sem);
		return mav;
	}

}
