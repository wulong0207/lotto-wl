package com.hhly.lotto.api.common.controller.draw.high.k3.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 河北快3
 * @author huangchengfang1219
 * @date 2018年01月02日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawHebK3CommonV10Controller extends DrawK3CommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.HEBK3.getName();
	}

}
