package com.hhly.lotto.api.h5.activity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.pc.activity.controller.YfgcActivityController;
import com.hhly.skeleton.base.common.ChaseEnum.ClientType;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * 
 * @desc 江西11选5一分钱活动
 * @author chenghougui
 * @Date 2017年11月18日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/h5/jx11x5/activity/yfgc")
public class JxYfgcActivityH5Controller extends YfgcActivityController {

	@Override
	protected Short getClientType() {
		return ClientType.H5.getValue();
	}

	@Override
	public int getLottery() {
		return Lottery.JX11X5.getName();
	}	
}
