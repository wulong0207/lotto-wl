package com.hhly.lotto.api.ios.controller.num.pl3.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.num.pl3.v1_0.Pl3MobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc ios端：排列三页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@RestController
@RequestMapping("/ios/v1.0/pl3")
public class Pl3IosV10Controller extends Pl3MobileV10Controller {

	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
