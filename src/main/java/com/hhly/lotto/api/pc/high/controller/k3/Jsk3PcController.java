package com.hhly.lotto.api.pc.high.controller.k3;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum.Lottery;

/**
 * @desc    PC端江办快3controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/jsk3")
public class Jsk3PcController extends K3PcController {

	@Override
	protected Integer getLottery() {
		return Lottery.JSK3.getName();
	}
}
