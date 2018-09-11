package com.hhly.lotto.api.pc.controller.area.xysc;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.area.xysc.XyscCommonV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 *幸运赛车通用
 * 
 * @desc
 * @author chenghougui
 * @Date 2018年1月19日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/pc/v1.0/xysc")
public class XyscCommonPcV10Controller extends XyscCommonV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.WEB;
	}
}
