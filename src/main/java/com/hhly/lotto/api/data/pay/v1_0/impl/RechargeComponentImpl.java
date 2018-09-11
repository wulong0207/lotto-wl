package com.hhly.lotto.api.data.pay.v1_0.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hhly.lotto.api.data.pay.v1_0.PayCommon;
import com.hhly.lotto.api.data.pay.v1_0.PayComponent;
import com.hhly.lotto.api.data.pay.v1_0.RechargeComponent;
import com.hhly.paycore.remote.service.IOperateCouponService;
import com.hhly.paycore.remote.service.IPayChannelService;
import com.hhly.paycore.remote.service.IPayService;
import com.hhly.paycore.remote.service.IRechargeService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.PayConstants;
import com.hhly.skeleton.base.constants.PayConstants.TakenPlatformEnum;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.pay.bo.OperateCouponBO;
import com.hhly.skeleton.pay.bo.PayOrderDetailBO;
import com.hhly.skeleton.pay.bo.UserPayTypeBO;
import com.hhly.skeleton.pay.vo.OperateCouponQueryVO;
import com.hhly.skeleton.pay.vo.PayNotifyMockVO;
import com.hhly.skeleton.pay.vo.RechargeInputParamVO;
import com.hhly.skeleton.pay.vo.RechargeParamVO;
import com.hhly.skeleton.pay.vo.ToRechargeParamVO;
import com.hhly.usercore.remote.member.service.IMemberInfoService;

@Component
public class RechargeComponentImpl implements RechargeComponent {
	private static final Logger logger = LoggerFactory.getLogger(RechargeComponentImpl.class);

	@Resource
	private IPayService payService;
	@Resource
	private IMemberInfoService userInfoService;
	@Resource
	private IOperateCouponService operateCouponService;
	@Resource
	private IPayChannelService payChannelService;
	@Resource
	private IRechargeService rechargeService;
	@Resource
	private PayComponent payComponent;
	@Value("${real_client_ip}") // 只适用于测试环境，微信支付需要真实的出网客户端IP（这里用的是科兴的wifi外网ip）
	private String testClientIp;//

	@Override
	public ResultBO<?> toRecharge(HttpServletRequest request, TakenPlatformEnum platform) throws Exception {
		ResultBO<?> resultBO = PayCommon.validateToRechargeParams(request);
		if (resultBO.isError()) {
			logger.error("跳转充值，参数验证失败！" + resultBO.getMessage());
			return resultBO;
		}
		ToRechargeParamVO toRechargeParamVO = (ToRechargeParamVO) resultBO.getData();
		toRechargeParamVO.setPlatform(platform.getPlatForm());
		toRechargeParamVO.setRechargeType(PayConstants.RechargeTypeEnum.RECHARGE.getKey());
		String token = toRechargeParamVO.getToken();
		// 1、验证用户登录状态，获取用户信息
		try {
			resultBO = userInfoService.findUserInfoByToken(token);
			if (resultBO.isError()) {
				return resultBO;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return ResultBO.err(MessageCodeConstants.ACCOUNT_IS_NOT_FOUND_SERVICE);
		}

		PayOrderDetailBO payOrderDetailBO = new PayOrderDetailBO();
		// 2、获取支付渠道
		try {
			logger.debug("获取支付渠道请求参数：" + toRechargeParamVO.toString());
			List<UserPayTypeBO> payTypeList = payChannelService.findUserPayTypes(toRechargeParamVO);
			if (!ObjectUtil.isBlank(payTypeList)) {
				payOrderDetailBO.setPtl(payTypeList);
			}
		} catch (Exception e) {
			logger.error("获取支付渠道异常", e);
		}
		return ResultBO.ok(payOrderDetailBO);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultBO<?> findRechargeRed(RechargeInputParamVO param) throws Exception {
		String token = param.getToken();
		// 1、验证用户登录状态，获取用户信息
		ResultBO<?> resultBO = userInfoService.findUserInfoByToken(token);
		if (resultBO.isError()) {
			return resultBO;
		}
		PayOrderDetailBO payOrderDetailBO = new PayOrderDetailBO();
		// 调用红包接口，获取用户的所有可用红包
		String redUserType = PayConstants.RedTypeEnum.RECHARGE_DISCOUNT.getUseType();// 红包使用类型（充值）
		OperateCouponQueryVO operateCouponQuery = new OperateCouponQueryVO(token, param.getPlatformEnum().getKey(), null, redUserType);

		operateCouponQuery.setChannelId(ObjectUtil.isBlank(param.getChannelId()) ? PayConstants.ChannelEnum.UNKNOWN.getKey() : param.getChannelId());
		resultBO = operateCouponService.findCurPlatformCoupon(operateCouponQuery);
		if (resultBO.isOK()) {
			List<OperateCouponBO> couponList = ((List<OperateCouponBO>) resultBO.getData());
			if (!ObjectUtil.isBlank(couponList)) {
				payOrderDetailBO.setCl(couponList, param.getRechargeAmount(), param.getPlatformEnum().getKey());
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("获取充值红包返回：" + JSON.toJSONString(payOrderDetailBO));
		}
		return ResultBO.ok(payOrderDetailBO);
	}

	/**  
	* 方法说明: 统一充值
	* @auth: xiongJinGang
	* @param rechargeInputParam
	* @param clientIp
	* @time: 2017年5月12日 下午12:02:20
	* @return: ResultBO<?> 
	*/
	@Override
	public ResultBO<?> recharge(RechargeInputParamVO rechargeInputParam, String clientIp, TakenPlatformEnum platform) {
		RechargeParamVO rechargeParam = null;
		try {
			boolean isTest = payComponent.isTestProject();
			// 验证充值参数，包括充值金额等信息
			ResultBO<?> resultBO = PayCommon.validateRechargeParams(rechargeInputParam, isTest, platform);
			if (resultBO.isError()) {
				return resultBO;
			}
			rechargeParam = (RechargeParamVO) resultBO.getData();
			String token = rechargeParam.getToken();
			// 1、验证用户登录状态，获取用户信息
			resultBO = userInfoService.findUserInfoByToken(token);
			if (resultBO.isError()) {
				return resultBO;
			}
			rechargeParam.setTest(isTest);
			if (isTest) {
				clientIp = testClientIp;// 测试环境下，很多支付要用客户端真实的出网IP
			}

			rechargeParam.setClientIp(clientIp);
			rechargeParam.setPlatform(platform.getKey());
			if (logger.isDebugEnabled()) {
				logger.debug("充值请求参数：" + rechargeParam.toString());
			}
			ResultBO<?> rechargeReq = rechargeService.recharge(rechargeParam);
			if (logger.isDebugEnabled()) {
				logger.debug("返回前端请求参数：" + JSON.toJSONString(rechargeReq));
			}
			return rechargeReq;
		} catch (Exception e) {
			logger.error("充值请求异常，支付参数：" + JSON.toJSONString(rechargeParam), e);
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, e.getMessage());
		}
	}

	@Override
	public ResultBO<?> rechargeNotifyMock(PayNotifyMockVO payNotifyMockVO) throws Exception {
		boolean isTest = payComponent.isTestProject();
		if (!isTest) {// 正式环境不能调用该接口
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS);
		}
		return rechargeService.rechargeNotifyMock(payNotifyMockVO);
	}

	@Override
	public ResultBO<?> rechargeResult(RechargeParamVO rechargeParam) throws Exception {
		return rechargeService.rechargeResult(rechargeParam);
	}

	@Override
	public ResultBO<?> rechargeNotify(Map<String, String> notifyMap) throws Exception {
		return rechargeService.rechargeNotify(notifyMap);
	}

}
