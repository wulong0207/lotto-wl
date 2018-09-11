package com.hhly.lotto.api.common.controller.draw.high.x115.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 河北11选5
 * @author huangchengfang1219
 * @date 2018年01月02日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawHeb11x5CommonV10Controller extends Draw11x5CommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.HEB11X5.getName();
	}

}
