package com.hhly.lotto.api.data.pay.v1_0.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hhly.lotto.api.common.ActivityValid;
import com.hhly.lotto.api.data.pay.v1_0.PayCommon;
import com.hhly.lotto.api.data.pay.v1_0.PayComponent;
import com.hhly.lottoactivity.remote.service.IActivityAddedService;
import com.hhly.lottoactivity.remote.service.IActivityService;
import com.hhly.lottocore.remote.order.service.IOrderSearchService;
import com.hhly.lottocore.remote.ordercopy.service.IOrderCopyService;
import com.hhly.paycore.remote.service.IOperateCouponService;
import com.hhly.paycore.remote.service.IPayChannelService;
import com.hhly.paycore.remote.service.IPayService;
import com.hhly.paycore.remote.service.ITransRechargeService;
import com.hhly.paycore.remote.service.IUserWalletService;
import com.hhly.skeleton.activity.bo.JzstActivityInfo;
import com.hhly.skeleton.activity.bo.JzstActivityRule;
import com.hhly.skeleton.activity.bo.YfgcActivityBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.OrderGroupConstants;
import com.hhly.skeleton.base.constants.PayConstants;
import com.hhly.skeleton.base.constants.PayConstants.BatchPayEnum;
import com.hhly.skeleton.base.constants.PayConstants.TakenPlatformEnum;
import com.hhly.skeleton.base.util.MathUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.group.bo.OrderDetailGroupInfoBO;
import com.hhly.skeleton.lotto.base.order.bo.OrderBaseInfoBO;
import com.hhly.skeleton.lotto.base.order.vo.OrderAddVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderInfoVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderQueryVo;
import com.hhly.skeleton.lotto.base.ordercopy.bo.OrderCopyPayInfoBO;
import com.hhly.skeleton.pay.bo.OperateCouponBO;
import com.hhly.skeleton.pay.bo.PayOrderDetailBO;
import com.hhly.skeleton.pay.bo.UserPayTypeBO;
import com.hhly.skeleton.pay.trans.vo.ToPushPayVO;
import com.hhly.skeleton.pay.vo.OperateCouponQueryVO;
import com.hhly.skeleton.pay.vo.PayInputParamVO;
import com.hhly.skeleton.pay.vo.PayNotifyMockVO;
import com.hhly.skeleton.pay.vo.PayParamVO;
import com.hhly.skeleton.pay.vo.PayQueryParamVO;
import com.hhly.skeleton.pay.vo.PayUserWalletVO;
import com.hhly.skeleton.pay.vo.RefundParamVO;
import com.hhly.skeleton.pay.vo.ToPayEndTimeVO;
import com.hhly.skeleton.pay.vo.ToPayInputParamVO;
import com.hhly.skeleton.pay.vo.ToPayParamVO;
import com.hhly.skeleton.pay.vo.ToRechargeParamVO;
import com.hhly.skeleton.user.bo.UserInfoBO;
import com.hhly.skeleton.user.bo.UserWalletBO;
import com.hhly.usercore.remote.member.service.IMemberInfoService;

@Component
public class PayComponentImpl implements PayComponent {
	private Logger logger = Logger.getLogger(PayComponentImpl.class);
	@Resource
	private IPayService payService;
	@Resource
	private IMemberInfoService userInfoService;
	@Resource
	private IUserWalletService userWalletService;
	@Resource
	private IPayChannelService payChannelService;
	@Resource
	private ITransRechargeService transRechargeService;
	@Resource
	private IOrderSearchService orderSearchService;
	@Resource
	private IOperateCouponService operateCouponService;
	@Resource
	private IActivityService activityService;
	@Resource
	private IActivityAddedService activityAddedService;
	@Resource
	private ActivityValid activityValid;
	@Resource
	private IOrderCopyService orderCopyService;
	@Value("${pay.context.istest}")
	private String isTest;// 是否为测试环境（true是 false否）
	@Value("${footboll.activity.code}")
	private String footbollActivityCode;// 竟足活动编号
	@Value("${basketboll.activity.code}")
	private String basketbollActivityCode;// 竟篮活动编号
	@Value("${redcent.activity.code}")
	private String gdRedCentActivityCode;// 广东十一选五1分钱活动编号
	@Value("${jx.redcent.activity.code}")
	private String jxRedCentActivityCode;// 江西十一选五1分钱活动编号
	@Value("${real_client_ip}")
	// 只适用于测试环境，微信支付需要真实的出网客户端IP（这里用的是科兴的wifi外网ip）
	private String testClientIp;//
	@Value("${before_file_url}")
	private String imgUrl;// 访问图片前缀

