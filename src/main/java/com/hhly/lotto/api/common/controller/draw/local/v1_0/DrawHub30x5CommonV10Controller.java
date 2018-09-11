package com.hhly.lotto.api.common.controller.draw.local.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 湖北福彩30选5
 * @author huangchengfang1219
 * @date 2018年01月10日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawHub30x5CommonV10Controller extends DrawLocalCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.HUB30X5.getName();
	}

}
