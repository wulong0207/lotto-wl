package com.hhly.lotto.api.h5.num.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hhly.lotto.api.pc.num.controller.f3d.F3dMobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc h5端：福彩3D页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
@RequestMapping("/h5/f3d")
public class F3dH5Controller extends F3dMobileController {

	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
