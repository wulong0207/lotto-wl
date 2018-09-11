package com.hhly.lotto.api.common.controller.draw.sport.v1_0;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.exception.Assert;

/**
 * @desc 老足彩胜负彩
 * @author huangchengfang1219
 * @date 2017年10月10日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawSportSfcCommonV10Controller extends BaseController {

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
		String url = drawCoreUrl + "draw/sfc/list";
		return restTemplate.postForEntity(url, params, String.class).getBody();
	}

	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public Object drawDetail(String issueCode) {
		Assert.paramNotNull(issueCode, "issueCode");
		String url = drawCoreUrl + "draw/sfc/detail/rx9";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("issueCode", issueCode);
		return restTemplate.postForEntity(url, params, String.class).getBody();
	}

}
