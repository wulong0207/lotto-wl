package com.hhly.lotto.api.common.controller.draw.local.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @author lidecheng
 * @version 1.0
 * @desc  浙江体彩20选5
 * @date 2017年12月02日
 * @company 益彩网络科技公司
 */
public class DrawZj20x5CommonV10Controller extends DrawLocalCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.ZJ20X5.getName();
	}

}
