package com.hhly.lotto.api.common.controller.high.ssc.cq.v1_0;

import com.hhly.lotto.api.common.controller.high.ssc.SscMobileV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc    重庆时时彩移动端controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class CqsscMobileV10Controller extends SscMobileV10Controller {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.CQSSC.getName();
	}
}
