package com.hhly.lotto.api.common.controller.num.qxc.v1_0;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.api.common.controller.num.NumV10Controller;
import com.hhly.lottocore.remote.trend.service.IQxcTrendService;
import com.hhly.skeleton.activity.bo.ActivityBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryIssueBaseBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryTrendVO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;

/**
 * @desc    七星彩抽象类（定义pc和手机端共有信息）
 * @author  Tony Wang
 * @date    2017年7月6日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class QxcV10Controller extends NumV10Controller {

	/**
	 * 七星彩走势服务
	 */
	@Autowired
	protected IQxcTrendService qxcTrendService;

	/**
	 * @desc   
	 * @author Tony Wang
	 * @create 2017年7月6日
	 * @return 
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Object info(HttpServletRequest request) {
		logger.debug("七星彩:查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号");
		ResultBO<LotteryIssueBaseBO> result = lotteryIssueService.findLotteryIssueBase(getLottery());
		LotteryIssueBaseBO data = result.getData();
		ActivityBO activityBO = iActivityService.findActivityAddAwardInfo(getLottery(), getHeaderParam(request).getChannelId(), (int)getPlatform().getValue()).getData();
		data.setActivityBO(activityBO);
		return result;
	}

	/**
	 * @desc 七星彩：查询最近开奖详情列表
	 * @author Tony Wang
	 * @date 2017年7月6日
	 * @return 七星彩：查询最近开奖详情列表
	 */
	@RequestMapping(value = "/recent/drawissue", method = RequestMethod.GET)
	public Object findRecentDrawDetail() {
		logger.debug("七星彩：查询最近开奖详情列表");
		// 移动端和PC端都是查询10期
		return qxcTrendService.findRecentDrawIssue(new LotteryVO(getLottery(), 10));
	}

	/**
	 * @desc 七星彩：查询遗漏/冷热（统计期数内出现最多或最少的号码）/概率（出号概率=号码当前遗漏值/号码历史平均遗漏值）数据
	 * @author Tony Wang
	 * @date 2017年7月6日
	 * @return 七星彩：查询遗漏/冷热（统计期数内出现最多或最少的号码）/概率（出号概率=号码当前遗漏值/号码历史平均遗漏值）数据
	 */
	@RequestMapping(value = "/omit", method = RequestMethod.GET)
	public Object findOmitChanceColdHot(LotteryVO param) throws Exception {
		logger.debug("七星彩：查询遗漏/冷热/概率数据");
		// 现只支持查询普通玩法的遗漏数据
		param.setLotteryCode(getLottery());
		ResultBO<TrendBaseBO> ret = trendService.findOmitChanceColdHot(param);
		ret.getData().setFlag(param.getQryFlag());
		return ret;
	}
	
	/**
	 * 基础走势
	 * @desc 
	 * @create 2017年11月15日
	 * @param param
	 * @return List<QxcTrendBO>
	 * @throws Exception 
	 */
	@RequestMapping("baseTrend")
	public ResultBO<List<TrendBaseBO>> getBaseTrend(LotteryTrendVO param) throws Exception{
		logger.debug("七星彩：基本走势");
		Integer lotteryCode = param.getLotteryCode();
		Assert.paramNotNull(lotteryCode);
		Assert.paramLegal(getLottery().equals(lotteryCode), "lotteryCode");
		Assert.paramNotNull(param.getStartIssue(),"开始彩期");
		Assert.paramNotNull(param.getEndIssue(),"结束彩期");
		return qxcTrendService.findBaseTrend(param);
	}
	
	/**
	 * 大小走势
	 * @desc 
	 * @create 2017年11月15日
	 * @param param
	 * @return List<QxcTrendBO>
	 * @throws Exception 
	 */
	@RequestMapping("bsTrend")
	public ResultBO<List<TrendBaseBO>> getBSTrend(LotteryTrendVO param) throws Exception{
		logger.debug("七星彩：大小走势");
		Integer lotteryCode = param.getLotteryCode();
		Assert.paramNotNull(lotteryCode);
		Assert.paramLegal(getLottery().equals(lotteryCode), "lotteryCode");
		Assert.paramNotNull(param.getStartIssue(),"开始彩期");
		Assert.paramNotNull(param.getEndIssue(),"结束彩期");
		return qxcTrendService.findOETrend(param);
	}
	
	/**
	 * 奇偶走势
	 * @desc 
	 * @create 2017年11月15日
	 * @param param
	 * @return List<QxcTrendBO>
	 * @throws Exception 
	 */
	@RequestMapping("oeTrend")
	public ResultBO<List<TrendBaseBO>> getOETrend(LotteryTrendVO param) throws Exception{
		logger.debug("七星彩：奇偶走势");
		Integer lotteryCode = param.getLotteryCode();
		Assert.paramNotNull(lotteryCode);
		Assert.paramLegal(getLottery().equals(lotteryCode), "lotteryCode");
		Assert.paramNotNull(param.getStartIssue(),"开始彩期");
		Assert.paramNotNull(param.getEndIssue(),"结束彩期");
		return	qxcTrendService.findOETrend(param);
	}
	

	@Override
	protected Integer getLottery() {
		return Lottery.QXC.getName();
	}

	@Override
	protected String getLotName() {
		return  Lottery.QXC.getDesc();
	}
	
	
	/***************************开奖信息*******************************/
	
	/**
	 * 
	 * @desc 用户开奖信息的遗漏，冷/热
	 * @create 2018年1月5日
	 * @param param
	 * @return
	 * @throws Exception Object
	 */
	@Override
	public Object findDrawColdHotOmit(LotteryVO param) throws Exception {
		logger.debug("开奖信息{}接口：{}:查询冷热数据",getPlatform().getDesc(),getLotName());
		param.setLotteryCode(getLottery());
		param.setQryCount(Constants.NUM_100);
		return qxcTrendService.findDrawColdHotOmit(param);
	}
	
}
