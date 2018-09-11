package com.hhly.lotto.api.pc.high.controller.kl10;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class Cqkl10PcControllerTest {

	@Autowired
	private Cqkl10PcController controller;
	
	@Test
	public void testFindOmit() {
		ResultBO<HighOmitDataBO> ret = controller.findOmit(new HighLotteryVO());
		System.out.println(ret);
	}

}
