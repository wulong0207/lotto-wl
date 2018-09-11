package com.hhly.lotto.api.ios.num.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hhly.lotto.api.pc.num.controller.qlc.QlcMobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc ios端：七乐彩页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
@RequestMapping("/ios/qlc")
public class QlcIosController extends QlcMobileController {

	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
