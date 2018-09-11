package com.hhly.lotto.api.pc.controller.area.ssl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.area.ssl.SslCommonV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * 时时乐通用
 * 
 * @desc
 * @author chenghougui
 * @Date 2018年1月19日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/pc/v1.0/ssl")
public class SslCommonPcV10Controller extends SslCommonV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WEB;
	}
}
