/**
 * @see      对外提供API启动类
 * @author   scott
 * @version  v1.0
 * @date      2018-03-06
 * @company   益彩网络科技
 */
package com.hhly.lotto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.hhly.skeleton.base.util.PropertyUtil;


@SpringBootApplication(scanBasePackages = { "com.hhly.*" }, exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@PropertySources(value = {@PropertySource(value = {"classpath:redis-config.properties"}), @PropertySource(value = {"classpath:hessian.properties"})})
@ImportResource("classpath:msg-converter.xml")
public class LottoSpringBootApplication {

    public static void main(String[] args) {
        {
        	if(args.length > 0){
        		String env = args[0];
        		PropertyUtil.env = env.split("=")[1];
        	}
        	SpringApplication.run(LottoSpringBootApplication.class, args);
        }

    }}
