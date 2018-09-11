package com.hhly.lotto.api.pc.controller.high.poker.sd.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.poker.PokerPcV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;

@RestController
@RequestMapping("/pc/v1.0/sdpk")
public class SdPokerPcV10Controller extends PokerPcV10Controller {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.SDPOKER.getName();
	}
}
