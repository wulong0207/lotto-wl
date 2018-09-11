package com.hhly.lotto.api.common.controller.cooperate.v1_0;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lotto.base.util.CoOperateChannelUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.cooperate.vo.CoOperateAgencyVO;

/**
 * 渠道
 *
 * @author huangchengfang1219
 * @date 2018年3月24日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class CoOperateAgencyCommonV10Controller extends BaseController {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CoOperateChannelUtil coOperateChannelUtil;
	@Value("${lotto_common_core_url}")
	private String lottoCommonCoreUrl;
	@Value("${lotto_report_url}")
	private String lottoReportUrl;

	/**
	 * 查询中介下的渠道列表
	 * 
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "info", method = RequestMethod.POST)
	public Object findAgencyChannelList(@RequestBody CoOperateAgencyVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/agency/info", vo, String.class).getBody();
	}

	/**
	 * 查询渠道的订单列表
	 * 
	 * @param channelId
	 * @return
	 */
	@RequestMapping(value = "channel/orders", method = RequestMethod.POST)
	public Object findChannelOrderList(@RequestBody CoOperateAgencyVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		Assert.paramNotNull(vo.getQueryChannelId(), "queryChannelId");
		return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/agency/channel/orders", vo, String.class).getBody();
	}

	/**
	 * 查询渠道最近交易数据（首页）
	 * 
	 * @return
	 */
	@RequestMapping(value = "channel/recently", method = RequestMethod.POST)
	public Object findChannelRecently(@RequestBody CoOperateAgencyVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		Assert.paramNotNull(vo.getQueryChannelId(), "queryChannelId");
		String channelId = coOperateChannelUtil.getChannelIdByToken(vo.getChannelToken());
		if (ObjectUtil.isBlank(channelId)) {
			return ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentChannelId", channelId);
		params.put("channelId", vo.getQueryChannelId());
		return restTemplate.getForObject(
				lottoReportUrl + "channelSale/findRecentlySum?parentChannelId={parentChannelId}&channelId={channelId}", String.class,
				params);
	}

	/**
	 * 查询渠道每日交易报表
	 * 
	 * @return
	 */
	@RequestMapping(value = "channel/day", method = RequestMethod.POST)
	public Object findChannelDay(@RequestBody CoOperateAgencyVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		Assert.paramNotNull(vo.getQueryChannelId(), "queryChannelId");
		String channelId = coOperateChannelUtil.getChannelIdByToken(vo.getChannelToken());
		if (ObjectUtil.isBlank(channelId)) {
			return ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentChannelId", channelId);
		params.put("channelId", vo.getQueryChannelId());
		params.put("startDate", DateUtil.convertDateToStr(vo.getStartDate(), DateUtil.DATE_FORMAT));
		params.put("endDate", DateUtil.convertDateToStr(vo.getEndDate(), DateUtil.DATE_FORMAT));
		params.put("pageIndex", vo.getPageIndex() == null ? Constants.NUM_0 : vo.getPageIndex());
		params.put("pageSize", vo.getPageSize() == null ? Constants.NUM_20 : vo.getPageSize());
		return restTemplate.getForObject(
				lottoReportUrl
						+ "channelSale/findDaySum?parentChannelId={parentChannelId}&channelId={channelId}&startDate={startDate}&endDate={endDate}&pageIndex={pageIndex}&pageSize={pageSize}",
				String.class, params);
	}

	/**
	 * 查询渠道每月交易报表
	 * 
	 * @return
	 */
	@RequestMapping(value = "channel/month", method = RequestMethod.POST)
	public Object findChannelMonth(@RequestBody CoOperateAgencyVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		Assert.paramNotNull(vo.getQueryChannelId(), "queryChannelId");
		String channelId = coOperateChannelUtil.getChannelIdByToken(vo.getChannelToken());
		if (ObjectUtil.isBlank(channelId)) {
			return ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("parentChannelId", channelId);
		params.put("channelId", vo.getQueryChannelId());
		params.put("startDate", DateUtil.convertDateToStr(vo.getStartMonth(), DateUtil.FORMAT_YYYY_MM));
		params.put("endDate", DateUtil.convertDateToStr(vo.getEndMonth(), DateUtil.FORMAT_YYYY_MM));
		params.put("pageIndex", vo.getPageIndex() == null ? Constants.NUM_0 : vo.getPageIndex());
		params.put("pageSize", vo.getPageSize() == null ? Constants.NUM_20 : vo.getPageSize());
		return restTemplate.getForObject(
				lottoReportUrl
						+ "channelSale/findMonthSum?parentChannelId={parentChannelId}&channelId={channelId}&startDate={startDate}&endDate={endDate}&pageIndex={pageIndex}&pageSize={pageSize}",
				String.class, params);
	}
}
