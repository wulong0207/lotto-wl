package com.hhly.lotto.api.ios.high.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.pc.high.controller.x115.Xj11x5MobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    iOS端新疆十一选五controller
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/ios/xj11x5")
public class Xj11x5IosController extends Xj11x5MobileController {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
