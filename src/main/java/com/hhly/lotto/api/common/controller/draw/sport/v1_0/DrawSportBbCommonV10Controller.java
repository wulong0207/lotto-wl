package com.hhly.lotto.api.common.controller.draw.sport.v1_0;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.constants.SportConstants;
import com.hhly.skeleton.base.exception.Assert;

/**
 * @desc 开奖系统竞彩篮球相关接口
 * @author huangchengfang1219
 * @date 2017年9月25日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawSportBbCommonV10Controller extends BaseController {

	protected Integer lotteryCode = LotteryEnum.Lottery.BB.getName();
	@Autowired
	protected RestTemplate restTemplate;
	@Value("${draw_core_url}")
	protected String drawCoreUrl;

	@RequestMapping(method = RequestMethod.GET)
	public Object list(@RequestParam Map<String, Object> params) {
		Assert.paramNotNull(params);
		Assert.paramNotNull(params.get("pageIndex"), "pageIndex");
		Assert.paramNotNull(params.get("pageSize"), "pageSize");
		params.put("matchStatusArr", SportConstants.SPORT_DRAW_CANCEL_STATUS);
		params.put("endTime", params.get("startTime"));
		String url = drawCoreUrl + "draw/bb/list";
		return restTemplate.postForEntity(url, params, String.class).getBody();
	}

	/**
	 * 胜负
	 * 
	 * @param matchId
	 * @return
	 */
	@RequestMapping(value = "/wf", method = RequestMethod.GET)
	public Object findWfData(Integer matchId) {
		Assert.paramNotNull(matchId, "matchId");
		String url = drawCoreUrl + "/draw/bb/{matchId}/wf";
		return restTemplate.getForObject(url, String.class, matchId);
	}

	/**
	 * 让分胜负
	 * 
	 * @param matchId
	 * @return
	 */
	@RequestMapping(value = "/letwf", method = RequestMethod.GET)
	public Object findLetWfData(Integer matchId) {
		Assert.paramNotNull(matchId, "matchId");
		String url = drawCoreUrl + "/draw/bb/{matchId}/letwf";
		return restTemplate.getForObject(url, String.class, matchId);
	}

	/**
	 * 大小分
	 * 
	 * @param matchId
	 * @return
	 */
	@RequestMapping(value = "/sizescore", method = RequestMethod.GET)
	public Object findSizeScoreData(Integer matchId) {
		Assert.paramNotNull(matchId, "matchId");
		String url = drawCoreUrl + "/draw/bb/{matchId}/sizescore";
		return restTemplate.getForObject(url, String.class, matchId);
	}

	/**
	 * 胜分差
	 * 
	 * @param matchId
	 * @return
	 */
	@RequestMapping(value = "/winscore", method = RequestMethod.GET)
	public Object findWinScoreData(Integer matchId) {
		Assert.paramNotNull(matchId, "matchId");
		String url = drawCoreUrl + "/draw/bb/{matchId}/winscore";
		return restTemplate.getForObject(url, String.class, matchId);
	}
}
