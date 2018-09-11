package com.hhly.lotto.api.h5.controller.num.pl5.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.num.pl5.v1_0.Pl5MobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc h5端：排列五页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@RestController
@RequestMapping("/h5/v1.0/pl5")
public class Pl5H5V10Controller extends Pl5MobileV10Controller {

	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
