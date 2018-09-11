package com.hhly.lotto.api.common.draw.lottery.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.exception.Assert;

/**
 * 查询开奖公告彩种信息
 *
 * @author huangchengfang1219
 * @date 2017年11月16日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/common/draw/lottery")
public class DrawLotteryController extends BaseController {

	@Autowired
	protected RestTemplate restTemplate;
	@Value("${draw_core_url}")
	protected String drawCoreUrl;

	@RequestMapping(value = "{lotteryCode}", method = RequestMethod.GET)
	public Object lottery(@PathVariable Integer lotteryCode) {
		Assert.paramLegal(LotteryEnum.Lottery.contain(lotteryCode), "lotteryCoe");
		Assert.paramLegal(LotteryEnum.Lottery.contain(lotteryCode), "lotteryCoe");
		String url = drawCoreUrl + "/draw/lottery/{lotteryCode}";
		return restTemplate.getForObject(url, String.class, lotteryCode);
	}
}
