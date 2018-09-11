package com.hhly.lotto.api.common.controller.draw.high.other.v1_0;

import com.hhly.lotto.api.common.controller.draw.high.v1_0.DrawHighCommonV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 北京赛车
 * @author lidecheng
 * @date 2018年1月2日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawBjScCommonV10Controller extends DrawHighCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.PK10.getName();
	}
}
