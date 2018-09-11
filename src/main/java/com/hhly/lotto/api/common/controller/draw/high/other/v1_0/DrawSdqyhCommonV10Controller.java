package com.hhly.lotto.api.common.controller.draw.high.other.v1_0;

import com.hhly.lotto.api.common.controller.draw.high.v1_0.DrawHighCommonV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @author chenkn679
 * @version 1.0
 * @desc
 * @date 2018/1/3
 * @company 益彩网络科技公司
 */
public class DrawSdqyhCommonV10Controller extends DrawHighCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.SDQYH.getName();
	}

}
