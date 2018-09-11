package com.hhly.lotto.api.pc.order.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hhly.lotto.LottoSpringBootApplication;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.order.vo.OrderDetailVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderInfoVO;
import com.hhly.skeleton.lotto.base.recommend.vo.RcmdDetailVO;
import com.hhly.skeleton.lotto.base.recommend.vo.RcmdInfoVO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LottoSpringBootApplication.class)
@WebAppConfiguration
public class OrderControllerTest {

	@Autowired
	private OrderController orderController;
	
	private String token;
	
	private OrderInfoVO vo;
	
	 @Autowired
	 private StringRedisTemplate strRedisTemplate;
	
	@Before
	public void setUp() {
		Set<String> tokens = strRedisTemplate.keys("c_core_member_info_token_*");
		token = tokens.iterator().next().replace("c_core_member_info_token_", "");
		vo = new OrderInfoVO();
		vo.setBuyType((short)1);
		vo.setChannelId("Android");
		vo.setIsDltAdd((short)0);
		vo.setLotteryIssue("17061524");
		vo.setPlatform((short)3);
		vo.setTabType(0);
		vo.setToken(token);
		vo.setLotteryCode(215);
		vo.setMultipleNum(1);
		vo.setOrderAmount(2D);
	}
	
	@Test
	public void testAddOrder_Sd11x5_R2_Single() {
		List<OrderDetailVO> orderDetailList = new ArrayList<>();
		OrderDetailVO orderDetailVO = new OrderDetailVO();
		orderDetailVO.setPlanContent("01|01,02|03");
		orderDetailVO.setAmount(2D);
		orderDetailVO.setBuyNumber(1);
		orderDetailVO.setCodeWay(1);
		orderDetailVO.setContentType(2);
		orderDetailVO.setLotteryChildCode(21513);
		orderDetailVO.setMultiple(1);
		orderDetailList.add(orderDetailVO);
		vo.setOrderDetailList(orderDetailList);
		ResultBO<?> ret = orderController.addOrder(vo);
		System.out.println(ret);
//		Assert.assertTrue(ret.getSuccess()==1);
	}
	
	@Test
	public void addRcmdOrder() {
		List<RcmdDetailVO> orderDetailList = new ArrayList<>();
		RcmdInfoVO rvo = new RcmdInfoVO();
		RcmdDetailVO detail = new RcmdDetailVO();
		rvo.setUserId(14);
		rvo.setLotteryChildCode(30001);
		rvo.setLotteryCode((short)300);
		rvo.setLotteryIssue("180725");
		rvo.setTypeId(0);
		rvo.setTitle("test 测试");
		rvo.setReason("必中奖");
		rvo.setLabel("竞足");
		rvo.setRcmdCode("RO0001");
		detail.setLotteryChildCode(30001);
		detail.setPlanContent("1710146021(3@2.01)|1710146022(3@2.40)^2_1^1");
		detail.setScreens("1710146021,1710146022");
		detail.setMinScreen("1710146021");
		detail.setMaxScreen("1710146022");
		detail.setPassWay((short)2);
		detail.setRcmdCode("RO0001");
		detail.setPlanAmount(2.00);
		detail.setPlanNumber(1);
		orderDetailList.add(detail);
		rvo.setRcmdDetailList(orderDetailList);
		rvo.setToken(token);
		rvo.setPassWay((short)2);
		rvo.setPlanAmount(2.00);
		ResultBO<?> ret = orderController.addRcmdOrder(rvo);
		System.out.println(ret.getMessage());
	}

	/*
	 * 追号
	 * {
"activityId": "123456",
"addAmount": "4",
"addCount": "1",
"addIssues": "2",
"addMultiples": "1",
"addType": "1",
"channelId": "android",
"isDltAdd": "0",
"issueCode": "17061312",
"lotteryCode": "215",
"multipleNum": "2",
"orderAddContentList": [{
	"amount": "2",
	"buyNumber": "1",
	"codeWay": "1",
	"contentType": "1",
	"lotteryChildCode": "21502",
	"multiple": "1",
	"planContent": "02,03"
	}],
"token": "06badcfde0dc4f0a8a15663d83c4a263",
	"stopCondition": "",
	"stopType": "3"
}
	 */
}
