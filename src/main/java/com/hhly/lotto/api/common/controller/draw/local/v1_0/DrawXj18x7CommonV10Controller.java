package com.hhly.lotto.api.common.controller.draw.local.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @author chenkn679
 * @version 1.0
 * @desc 新疆福彩18选7
 * @date 2018/1/5
 * @company 益彩网络科技公司
 */
public class DrawXj18x7CommonV10Controller extends DrawLocalCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.XJ18X7.getName();
	}

}
