package com.hhly.lotto.api.mobile.draw.home.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.draw.home.controller.DrawHomeController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.draw.issue.bo.DrawLotteryBO;

/**
 * @desc 开奖公告移动端首页
 * @author huangchengfang1219
 * @date 2017年10月24日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/mobile/draw/home")
public class DrawHomeMobileController extends DrawHomeController {

	@Override
	public ResultBO<List<DrawLotteryBO>> country() {
		ResultBO<List<DrawLotteryBO>> result = super.country();
		if (result == null || result.isError() || ObjectUtil.isBlank(result.getData())) {
			return result;
		}
		List<DrawLotteryBO> drawLotteryList = new ArrayList<DrawLotteryBO>();
		for (DrawLotteryBO drawLottery : result.getData()) {
			if (drawLottery.getLotteryCode() != LotteryEnum.Lottery.ZC_NINE.getName()) {
				if (drawLottery.getLotteryCode() == LotteryEnum.Lottery.SFC.getName()) {
					drawLottery.setLotteryName(drawLottery.getLotteryName() + "(任九)");
				}
				drawLotteryList.add(drawLottery);
			}
		}
		result.setData(drawLotteryList);
		return result;
	}
}
