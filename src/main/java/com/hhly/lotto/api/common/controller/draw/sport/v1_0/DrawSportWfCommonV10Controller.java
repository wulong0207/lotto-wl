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
 * @desc 开奖系统胜负过关相关接口
 * @author huangchengfang1219
 * @date 2017年9月25日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawSportWfCommonV10Controller extends BaseController {

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
		String url = drawCoreUrl + "draw/wf/list";
		return restTemplate.postForEntity(url, params, String.class).getBody();
	}
}
