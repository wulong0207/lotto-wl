package com.hhly.lotto.api.h5.pay.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.data.pay.v1_0.PayCommon;
import com.hhly.lotto.api.data.pay.v1_0.RechargeComponent;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.pay.vo.PayNotifyMockVO;
import com.hhly.skeleton.pay.vo.RechargeInputParamVO;

/**
 * @desc H5充值中心
 * @author xiongJinGang
 * @date 2017年4月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/h5/rechargeCenter")
public class RechargeH5Controller extends BaseController {

	@Resource
	private RechargeComponent rechargeComponent;

	/**  
	* 方法说明: 跳转到充值页面（获取红包列表，支付渠道等信息）
	* @auth: xiongJinGang
	* @param token 登录token 
	* @param orderCode 订单号
	* @throws Exception
	* @time: 2017年3月21日 下午4:39:59
	* @return: Object 
	*/
	@RequestMapping(value = "/toRecharge", method = RequestMethod.GET)
	public Object toRecharge(HttpServletRequest request, String clientType) throws Exception {
		return rechargeComponent.toRecharge(request, PayCommon.transPlatform(clientType));
	}

	/**  
	* 方法说明: 获取充值红包
	* @auth: xiongJinGang
	* @param rechargeInputParam
	* @throws Exception
	* @time: 2017年5月18日 上午10:34:04
	* @return: Object 
	*/
	@RequestMapping(value = "/findRechargeRed", method = RequestMethod.POST)
	public Object findRechargeRed(@RequestBody RechargeInputParamVO rechargeInputParam) throws Exception {
		rechargeInputParam.setPlatformEnum(PayCommon.transPlatform(rechargeInputParam.getClientType()));
		return rechargeComponent.findRechargeRed(rechargeInputParam);
	}

	/**  
	* 方法说明:  统一充值接口
	* @auth: xiongJinGang
	* @throws Exception
	* @time: 2017年4月8日 下午1:16:17
	* @return: Object 
	*/
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public Object recharge(@RequestBody RechargeInputParamVO rechargeInputParam, HttpServletRequest request) throws Exception {
		return rechargeComponent.recharge(rechargeInputParam, getIp(request), PayCommon.transPlatform(rechargeInputParam.getClientType()));
	}

	/**  
	* 方法说明: 模拟充值回调
	* @auth: xiongJinGang
	* @param payNotifyMockVO
	* @param request
	* @throws Exception
	* @time: 2017年5月25日 下午5:47:41
	* @return: Object 
	*/
	@RequestMapping(value = "/rechargeNotifyMock", method = RequestMethod.GET)
	public Object rechargeNotifyMock(PayNotifyMockVO payNotifyMockVO, HttpServletRequest request) throws Exception {
		if (ObjectUtil.isBlank(payNotifyMockVO.getTransCode())) {
			return PayCommon.mockResult(0, "交易号为空");
		}
		if (ObjectUtil.isBlank(payNotifyMockVO.getTransAmount())) {
			return PayCommon.mockResult(0, "交易金额为空");
		}
		return rechargeComponent.rechargeNotifyMock(payNotifyMockVO);
	}
}
