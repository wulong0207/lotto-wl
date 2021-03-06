package com.hhly.lotto.api.common.draw.num.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @author zhouyang
 * @version 1.0
 * @desc 七乐彩开奖详情
 * @date 2017/9/29
 * @company 益彩网络科技公司
 */
@RestController
@RequestMapping("/common/draw/qlc")
public class DrawQlcController extends DrawNumController {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.QLC.getName();
	}


}
