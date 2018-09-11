package com.hhly.lotto.api.common.draw.num.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 大乐透
 * @author huangchengfang1219
 * @date 2017年9月29日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/common/draw/dlt")
public class DrawDltController extends DrawNumController {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.DLT.getName();
	}

}
