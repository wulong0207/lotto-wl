package com.hhly.lotto.api.common.controller.cooperate.v1_0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import com.hhly.lotto.base.util.CoOperateChannelUtil;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.cooperate.vo.CoOperateProxyOrderVO;
import com.hhly.skeleton.lotto.base.cooperate.vo.CoOperateProxyRechargeVO;
public class CoOperateProxyAgencyCommonV10Controller {

	@Autowired
	private RestTemplate restTemplate;
	@SuppressWarnings("unused")
	@Autowired
	private CoOperateChannelUtil coOperateChannelUtil;
	@Value("${lotto_common_core_url}")
	private String lottoCommonCoreUrl;
	@Value("${lotto_report_url}")
	private String lottoReportUrl;

	@RequestMapping(value = "recharge/list", method = RequestMethod.POST)
	public Object findRechargeList(@RequestBody CoOperateProxyRechargeVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		Assert.paramNotNull(vo.getQueryChannelId(), "queryChannelId");
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/proxyagency/recharge/list", vo, String.class).getBody();
	}

	@RequestMapping(value = "order/list", method = RequestMethod.POST)
	public Object findOrderList(@RequestBody CoOperateProxyOrderVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		Assert.paramNotNull(vo.getQueryChannelId(), "queryChannelId");
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/proxyagency/order/list", vo, String.class).getBody();
	}
}
