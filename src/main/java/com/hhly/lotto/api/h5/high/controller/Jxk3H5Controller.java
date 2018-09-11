package com.hhly.lotto.api.h5.high.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.pc.high.controller.k3.Jxk3MobileController;
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
@RequestMapping("/h5/jxk3")
public class Jxk3H5Controller extends Jxk3MobileController{

	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
