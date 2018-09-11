package com.hhly.lotto.api.common.draw.high.k3.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 江西快3
 * @author huangchengfang1219
 * @date 2017年12月02日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/common/draw/jxk3")
public class DrawJxK3Controller extends DrawK3Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.JXK3.getName();
	}

}
