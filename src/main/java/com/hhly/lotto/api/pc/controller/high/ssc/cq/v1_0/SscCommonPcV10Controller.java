package com.hhly.lotto.api.pc.controller.high.ssc.cq.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.ssc.SscCommonV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc    非运营时时时彩controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/v1.0/ssc")
public class SscCommonPcV10Controller extends SscCommonV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WEB;
	}
}
