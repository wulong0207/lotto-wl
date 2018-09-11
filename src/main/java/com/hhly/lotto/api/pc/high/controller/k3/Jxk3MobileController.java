package com.hhly.lotto.api.pc.high.controller.k3;

import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * 
 * @desc 
 * @author chenghougui
 * @Date 2017年10月20日
 * @Company 益彩网络科技公司
 * @version
 */
public abstract class Jxk3MobileController extends K3MobileController {

	@Override
	protected Integer getLottery() {
		return Lottery.JXK3.getName();
	}
}
