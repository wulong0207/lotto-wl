package com.hhly.lotto.api.android.controller.area.bbwp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.area.bbwp.BbwpCommonV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * 百变王牌通用
 * 
 * @desc
 * @author chenghougui
 * @Date 2018年1月19日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/android/v1.0/bbwp")
public class BbwpCommonAndrV10Controller extends BbwpCommonV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.ANDROID;
	}
}
