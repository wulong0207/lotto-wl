package com.hhly.lotto.api.ios.controller.num.dlt.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.num.dlt.v1_0.DltMobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc ios端：大乐透页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@RestController
@RequestMapping("/ios/v1.0/dlt")
public class DltIosV10Controller extends DltMobileV10Controller {

	
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
