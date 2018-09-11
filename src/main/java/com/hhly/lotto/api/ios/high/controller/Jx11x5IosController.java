package com.hhly.lotto.api.ios.high.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.pc.high.controller.x115.Jx11x5MobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    iOS端江西十一选五controller
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/ios/jx11x5")
public class Jx11x5IosController extends Jx11x5MobileController {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
