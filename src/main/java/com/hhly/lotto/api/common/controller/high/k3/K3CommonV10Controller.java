package com.hhly.lotto.api.common.controller.high.k3;

import org.springframework.util.Assert;

import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;

public class K3CommonV10Controller extends K3V10Controller{

	@Override
	protected int getIssueCount() {
		return 20;
	}

	@Override
	protected Integer getLottery() {
		return null;
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
		Assert.notNull(param);
		Integer lotteryCode = param.getLotteryCode();
		Assert.notNull(lotteryCode,"彩种编码不能为空");
		logger.debug("开奖信息{}接口：{}:查询遗漏/冷热数据",getPlatform().getDesc(),lotteryCode);
		param.setLotteryCode(lotteryCode);
		param.setQryCount(Constants.NUM_100);
		return k3TrendService.findDrawColdHotOmit(param);
	}
}
