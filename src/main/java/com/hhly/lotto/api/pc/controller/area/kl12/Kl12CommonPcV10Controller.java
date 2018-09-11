package com.hhly.lotto.api.pc.controller.area.kl12;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.area.kl12.Kl12CommonV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * 快乐12通用
 * 
 * @desc
 * @author chenghougui
 * @Date 2018年1月19日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/pc/v1.0/kl12")
public class Kl12CommonPcV10Controller extends Kl12CommonV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WEB;
	}
}
