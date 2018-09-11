package com.hhly.lotto.api.common.draw.high.x115.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 山东11选5
 * @author huangchengfang1219
 * @date 2017年9月28日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/common/draw/sd11x5")
public class DrawSd11x5Controller extends Draw11x5Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.SD11X5.getName();
	}

}
