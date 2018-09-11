package com.hhly.lotto.api.pc.high.controller.ssc;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc    重庆时时彩移动端controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class CqsscMobileController extends SscMobileController {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.CQSSC.getName();
	}
}
