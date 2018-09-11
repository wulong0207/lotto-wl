package com.hhly.lotto.api.pc.high.controller.x115;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.util.highutil.HighUtil;

/**
 * @desc    PC端新疆十一选五controller
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/xj11x5")
public class Xj11x5PcController extends X115PcController {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.XJ11X5.getName();
	}

	@Override
	protected boolean isSubPlay(Integer subPlay) {
		return HighUtil.isXJ11x5(subPlay);
	}
}
