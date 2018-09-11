package com.hhly.lotto.api.pc.num.controller.pl3;

import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.lotto.api.pc.num.controller.NumController;
import com.hhly.lottocore.remote.trend.service.IPl3TrendService;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * @desc 排列三抽象类（定义pc和手机端共有信息）
 * @author huangb
 * @date 2017年11月10日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class Pl3Controller extends NumController {

	/**
	 * 远程服务：排列三遗漏走势服务
	 */
	@Autowired
	protected IPl3TrendService pl3TrendService;
	
	@Override
	protected Integer getLottery() {
		return Lottery.PL3.getName();
	}

	@Override
	protected String getLotName() {
		return Lottery.PL3.getDesc();
	}
	
}
