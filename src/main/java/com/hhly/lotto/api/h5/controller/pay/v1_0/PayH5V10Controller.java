package com.hhly.lotto.api.h5.controller.pay.v1_0;

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
import com.hhly.skeleton.pay.vo.PayParamVO;
import com.hhly.skeleton.pay.vo.ToPayInputParamVO;

@RestController
@RequestMapping("/h5/v1.0/payCenter")
public class PayH5V10Controller extends BaseController {
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
		String jsApi = request.getParameter("jsApi");// 是否是微信公众号支付，1是，空不是。前端不肯将clientType传7，只能加一个参数来判断转成7
		if (!ObjectUtil.isBlank(jsApi) && jsApi.equals("1")) {
			clientType = "7";// 微信公众号支付
		}
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
		String jsApi = payInputParam.getJsApi();// 是否是微信公众号支付，1是，空不是。前端不肯将clientType传7，只能加一个参数来判断转成7
		String clientType = payInputParam.getClientType();
		if (!ObjectUtil.isBlank(jsApi) && jsApi.equals("1")) {
			clientType = "7";// 微信公众号支付
		}
		ResultBO<?> resultBO = payComponent.pay(payInputParam, getIp(request), PayCommon.transPlatform(clientType));
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
		String jsApi = toPayInputParamVO.getJsApi();// 是否是微信公众号支付，1是，空不是。前端不肯将clientType传7，只能加一个参数来判断转成7
		String clientType = toPayInputParamVO.getClientType();
		if (!ObjectUtil.isBlank(jsApi) && jsApi.equals("1")) {
			clientType = "7";// 微信公众号支付
		}
		ResultBO<?> resultBO = payComponent.toBatchPay(toPayInputParamVO, PayCommon.transPlatform(clientType));
		if (resultBO.isError()) {
			logger.error("批量支付请求失败【" + resultBO.getMessage() + "】，请求参数：" + toPayInputParamVO.toString());
		}
		return resultBO;
	}

	/**  
	* 方法说明: 支付结果查询，供客户端来获取支付结果
	* @auth: xiongJinGang
	* @param payParam 只需要token和transCode
	* @throws Exception
	* @time: 2017年5月23日 下午8:41:02
	* @return: Object 
	*/
	@RequestMapping(value = "/payResult", method = RequestMethod.GET)
	public Object payResult(PayParamVO payParam) throws Exception {
		return payComponent.payResult(payParam);
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
