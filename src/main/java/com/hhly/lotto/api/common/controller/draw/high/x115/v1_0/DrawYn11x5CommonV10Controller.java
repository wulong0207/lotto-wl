package com.hhly.lotto.api.common.controller.draw.high.x115.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 云南11选5
 * @author huangchengfang1219
 * @date 2018年01月02日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawYn11x5CommonV10Controller extends Draw11x5CommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.YN11X5.getName();
	}

}
