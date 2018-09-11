package com.hhly.redis.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @author Bruce
 *
 * @date 2016年5月27日
 *
 * @desc 
 */
@ContextConfiguration(locations={"classpath:applicationContext.xml"}) 
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest extends AbstractJUnit4SpringContextTests {
	@Test
	public void test(){
		
	}
}