package com.hhly.lotto.api.ios.controller.high.x115.gx.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.x115.gx.v1_0.Gx11x5MobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;
/**
 * 
 * @desc 广西11选5 IOS
 * @author chenghougui
 * @Date 2017年12月13日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/ios/v1.0/gx11x5")
public class Gx11x5IosV10Controller extends Gx11x5MobileV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
