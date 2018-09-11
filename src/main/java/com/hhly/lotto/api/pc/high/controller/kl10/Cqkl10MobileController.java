package com.hhly.lotto.api.pc.high.controller.kl10;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc    重庆时时彩移动端controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class Cqkl10MobileController extends Kl10MobileController {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.CQKL10.getName();
	}
}
