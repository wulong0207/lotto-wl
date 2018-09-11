package com.hhly.lotto.api.common.controller.num.qxc.v1_0;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.skeleton.activity.bo.ActivityBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryIssueBaseBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;
import com.hhly.skeleton.lotto.mobile.num.bo.NumBO;

/**
 * @desc    七星彩手机端抽象类（定义安卓、ios、h5端共有信息）
 * @author  Tony Wang
 * @date    2017年7月6日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class QxcMobileV10Controller extends QxcV10Controller {


	/**
	 * @desc   
	 * @author Tony Wang
	 * @create 2017年7月6日
	 * @return 
	 */
	@Override
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Object info(HttpServletRequest request) {
		logger.debug("七星彩:查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号");
		// 1.基础信息
		LotteryIssueBaseBO lotBase = lotteryIssueService.findLotteryIssueBase(getLottery(), getPlatform().getValue()).getData();
		ActivityBO activityBO = iActivityService.findActivityAddAwardInfo(getLottery(), getHeaderParam(request).getChannelId(), (int)getPlatform().getValue()).getData();
		lotBase.setActivityBO(activityBO);
		// 2.最近期遗漏信息
		LotteryVO lot = new LotteryVO(getLottery());
		lot.setQryFlag(1);
		TrendBaseBO trendDigit = null;
		try {
			trendDigit = trendService.findOmitChanceColdHot(lot).getData();
			trendDigit.setFlag(lot.getQryFlag());
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return ResultBO.ok(new NumBO(lotBase, trendDigit));
	}
}
