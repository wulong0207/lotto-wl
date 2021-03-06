package com.hhly.lotto.api.pc.high.controller.kl10;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc    重庆时时彩PC端controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/cqkl10")
public class Cqkl10PcController extends Kl10PcController {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.CQKL10.getName();
	}
}
