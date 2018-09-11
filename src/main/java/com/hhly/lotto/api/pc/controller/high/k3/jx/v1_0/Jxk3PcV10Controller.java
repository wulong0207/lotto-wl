package com.hhly.lotto.api.pc.controller.high.k3.jx.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.k3.K3PcV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * 
 * @desc 江西快3  PC
 * @author chenghougui
 * @Date 2017年10月20日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/pc/v1.0/jxk3")
public class Jxk3PcV10Controller extends K3PcV10Controller {

	@Override
	protected Integer getLottery() {
		return Lottery.JXK3.getName();
	}
}
