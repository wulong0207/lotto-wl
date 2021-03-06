package com.hhly.lotto.api.pc.controller.order.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.order.v1_0.ChaseV10Controller;
import com.hhly.skeleton.base.common.ChaseEnum.ClientType;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc PC端：追号计划控制器
 * @author huangb
 * @date 2017年3月29日
 * @company 益彩网络
 * @version v1.0
 */
@RestController
@RequestMapping("/pc/v1.0/chase")
public class ChasePcV10Controller extends ChaseV10Controller {

	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WEB;
	}

	@Override
	protected ClientType getClientType() {
		return ClientType.PC;
	}
}
