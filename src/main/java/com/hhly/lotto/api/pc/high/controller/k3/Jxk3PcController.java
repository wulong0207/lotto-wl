package com.hhly.lotto.api.pc.high.controller.k3;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/pc/jxk3")
public class Jxk3PcController extends K3PcController {

	@Override
	protected Integer getLottery() {
		return Lottery.JXK3.getName();
	}
}