	@Override
	public ResultBO<?> toPay(HttpServletRequest request, TakenPlatformEnum platform) {
		ResultBO<?> resultBO = PayCommon.validateToPayParams(request);
		if (resultBO.isError()) {
			logger.error("跳转支付，参数验证失败！" + resultBO.getMessage());
			return resultBO;
		}
		ToPayParamVO toPayParamVO = (ToPayParamVO) resultBO.getData();
		String token = toPayParamVO.getToken();
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
		UserInfoBO userInfo = (UserInfoBO) resultBO.getData();

		// 2、调用订单服务，获取订单详情
		OrderBaseInfoBO orderBaseInfo = this.findOrderInfo(toPayParamVO.getOrderCode(), token, userInfo);

		// 验证订单号是否正确，验证订单支付状态是否已支付，返回支付金额和支付剩余时间
		resultBO = PayCommon.validateOrder(orderBaseInfo);
		if (resultBO.isError()) {
			logger.error("验证订单【" + toPayParamVO.getOrderCode() + "】失败！" + resultBO.getMessage());
			return resultBO;
		}
		return getWalletAndCoupon(resultBO, toPayParamVO, platform, PayConstants.BatchPayEnum.SINGLE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultBO<?> toBatchPay(ToPayInputParamVO toPayInputParamVO, TakenPlatformEnum platform) {
		logger.debug("批量支付请求参数：" + JSON.toJSONString(toPayInputParamVO));
		ResultBO<?> resultBO = PayCommon.validateToPayParams(toPayInputParamVO);
		if (resultBO.isError()) {
			logger.info("批量支付参数错误：" + resultBO.getMessage());
			return resultBO;
		}
		ToPayParamVO toPayParamVO = (ToPayParamVO) resultBO.getData();
		// 1、验证用户登录状态，获取用户信息
		try {
			resultBO = userInfoService.findUserInfoByToken(toPayParamVO.getToken());
			if (resultBO.isError()) {
				return resultBO;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.ACCOUNT_IS_NOT_FOUND_SERVICE);
		}
		// 2、调用订单服务，获取订单详情
		resultBO = this.findOrderList(toPayParamVO.getOrderQueryVoList(), toPayParamVO.getToken());
		if (resultBO.isError()) {
			logger.error("获取订单详情【" + toPayParamVO.getOrderCode() + "】返回：" + resultBO.getMessage());
			return resultBO;
		}
		List<OrderBaseInfoBO> list = (List<OrderBaseInfoBO>) resultBO.getData();

		// 验证订单号是否正确，验证订单支付状态是否已支付
		resultBO = PayCommon.validateOrder(list);
		if (resultBO.isError()) {
			logger.error("验证订单【" + toPayInputParamVO.getOrderCode() + "】失败！" + resultBO.getMessage());
			return resultBO;
		}
		return getWalletAndCoupon(resultBO, toPayParamVO, platform, PayConstants.BatchPayEnum.BATCH);
	}

	@Override
	public ResultBO<?> toPushPay(HttpServletRequest request, TakenPlatformEnum platform) {
		ResultBO<?> resultBO = PayCommon.validateToPushParams(request);
		if (resultBO.isError()) {
			logger.error("推单跳转支付，参数验证失败！" + resultBO.getMessage());
			return resultBO;
		}
		ToPushPayVO pushPay = (ToPushPayVO) resultBO.getData();
		// 1、验证用户登录状态，获取用户信息
		resultBO = userInfoService.findUserInfoByToken(pushPay.getToken());
		if (resultBO.isError()) {
			return resultBO;
		}
		// 2、调用推单服务，获取推单详情
		try {
			resultBO = orderCopyService.getCopyOrderInfoForPay(Integer.parseInt(pushPay.getIssueId()), pushPay.getToken());
			if (resultBO.isError()) {
				logger.error("获取推单方案详情【" + pushPay.getIssueId() + "】返回：" + resultBO.getMessage());
				return resultBO;
			}
		} catch (Exception e) {
			logger.error("获取推单方案详情【" + pushPay.getIssueId() + "】异常：", e);
			return ResultBO.err(MessageCodeConstants.PLAN_CONTENT_IS_NULL_FIELD);
		}
		OrderCopyPayInfoBO orderCopyPayInfoBO = (OrderCopyPayInfoBO) resultBO.getData();

		// 验证方案支付截止时间
		resultBO = PayCommon.validatePushOrder(orderCopyPayInfoBO);
		if (resultBO.isError()) {
			logger.error("验证推单方案详情【" + pushPay.getIssueId() + "】失败！" + resultBO.getMessage());
			return resultBO;
		}
		ToPayEndTimeVO toPayEndTimeVO = (ToPayEndTimeVO) resultBO.getData();
		// 获取用户钱包记录
		resultBO = userWalletService.findUserWallet(pushPay.getToken());
		/**用户钱包*/
		UserWalletBO userWalletBO = (UserWalletBO) resultBO.getData();

		PayOrderDetailBO payOrderDetailBO = new PayOrderDetailBO();
		payOrderDetailBO.setLpt(toPayEndTimeVO.getLeavePayTime());// 支付截止时间

		PayUserWalletVO payUserWalletVO = null;
		if (!ObjectUtil.isBlank(userWalletBO)) {
			payUserWalletVO = new PayUserWalletVO(userWalletBO);
			payOrderDetailBO.setUw(payUserWalletVO);// 用户钱包余额
		}
		payOrderDetailBO.setPi(orderCopyPayInfoBO, toPayEndTimeVO);
		// 4、获取支付渠道
		try {
			ToRechargeParamVO toRechargeParamVO = new ToRechargeParamVO(pushPay.getToken(), null, null, platform.getPlatForm(), PayConstants.RechargeTypeEnum.PAY.getKey());
			List<UserPayTypeBO> payTypeList = payChannelService.findUserPayTypes(toRechargeParamVO);
			if (!ObjectUtil.isBlank(payTypeList)) {
				payOrderDetailBO.setPtl(payTypeList);
			}
		} catch (Exception e) {
			logger.error("获取支付渠道异常", e);
			return ResultBO.err(MessageCodeConstants.TRANS_PAY_CHANNEL_IS_ERROR_SERVICE);
		}
		return ResultBO.ok(payOrderDetailBO);
	}

	/**  
	* 方法说明: 获得钱包信息及优惠券
	* @auth: xiongJinGang
	* @param resultBO
	* @param toPayParam
	* @param platform
	* @param batchPayEnum
	* @time: 2018年5月2日 下午2:37:30
	* @return: ResultBO<?> 
	*/
	private ResultBO<?> getWalletAndCoupon(ResultBO<?> resultBO, ToPayParamVO toPayParam, TakenPlatformEnum platform, BatchPayEnum batchPayEnum) {
		ToPayEndTimeVO toPayEndTimeVO = (ToPayEndTimeVO) resultBO.getData();
		OrderBaseInfoBO orderBaseInfo = toPayEndTimeVO.getOrderBaseInfo();

		// 验证合买订单
		ResultBO<?> resultBO2 = validateOrderGroupInfo(toPayParam, batchPayEnum, toPayEndTimeVO, orderBaseInfo);
		if (resultBO2.isError()) {
			return resultBO2;
		}

		// 验证活动订单
		String activityCode = orderBaseInfo.getActivityCode();// 活动编号
		String token = toPayParam.getToken();
		if (!ObjectUtil.isBlank(activityCode)) {
			resultBO = getActivityAmount(activityCode, token, orderBaseInfo);
			if (resultBO.isError()) {
				return resultBO;
			}
			// 折扣金额
			Double discountAmount = (Double) resultBO.getData();
			// 支付金额 = 订单金额 - 折扣金额
			Double activityAmount = MathUtil.sub(orderBaseInfo.getOrderAmount(), discountAmount);
			toPayEndTimeVO.setOrderAmount(activityAmount);
		}
		// 获取用户钱包记录
		resultBO = userWalletService.findUserWallet(token);

		PayOrderDetailBO payOrderDetailBO = new PayOrderDetailBO();

		payOrderDetailBO.setLpt(toPayEndTimeVO.getLeavePayTime());// 支付截止时间

		/**用户钱包*/
		UserWalletBO userWalletBO = (UserWalletBO) resultBO.getData();
		PayUserWalletVO payUserWalletVO = null;
		if (!ObjectUtil.isBlank(userWalletBO)) {
			payUserWalletVO = new PayUserWalletVO(userWalletBO);
			payOrderDetailBO.setUw(payUserWalletVO);// 用户钱包余额
		}

		// 获取订单及红包信息
		queryAndSetOrderDetail(toPayParam, platform, batchPayEnum, toPayEndTimeVO, orderBaseInfo, payOrderDetailBO, userWalletBO);
		// 4、获取支付渠道
		try {
			ToRechargeParamVO toRechargeParamVO = new ToRechargeParamVO(token, toPayParam.getChannelId(), toPayParam.getBrowserType(), platform.getPlatForm(), PayConstants.RechargeTypeEnum.PAY.getKey());
			List<UserPayTypeBO> payTypeList = payChannelService.findUserPayTypes(toRechargeParamVO);
			if (!ObjectUtil.isBlank(payTypeList)) {
				payOrderDetailBO.setPtl(payTypeList);
			}
		} catch (Exception e) {
			logger.error("获取支付渠道异常", e);
			return ResultBO.err(MessageCodeConstants.TRANS_PAY_CHANNEL_IS_ERROR_SERVICE);
		}
		logger.debug("跳转支付返回数据：" + JSON.toJSONString(payOrderDetailBO));
		return ResultBO.ok(payOrderDetailBO);
	}

	/**  
	* 方法说明: 验证合买订单信息
	* @auth: xiongJinGang
	* @param toPayParam
	* @param batchPayEnum
	* @param toPayEndTimeVO
	* @param orderBaseInfo
	* @time: 2018年5月3日 下午2:43:59
	* @return: void 
	*/
	public ResultBO<?> validateOrderGroupInfo(ToPayParamVO toPayParam, BatchPayEnum batchPayEnum, ToPayEndTimeVO toPayEndTimeVO, OrderBaseInfoBO orderBaseInfo) {
		// 如果是单个支付并且是合买，需要判断合买状态和金额
		if (batchPayEnum.equals(PayConstants.BatchPayEnum.SINGLE) && orderBaseInfo.getBuyType().equals(PayConstants.BuyTypeEnum.JOINT_PURCHASE.getKey())) {
			// 如果参与合买金额不为空，表示是发起合买单的支付，需要验证订单是否支付，不是等待支付状态的订单都不能支付
			if (ObjectUtil.isBlank(toPayParam.getBuyAmount()) && (ObjectUtil.isBlank(orderBaseInfo.getPayStatus()) || (!orderBaseInfo.getPayStatus().equals(Integer.valueOf(PayConstants.PayStatusEnum.WAITTING_PAYMENT.getKey() + ""))
					&& !orderBaseInfo.getPayStatus().equals(Integer.valueOf(PayConstants.PayStatusEnum.BEING_PAID.getKey() + ""))))) {
				logger.info("订单【" + orderBaseInfo.getOrderCode() + "】的当前支付状态【" + orderBaseInfo.getPayStatus() + "】不是等待支付和支付中状态");
				return ResultBO.err(MessageCodeConstants.PAY_STATUS_ERROR_SERVICE);
			}
			// 合买订单信息
			OrderDetailGroupInfoBO orderDetailGroupInfoBO = orderBaseInfo.getOrderDetailGroupInfoBO();
			// 如果合买已满员，返回错误
			if (OrderGroupConstants.OrderGroupStatusEnum.FULL.getKey().intValue() == orderDetailGroupInfoBO.getGrpbuyStatus().intValue()) {
				logger.info("合买订单【" + toPayParam.getOrderCode() + "】已满员，不能发起支付");
				return ResultBO.err(MessageCodeConstants.ORDER_GROUP_HAD_FULL);
			}
			// 判断参与合买金额是否为空，不为空，表示是参与合买；为空表示发布合买
			if (ObjectUtil.isBlank(toPayParam.getBuyAmount())) {
				// 发起合买，需要支付的金额是（发起人认购金额+保底金额）
				toPayEndTimeVO.setOrderAmount(MathUtil.add(orderDetailGroupInfoBO.getGuaranteeAmount(), orderDetailGroupInfoBO.getGroupAmount()));
			} else {
				// 已合买金额+当前参与合买的金额大于订单金额，返回错误
				if (MathUtil.compareTo(orderDetailGroupInfoBO.getResidualAmount(), toPayParam.getBuyAmount()) < 0) {
					logger.info("合买订单【" + toPayParam.getOrderCode() + "】已完成合买：" + orderDetailGroupInfoBO.getResidualAmount() + "加上参与合买" + toPayParam.getBuyAmount() + "大于订单金额");
					return ResultBO.err(MessageCodeConstants.ORDER_GROUP_PAY_MONEY_FAIL);
				}
				// 剩余认购金额小于参与合买金额大于订单金额，不让支付
				if (MathUtil.compareTo(orderDetailGroupInfoBO.getResidualAmount(), toPayParam.getBuyAmount()) < 0) {
					logger.info("参与订单【" + toPayParam.getOrderCode() + "】合买金额【" + toPayParam.getBuyAmount() + "】大于订单总金额，不能发起支付");
					return ResultBO.err(MessageCodeConstants.ORDER_GROUP_PAY_MONEY_FAIL);
				}
				// 参与合买的金额是用户认购的金额（页面传过来）
				toPayEndTimeVO.setOrderAmount(toPayParam.getBuyAmount());
			}
		}
		return ResultBO.ok();
	}

	/**  
	* 方法说明: 查询并且设置订单详情及红包信息
	* @auth: xiongJinGang
	* @param toPayParam
	* @param platform
	* @param batchPayEnum
	* @param toPayEndTimeVO
	* @param orderBaseInfo
	* @param activityCode
	* @param token
	* @param payOrderDetailBO
	* @param userWalletBO
	* @time: 2018年1月2日 下午5:01:49
	* @return: void 
	*/
	@SuppressWarnings("unchecked")
	private void queryAndSetOrderDetail(ToPayParamVO toPayParam, TakenPlatformEnum platform, BatchPayEnum batchPayEnum, ToPayEndTimeVO toPayEndTimeVO, OrderBaseInfoBO orderBaseInfo, PayOrderDetailBO payOrderDetailBO, UserWalletBO userWalletBO) {
		// 订单详情
		toPayParam.setImgUrl(imgUrl);
		payOrderDetailBO.setOd(toPayParam, batchPayEnum, toPayEndTimeVO);
		// 单个支付并且活动编号为空才获取红包
		if (batchPayEnum.equals(BatchPayEnum.SINGLE) && ObjectUtil.isBlank(orderBaseInfo.getActivityCode())) {
			// 合买和追号计划都不获取红包
			if (!orderBaseInfo.getBuyType().equals(PayConstants.BuyTypeEnum.TRACKING_PLAN.getKey()) && !orderBaseInfo.getBuyType().equals(PayConstants.BuyTypeEnum.JOINT_PURCHASE.getKey())) {
				// 3、调用红包接口，获取用户的所有可用红包
				String redUserType = PayConstants.RedTypeEnum.RED_COLOR.getUseType();// 红包使用类型（支付）
				String lotteryCode = String.valueOf(orderBaseInfo.getLotteryChildCode());
				OperateCouponQueryVO operateCouponQuery = new OperateCouponQueryVO(toPayParam.getToken(), platform.getKey(), lotteryCode, redUserType);
				operateCouponQuery.setChannelId(ObjectUtil.isBlank(toPayParam.getChannelId()) ? PayConstants.ChannelEnum.UNKNOWN.getKey() : toPayParam.getChannelId());
				ResultBO<?> resultBO = operateCouponService.findCurPlatformCoupon(operateCouponQuery);
				if (resultBO.isOK()) {
					List<OperateCouponBO> couponList = (List<OperateCouponBO>) resultBO.getData();
					if (!ObjectUtil.isBlank(couponList)) {
						// 优惠券列表，订单金额，账户可用红包余额
						Double redBalance = 0d;
						if (!ObjectUtil.isBlank(userWalletBO)) {
							redBalance = userWalletBO.getEffRedBalance();
						}
						payOrderDetailBO.setCl(couponList, orderBaseInfo.getOrderAmount(), redBalance, lotteryCode, platform.getKey());
					}
				}
			}
		}
	}

	@Override
	public ResultBO<?> pay(PayInputParamVO payInputParam, String clientIp, TakenPlatformEnum platform) {
		PayParamVO payParam = null;
		try {
			logger.info("统一支付参数：" + JSON.toJSONString(payInputParam));
			ResultBO<?> resultBO = PayCommon.validatePayParams(payInputParam, platform);
			if (resultBO.isError()) {
				logger.info("统一支付参数错误：" + resultBO.getMessage());
				return resultBO;
			}
			payParam = (PayParamVO) resultBO.getData();
			// 1、验证用户登录状态，获取用户信息
			resultBO = userInfoService.findUserInfoByToken(payParam.getToken());
			if (resultBO.isError()) {
				return resultBO;
			}
			UserInfoBO userInfo = (UserInfoBO) resultBO.getData();
			// 判断账号是否经过实名，2018-01-05开始，支付不验证实名
			/*if (ObjectUtil.isBlank(userInfo.getRealName()) || ObjectUtil.isBlank(userInfo.getIdCard())) {
				logger.error("订单【" + payInputParam.getOrderCode() + "】的账户【" + userInfo.getRealName() + "】、身份证【" + userInfo.getIdCard() + "】未经过实名认证");
				return ResultBO.err(MessageCodeConstants.ACCOUNT_NOT_REALNAME_AUTHENTICTION_SERVICE);
			}*/
			payParam.setTest(isTestProject());
			if (isTestProject()) {
				clientIp = testClientIp;// 测试环境下，很多支付要用客户端真实的出网IP
			}
			if (payParam.getIsBatchPay().equals(PayConstants.BatchPayEnum.SINGLE.getKey())) {
				// 单个支付
				return singlePay(payParam, clientIp, platform, userInfo);
			} else {
				// 批量支付
				return batchPay(payParam, clientIp, platform, userInfo);
			}

		} catch (Exception e) {
			logger.error("支付请求异常，支付参数：" + payParam.toString(), e);
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, e.getMessage());
		}
	}

	@Override
	public ResultBO<?> pushPay(PayInputParamVO payInputParam, String clientIp, TakenPlatformEnum platform) {
		PayParamVO payParam = null;
		try {
			logger.info("查看推单支付参数：" + JSON.toJSONString(payInputParam));
			ResultBO<?> resultBO = PayCommon.validatePayParams(payInputParam, platform);
			if (resultBO.isError()) {
				logger.info("统一支付参数错误：" + resultBO.getMessage());
				return resultBO;
			}
			payParam = (PayParamVO) resultBO.getData();
			// 1、验证用户登录状态，获取用户信息
			resultBO = userInfoService.findUserInfoByToken(payParam.getToken());
			if (resultBO.isError()) {
				return resultBO;
			}
			payParam.setTest(isTestProject());
			if (isTestProject()) {
				clientIp = testClientIp;// 测试环境下，很多支付要用客户端真实的出网IP
			}

			try {
				resultBO = orderCopyService.getCopyOrderInfoForPay(Integer.parseInt(payParam.getIssueId()), payParam.getToken());
				if (resultBO.isError()) {
					logger.error("获取推单方案详情【" + payParam.getIssueId() + "】返回：" + resultBO.getMessage());
					return resultBO;
				}
			} catch (Exception e) {
				logger.error("获取推单方案详情【" + payParam.getIssueId() + "】异常：", e);
				return ResultBO.err(MessageCodeConstants.PLAN_CONTENT_IS_NULL_FIELD);
			}
			OrderCopyPayInfoBO orderCopyPayInfoBO = (OrderCopyPayInfoBO) resultBO.getData();
			orderCopyPayInfoBO.setIssueId(payParam.getIssueId());// 推单编号
			payParam.setOrderCopyPayInfoBO(orderCopyPayInfoBO);
			payParam.setClientIp(clientIp);
			payParam.setPlatform(platform.getKey());
			payParam.setIsBatchPay(PayConstants.BatchPayEnum.PUSH.getKey());// 推单支付
			// 调用推单支付接口进行支付
			resultBO = payService.pushPay(payParam);
			logger.debug("推单支付请求参数：" + JSON.toJSONString(resultBO));
			return resultBO;
		} catch (Exception e) {
			logger.error("支付请求异常，支付参数：" + payParam.toString(), e);
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, e.getMessage());
		}
	}

