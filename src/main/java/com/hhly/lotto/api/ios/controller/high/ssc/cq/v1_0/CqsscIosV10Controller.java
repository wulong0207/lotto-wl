package com.hhly.lotto.api.ios.controller.high.ssc.cq.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.ssc.cq.v1_0.CqsscMobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    重庆时时时彩controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/ios/v1.0/cqssc")
public class CqsscIosV10Controller extends CqsscMobileV10Controller {
	
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.IOS;
	}
}
