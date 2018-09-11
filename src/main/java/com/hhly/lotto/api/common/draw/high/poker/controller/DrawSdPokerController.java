package com.hhly.lotto.api.common.draw.high.poker.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 山东快乐扑克3
 * @author huangchengfang1219
 * @date 2017年9月28日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/common/draw/sdpoker")
public class DrawSdPokerController extends DrawPokerController {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.SDPOKER.getName();
	}

}
