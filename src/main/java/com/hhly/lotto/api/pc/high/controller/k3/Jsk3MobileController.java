package com.hhly.lotto.api.pc.high.controller.k3;

import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * @desc    移动端江办快3controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class Jsk3MobileController extends K3MobileController {

	@Override
	protected Integer getLottery() {
		return Lottery.JSK3.getName();
	}
}
