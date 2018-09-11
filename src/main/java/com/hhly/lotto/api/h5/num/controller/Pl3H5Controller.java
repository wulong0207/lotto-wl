package com.hhly.lotto.api.h5.num.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hhly.lotto.api.pc.num.controller.pl3.Pl3MobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc h5端：排列三页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
@RequestMapping("/h5/pl3")
public class Pl3H5Controller extends Pl3MobileController {

	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
