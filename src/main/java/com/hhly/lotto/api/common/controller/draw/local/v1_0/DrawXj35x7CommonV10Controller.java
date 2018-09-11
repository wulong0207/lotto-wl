package com.hhly.lotto.api.common.controller.draw.local.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @author chenkn679
 * @version 1.0
 * @desc 新疆福彩35选7
 * @date 2018/1/5
 * @company 益彩网络科技公司
 */
public class DrawXj35x7CommonV10Controller extends DrawLocalCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.XJ35X7.getName();
	}

}
