package com.hhly.lotto.api.ios.high.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.pc.high.controller.poker.SdPokerMobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

@RestController
@RequestMapping("/ios/sdpk")
public class SdPokerIosController extends SdPokerMobileController {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
