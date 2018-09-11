package com.hhly.lotto.api.common.draw.high.kl10.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 广东快乐10分
 * @author huangchengfang1219
 * @date 2017年9月28日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/common/draw/gdkl10")
public class DrawGdKl10Controller extends DrawKl10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.DKL10.getName();
	}

}
