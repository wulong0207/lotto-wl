package com.hhly.lotto.api.common.controller.draw.high.other.v1_0;

import com.hhly.lotto.api.common.controller.draw.high.v1_0.DrawHighCommonV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 北京快中彩
 * @author huangchengfang1219
 * @date 2018年01月05日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawBjkzcCommonV10Controller extends DrawHighCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.BJKZC.getName();
	}

}
