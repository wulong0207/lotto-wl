package com.hhly.lotto.api.h5.high.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.pc.high.controller.poker.SdPokerMobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

@RestController
@RequestMapping("/h5/sdpk")
public class SdPokerH5Controller extends SdPokerMobileController {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}