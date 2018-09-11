package com.hhly.lotto.api.common.controller.high.x115.gd.v1_0;

import com.hhly.lotto.api.common.controller.high.x115.X115MobileV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.util.highutil.HighUtil;

/**
 * @desc    移动端广东十一选五抽象controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class D11x5MobileV10Controller extends X115MobileV10Controller {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.D11X5.getName();
	}

	@Override
	protected boolean isSubPlay(Integer subPlay) {
		return HighUtil.isD11x5(subPlay);
	}
}
