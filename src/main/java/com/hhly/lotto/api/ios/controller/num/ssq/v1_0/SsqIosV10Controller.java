package com.hhly.lotto.api.ios.controller.num.ssq.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.num.ssq.v1_0.SsqMobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc ios端：双色球页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@RestController
@RequestMapping("/ios/v1.0/ssq")
public class SsqIosV10Controller extends SsqMobileV10Controller {

	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
