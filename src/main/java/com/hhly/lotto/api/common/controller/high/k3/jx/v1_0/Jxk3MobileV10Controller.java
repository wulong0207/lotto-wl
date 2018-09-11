package com.hhly.lotto.api.common.controller.high.k3.jx.v1_0;

import com.hhly.lotto.api.common.controller.high.k3.K3MobileV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * 
 * @desc 
 * @author chenghougui
 * @Date 2017年10月20日
 * @Company 益彩网络科技公司
 * @version
 */
public abstract class Jxk3MobileV10Controller extends K3MobileV10Controller {

	@Override
	protected Integer getLottery() {
		return Lottery.JXK3.getName();
	}
}
