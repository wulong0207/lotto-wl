package com.hhly.lotto.api.android.num.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hhly.lotto.api.pc.num.controller.qlc.QlcMobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc Android端：七乐彩页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
@RequestMapping("/android/qlc")
public class QlcAndrController extends QlcMobileController {

	@Override
	protected PlatformType getPlatform() {
		return PlatformType.ANDROID;
	}
}
