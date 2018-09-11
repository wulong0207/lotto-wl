
package com.hhly.lotto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hhly.lotto.base.exception.handler.ExceptionHandler;

/**
 * @desc
 * @author cheng chen
 * @date 2018年8月7日
 * @company 益彩网络科技公司
 * @version 1.0
 */

@Configuration
public class CommonConfig {

	@Bean
	public ExceptionHandler exceptionHandler() {
		return new ExceptionHandler();
	}

	/**
	 * @desc 自定义序列化
	 * @author huangb
	 * @date 2018年8月10日
	 * @return
	 */
	/*@Bean
	public ObjectMappingCustomer createObjectMapping() {
		return new ObjectMappingCustomer();
	}

	*//**
	 * @desc http消息转换器
	 * @author huangb
	 * @date 2018年8月10日
	 * @return
	 *//*
	@Bean
	public StringHttpMessageConverter genStringHttpMessageConverter() {
		StringHttpMessageConverter target = new StringHttpMessageConverter(Charset.forName("UTF-8"));
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.TEXT_PLAIN);
		supportedMediaTypes.add(MediaType.TEXT_HTML);
		target.setSupportedMediaTypes(supportedMediaTypes);
		return target;
	}

	*//**
	 * @desc http消息转换器
	 * @author huangb
	 * @date 2018年8月10日
	 * @return
	 *//*
	@Bean
	public MappingJackson2HttpMessageConverter genJsonHttpMessageConverter() {
		MappingJackson2HttpMessageConverter target = new MappingJackson2HttpMessageConverter();
		List<MediaType> supportedMediaTypes = new ArrayList<>();
		supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
		supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
		supportedMediaTypes.add(MediaType.MULTIPART_FORM_DATA);
		target.setSupportedMediaTypes(supportedMediaTypes);
		target.setObjectMapper(createObjectMapping());
		return target;
	}

	@Bean
	public RequestMappingHandlerAdapter genHandlerAdapter() {
		RequestMappingHandlerAdapter target = new RequestMappingHandlerAdapter();
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(genStringHttpMessageConverter());
		messageConverters.add(genJsonHttpMessageConverter());
		target.setMessageConverters(messageConverters);
		return target;
	}*/
	
}
