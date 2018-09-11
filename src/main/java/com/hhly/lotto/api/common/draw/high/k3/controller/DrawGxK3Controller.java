package com.hhly.lotto.api.common.draw.high.k3.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 广西快3
 * @author huangchengfang1219
 * @date 2017年9月28日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/common/draw/gxk3")
public class DrawGxK3Controller extends DrawK3Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.GXK3.getName();
	}

}
