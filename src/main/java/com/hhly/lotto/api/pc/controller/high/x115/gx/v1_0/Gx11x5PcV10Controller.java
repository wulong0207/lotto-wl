package com.hhly.lotto.api.pc.controller.high.x115.gx.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.x115.X115PcV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.util.highutil.HighUtil;

/**
 * 
 * @desc 广西11选5  pc
 * @author chenghougui
 * @Date 2017年12月13日
 * @Company 益彩网络科技公司
 * @version
 */
@RestController
@RequestMapping("/pc/v1.0/gx11x5")
public class Gx11x5PcV10Controller extends X115PcV10Controller {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.GX11X5.getName();
	}

	@Override
	protected boolean isSubPlay(Integer subPlay) {
		return HighUtil.isGX11x5(subPlay);
	}
}
