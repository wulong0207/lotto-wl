/**
 * 
 */
package com.hhly.lotto.base.exception.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Bruce
 *
 * @date 2016年12月13日
 *
 * @desc
 */
public class ExceptionHandler implements HandlerExceptionResolver {
	public static final Logger LOGGER = Logger.getLogger(ExceptionHandler.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		LOGGER.error("异常信息： ", ex);
		ModelAndView modelAndView = new ModelAndView("error/error_page");
	    return modelAndView;
	}

}
