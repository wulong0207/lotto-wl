package com.hhly.lotto.api.ios.num.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hhly.lotto.api.pc.num.controller.f3d.F3dMobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc ios端：福彩3D页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
@RequestMapping("/ios/f3d")
public class F3dIosController extends F3dMobileController {


	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
