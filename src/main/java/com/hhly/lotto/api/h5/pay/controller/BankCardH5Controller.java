package com.hhly.lotto.api.h5.pay.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.pay.vo.PayBankcardVO;
import com.hhly.usercore.remote.member.service.IMemberBankcardService;

/**
 * @version 1.0
 * @auth chenkangning
 * @date 2017.03.06
 * @desc 银行卡管理控制类
 * @compay 益彩网络科技有限公司
 */
@RestController
@RequestMapping("/bankCard")
public class BankCardH5Controller extends BaseController {

	private static final Logger logger = Logger.getLogger(BankCardH5Controller.class);

	@Resource
	private IMemberBankcardService bankcardService;

	/**
	 * 保存银行卡
	 *
	 * @param payBankcardVO 数组对象
	 * @return Object
	 */
	@RequestMapping(value = "/addBankCard", method = RequestMethod.POST)
	public Object addBandCard(HttpServletRequest httpRequest, @RequestBody PayBankcardVO payBankcardVO) {
		try {
			payBankcardVO.setIp(getIp(httpRequest));
			return bankcardService.addBankCard(payBankcardVO.getToken(), payBankcardVO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "bankcardService.addBankCard"));
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "bankcardService.addBankCard");
		}
	}

	/**
	 * 查询用户银行卡信息
	 *
	 * @param token 用户唯一标识
	 * @return Object
	 */
	@RequestMapping(value = "/selectCardForModile/{token}", method = RequestMethod.GET)
	public Object selectBankCardMobile(@PathVariable String token) {
		try {
			return bankcardService.selectCardForModile(token);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "bankcardService.selectBankCardMobile"));
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "bankcardService.selectBankCardMobile");
		}
	}

	/**
	 * 处理快捷支付
	 *
	 * @param token  用户唯一标识
	 * @param cardId 银行卡id
	 * @return Object
	 */
	@RequestMapping(value = "/closeQuickPay/{token}/{cardId}/{status}", method = RequestMethod.GET)
	public Object closeOrOpenQuickPayment(@PathVariable String token, @PathVariable Integer cardId, @PathVariable Integer status) {
		try {
			return bankcardService.closenQuickPayment(token, cardId, status);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "bankcardService.closeOrOpenQuickPayment"));
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "bankcardService.closeOrOpenQuickPayment");
		}
	}

	/**
	 * 根据银行卡号取到银行卡名称，类型
	 *
	 * @param token    用户唯一标识
	 * @param cardCode 银行卡号
	 * @return Object
	 */
	@RequestMapping(value = "/getBankName/{token}/{bc_no}", method = RequestMethod.GET)
	public Object getBankName(@PathVariable String token, @PathVariable("bc_no") String cardCode) {
		try {
			return bankcardService.getBankName(token, cardCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "bankcardService.getBankName"));
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "bankcardService.getBankName");
		}
	}

	/**
	 * 获取验证码
	 *
	 * @param token  用户唯一标识
	 * @param mobile 手机号
	 * @return object
	 */
	@RequestMapping(value = "/getValidateCode/{token}/{mobile}", method = RequestMethod.GET)
	public Object getValidateCode(@PathVariable String token, @PathVariable String mobile) {
		try {
			return bankcardService.getValidateCode(token, mobile);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "bankcardService.getValidateCode"));
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "bankcardService.getValidateCode");
		}
	}

	@RequestMapping(value = "/getCardLimitDetail/{token}/{bankId}/{bankType}", method = RequestMethod.GET)
	public Object getCardLimitDetail(@PathVariable String token, @PathVariable Short bankId, @PathVariable Short bankType) {
		try {
			return bankcardService.getCardLimitDetail(token, bankId, bankType);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "bankcardService.getCardLimitDetail"));
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "bankcardService.getCardLimitDetail");
		}
	}

}
