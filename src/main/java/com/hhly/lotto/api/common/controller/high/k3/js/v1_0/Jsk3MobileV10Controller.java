package com.hhly.lotto.api.common.controller.high.k3.js.v1_0;

import com.hhly.lotto.api.common.controller.high.k3.K3MobileV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * @desc    移动端江办快3controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class Jsk3MobileV10Controller extends K3MobileV10Controller {

	@Override
	protected Integer getLottery() {
		return Lottery.JSK3.getName();
	}
}
