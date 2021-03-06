package com.hhly.lotto.api.common.controller.draw.num.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 大乐透
 * @author huangchengfang1219
 * @date 2017年9月29日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawDltCommonV10Controller extends DrawNumCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.DLT.getName();
	}

}
