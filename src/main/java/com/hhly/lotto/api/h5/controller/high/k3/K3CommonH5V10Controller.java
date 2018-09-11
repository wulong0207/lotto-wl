package com.hhly.lotto.api.h5.controller.high.k3;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.k3.K3CommonV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc h5端快3controller
 * @author Tony Wang
 * @date 2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/h5/v1.0/k3")
public class K3CommonH5V10Controller extends K3CommonV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
