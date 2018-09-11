package com.hhly.lotto.api.common.controller.draw.num.v1_0;

import com.hhly.skeleton.base.common.LotteryEnum;

/**
 * 福彩3D
 *
 * @author huangchengfang1219
 * @date 2017年12月2日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawF3dCommonV10Controller extends DrawNumCommonV10Controller {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.F3D.getName();
	}

}