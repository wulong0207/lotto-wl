package com.hhly.lotto.api.android.controller.pay.v1_0;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.data.pay.v1_0.PayCommon;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.paycore.remote.service.ITransRechargeService;
import com.hhly.paycore.remote.service.ITransRedService;
import com.hhly.paycore.remote.service.ITransTakenConfirmService;
import com.hhly.paycore.remote.service.ITransTakenService;
import com.hhly.paycore.remote.service.ITransUserLogService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.PayConstants;
import com.hhly.skeleton.pay.vo.TransParamVO;
import com.hhly.skeleton.pay.vo.TransRedVO;

/**
 * @desc 交易相关控制层
 * @author xiongJinGang
 * @date 2017年4月26日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/android/v1.0/trans")
public class TransUserAndroidV10Controller extends BaseController {

	private static final Logger logger = Logger.getLogger(TransUserAndroidV10Controller.class);

	@Resource
	private ITransUserLogService transUserService;
	@Resource
	private ITransRechargeService transRechargeService;
	@Resource
	private ITransTakenService transTakenService;
	@Resource
	private ITransTakenConfirmService transTakenConfirmService;
	@Resource
	private ITransRedService transRedService;

	/**  
	* 方法说明: 分页获取用户交易列表
	* @param transParam
	* @time: 2017年3月7日 下午4:18:28
	* @return: Object 
	*/
	@RequestMapping(value = "/transList", method = { RequestMethod.POST, RequestMethod.GET })
	public Object transList(HttpServletRequest request) {
		ResultBO<?> resultBO = PayCommon.validateAppTransParams(request);
		if (resultBO.isError()) {
			return resultBO;
		}
		TransParamVO transParam = (TransParamVO) resultBO.getData();
		try {
			return transUserService.findAppTransUserByPage(transParam);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, e.getMessage()));
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "TransUserController.userTransList");
		}
	}

	/**  
	* 方法说明: 用户交易详情
	* @param token 登录token
	* @param transCode 交易编号
	* @time: 2017年3月7日 下午4:24:34
	* @return: Object 
	*/
	@RequestMapping(value = "/transDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public Object transDetail(String token, String transCode) {
		try {
			return transUserService.findTransUserByCode(token, transCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, e.getMessage()));
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "transUserService.findTransUserByCode");
		}
	}

	/**  
	* 方法说明: 用户充值记录详情
	* @param token 登录token
	* @param transCode 充值编号
	* @time: 2017年3月7日 下午4:31:33
	* @return: Object 
	*/
	@RequestMapping(value = "/rechargeDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public Object rechargeDetail(String token, String transCode) {
		try {
			return transRechargeService.findRechargeByCode(token, transCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, e.getMessage()));
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "transRechargeService.findRechargeByCode");
		}
	}

	/**  
	* 方法说明: 提款列表
	* @auth: xiongJinGang
	* @param request
	* @time: 2017年3月31日 下午5:47:56
	* @return: Object 
	*/
	@RequestMapping(value = "/takenList", method = { RequestMethod.POST, RequestMethod.GET })
	public Object takenList(HttpServletRequest request) {
		ResultBO<?> resultBO = PayCommon.validateTransParams(request);
		if (resultBO.isError()) {
			return resultBO;
		}
		TransParamVO transParam = (TransParamVO) resultBO.getData();
		try {
			transParam.setPlatform(PayConstants.TakenPlatformEnum.ANDROID.getKey());
			return transTakenConfirmService.findTakenListByPage(transParam);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, e.getMessage()));
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "TransUserPCV10Controller.userTransList");
		}
	}

	/**  
	* 方法说明: 用户提款详情
	* @param token 登录token
	* @param transCode 提款编号
	* @time: 2017年3月7日 下午4:33:40
	* @return: Object 
	*/
	@RequestMapping(value = "/takenDetail", method = { RequestMethod.POST, RequestMethod.GET })
	public Object takenDetail(String token, String transCode) {
		try {
			return transTakenConfirmService.findTakenByCode(token, transCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, e.getMessage()));
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "transTakenService.findTakenByCode");
		}
	}

	/** 
	* @Title: transRedList 
	* @Description: 分页获取用户红包交易列表
	*  @param request
	*  @return
	* @time 2017年5月6日 下午4:36:17
	*/
	@RequestMapping(value = "/transRedList", method = RequestMethod.GET)
	public Object transRedList(HttpServletRequest request) {
		ResultBO<?> resultBO = PayCommon.validateAppTransRedParams(request);
		if (resultBO.isError()) {
			return resultBO;
		}
		TransRedVO transRedVO = (TransRedVO) resultBO.getData();
		try {
			return transRedService.findUserTransRedByPage(transRedVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, e.getMessage()));
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "TransUserController.transRedList");
		}
	}

}
