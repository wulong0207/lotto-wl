package com.hhly.lotto.api.pc.controller.high.x115;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.x115.X115CommonV10Contoller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    pc端非运营十一选五controller
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/v1.0/11x5")
public class X115CommonPcV10Controller extends X115CommonV10Contoller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WEB;
	}
}
