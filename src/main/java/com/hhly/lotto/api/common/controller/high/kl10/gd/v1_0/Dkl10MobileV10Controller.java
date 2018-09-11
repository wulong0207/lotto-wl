package com.hhly.lotto.api.common.controller.high.kl10.gd.v1_0;

import com.hhly.lotto.api.common.controller.high.kl10.Kl10MobileV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc    广东快乐10分移动端controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class Dkl10MobileV10Controller extends Kl10MobileV10Controller {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.DKL10.getName();
	}
}
