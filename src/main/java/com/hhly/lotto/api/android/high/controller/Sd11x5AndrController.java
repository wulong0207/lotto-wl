package com.hhly.lotto.api.android.high.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.pc.high.controller.x115.Sd11x5MobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    android端山东十一选五controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/android/sd11x5")
public class Sd11x5AndrController extends Sd11x5MobileController {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.ANDROID;
	}
}
