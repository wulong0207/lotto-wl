package com.hhly.lotto.api.pc.high.controller.poker;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

@RestController
@RequestMapping("/pc/sdpk")
public class SdPokerPcController extends PokerPcController {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.SDPOKER.getName();
	}
}
