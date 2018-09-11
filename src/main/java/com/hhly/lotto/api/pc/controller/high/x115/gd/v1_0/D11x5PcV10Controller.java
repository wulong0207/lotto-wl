package com.hhly.lotto.api.pc.controller.high.x115.gd.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.x115.X115PcV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.util.highutil.HighUtil;

/**
 * @desc    PC端广东十一选五controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/v1.0/gd11x5")
public class D11x5PcV10Controller extends X115PcV10Controller {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.D11X5.getName();
	}

	@Override
	protected boolean isSubPlay(Integer subPlay) {
		return HighUtil.isD11x5(subPlay);
	}
}
