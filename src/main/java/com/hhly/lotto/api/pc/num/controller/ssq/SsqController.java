package com.hhly.lotto.api.pc.num.controller.ssq;

import com.hhly.lotto.api.pc.num.controller.NumController;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * @desc 双色球抽象类（定义pc和手机端共有信息）
 * @author huangb
 * @date 2017年11月10日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class SsqController extends NumController {

	@Override
	protected Integer getLottery() {
		return Lottery.SSQ.getName();
	}

	@Override
	protected String getLotName() {
		return Lottery.SSQ.getDesc();
	}
	
}
