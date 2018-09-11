package com.hhly.lotto.api.common.controller.cooperate.v1_0;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.hhly.lotto.base.common.HeaderParam;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lotto.base.util.CoOperateChannelUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.cms.report.bo.ExchangeRecentlySumBO;
import com.hhly.skeleton.lotto.base.cooperate.bo.CoOperateChannelAndLogBO;
import com.hhly.skeleton.lotto.base.cooperate.bo.CoOperateChannelInfoBO;
import com.hhly.skeleton.lotto.base.cooperate.vo.CoOperateChannelInfoVO;
import com.hhly.skeleton.lotto.base.cooperate.vo.CoOperateRecordQueryVO;

/**
 * 渠道
 *
 * @author huangchengfang1219
 * @date 2018年3月24日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class CoOperateChannelCommonV10Controller extends BaseController {

	@Autowired
	private RestTemplate restTemplate;	
	@Autowired
	private CoOperateChannelUtil channelUtil;
	@Value("${lotto_common_core_url}")
	private String lottoCommonCoreUrl;
	@Value("${lotto_report_url}")
	private String lottoReportUrl;
	/**
	 * 登录
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public Object login(@RequestBody CoOperateChannelInfoVO vo, HttpServletRequest request) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelName());
		Assert.paramNotNull(vo.getPassword(), "password");
		vo.setIp(getIp(request));
		HeaderParam header = getHeaderParam(request);
		vo.setPlatform(header.getPlatformId().shortValue());
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/channel/login", vo, String.class).getBody();
	}

	/**
	 * 退出登录
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public Object logout(@RequestBody CoOperateChannelInfoVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/channel/logout", vo, String.class).getBody();
	}
	
	/**
	 * 返回登录用户基本信息
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "info/base", method = RequestMethod.POST)
	public Object findBaseInfo(@RequestBody CoOperateChannelInfoVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/channel/info/base", vo, String.class).getBody();
	}
	
	/**
	 * 查询余额类商户渠道信息
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "findChannelBalAndHis", method = RequestMethod.GET)
	public Object findChannelBalAndHis(String channelToken) {
		Assert.paramNotNull(channelToken, "channelToken");
		CoOperateChannelInfoBO coOperateChannelInfoBO = channelUtil.getChannelByToken(channelToken);
		if (coOperateChannelInfoBO == null) {
			return ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE);
		}
		ResultBO<?> resultBO = restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/channel/findChannelAndHis", coOperateChannelInfoBO,ResultBO.class).getBody();
		CoOperateChannelAndLogBO channelBO = JSON.parseObject(JSON.toJSONString(resultBO.getData()), CoOperateChannelAndLogBO.class);		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("channelId", coOperateChannelInfoBO.getChannelId());
		ResultBO<?> sumBOResult =  restTemplate.getForObject(
				lottoReportUrl + "channelSale/findRecentlySum?channelId={channelId}", ResultBO.class,
				params);
		ExchangeRecentlySumBO sumBO = JSON.parseObject(JSON.toJSONString(sumBOResult.getData()), ExchangeRecentlySumBO.class);			
		channelBO.setOrderToday(sumBO.getTodaySaleMoney());
		channelBO.setOrderWeek(sumBO.getWeekSaleMoney());
		channelBO.setOrderMonth(sumBO.getMonthSaleMoney());
		return ResultBO.ok(channelBO);
	}
	/**
	 * 查询余额类商户渠道信息+彩种信息
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "findChannelHisAndLott", method = RequestMethod.GET)
	public Object getChannelHisAndLottery(String channelToken){
		Assert.paramNotNull(channelToken, "channelToken");
		CoOperateChannelInfoBO coOperateChannelInfoBO = channelUtil.getChannelByToken(channelToken);
		if (coOperateChannelInfoBO == null) {
			return ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE);
		}
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/channel/findChannelHisAndLott", coOperateChannelInfoBO,String.class).getBody();	
	}
	/**
	 * 查询余额类渠道订单信息
	 * @param channelId
	 */
	@RequestMapping(value = "findMerchantBalanceInfo", method = RequestMethod.POST)
	public Object findMerchantBalanceInfo(@RequestBody CoOperateRecordQueryVO vo){
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		CoOperateChannelInfoBO coOperateChannelInfoBO = channelUtil.getChannelByToken(vo.getChannelToken());
		if (coOperateChannelInfoBO == null) {
			return ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE);
		}
		if(vo.getPageIndex()==null)vo.setPageIndex(Constants.NUM_0);
		if(vo.getPageSize()==null)vo.setPageIndex(Constants.NUM_10);
		vo.setChannelId(coOperateChannelInfoBO.getChannelId());
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/channel/findMerchantBalanceInfo", vo,String.class).getBody();
	}
	
	/**
	 * 查询库存类渠道订单信息
	 * @param channelId
	 */
	@RequestMapping(value = "findMerchantNumInfo", method = RequestMethod.POST)
	public Object findMerchantNumInfo(@RequestBody CoOperateRecordQueryVO vo){
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		CoOperateChannelInfoBO coOperateChannelInfoBO = channelUtil.getChannelByToken(vo.getChannelToken());
		if (coOperateChannelInfoBO == null) {
			return ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE);
		}
		if(vo.getPageIndex()==null)vo.setPageIndex(Constants.NUM_0);
		if(vo.getPageSize()==null)vo.setPageIndex(Constants.NUM_10);
		vo.setChannelId(coOperateChannelInfoBO.getChannelId());
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/channel/findMerchantNumInfo", vo,String.class).getBody();
	}
	
	/**
	 * 修改密码
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "modify/password", method = RequestMethod.POST)
	public Object modifyPassword(@RequestBody CoOperateChannelInfoVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		Assert.paramNotNull(vo.getPassword(), "password");
		Assert.paramNotNull(vo.getNewPassword(), "newPassword");
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/channel/modify/password", vo, String.class).getBody();
	}
}
