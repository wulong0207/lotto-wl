package com.hhly.lotto.api.ios.controller.num.f3d.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.num.f3d.v1_0.F3dMobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc ios端：福彩3D页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@RestController
@RequestMapping("/ios/v1.0/f3d")
public class F3dIosV10Controller extends F3dMobileV10Controller {


	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
