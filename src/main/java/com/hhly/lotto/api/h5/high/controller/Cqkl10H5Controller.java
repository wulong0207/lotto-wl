package com.hhly.lotto.api.h5.high.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.pc.high.controller.kl10.Cqkl10MobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    重庆快乐十分controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/h5/cqkl10")
public class Cqkl10H5Controller extends Cqkl10MobileController {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
