package com.hhly.lotto.api.common.controller.high.poker.sd.v1_0;

import com.hhly.lotto.api.common.controller.high.poker.PokerMobileV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;

public abstract class SdPokerMobileV10Controller extends PokerMobileV10Controller {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.SDPOKER.getName();
	}
}
