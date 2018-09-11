package com.hhly.lotto.api.common.draw.high.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hhly.skeleton.base.exception.Assert;

/**
 * @desc 高频彩
 * @author huangchengfang1219
 * @date 2017年9月28日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class DrawHighController {
	
	@Autowired
	protected RestTemplate restTemplate;
	@Value("${draw_core_url}")
	protected String drawCoreUrl;

	@RequestMapping(method = RequestMethod.GET)
	public Object list(@RequestParam Map<String, Object> params) {
		Assert.paramNotNull(params);
		Assert.paramNotNull(params.get("pageIndex"), "pageIndex");
		Assert.paramNotNull(params.get("pageSize"), "pageSize");
		params.put("lotteryCode", getLotteryCode());
		String url = drawCoreUrl + "draw/high/list";
		return restTemplate.postForEntity(url, params, String.class).getBody();
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public Object drawDetail() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lotteryCode", getLotteryCode());
		String url = drawCoreUrl + "draw/high/detail";
		String result = restTemplate.postForEntity(url, params, String.class).getBody();
		JSONObject resultJson = JSON.parseObject(result);
		if (resultJson.getInteger("success") != 1 || !resultJson.containsKey("data")) {
			return result;
		}
		JSONObject newDataJson = new JSONObject();
		newDataJson.put("drawDetailList", resultJson.getJSONObject("data").get("drawDetailList"));
		resultJson.put("data", newDataJson);
		return resultJson;
	}

	public abstract Integer getLotteryCode();
}
