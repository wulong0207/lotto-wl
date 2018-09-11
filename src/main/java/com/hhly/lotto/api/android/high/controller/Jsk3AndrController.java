package com.hhly.lotto.api.android.high.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.pc.high.controller.k3.Jsk3MobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    android端江办快3controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/android/jsk3")
public class Jsk3AndrController extends Jsk3MobileController {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.ANDROID;
	}
}
