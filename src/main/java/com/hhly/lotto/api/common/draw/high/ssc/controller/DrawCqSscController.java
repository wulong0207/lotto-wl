package com.hhly.lotto.api.common.draw.high.ssc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * 重庆时时彩
 * 
 * @desc
 * @author huangchengfang1219
 * @date 2017年9月28日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/common/draw/cqssc")
public class DrawCqSscController extends DrawSscController {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.CQSSC.getName();
	}

}
