package com.hhly.lotto.api.common.draw.num.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.exception.Assert;

/**
 * @desc 数字彩公共接口
 * @author huangchengfang1219
 * @date 2017年9月29日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class DrawNumController {
	
	@Autowired
	protected RestTemplate restTemplate;
	@Value("${draw_core_url}")
	protected String drawCoreUrl;

	@RequestMapping(method = RequestMethod.GET)
	public Object list(@RequestParam Map<String, Object> params) {
		Assert.paramNotNull(params);
		if (params.get("pageIndex") == null) {
			params.put("pageIndex", Constants.NUM_0);
		}
		if (params.get("pageSize") == null) {
			params.put("pageSize", Constants.NUM_100);
		}
		params.put("lotteryCode", getLotteryCode());
		String url = drawCoreUrl + "draw/num/list";
		return restTemplate.postForEntity(url, params, String.class).getBody();
	}

	@RequestMapping(value = "/{issueCode}/detail", method = RequestMethod.GET)
	public Object drawDetail(@PathVariable("issueCode") String issueCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("lotteryCode", getLotteryCode());
		params.put("issueCode", issueCode);
		String url = drawCoreUrl + "draw/num/detail";
		return restTemplate.postForEntity(url, params, String.class).getBody();
	}

	public abstract Integer getLotteryCode();
}
