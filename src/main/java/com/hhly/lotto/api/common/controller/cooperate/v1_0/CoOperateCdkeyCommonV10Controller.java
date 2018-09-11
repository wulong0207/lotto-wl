package com.hhly.lotto.api.common.controller.cooperate.v1_0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.cooperate.vo.CoOperateCdkeyExchangeVO;

/**
 * 积分兑换接口
 *
 * @author huangchengfang1219
 * @date 2018年3月21日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class CoOperateCdkeyCommonV10Controller extends BaseController {

	@Autowired
	private RestTemplate restTemplate;
	@Value("${lotto_common_core_url}")
	private String lottoCommonCoreUrl;

	/**
	 * 兑换码详情
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "valid", method = RequestMethod.POST)
	public Object valid(@RequestBody CoOperateCdkeyExchangeVO vo) {
		Assert.paramNotNull(vo.getCdkey(), "cdkey");
		Assert.paramNotNull(vo.getToken(), "token");
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/cdkey/valid", vo, String.class).getBody();
	}
}
