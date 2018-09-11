//package com.hhly.application.service.impl;
//
//import static org.junit.Assert.fail;
//
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.hhly.lottocore.lotto.remote.service.impl.mock.X115SerivceMock;
//import com.hhly.skeleton.lotto.hight.bo.X115IssueBO;
//import com.hhly.skeleton.lotto.hight.vo.X115IssueVO;
//
///**
// * 
// * @author wytong
// * @Version 1.0
// * @CreatDate 2016年11月23日 下午3:36:28
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="classpath:applicationContext.xml")
//public class X115ServiceImplTest {
//
//	@Autowired
//	private X115SerivceMock x115SerivceMock;
//	
//	@Test
//	public void testSave() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testFindRecentIssue() {
//		X115IssueVO x115VO = new X115IssueVO(20);
////		PageVO pageVO = new PageVO();
////		pageVO.setEndRow(11);
////		x115VO.setPageVO(pageVO);
//		List<X115IssueBO> expectedList = x115SerivceMock.findRecentIssue(x115VO);
//		Assert.assertNotNull(expectedList);
////		Assert.assertEquals(x115VO.getPageVO().getEndRow().intValue(), expectedList.size());
//	}
//
//}
