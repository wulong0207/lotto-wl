package com.hhly.lotto.api.ios.controller.high.kl10.gd.v1_0;

import com.hhly.lotto.api.common.controller.high.kl10.gd.v1_0.Dkl10MobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    广东快乐十分controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
//@RestController
//@RequestMapping("/ios/v1.0/gdkl10")
public class Dkl10IosV10Controller extends Dkl10MobileV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
