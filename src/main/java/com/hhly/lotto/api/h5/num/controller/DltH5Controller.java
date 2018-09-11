package com.hhly.lotto.api.h5.num.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hhly.lotto.api.pc.num.controller.dlt.DltMobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc h5端：大乐透页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
@RequestMapping("/h5/dlt")
public class DltH5Controller extends DltMobileController {
	
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
