package com.hhly.lotto.api.android.controller.high.x115;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.x115.X115CommonV10Contoller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    android端非运营十一选五controller
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/android/v1.0/11x5")
public class X115CommonAndrV10Controller extends X115CommonV10Contoller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.ANDROID;
	}
}
