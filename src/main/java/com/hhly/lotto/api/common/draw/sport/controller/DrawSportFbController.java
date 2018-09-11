package com.hhly.lotto.api.common.draw.sport.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
@RestController
@RequestMapping("/common/draw/fb")
public class DrawSportFbController {

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

	@RequestMapping(value = "/{id}/wdf", method = RequestMethod.GET)
	public Object findWdfData(@PathVariable("id") Integer matchId) {
		String url = drawCoreUrl + "/draw/fb/{matchId}/wdf";
		return restTemplate.getForObject(url, String.class, matchId);
	}

	@RequestMapping(value = "/{id}/letwdf", method = RequestMethod.GET)
	public Object findLetWdfData(@PathVariable("id") Integer matchId) {
		String url = drawCoreUrl + "/draw/fb/{matchId}/letwdf";
		return restTemplate.getForObject(url, String.class, matchId);
	}

	@RequestMapping(value = "/{id}/score", method = RequestMethod.GET)
	public Object findScoreData(@PathVariable("id") Integer matchId) {
		String url = drawCoreUrl + "/draw/fb/{matchId}/score";
		return restTemplate.getForObject(url, String.class, matchId);
	}

	@RequestMapping(value = "/{id}/goal", method = RequestMethod.GET)
	public Object findGoalData(@PathVariable("id") Integer matchId) {
		String url = drawCoreUrl + "/draw/fb/{matchId}/goal";
		return restTemplate.getForObject(url, String.class, matchId);
	}

	@RequestMapping(value = "/{id}/hfwdf", method = RequestMethod.GET)
	public Object findHfWdfData(@PathVariable("id") Integer matchId) {
		String url = drawCoreUrl + "/draw/fb/{matchId}/hfwdf";
		return restTemplate.getForObject(url, String.class, matchId);
	}
}
