package com.hhly.lotto.api.pc.high.controller.poker;

import com.hhly.skeleton.base.common.LotteryEnum;

public abstract class SdPokerMobileController extends PokerMobileController {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.SDPOKER.getName();
	}
}
