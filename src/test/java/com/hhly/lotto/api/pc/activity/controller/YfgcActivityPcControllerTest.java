package com.hhly.lotto.api.pc.activity.controller;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.skeleton.activity.bo.YfgcActivityBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.common.LotteryEnum.ConIssue;
import com.hhly.skeleton.lotto.base.issue.bo.IssueBO;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryIssueBaseBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.order.bo.WinBO;
import com.hhly.skeleton.lotto.base.order.vo.OrderAddVO;

@SuppressWarnings("unchecked")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
@Transactional
public class YfgcActivityPcControllerTest extends GdYfgcActivityPcController {

	private RestTemplate rest;
	private String preUrl;
	private ObjectMapper mapper;
	private IssueBO currIssue;
	
	@Autowired
	private GdYfgcActivityPcController controller;
	@Autowired
    private ILotteryIssueService lotteryIssueService;
	
	@Before
	public void setUp() {
		preUrl = "http://localhost:8080/lotto/pc/activity/yfgc/";
		rest = new RestTemplate();
		mapper = new ObjectMapper();
		LotteryVO issueVO = new LotteryVO();
    	issueVO.setLotteryCode(LotteryEnum.Lottery.D11X5.getName());
    	issueVO.setCurrentIssue(ConIssue.CURRENT.getValue());
		currIssue = lotteryIssueService.findSingleFront(issueVO);
	}
	
	// 对接调试
	@Test
	public void testOrderYfgc2() throws Exception {
		String jsonInString = 	
				
				"{activityId: \"GPCHD20170801001\",    addAmount: 6,    addCount: 1,    addType: 1,    channelId: 2,    isDltAdd: 0,    issueCode: \"17082922\",    lotteryCode: 210,    multipleNum: 3,    orderAddContentList: [        0: {            amount: 2,            buyNumber: 1,            codeWay: 2,            contentType: 1,            lotteryChildCode: 21008,            multiple: 1,            planContent: \"01,05,06,07,08,09,10,11\",                    },            ],    orderAddIssueList: [        0: {            buyAmount: 2,            chaseBetNum: 1,            issueCode: \"17082922\",            multiple: 1        },        1: {            buyAmount: 2,            chaseBetNum: 1,            issueCode: 17082923,            multiple: 1        },        2: {            buyAmount: 2,            chaseBetNum: 1,            issueCode: 17082924,            multiple: 1        }    ],    platform: 1,    stopType: 1,    token: \"cb7fb00f4e5a43b9afd23e7bd9935716\"}";		
		
		OrderAddVO vo = mapper.readValue(jsonInString, OrderAddVO.class);
		ETL(vo);
		ResultBO<?> ret = controller.orderYfgc(vo);
		assertTrue(ret.getSuccess()==1);
		
	}
	
	@Test
	public void testInfo() {
		ResultBO<YfgcActivityBO> ret = (ResultBO<YfgcActivityBO>)rest.getForObject(preUrl+"info",ResultBO.class);
		assertTrue(ret.getData() != null);
		assertTrue(ret.getSuccess()==1);
	}

	@Test
	public void testLottoInfo() {
		ResultBO<LotteryIssueBaseBO> ret = (ResultBO<LotteryIssueBaseBO>)rest.getForObject(preUrl+"lottoinfo",ResultBO.class);
		assertTrue(ret.getData() != null);
		assertTrue(ret.getSuccess()==1);
	}

	@Test
	public void testWinInfo() {
		ResultBO<List<WinBO>> ret = (ResultBO<List<WinBO>>)rest.getForObject(preUrl+"win",ResultBO.class);
		assertTrue(ret.getData() != null);
		assertTrue(ret.getSuccess()==1);
	}

	@Test
	public void validate() throws Exception {
		String jsonInString = 	
				"{    \"addIssues\":3,    \"issueCode\":17081629,    \"token\":\"ed4fdf1abc0e492ca639e0da20041bf1\",    \"lotteryCode\":210,    \"platform\":2,    \"addCount\":1,    \"addMultiples\":1,    \"addAmount\":6,    \"addType\":1,    \"isDltAdd\":0,    \"channelId\":1,    \"multipleNum\":3,    \"stopType\":3,    \"orderAddContentList\":[    {        \"codeWay\": 1,        \"planContent\":\"01,02,03,04,05,06,07,08\",        \"lotteryChildCode\":21008,        \"buyNumber\":1,        \"multiple\":1,        \"amount\":2,        \"contentType\":1    }],    \"orderAddIssueList\":[{        \"issueCode\":17081629,        \"multiple\":1,        \"chaseBetNum\":1,        \"buyAmount\":2    },    {        \"issueCode\":17081630,        \"multiple\":1,        \"chaseBetNum\":1,        \"buyAmount\":2    },    {        \"issueCode\":17081631,        \"multiple\":1,        \"chaseBetNum\":1,        \"buyAmount\":2    }        ]    }";		
		OrderAddVO vo = mapper.readValue(jsonInString, OrderAddVO.class);
		ETL(vo);
		ResultBO<?> ret = controller.validate(vo);
		assertTrue(ret.getSuccess()==1);

	}
	
	@Test
	public void testOrderYfgc() throws Exception {
		String jsonInString = 	
				"{    \"addIssues\":3,    \"issueCode\":17081629,    \"token\":\"ed4fdf1abc0e492ca639e0da20041bf1\",    \"lotteryCode\":210,    \"platform\":2,    \"addCount\":1,    \"addMultiples\":1,    \"addAmount\":6,    \"addType\":1,    \"isDltAdd\":0,    \"channelId\":1,    \"multipleNum\":3,    \"stopType\":3,    \"orderAddContentList\":[    {        \"codeWay\": 1,        \"planContent\":\"01,02,03,04,05,06,07,08\",        \"lotteryChildCode\":21008,        \"buyNumber\":1,        \"multiple\":1,        \"amount\":2,        \"contentType\":1    }],    \"orderAddIssueList\":[{        \"issueCode\":17081629,        \"multiple\":1,        \"chaseBetNum\":1,        \"buyAmount\":2    },    {        \"issueCode\":17081630,        \"multiple\":1,        \"chaseBetNum\":1,        \"buyAmount\":2    },    {        \"issueCode\":17081631,        \"multiple\":1,        \"chaseBetNum\":1,        \"buyAmount\":2    }        ]    }";		
		OrderAddVO vo = mapper.readValue(jsonInString, OrderAddVO.class);
		ETL(vo);
		ResultBO<?> ret = controller.orderYfgc(vo);
		assertTrue(ret.getSuccess()==1);
		
	}

	private void ETL(OrderAddVO vo) {
		vo.setLotteryCode(currIssue.getLotteryCode());
		vo.setIssueCode(currIssue.getIssueCode());
		for(int i = 0; i < vo.getAddIssues(); i++) {
			vo.getOrderAddIssueList().get(i).setIssueCode((Integer.parseInt(currIssue.getIssueCode()) + i)+"");
		}
		vo.setToken("1234e4ccf4934a39b2c4918d30d3d93b");
	}
}
