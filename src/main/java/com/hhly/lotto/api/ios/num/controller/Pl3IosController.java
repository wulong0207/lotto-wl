package com.hhly.lotto.api.ios.num.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hhly.lotto.api.pc.num.controller.pl3.Pl3MobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc ios端：排列三页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
@RequestMapping("/ios/pl3")
public class Pl3IosController extends Pl3MobileController {

	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
