package com.hhly.lotto.api.pc.high.controller.ssc;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryIssueBaseBO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.high.bo.SscOmitBO;

@SuppressWarnings("unchecked")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
@Transactional
public class CqsscPcControllerTest {

	@Autowired
	private CqsscPcController controller;
	
	private String url;
	private RestTemplate rest;
	
	@Before
	public void setUp() {
		url = "http://localhost:8080/lotto/pc/cqssc/";
		rest = new RestTemplate();
	}
	
	@Test
	public void testGetLottery() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetIssueCount() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOmit() {
		ResultBO<HighOmitDataBO> ret = (ResultBO<HighOmitDataBO>)rest.getForObject(url+"omit?omitTypes=1,2&qryCount=2&qryFlag=2&subPlays=1",ResultBO.class);
		assertTrue(ret.getData() != null);
	}

	@Test
	public void testFindRecentDrawDetail() {
		ResultBO<List<SscOmitBO>> ret = (ResultBO<List<SscOmitBO>>)controller.findRecentDrawDetail();
		assertTrue(ret.getData() != null);
		for(SscOmitBO ssc : ret.getData()) {
			if(ssc.getIssue().equals("20170822032")) {
				System.out.println(ssc);
			}
		}
	}

	@Test
	public void testInfo() {
//		CqsscPcController controller = new CqsscPcController();
//	    MockMvc mockMvc = standaloneSetup(controller).build();
//	    Object ret = mockMvc.perform(get("/pc/cqssc/info"));
//	   System.out.println(ret);
		Map<String, String> urlVariables = new HashMap<>();
		urlVariables.put("id", "1");
		ResultBO<LotteryIssueBaseBO> ret = (ResultBO<LotteryIssueBaseBO>)rest.getForObject(url+"info",ResultBO.class);
		assertTrue(ret.getData() != null);
	}

	@Test
	public void testFindLimit() {
		fail("Not yet implemented");
	}

}
