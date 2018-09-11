package com.hhly.lotto.api.ios.pay.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.data.pay.v1_0.PayComponent;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.constants.PayConstants;
import com.hhly.skeleton.pay.vo.PayInputParamVO;
import com.hhly.skeleton.pay.vo.ToPayInputParamVO;

@RestController
@RequestMapping("/ios/payCenter")
public class PayIosController extends BaseController {
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
		return payComponent.toPay(request, PayConstants.TakenPlatformEnum.IOS);
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
		return payComponent.toBatchPay(toPayInputParamVO, PayConstants.TakenPlatformEnum.IOS);
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
		return payComponent.pay(payInputParam, getIp(request), PayConstants.TakenPlatformEnum.IOS);
	}

}
