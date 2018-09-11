package com.hhly.lotto.api.ios.num.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hhly.lotto.api.pc.num.controller.qxc.QxcMobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    PC端：七星彩页面控制器
 * @author  Tony Wang
 * @date    2017年7月6日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping("/ios/qxc")
public class QxcIosController extends QxcMobileController {
	
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
	

