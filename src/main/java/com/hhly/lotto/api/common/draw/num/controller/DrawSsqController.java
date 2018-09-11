package com.hhly.lotto.api.common.draw.num.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 双色球
 * @author huangchengfang1219
 * @date 2017年9月29日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/common/draw/ssq")
public class DrawSsqController extends DrawNumController {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.SSQ.getName();
	}

}
