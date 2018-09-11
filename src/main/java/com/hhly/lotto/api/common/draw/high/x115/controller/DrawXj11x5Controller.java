package com.hhly.lotto.api.common.draw.high.x115.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 新疆11选5
 * @author huangchengfang1219
 * @date 2017年12月02日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/common/draw/xj11x5")
public class DrawXj11x5Controller extends Draw11x5Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.XJ11X5.getName();
	}

}
