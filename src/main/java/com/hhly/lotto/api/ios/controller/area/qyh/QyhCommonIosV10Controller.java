package com.hhly.lotto.api.ios.controller.area.qyh;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.area.qyh.QyhCommonV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * 群英会通用
 * 
 * @desc
 * @author chenghougui
 * @Date 2018年1月19日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/ios/v1.0/qyh")
public class QyhCommonIosV10Controller extends QyhCommonV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
