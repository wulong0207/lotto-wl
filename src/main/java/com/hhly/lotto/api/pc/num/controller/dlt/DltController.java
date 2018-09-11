package com.hhly.lotto.api.pc.num.controller.dlt;

import com.hhly.lotto.api.pc.num.controller.NumController;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * @desc 大乐透抽象类（定义pc和手机端共有信息）
 * @author huangb
 * @date 2017年11月10日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class DltController extends NumController {

	@Override
	protected Integer getLottery() {
		return Lottery.DLT.getName();
	}

	@Override
	protected String getLotName() {
		return Lottery.DLT.getDesc();
	}
	
}
