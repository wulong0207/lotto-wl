package com.hhly.lotto.api.pc.controller.high.ssc.cq.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.high.ssc.SscPcV10Controller;
import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc    重庆时时彩PC端controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/v1.0/cqssc")
public class CqsscPcV10Controller extends SscPcV10Controller {

	@Override
	protected Integer getLottery() {
		return LotteryEnum.Lottery.CQSSC.getName();
	}
}
