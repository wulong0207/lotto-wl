package com.hhly.lotto.api.common.controller.num.pl3.v1_0;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.skeleton.activity.bo.ActivityBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.NUMConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryIssueBaseBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.num.bo.pl3.Pl3OmitOutBO;
import com.hhly.skeleton.lotto.mobile.num.bo.NumBO;

/**
 * @desc 排列三手机端抽象类（定义安卓、ios、h5端共有信息）
 * @author huangb
 * @date 2017年10月21日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class Pl3MobileV10Controller extends Pl3V10Controller {

	/**
	 * @desc 移动端接口：排列三：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号
	 * @author huangb
	 * @date 2017年3月6日
	 * @return 移动端接口：排列三：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号
	 * @throws Exception 
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Object findPl3(HttpServletRequest request) throws Exception {
		logger.debug("{}接口：{}：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号",getPlatform().getDesc(),getLotName());

		// 1.基础信息
		LotteryIssueBaseBO lotBase = lotteryIssueService.findLotteryIssueBase(getLottery(), getPlatform().getValue()).getData();
		ActivityBO activityBO = iActivityService.findActivityAddAwardInfo(getLottery(), getHeaderParam(request).getChannelId(), (int)getPlatform().getValue()).getData();
		lotBase.setActivityBO(activityBO);
		// 2.最近期遗漏信息
		LotteryVO lot = new LotteryVO(getLottery());
		lot.setQryFlag(Constants.NUM_1);
		List<Pl3OmitOutBO> trendDigits = pl3TrendService.findOmitChanceColdHotAll(lot).getData();

		Assert.isTrue(!(null == lotBase && null == trendDigits), "40003");

		return ResultBO.ok(new NumBO(lotBase, trendDigits));
	}
	
	/**
	 * @desc 移动端接口：排列三：查询最近开奖详情列表
	 * @author huangb
	 * @date 2017年3月6日
	 * @return 移动端接口：排列三：查询最近开奖详情列表
	 */
	@RequestMapping(value = "/recent/drawissue", method = RequestMethod.GET)
	public Object findRecentDrawDetail() {
		logger.debug("{}接口：{}：查询最近开奖详情列表",getPlatform().getDesc(),getLotName());
		return pl3TrendService.findRecentTrendSimple(new LotteryVO(getLottery(), NUMConstants.NUM_10));
	}
	
	/**
	 * @desc 移动端接口：排列三：查询遗漏/冷热（统计期数内出现最多或最少的号码）/概率（出号概率=号码当前遗漏值/号码历史平均遗漏值）数据
	 * @author huangb
	 * @date 2017年3月6日
	 * @return 移动端接口：排列三：查询遗漏/冷热（统计期数内出现最多或最少的号码）/概率（出号概率=号码当前遗漏值/号码历史平均遗漏值）数据
	 * @throws Exception 
	 */
	@RequestMapping(value = "/omit", method = RequestMethod.GET)
	public Object findOmitChanceColdHot(LotteryVO param) throws Exception {
		logger.debug("{}接口：{}：查询遗漏/冷热/概率数据",getPlatform().getDesc(),getLotName());
		param.setLotteryCode(getLottery());
		return pl3TrendService.findOmitChanceColdHotAll(param);
	}
	
	/**
	 * @desc 移动端接口：all彩种：查询走势投注列表
	 * @author huangb
	 * @date 2017年3月6日
	 * @param param(qryCount期数)
	 * @return 移动端接口：all彩种：查询走势投注列表
	 */
	@RequestMapping(value = "/trend/betting", method = RequestMethod.GET)
	public Object findTrendBettingInfo(LotteryVO param) {
		logger.debug("{}接口：{}：查询走势投注列表",getPlatform().getDesc(),getLotName());
		param.setLotteryCode(getLottery());
		return pl3TrendService.findRecentTrend(param);
	}
}
