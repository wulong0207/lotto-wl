package com.hhly.lotto.api.common.controller.draw.high.ssc.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * @desc 天津时时彩
 * @author huangchengfang1219
 * @date 2018年01月02日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawTjSscCommonV10Controller extends DrawSscCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.TJSSC.getName();
	}

}
