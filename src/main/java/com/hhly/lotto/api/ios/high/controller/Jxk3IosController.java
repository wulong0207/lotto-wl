package com.hhly.lotto.api.ios.high.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.pc.high.controller.k3.Jxk3MobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * 
 * @desc 江西快3  IOS
 * @author chenghougui
 * @Date 2017年10月20日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/ios/jxk3")
public class Jxk3IosController extends Jxk3MobileController {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
