package com.hhly.lotto.api.common.controller.draw.local.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 黑龙江福彩22选5
 * @author huangchengfang1219
 * @date 2018年01月04日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawHlj22x5CommonV10Controller extends DrawLocalCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.HLJ22X5.getName();
	}

}
