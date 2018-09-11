package com.hhly.lotto.api.pc.num.controller.qlc;

import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.lotto.api.pc.num.controller.NumController;
import com.hhly.lottocore.remote.trend.service.IQlcTrendService;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * @desc 七乐彩抽象类（定义pc和手机端共有信息）
 * @author huangb
 * @date 2017年10月24日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class QlcController extends NumController {

	/**
	 * 远程服务：七乐彩遗漏走势服务
	 */
	@Autowired
	protected IQlcTrendService qlcTrendService;
	
	@Override
	protected Integer getLottery() {
		return Lottery.QLC.getName();
	}

	@Override
	protected String getLotName() {
		return Lottery.QLC.getDesc();
	}
}
