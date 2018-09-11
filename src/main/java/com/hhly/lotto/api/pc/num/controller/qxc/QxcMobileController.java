package com.hhly.lotto.api.pc.num.controller.qxc;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
public abstract class QxcMobileController extends QxcController {

	private static Logger logger = Logger.getLogger(QxcMobileController.class);

	/**
	 * @desc   
	 * @author Tony Wang
	 * @create 2017年7月6日
	 * @return 
	 */
	@Override
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public Object info() {
		logger.debug("七星彩:查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号");
		// 1.基础信息
		LotteryIssueBaseBO lotBase = lotteryIssueService.findLotteryIssueBase(getLottery(), getPlatform().getValue()).getData();
		// 2.最近期遗漏信息
		LotteryVO lot = new LotteryVO(getLottery());
		lot.setQryFlag(1);
		TrendBaseBO trendDigit = null;
		try {
			trendDigit = trendService.findOmitChanceColdHot(lot).getData();
			trendDigit.setFlag(lot.getQryFlag());
		} catch (Exception e) {
			logger.error(e);
		}
		return ResultBO.ok(new NumBO(lotBase, trendDigit));
	}
}
