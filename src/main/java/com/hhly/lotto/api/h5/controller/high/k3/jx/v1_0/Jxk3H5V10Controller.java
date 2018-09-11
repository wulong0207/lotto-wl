package com.hhly.lotto.api.h5.controller.high.k3.jx.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.k3.jx.v1_0.Jxk3MobileV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;



/**
 * 
 * @desc 江西快3  H5
 * @author chenghougui
 * @Date 2017年10月20日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/h5/v1.0/jxk3")
public class Jxk3H5V10Controller extends Jxk3MobileV10Controller{

	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
