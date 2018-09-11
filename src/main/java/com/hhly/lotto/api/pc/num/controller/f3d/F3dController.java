package com.hhly.lotto.api.pc.num.controller.f3d;

import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.lotto.api.pc.num.controller.NumController;
import com.hhly.lottocore.remote.trend.service.IF3dTrendService;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * @desc 福彩3d抽象类（定义pc和手机端共有信息）
 * @author huangb
 * @date 2017年11月10日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class F3dController extends NumController {

	/**
	 * 远程服务：福彩3D遗漏走势的服务接口
	 */
	@Autowired
	protected IF3dTrendService f3dTrendService;
	
	@Override
	protected Integer getLottery() {
		return Lottery.F3D.getName();
	}

	@Override
	protected String getLotName() {
		return Lottery.F3D.getDesc();
	}
	
}
