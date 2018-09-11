package com.hhly.lotto.api.h5.high.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.pc.high.controller.ssc.CqsscMobileController;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    重庆时时时彩controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/h5/cqssc")
public class CqsscH5Controller extends CqsscMobileController {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
