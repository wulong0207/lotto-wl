package com.hhly.lotto.api.h5.controller.area.yydj;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.area.yydj.YydjCommonV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * 游泳夺金通用
 * 
 * @desc
 * @author chenghougui
 * @Date 2018年1月19日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/h5/v1.0/yydj")
public class YydjCommonH5V10Controller extends YydjCommonV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WAP;
	}
}
