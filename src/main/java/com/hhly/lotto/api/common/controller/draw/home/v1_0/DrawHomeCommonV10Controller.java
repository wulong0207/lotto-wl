package com.hhly.lotto.api.common.controller.draw.home.v1_0;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.draw.home.bo.DrawHomeHighLotteryBO;
import com.hhly.skeleton.draw.home.bo.DrawHomeLotteryIssueBO;
import com.hhly.skeleton.draw.issue.bo.DrawLotteryBO;

/**
 * @desc 首页相关
 * @author huangchengfang1219
 * @date 2017年10月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawHomeCommonV10Controller {

	@Autowired
	protected RestTemplate restTemplate;
	@Value("${draw_core_url}")
	protected String drawCoreUrl;

	@RequestMapping(value = "country", method = RequestMethod.GET)
	public ResultBO<List<DrawLotteryBO>> country() {
		String resultStr = restTemplate.getForObject(drawCoreUrl + "draw/lottery/country", String.class);
		ResultBO<List<DrawLotteryBO>> result = JsonUtil.jsonToJackObject(resultStr, new TypeReference<ResultBO<List<DrawLotteryBO>>>() {
		});
		if (result == null || result.isError()) {
			return result;
		}
		List<DrawLotteryBO> drawLotteryList = result.getData();
		List<DrawLotteryBO> newDrawLotteryList = new ArrayList<DrawLotteryBO>();
		for (DrawLotteryBO drawLottery : drawLotteryList) {
			if (drawLottery.getLotteryCode() != LotteryEnum.Lottery.ZC_NINE.getName()) {
				if (drawLottery.getLotteryCode() == LotteryEnum.Lottery.SFC.getName()) {
					drawLottery.setLotteryName(drawLottery.getLotteryName() + "(任九)");
				}
				newDrawLotteryList.add(drawLottery);
			}
		}
		return ResultBO.ok(newDrawLotteryList);
	}

	@RequestMapping(value = "high", method = RequestMethod.GET)
	public Object high() {
		String resultStr = restTemplate.getForObject(drawCoreUrl + "draw/lottery/high", String.class);
		ResultBO<List<DrawHomeHighLotteryBO>> result = JsonUtil.jsonToJackObject(resultStr,
				new TypeReference<ResultBO<List<DrawHomeHighLotteryBO>>>() {
				});
		if (result == null || result.isError()) {
			return result;
		}
		List<DrawHomeHighLotteryBO> drawHighList = result.getData();
		if (ObjectUtil.isBlank(drawHighList)) {
			return result;
		}
		for (DrawHomeHighLotteryBO drawHigh : drawHighList) {
			if (drawHigh.getChildCategoryId() != Constants.NUM_99) {
				continue;
			}
			// V1.0版本，高频彩其它类彩种只保留山东快乐扑克3
			List<DrawHomeLotteryIssueBO> newDrawLotteryList = new ArrayList<>();
			for (DrawHomeLotteryIssueBO drawLottery : drawHigh.getDrawLotteryList()) {
				if (drawLottery.getLotteryCode() == LotteryEnum.Lottery.SDPOKER.getName()) {
					newDrawLotteryList.add(drawLottery);
				}
			}
			drawHigh.setDrawLotteryList(newDrawLotteryList);
		}
		return ResultBO.ok(drawHighList);
	}

	@RequestMapping(value = "local", method = RequestMethod.GET)
	public Object local() {
		return restTemplate.getForObject(drawCoreUrl + "draw/lottery/local", String.class);
	}
}
