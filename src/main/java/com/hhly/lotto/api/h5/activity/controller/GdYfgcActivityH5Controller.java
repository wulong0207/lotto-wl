package com.hhly.lotto.api.h5.activity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.pc.activity.controller.YfgcActivityController;
import com.hhly.skeleton.base.common.ChaseEnum.ClientType;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * @desc    广东11选5一分购彩活动
 * @author  Tony Wang
 * @date    2017年8月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/h5/gd11x5/activity/yfgc")
public class GdYfgcActivityH5Controller extends YfgcActivityController {

	@Override
	protected Short getClientType() {
		return ClientType.H5.getValue();
	}

	@Override
	public int getLottery() {
		return Lottery.D11X5.getName();
	}	
}
