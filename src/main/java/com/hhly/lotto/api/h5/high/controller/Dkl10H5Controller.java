package com.hhly.lotto.api.h5.high.controller;

import com.hhly.lotto.api.pc.high.controller.kl10.Dkl10MobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    广东快乐十分controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
//@RestController
//@RequestMapping("/h5/gdkl10")
public class Dkl10H5Controller extends Dkl10MobileController {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
