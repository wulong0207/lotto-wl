package com.hhly.lotto.api.pc.num.controller.pl5;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.NUMConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryIssueBaseBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.num.bo.pl5.Pl5OmitOutBO;
import com.hhly.skeleton.lotto.mobile.num.bo.NumBO;

/**
 * @desc 排列五手机端抽象类（定义安卓、ios、h5端共有信息）
 * @author huangb
 * @date 2017年10月24日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class Pl5MobileController extends Pl5Controller {

	private static Logger logger = Logger.getLogger(Pl5MobileController.class);
	
	/**
	 * @desc 移动端接口：排列五：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号
	 * @author huangb
	 * @date 2017年3月6日
	 * @return 移动端接口：排列五：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号
	 * @throws Exception 
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public Object findPl5() throws Exception {
		logger.debug(getPlatform().getDesc() + "接口：" + getLotName() + "：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号");

		// 1.基础信息
		LotteryIssueBaseBO lotBase = lotteryIssueService.findLotteryIssueBase(getLottery(), getPlatform().getValue()).getData();
		// 2.最近期遗漏信息
		LotteryVO lot = new LotteryVO(getLottery());
		lot.setQryFlag(Constants.NUM_1);
		Pl5OmitOutBO trendDigit = pl5TrendService.findOmitChanceColdHotAll(lot).getData();

		Assert.isTrue(!(null == lotBase && null == trendDigit), "40003");

		return ResultBO.ok(new NumBO(lotBase, trendDigit));
	}
	
	/**
	 * @desc 移动端接口：排列五：查询最近开奖详情列表
	 * @author huangb
	 * @date 2017年3月6日
	 * @return 移动端接口：排列五：查询最近开奖详情列表
	 */
	@RequestMapping(value = "/recent/drawissue", method = RequestMethod.GET)
	@ResponseBody
	public Object findRecentDrawDetail() {
		logger.debug(getPlatform().getDesc() + "接口：" + getLotName() + "：查询最近开奖详情列表");
		return pl5TrendService.findRecentTrend(new LotteryVO(getLottery(), NUMConstants.NUM_10));
	}
	
	/**
	 * @desc 移动端接口：排列五：查询遗漏/冷热（统计期数内出现最多或最少的号码）/概率（出号概率=号码当前遗漏值/号码历史平均遗漏值）数据
	 * @author huangb
	 * @date 2017年3月6日
	 * @return 移动端接口：排列五：查询遗漏/冷热（统计期数内出现最多或最少的号码）/概率（出号概率=号码当前遗漏值/号码历史平均遗漏值）数据
	 * @throws Exception 
	 */
	@RequestMapping(value = "/omit", method = RequestMethod.GET)
	@ResponseBody
	public Object findOmitChanceColdHot(LotteryVO param) throws Exception {
		logger.debug(getPlatform().getDesc() + "接口：" + getLotName() + "：查询遗漏/冷热/概率数据");
		param.setLotteryCode(getLottery());
		return pl5TrendService.findOmitChanceColdHotAll(param);
	}
}
