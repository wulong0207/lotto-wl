package com.hhly.lotto.api.common.controller.draw.high.ssc.v1_0;

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
public class DrawCqSscCommonV10Controller extends DrawSscCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.CQSSC.getName();
	}

}
