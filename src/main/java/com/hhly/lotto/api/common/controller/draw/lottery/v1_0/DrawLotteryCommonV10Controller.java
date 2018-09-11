package com.hhly.lotto.api.common.controller.draw.lottery.v1_0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.lottocore.remote.lotto.service.ILotteryService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;

/**
 * 查询开奖公告彩种信息
 *
 * @author huangchengfang1219
 * @date 2017年11月16日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawLotteryCommonV10Controller extends BaseController {

	@Autowired
	protected RestTemplate restTemplate;
	@Value("${draw_core_url}")
	protected String drawCoreUrl;

	@Autowired
	protected ILotteryService lotteryService;

	@Autowired
	protected ILotteryIssueService lotteryIssueService;

	@RequestMapping(method = RequestMethod.GET)
	public Object lottery(Integer lotteryCode) {
		Assert.paramNotNull(lotteryCode, "lotteryCode");
		Assert.paramLegal(LotteryEnum.Lottery.contain(lotteryCode), "lotteryCoe");
		String url = drawCoreUrl + "/draw/lottery/{lotteryCode}";
		return restTemplate.getForObject(url, String.class, lotteryCode);
	}

	@RequestMapping(value = "list", method = { RequestMethod.GET })
	public Object queryLotterySelectList(LotteryVO vo) {
		if (ObjectUtil.isBlank(vo))
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);

		Assert.paramNotNull(vo.getDrawType(), "drawType");

		return ResultBO.ok(lotteryService.queryLotterySelectList(vo));
	}

	@RequestMapping(value = "issue/list", method = { RequestMethod.GET })
	public Object queryIssueByLottery(LotteryVO lotteryVO) {
		if (ObjectUtil.isBlank(lotteryVO))
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		Assert.paramNotNull(lotteryVO.getLotteryCode(), "lotteryCode");
		return ResultBO.ok(lotteryIssueService.queryIssueByLottery(lotteryVO));
	}

}
