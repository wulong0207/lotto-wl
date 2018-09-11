package com.hhly.lotto.api.pc.controller.high.x115.xj.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.x115.X115PcV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.util.highutil.HighUtil;

/**
 * @desc    PC端新疆十一选五controller
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/v1.0/xj11x5")
public class Xj11x5PcV10Controller extends X115PcV10Controller {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.XJ11X5.getName();
	}

	@Override
	protected boolean isSubPlay(Integer subPlay) {
		return HighUtil.isXJ11x5(subPlay);
	}
}
