package com.hhly.lotto.api.h5.controller.high.poker.sd.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.poker.sd.v1_0.SdPokerMobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

@RestController
@RequestMapping("/h5/v1.0/sdpk")
public class SdPokerH5V10Controller extends SdPokerMobileV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}