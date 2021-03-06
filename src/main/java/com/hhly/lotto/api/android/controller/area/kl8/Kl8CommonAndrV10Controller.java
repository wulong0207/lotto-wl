package com.hhly.lotto.api.android.controller.area.kl8;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.area.kl8.Kl8CommonV10Controller;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * 快乐8通用
 * 
 * @desc
 * @author chenghougui
 * @Date 2018年1月19日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/android/v1.0/kl8")
public class Kl8CommonAndrV10Controller extends Kl8CommonV10Controller {
	@Override
	protected PlatformType getPlatform() {
		return PlatformType.ANDROID;
	}
}
