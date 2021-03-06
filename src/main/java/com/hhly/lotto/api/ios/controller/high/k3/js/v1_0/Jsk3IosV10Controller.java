package com.hhly.lotto.api.ios.controller.high.k3.js.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.k3.js.v1_0.Jsk3MobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    iOS端江办快3controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/ios/v1.0/jsk3")
public class Jsk3IosV10Controller extends Jsk3MobileV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
