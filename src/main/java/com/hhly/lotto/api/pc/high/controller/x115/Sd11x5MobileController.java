package com.hhly.lotto.api.pc.high.controller.x115;

import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.util.highutil.HighUtil;

/**
 * @desc    移动端山东十一选五抽象controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class Sd11x5MobileController extends X115MobileController {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.SD11X5.getName();
	}

	@Override
	protected boolean isSubPlay(Integer subPlay) {
		return HighUtil.isSd11x5(subPlay);
	}
}
