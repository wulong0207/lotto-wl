package com.hhly.lotto.api.h5.controller.order.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.order.v1_0.ChaseV10Controller;
import com.hhly.skeleton.base.common.ChaseEnum.ClientType;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc h5端：追号计划控制器
 * @author huangb
 * @date 2017年3月29日
 * @company 益彩网络
 * @version v1.0
 */
@RestController
@RequestMapping("/h5/v1.0/chase")
public class ChaseH5V10Controller extends ChaseV10Controller {

	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}

	@Override
	protected ClientType getClientType() {
		return ClientType.H5;
	}
}
