package com.hhly.lotto.api.common.controller.cooperate.v1_0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hhly.lotto.base.util.CoOperateChannelUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.cms.report.bo.ExchangeRecentlySumBO;
import com.hhly.skeleton.lotto.base.cooperate.bo.CoOperateProxyInfoBO;
import com.hhly.skeleton.lotto.base.cooperate.vo.CoOperateProxyOrderVO;
import com.hhly.skeleton.lotto.base.cooperate.vo.CoOperateProxyRechargeVO;
import com.hhly.skeleton.lotto.base.cooperate.vo.CoOperateProxyVO;

public class CoOperateProxyCommonV10Controller {

	@Autowired
	private RestTemplate restTemplate;
	@SuppressWarnings("unused")
	@Autowired
	private CoOperateChannelUtil coOperateChannelUtil;
	@Value("${lotto_common_core_url}")
	private String lottoCommonCoreUrl;
	@Value("${lotto_report_url}")
	private String lottoReportUrl;

	@RequestMapping(value = "info", method = RequestMethod.POST)
	public Object findProxyInfo(@RequestBody CoOperateProxyVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		vo.setQueryChannelId(null);
		String resultStr = restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/proxy/info", vo, String.class).getBody();
		ResultBO<CoOperateProxyInfoBO> result = JsonUtil.jsonToJackObject(resultStr, new TypeReference<ResultBO<CoOperateProxyInfoBO>>() {
		});
		if (result == null || result.isError() || result.getData() == null) {
			return result;
		}
		CoOperateProxyInfoBO proxyInfo = result.getData();
		String recentlyResultStr = restTemplate.getForObject(lottoReportUrl + "channelSale/findRecentlySum?channelId={channelId}",
				String.class, proxyInfo.getChannelId());
		ResultBO<ExchangeRecentlySumBO> recentlyResult = JsonUtil.jsonToJackObject(recentlyResultStr,
				new TypeReference<ResultBO<ExchangeRecentlySumBO>>() {
				});
		if (recentlyResult == null || recentlyResult.isError() || recentlyResult.getData() == null) {
			return recentlyResult;
		}
		ExchangeRecentlySumBO sumBO = recentlyResult.getData();
		proxyInfo.setOrderToday(sumBO.getTodaySaleMoney());
		proxyInfo.setOrderWeek(sumBO.getWeekSaleMoney());
		proxyInfo.setOrderMonth(sumBO.getMonthSaleMoney());
		return result;
	}

	@RequestMapping(value = "recharge/list", method = RequestMethod.POST)
	public Object findRechargeList(@RequestBody CoOperateProxyRechargeVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		vo.setQueryChannelId(null);
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/proxy/recharge/list", vo, String.class).getBody();
	}

	@RequestMapping(value = "order/list", method = RequestMethod.POST)
	public Object findOrderList(@RequestBody CoOperateProxyOrderVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		vo.setQueryChannelId(null);
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/proxy/order/list", vo, String.class).getBody();
	}
}
