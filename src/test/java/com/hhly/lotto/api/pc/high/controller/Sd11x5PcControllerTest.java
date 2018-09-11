package com.hhly.lotto.api.pc.high.controller;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hhly.lotto.api.pc.high.controller.x115.Sd11x5PcController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryIssueBaseBO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class Sd11x5PcControllerTest {

	@Autowired
	private Sd11x5PcController sd11x5PcController;
	
	@Test
	public void testInfo() {
		@SuppressWarnings("unchecked")
		ResultBO<LotteryIssueBaseBO> ret = (ResultBO<LotteryIssueBaseBO>)sd11x5PcController.info();
		ret.getData().getCurIssue();
		assertTrue(ret.isOK());
	}

	@Test
	public void testFindRecentDrawDetail() {
//		fail("Not yet implemented");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testFindOmit_OmitTypesNull() {
		HighLotteryVO vo = new HighLotteryVO();
		ResultBO<HighOmitDataBO> ret = sd11x5PcController.findOmit(vo);
		System.out.println(ret);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindOmit_subPlaysNull() {
		HighLotteryVO vo = new HighLotteryVO();
		vo.setOmitTypes(Arrays.asList(1));
		ResultBO<HighOmitDataBO> ret = sd11x5PcController.findOmit(vo);
		System.out.println(ret);
	}
	
	@Test
	public void testFindOmitHistory() {
		HighLotteryVO vo = new HighLotteryVO();
		vo.setOmitTypes(Arrays.asList(1));
		vo.setSubPlays(Arrays.asList(1,2));
		ResultBO<HighOmitDataBO> ret = sd11x5PcController.findOmit(vo);
		assertTrue(ret.isOK());
	}
	
	@Test
	public void testFindOmitRecent() {
		HighLotteryVO vo = new HighLotteryVO();
		vo.setQryFlag((byte)2);
		vo.setOmitTypes(Arrays.asList(1));
		vo.setSubPlays(Arrays.asList(1,2));
		ResultBO<HighOmitDataBO> ret = sd11x5PcController.findOmit(vo);
		assertTrue(ret.isOK());
	}

	@Test
	public void testFindLimit() {
//		fail("Not yet implemented");
	}
	
	@Test
	public void test() {
		for (int i = 1; i <= 58; i++)
			System.out.print(i + ",");
		String str = " pa, p2, p3, p4, p5, p6, p7, p8, p9, p10, pj, pq, pk, pt, pt1, pt2, pt3, pt4, px, pxa23, px234, px345, px456, px567, px678, px789, px8910, px10jq, pxjqk, pxqka, pd, pda, pd2, pd3, pd4, pd5, pd6, pd7, pd8, pd9, pd10, pdj, pdq, pdk, pb, pba, pb2, pb3, pb4, pb5, pb6, pb7, pb8, pb9, pb10, pbj, pbq, pbk";
		String[] arr = str.split(",");
		System.out.println(arr.length);
	}

	
}
