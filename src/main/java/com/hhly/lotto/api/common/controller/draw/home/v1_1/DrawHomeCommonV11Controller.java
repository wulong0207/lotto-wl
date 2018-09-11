package com.hhly.lotto.api.common.controller.draw.home.v1_1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

/**
 * @desc 首页相关
 * @author huangchengfang1219
 * @date 2017年10月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class DrawHomeCommonV11Controller {

	@Autowired
	protected RestTemplate restTemplate;
	@Value("${draw_core_url}")
	protected String drawCoreUrl;

	@RequestMapping(value = "high", method = RequestMethod.GET)
	public Object high() {
		return restTemplate.getForObject(drawCoreUrl + "draw/lottery/high", String.class);
	}
}
