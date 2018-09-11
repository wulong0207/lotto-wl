package com.hhly.lotto.api.common.controller.high.x115.gx.v1_0;

import com.hhly.lotto.api.common.controller.high.x115.X115MobileV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.util.highutil.HighUtil;

/**
 * @desc    移动端江西十一选五抽象controller
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class Gx11x5MobileV10Controller extends X115MobileV10Controller {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.GX11X5.getName();
	}

	@Override
	protected boolean isSubPlay(Integer subPlay) {
		return HighUtil.isGX11x5(subPlay);
	}
}
