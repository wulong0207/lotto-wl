package com.hhly.lotto.api.common.controller.draw.num.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @author zhouyang
 * @version 1.0
 * @desc 七星彩开奖详情
 * @date 2017/9/29
 * @company 益彩网络科技公司
 */
public class DrawQxcCommonV10Controller extends DrawNumCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.QXC.getName();
	}

}
