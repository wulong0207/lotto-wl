package com.hhly.lotto.api.h5.controller.high.x115.gd.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.x115.gd.v1_0.D11x5MobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    广东十一选五controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/h5/v1.0/gd11x5")
public class D11x5H5V10Controller extends D11x5MobileV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
