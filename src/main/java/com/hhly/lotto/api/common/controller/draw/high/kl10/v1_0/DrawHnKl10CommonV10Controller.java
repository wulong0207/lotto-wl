package com.hhly.lotto.api.common.controller.draw.high.kl10.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 湖南快乐10分
 * @author huangchengfang1219
 * @date 2018年01月02日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawHnKl10CommonV10Controller extends DrawKl10CommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.HNKL10.getName();
	}

}
