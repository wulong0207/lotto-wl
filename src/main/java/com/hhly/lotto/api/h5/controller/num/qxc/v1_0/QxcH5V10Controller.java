package com.hhly.lotto.api.h5.controller.num.qxc.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.num.qxc.v1_0.QxcMobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    PC端：七星彩页面控制器
 * @author  Tony Wang
 * @date    2017年7月6日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/h5/v1.0/qxc")
public class QxcH5V10Controller extends QxcMobileV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
	

