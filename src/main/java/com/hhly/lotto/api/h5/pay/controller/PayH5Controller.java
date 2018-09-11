package com.hhly.lotto.api.h5.pay.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.data.pay.v1_0.PayCommon;
import com.hhly.lotto.api.data.pay.v1_0.PayComponent;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.pay.vo.PayInputParamVO;
import com.hhly.skeleton.pay.vo.PayNotifyMockVO;
import com.hhly.skeleton.pay.vo.ToPayInputParamVO;

@RestController
@RequestMapping("/h5/payCenter")
public class PayH5Controller extends BaseController {
	@Resource
	private PayComponent payComponent;

	/**  
	* 方法说明: 跳转到支付页面（获取订单详情，红包列表，支付渠道等信息）
	* @auth: xiongJinGang
	* @param token 登录token 
	* @param orderCode 订单号
	* @throws Exception
	* @time: 2017年3月21日 下午4:39:59
	* @return: Object 
	*/
	@RequestMapping(value = "/toPay", method = RequestMethod.GET)
	public Object toPay(HttpServletRequest request) throws Exception {
		String clientType = request.getParameter("clientType");
		ResultBO<?> resultBO = payComponent.toPay(request, PayCommon.transPlatform(clientType));
		if (resultBO.isError()) {
			Map<String, String> notifyMap = PayCommon.getReqParams(request);
			logger.error("支付请求失败【" + resultBO.getMessage() + "】，请求参数：" + notifyMap.toString());
		}
		return resultBO;
	}

	/**  
	* 方法说明: 统一支付接口
	* @auth: xiongJinGang
	* @param payParam
	* @throws Exception
	* @time: 2017年3月21日 下午4:40:20
	* @return: Object 
	*/
	@RequestMapping(value = "/pay", method = RequestMethod.POST)
	public Object pay(@RequestBody PayInputParamVO payInputParam, HttpServletRequest request) throws Exception {
		ResultBO<?> resultBO = payComponent.pay(payInputParam, getIp(request), PayCommon.transPlatform(payInputParam.getClientType()));
		if (resultBO.isError()) {
			logger.error("统一支付失败【" + resultBO.getMessage() + "】，请求参数：" + payInputParam.toString());
		}
		return resultBO;
	}

	/**  
	* 方法说明: 批量支付跳转（获取订单详情，支付渠道，钱包余额等信息）
	* @auth: xiongJinGang
	* @param toPayInputParamVO
	* @throws Exception
	* @time: 2017年5月10日 下午2:17:40
	* @return: Object 
	*/
	@RequestMapping(value = "/toBatchPay", method = RequestMethod.POST)
	public Object toBatchPay(@RequestBody ToPayInputParamVO toPayInputParamVO) throws Exception {
		ResultBO<?> resultBO = payComponent.toBatchPay(toPayInputParamVO, PayCommon.transPlatform(toPayInputParamVO.getClientType()));
		if (resultBO.isError()) {
			logger.error("批量支付请求失败【" + resultBO.getMessage() + "】，请求参数：" + toPayInputParamVO.toString());
		}
		return resultBO;
	}

	/**  
	* 方法说明: 
	* @auth: xiongJinGang
	* @param request
	* @throws Exception
	* @time: 2017年5月12日 上午11:44:09
	* @return: Object 
	*/
	@RequestMapping(value = "/payNotifyMock", method = RequestMethod.GET)
	public Object payNotifyMock(PayNotifyMockVO payNotifyMockVO, HttpServletRequest request) throws Exception {
		if (ObjectUtil.isBlank(payNotifyMockVO.getTransCode())) {
			return PayCommon.mockResult(0, "交易号为空");
		}
		if (ObjectUtil.isBlank(payNotifyMockVO.getTransAmount())) {
			return PayCommon.mockResult(0, "交易金额为空");
		}
		return payComponent.payNotifyMock(payNotifyMockVO);
	}

}
