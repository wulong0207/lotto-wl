package com.hhly.lotto.api.pc.controller.high.k3.js.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.k3.K3PcV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * @desc    PC端江办快3controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/v1.0/jsk3")
public class Jsk3PcV10Controller extends K3PcV10Controller {

	@Override
	protected Integer getLottery() {
		return Lottery.JSK3.getName();
	}
}
