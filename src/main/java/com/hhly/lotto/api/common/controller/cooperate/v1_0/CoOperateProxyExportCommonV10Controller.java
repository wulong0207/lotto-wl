package com.hhly.lotto.api.common.controller.cooperate.v1_0;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.ExcelUtil;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.lotto.base.cooperate.bo.CoOperateProxyOrderBO;
import com.hhly.skeleton.lotto.base.cooperate.bo.CoOperateProxyRechargeBO;
import com.hhly.skeleton.lotto.base.cooperate.vo.CoOperateProxyOrderVO;
import com.hhly.skeleton.lotto.base.cooperate.vo.CoOperateProxyRechargeVO;

/**
 * 代理商户导出
 *
 * @author huangchengfang1219
 * @date 2018年6月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class CoOperateProxyExportCommonV10Controller {

	@Autowired
	private RestTemplate restTemplate;
	@Value("${lotto_common_core_url}")
	private String lottoCommonCoreUrl;

	@RequestMapping(value = "recharge/export", method = { RequestMethod.GET, RequestMethod.POST })
	public Object exportRechargeList(CoOperateProxyRechargeVO vo, HttpServletResponse response) throws Exception {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		vo.setQueryChannelId(null);
		String resultStr = restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/proxy/recharge/listall", vo, String.class).getBody();
		ResultBO<List<CoOperateProxyRechargeBO>> result = JsonUtil.jsonToJackObject(resultStr,
				new TypeReference<ResultBO<List<CoOperateProxyRechargeBO>>>() {
				});
		response.setCharacterEncoding("UTF-8");
		if (result.isError()) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultStr);
			return null;
		}
		response.setContentType("application/x-msdownload");
		String fileName = String.format("充值明细_%s.xls", DateUtil.getNow("yyMMddHHmmss"));
		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		Map<String, List<CoOperateProxyRechargeBO>> excelMap = new HashMap<>();
		excelMap.put("充值明细", result.getData());
		ExcelUtil.dataToExeclByStream(excelMap, response.getOutputStream());
		return null;
	}

	@RequestMapping(value = "order/export", method = { RequestMethod.GET, RequestMethod.POST })
	public Object exportOrderList(CoOperateProxyOrderVO vo, HttpServletResponse response) throws Exception {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getChannelToken(), "channelToken");
		vo.setQueryChannelId(null);
		String resultStr = restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/proxy/order/listall", vo, String.class).getBody();
		ResultBO<List<CoOperateProxyOrderBO>> result = JsonUtil.jsonToJackObject(resultStr,
				new TypeReference<ResultBO<List<CoOperateProxyOrderBO>>>() {
				});
		response.setCharacterEncoding("UTF-8");
		if (result.isError()) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultStr);
			return null;
		}
		response.setContentType("application/x-msdownload");
		String fileName = String.format("订单明细_%s.xls", DateUtil.getNow("yyMMddHHmmss"));
		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
		Map<String, List<CoOperateProxyOrderBO>> excelMap = new HashMap<>();
		excelMap.put("订单明细", result.getData());
		ExcelUtil.dataToExeclByStream(excelMap, response.getOutputStream());
		return null;
	}

}
