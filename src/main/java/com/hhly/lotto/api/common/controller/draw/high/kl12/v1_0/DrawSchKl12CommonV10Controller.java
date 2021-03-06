package com.hhly.lotto.api.common.controller.draw.high.kl12.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 四川快乐12
 * @author huangchengfang1219
 * @date 2018年01月03日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawSchKl12CommonV10Controller extends DrawKl12CommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.SCHKL12.getName();
	}

}