	@Override
	public ResultBO<?> payNotifyMock(PayNotifyMockVO payNotifyMockVO) {
		// true为测试环境
		if (!isTestProject()) {// 正式环境不能调用该接口
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS);
		}
		return payService.payNotifyMock(payNotifyMockVO);
	}

	@Override
	public ResultBO<?> insideRefund(HttpServletRequest request) {
		// true为测试环境
		if (!isTestProject()) {// 正式环境不能调用该接口
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS);
		}
		RefundParamVO refundParam = new RefundParamVO();
		String transCode = request.getParameter("transCode");// transCode
		if (ObjectUtil.isBlank(transCode)) {
			return ResultBO.err(MessageCodeConstants.TRANS_CODE_IS_NULL_FIELD);
		}

		String tradeNo = request.getParameter("tradeNo");// tradeNo
		if (!ObjectUtil.isBlank(tradeNo)) {
			refundParam.setTradeNo(tradeNo);
		}

		String refundAmount = request.getParameter("refundAmount");// refundAmount
		if (ObjectUtil.isBlank(refundAmount)) {
			return ResultBO.err(MessageCodeConstants.PAY_AMOUNT_ERROR_SERVICE);
		}
		refundParam.setTransCode(transCode);
		refundParam.setRefundAmount(Double.parseDouble(refundAmount));
		refundParam.setRefundReason("不想要了");
		return payService.refund(refundParam);
	}

	@Override
	public ResultBO<?> payQuery(PayQueryParamVO payQueryParamVO, HttpServletRequest request) {
		// true为测试环境
		if (!isTestProject()) {// 正式环境不能调用该接口
			return ResultBO.err(MessageCodeConstants.DATA_NOT_FOUND_SYS);
		}
		return payService.payQuery(payQueryParamVO);
	}

	@Override
	public ResultBO<?> refundQuery(PayQueryParamVO payQueryParamVO, HttpServletRequest request) {
		// true为测试环境
		if (!isTestProject()) {// 正式环境不能调用该接口
			return ResultBO.err(MessageCodeConstants.DATA_NOT_FOUND_SYS);
		}
		return payService.refundQuery(payQueryParamVO);
	}

	/**  
	* 方法说明: 单个支付
	* @auth: xiongJinGang
	* @param payParam
	* @param clientIp
	* @param platform
	* @param userInfo
	* @time: 2017年5月10日 下午2:45:39
	* @return: ResultBO<?> 
	*/
	private ResultBO<?> singlePay(PayParamVO payParam, String clientIp, TakenPlatformEnum platform, UserInfoBO userInfo) {
		// 2、根据用户id和订单号，查找订单信息
		OrderBaseInfoBO orderDetail = this.findOrderInfo(payParam.getOrderCode(), payParam.getToken(), userInfo);
		// 验证订单号是否正确，验证订单支付状态是否已支付
		ResultBO<?> resultBO = PayCommon.validateOrder(orderDetail);
		if (resultBO.isError()) {
			return resultBO;
		}
		List<OrderBaseInfoBO> orderList = new ArrayList<OrderBaseInfoBO>();
		orderList.add(orderDetail);
		String activityCode = orderDetail.getActivityCode();// 活动编号
		// 验证活动
		if (!ObjectUtil.isBlank(activityCode)) {
			resultBO = getActivityAmount(activityCode, payParam.getToken(), orderDetail);
			if (resultBO.isError()) {
				return resultBO;
			}
			// 折扣金额
			Double discountAmount = (Double) resultBO.getData();
			// 支付金额 = 订单金额 - 折扣金额
			Double activityAmount = MathUtil.sub(orderDetail.getOrderAmount(), discountAmount);
			payParam.setActivityAmount(activityAmount);// 活动金额
			payParam.setUseRedAmount(discountAmount);// 折扣金额当做红包金额
			payParam.setActivityCode(activityCode);
		}
		// 3、调用统一支付接口,获取支付参数和链接
		payParam.setOrderList(orderList);
		payParam.setClientIp(clientIp);
		payParam.setPlatform(platform.getKey());
		payParam.setIsBatchPay(PayConstants.BatchPayEnum.SINGLE.getKey());// 单个支付
		// 调用支付接口进行支付
		resultBO = payService.pay(payParam);
		logger.debug("请求支付参数：" + JSON.toJSONString(resultBO));
		return resultBO;
	}

	@SuppressWarnings("unchecked")
	private ResultBO<?> batchPay(PayParamVO payParam, String clientIp, TakenPlatformEnum platform, UserInfoBO userInfo) {
		// 2、根据用户id和订单号，查找订单信息
		ResultBO<?> resultBO = this.findOrderList(payParam.getOrderQueryVoList(), payParam.getToken());
		if (resultBO.isError()) {
			return resultBO;
		}
		List<OrderBaseInfoBO> list = (List<OrderBaseInfoBO>) resultBO.getData();

		// 验证订单号是否正确，验证订单支付状态是否已支付
		resultBO = PayCommon.validateOrder(list);
		if (resultBO.isError()) {
			logger.error("批量支付验证订单" + payParam.getOrderCode() + "失败。" + resultBO.getMessage());
			return resultBO;
		}

		// 3、调用统一支付接口,获取支付参数和链接
		payParam.setOrderList(list);
		payParam.setClientIp(clientIp);
		payParam.setPlatform(platform.getKey());
		payParam.setIsBatchPay(PayConstants.BatchPayEnum.BATCH.getKey());// 批量支付

		// 调用支付接口进行支付
		resultBO = payService.pay(payParam);
		logger.debug("请求支付参数：" + JSON.toJSONString(resultBO));
		return resultBO;
	}

	/**  
	* 方法说明: 获取订单信息
	* @auth: xiongJinGang
	* @param orderCode
	* @param token
	* @param userInfo
	* @time: 2017年5月8日 下午6:27:47
	* @return: OrderBaseInfoBO 
	*/
	private OrderBaseInfoBO findOrderInfo(String orderCode, String token, UserInfoBO userInfo) {
		OrderBaseInfoBO orderListInfoBO = null;
		try {
			ResultBO<?> resultBO = orderSearchService.queryOrderInfo(orderCode, token);
			if (resultBO.isOK()) {
				orderListInfoBO = (OrderBaseInfoBO) resultBO.getData();
				if (ObjectUtil.isBlank(orderListInfoBO)) {
					logger.info("未获取到订单【" + orderCode + "】详情");
				}
			} else {
				logger.error("获取订单【" + orderCode + "】详情返回空");
			}
		} catch (Exception e) {
			logger.error("获取用户" + userInfo.getMobile() + "订单" + orderCode + "的详情异常：" + e.getMessage());
		}
		return orderListInfoBO;
	}

	/**  
	* 方法说明: 查找订单列表
	* @auth: xiongJinGang
	* @param orderQueryVoList
	* @param token
	* @time: 2017年5月9日 上午10:09:43
	* @return: ResultBO<?> 
	*/
	@SuppressWarnings("unchecked")
	private ResultBO<?> findOrderList(List<OrderQueryVo> orderQueryVoList, String token) {
		ResultBO<?> resultBO = orderSearchService.queryOrderListForOrderCodes(orderQueryVoList, token);
		if (resultBO.isError()) {
			logger.error("获取订单列表详情返回错误：" + resultBO.getMessage() + "，参数：" + JSON.toJSONString(orderQueryVoList));
			return resultBO;
		}
		List<OrderBaseInfoBO> list = (List<OrderBaseInfoBO>) resultBO.getData();
		if (ObjectUtil.isBlank(list)) {
			return ResultBO.err(MessageCodeConstants.ORDER_NOT_EXIST_OR_INVALILD);
		}
		// 查询返回的订单结果跟支付申请时的订单数量不一样
		if (list.size() != orderQueryVoList.size()) {
			return ResultBO.err(MessageCodeConstants.ORDER_NOT_EXIST_OR_INVALILD);
		}
		return resultBO;
	}

	// 获取活动信息，将折扣金额返回
	private ResultBO<?> getActivityAmount(String activityCode, String token, OrderBaseInfoBO orderBaseInfo) {
		Double discountAmount = 0d;// 折扣金额
		boolean checkDiscountAmount = true;// 是否计算折扣金额
		try {
			if (activityCode.equals(footbollActivityCode) || activityCode.equals(basketbollActivityCode)) {
				// 竟足活动
				// 是活动订单，获取活动信息，验证相关信息
				ResultBO<?> activityResult = activityService.findJzstActivityInfo(activityCode);
				if (activityResult.isError()) {
					logger.info("获取活动【" + activityCode + "】错误：" + activityResult.getMessage());
					return activityResult;
				}
				JzstActivityInfo jzstActivityInfo = (JzstActivityInfo) activityResult.getData();
				activityResult = jzstActivityInfo.getVerifyActivityInfo();
				if (activityResult.isError()) {
					logger.info("验证活动【" + activityCode + "】信息错误：" + activityResult.getMessage());
					return activityResult;
				}
				List<JzstActivityRule> activityRulesList = jzstActivityInfo.getJzstActivityRules();
				if (ObjectUtil.isBlank(activityRulesList)) {
					logger.info("活动【" + activityCode + "】规则为空");
					return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_ACTIVITY_NOT_EXIST);
				}
				OrderInfoVO orderInfoVO = new OrderInfoVO();
				orderInfoVO.setActivityCode(activityCode);
				orderInfoVO.setToken(token);
				orderInfoVO.setOrderAmount(orderBaseInfo.getOrderAmount());
				orderInfoVO.setMultipleNum(orderBaseInfo.getMultipleNum());
				orderInfoVO.setLotteryCode(orderBaseInfo.getLotteryCode());
				// 验证活动订单
				try {
					activityResult = activityValid.validJzstOrderInfo(orderInfoVO, PayConstants.ActivityValidateTypeEnum.PAY.getKey());
					if (activityResult.isError()) {
						logger.info("活动【" + activityCode + "】订单【" + orderInfoVO.getOrderCode() + "】不满足使用规则：" + activityResult.getMessage());
						return activityResult;
					}
				} catch (Exception e) {
					logger.error("验证活动【" + activityCode + "】订单【" + orderInfoVO.getOrderCode() + "】使用规则异常", e);
					return ResultBO.err(MessageCodeConstants.SYS_ERROR_SYS);
				}
				for (JzstActivityRule jzstActivityRule : activityRulesList) {
					if (jzstActivityRule.getMultipleNum().intValue() == orderInfoVO.getMultipleNum().intValue()) {// 校验五倍，二十倍
						discountAmount = jzstActivityRule.getReduceAmount();
						break;
					}
					if (orderInfoVO.getMultipleNum().intValue() >= jzstActivityRule.getMultipleNum().intValue() && jzstActivityRule.getMultipleNum().intValue() >= Constants.MULTIPLE_GREATER_50.intValue()) {// 校验大于等于五十倍
						discountAmount = jzstActivityRule.getReduceAmount();
						break;
					}
				}

			} else if (activityCode.equals(gdRedCentActivityCode) || activityCode.equals(jxRedCentActivityCode)) {
				// 一分钱活动
				ResultBO<?> activityResult = activityService.findYfgcActivityInfo(orderBaseInfo.getLotteryCode());
				if (activityResult.isError()) {
					logger.info("获取活动【" + activityCode + "】错误：" + activityResult.getMessage());
					return activityResult;
				}

				YfgcActivityBO yfgcActivityBO = (YfgcActivityBO) activityResult.getData();
				if (!ObjectUtil.isBlank(yfgcActivityBO)) {
					Map<Integer, Double> map = yfgcActivityBO.getActivityRules();
					discountAmount = map.get(orderBaseInfo.getTotalIssue());
				}
			} else {
				// 都不是上面的活动
				OrderAddVO orderVo = new OrderAddVO();
				orderVo.setActivityId(activityCode);// 活动来源ID
				orderVo.setLotteryCode(orderBaseInfo.getLotteryCode());
				orderVo.setChannelId(orderBaseInfo.getChannelId());
				orderVo.setAddAmount(orderBaseInfo.getOrderAmount());
				orderVo.setMultipleNum(orderBaseInfo.getMultipleNum());
				orderVo.setIsDltAdd(Short.parseShort(orderBaseInfo.getIsDltAdd() + ""));
				orderVo.setPlatform(Short.parseShort(orderBaseInfo.getPlatform() + ""));
				orderVo.setAddIssues(orderBaseInfo.getTotalIssue());
				orderVo.setToken(token);
				ResultBO<?> activityResult = activityAddedService.checkAdded(orderVo, 2);
				if (activityResult.isError()) {
					logger.info("验证活动【" + activityCode + "】错误：" + activityResult.getMessage());
					return activityResult;
				}
				checkDiscountAmount = false;
			}
		} catch (Exception e) {
			logger.error("获取活动【" + activityCode + "】优惠信息异常：", e);
			return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_ACTIVITY_NOT_EXIST);
		}
		if (checkDiscountAmount) {
			// 折扣金额为0，表示获取活动信息失败
			if (MathUtil.compareTo(discountAmount, 0d) == 0) {
				logger.error("获取活动【" + activityCode + "】折扣金额失败：");
				return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_ACTIVITY_NOT_EXIST);
			}
		}
		return ResultBO.ok(discountAmount);
	}

	@Override
	public ResultBO<?> payResult(PayParamVO payParam) {
		return payService.payResult(payParam);
	}

	@Override
	public ResultBO<?> payNotify(Map<String, String> notifyMap) {
		return payService.payNotify(notifyMap);
	}

	@Override
	public ResultBO<?> refundNotify(Map<String, String> notifyMap) {
		return payService.refundNotify(notifyMap);
	}

	@Override
	public boolean isTestProject() {
		boolean flag = false;
		if (!ObjectUtil.isBlank(isTest)) {
			flag = (isTest.equals("true")) ? true : false;
		}
		return flag;
	}

}
