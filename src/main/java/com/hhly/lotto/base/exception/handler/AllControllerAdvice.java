package com.hhly.lotto.base.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.exception.ResultJsonException;
/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-12-15 下午3:19:12
 * @Desc 异常处理
 */
//@EnableWebMvc
@ControllerAdvice
public class AllControllerAdvice {
	
	public static  Logger logger = Logger.getLogger(AllControllerAdvice.class);
//	/**
//	 * 用于解析日期格式参数
//	 * @param binder
//	 */
//	@InitBinder    
//	public void initBinder(WebDataBinder binder) {    
//	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");    
//	        dateFormat.setLenient(false);    
//	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));    
//	}
//	/**
//	 * 异常处理
//	 * @param request
//	 * @param ex
//	 * @return
//	 */
//	@ExceptionHandler(IllegalArgumentException.class)
//	public @ResponseBody Object exp(HttpServletRequest request, IllegalArgumentException ex) {
//		handleException(ex);
//		return new ResultBO<>("20001",ex.getMessage());
//	}
//	
//	@ExceptionHandler(IllegalStateException.class)
//	public @ResponseBody Object exp(HttpServletRequest request, IllegalStateException ex) {
//		handleException(ex);
//		return new ResultBO<>("20002",ex.getMessage());
//	}
	/*@ExceptionHandler(ValidException.class)
	public @ResponseBody Object exp(HttpServletRequest request, ValidException ex) {
		handleException(ex);
		return ResultBO.err("20001");
	}*/
	
	/**
	 * @desc result结果异常处理
	 * @author huangb
	 * @date 2017年3月14日
	 * @param request
	 * @param ex
	 *            result验证异常
	 * @return result结果异常处理
	 */
	@ExceptionHandler(ResultJsonException.class)
	@ResponseBody
	public Object exp(HttpServletRequest request, ResultJsonException ex) {
		handleException(ex);
		return ex.getResult();
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseBody
	public Object exp(HttpServletRequest request, IllegalArgumentException ex) {
		handleException(ex);
		// 40004=违法的参数绑定
		return ResultBO.err("20001");
	}
	
//	@ExceptionHandler(ServiceRuntimeException.class)
//	public @ResponseBody Object exp(HttpServletRequest request, ServiceRuntimeException ex) {
//		handleException(ex);
//		return new ResultBO<>(ex.getCode(),ex.getMsg());
//	}

	/**
	 * @desc 异常类型拦截及处理
	 * @author huangb
	 * @date 2017年3月6日
	 * @param request
	 * @param ex
	 *            违法的参数绑定异常
	 * @return 异常类型拦截及处理
	 */
	@ExceptionHandler(BindException.class)
	public @ResponseBody Object exp(HttpServletRequest request, BindException ex) {
		handleException(ex);
		return ResultBO.err("20001");
	}

	/**
	 * @desc 异常类型拦截及处理
	 * @author huangb
	 * @date 2017年3月6日
	 * @param request
	 * @param ex
	 *            违法的参数绑定异常
	 * @return 异常类型拦截及处理
	 */
	@ExceptionHandler(HttpMessageConversionException.class)
	public @ResponseBody Object exp(HttpServletRequest request, HttpMessageConversionException ex) {
		handleException(ex);
		return ResultBO.err("40004");
	}
	
	/**
	 * @desc 异常类型拦截及处理
	 * @author huangb
	 * @date 2017年3月6日
	 * @param request
	 * @param ex
	 *            异常信息
	 * @return 异常类型拦截及处理
	 */
	@ExceptionHandler(Exception.class)
	public @ResponseBody Object exp(HttpServletRequest request, Exception ex) {
		handleException(ex);
		return ResultBO.err("10002");
	}
	
	private void handleException(Exception ex){
		logger.error(ex);
		ex.printStackTrace();
	}
}
