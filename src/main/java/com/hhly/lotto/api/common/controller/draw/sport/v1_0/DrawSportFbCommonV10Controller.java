package com.hhly.lotto.api.common.controller.draw.sport.v1_0;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.constants.SportConstants;
import com.hhly.skeleton.base.exception.Assert;

/**
 * 开奖系统竞彩足球相关接口
 * 
 * @desc
 * @author huangchengfang1219
 * @date 2017年9月23日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawSportFbCommonV10Controller extends BaseController {

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
		String url = drawCoreUrl + "draw/fb/list";
		return restTemplate.postForEntity(url, params, String.class).getBody();
	}

	/**
	 * 胜平负
	 * 
	 * @param matchId
	 * @return
	 */
	@RequestMapping(value = "/wdf", method = RequestMethod.GET)
	public Object findWdfData(Integer matchId) {
		Assert.paramNotNull(matchId, "matchId");
		String url = drawCoreUrl + "/draw/fb/{matchId}/wdf";
		return restTemplate.getForObject(url, String.class, matchId);
	}

	/**
	 * 让球胜平负
	 * 
	 * @param matchId
	 * @return
	 */
	@RequestMapping(value = "/letwdf", method = RequestMethod.GET)
	public Object findLetWdfData(Integer matchId) {
		Assert.paramNotNull(matchId, "matchId");
		String url = drawCoreUrl + "/draw/fb/{matchId}/letwdf";
		return restTemplate.getForObject(url, String.class, matchId);
	}

	/**
	 * 全场比分
	 * 
	 * @param matchId
	 * @return
	 */
	@RequestMapping(value = "/score", method = RequestMethod.GET)
	public Object findScoreData(Integer matchId) {
		Assert.paramNotNull(matchId, "matchId");
		String url = drawCoreUrl + "/draw/fb/{matchId}/score";
		return restTemplate.getForObject(url, String.class, matchId);
	}

	/**
	 * 总进球数
	 * 
	 * @param matchId
	 * @return
	 */
	@RequestMapping(value = "/goal", method = RequestMethod.GET)
	public Object findGoalData(Integer matchId) {
		Assert.paramNotNull(matchId, "matchId");
		String url = drawCoreUrl + "/draw/fb/{matchId}/goal";
		return restTemplate.getForObject(url, String.class, matchId);
	}

	/**
	 * 半全场胜平负
	 * 
	 * @param matchId
	 * @return
	 */
	@RequestMapping(value = "/hfwdf", method = RequestMethod.GET)
	public Object findHfWdfData(Integer matchId) {
		Assert.paramNotNull(matchId, "matchId");
		String url = drawCoreUrl + "/draw/fb/{matchId}/hfwdf";
		return restTemplate.getForObject(url, String.class, matchId);
	}
}
