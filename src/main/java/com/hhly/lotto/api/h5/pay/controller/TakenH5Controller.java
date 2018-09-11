package com.hhly.lotto.api.h5.pay.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.data.pay.v1_0.PayCommon;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.paycore.remote.service.ITransTakenConfirmService;
import com.hhly.paycore.remote.service.ITransTakenService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.PayConstants;
import com.hhly.skeleton.pay.vo.TakenInputReqParamVO;
import com.hhly.skeleton.pay.vo.TakenReqParamVO;

@RestController
@RequestMapping("/h5/taken")
public class TakenH5Controller extends BaseController {

	private static final Logger logger = Logger.getLogger(TakenH5Controller.class);

	@Resource
	private ITransTakenService transTakenService;
	@Resource
	private ITransTakenConfirmService transTakenConfirmService;

	/**  
	* 方法说明: 验证提款次数是否超过当日最高次数，超过，则返回处理中的提款记录列表
	* @auth: xiongJinGang
	* @param token
	* @time: 2017年11月4日 下午3:04:07
	* @return: Object 
	*/
	@RequestMapping(value = "/takenTimesCheck", method = RequestMethod.GET)
	public Object takenTimesCheck(String token) {
		try {
			TakenReqParamVO takenReqParamVO = new TakenReqParamVO();
			takenReqParamVO.setToken(token);
			return transTakenConfirmService.validateTakenCount(takenReqParamVO);
		} catch (Exception e) {
			logger.error("检查提款次数异常", e);
		}
		return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS);
	}

	/**  
	* 方法说明: 提款请求
	* @auth: xiongJinGang
	* @param token
	* @time: 2017年4月20日 下午4:16:05
	* @return: Object 
	*/
	@RequestMapping(value = "/takenReq", method = RequestMethod.GET)
	public Object takenReq(String token) {
		try {
			ResultBO<?> resultBO = transTakenConfirmService.takenReqForApp(token);
			if (resultBO.isError()) {
				logger.info("申请提款失败：" + resultBO.getMessage());
			}
			return resultBO;
		} catch (Exception e) {
			logger.error("提款请求异常", e);
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "transTakenService.takenReq");
		}
	}

	/**  
	* 方法说明: 根据提款金额获取异常提款信息
	* @auth: xiongJinGang
	* @param TakenInputReqParamVO
	* @time: 2017年8月22日 下午6:36:36
	* @return: Object 
	*/
	@RequestMapping(value = "/takenAmountCount", method = RequestMethod.POST)
	public Object takenAmountCount(@RequestBody TakenInputReqParamVO TakenInputReqParamVO) {
		ResultBO<?> resultBO = PayCommon.validateTakenAmount(TakenInputReqParamVO);
		if (resultBO.isError()) {
			logger.info("确认提款金额参数错误【" + TakenInputReqParamVO.toString() + "】，" + resultBO.getMessage());
			return resultBO;
		}
		TakenReqParamVO takenReqParamVO = (TakenReqParamVO) resultBO.getData();
		try {
			resultBO = transTakenConfirmService.takenCount(takenReqParamVO);
			if (resultBO.isError()) {
				logger.info("根据提款金额获取异常提款信息失败：" + resultBO.getMessage());
			}
			return resultBO;
		} catch (Exception e) {
			logger.error("根据提款金额获取异常提款信息异常，请求参数【" + takenReqParamVO.toString() + "】", e);
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS);
		}
	}

	/**  
	* 方法说明: 确认提款金额
	* @auth: xiongJinGang
	* @param request
	* @time: 2017年4月20日 下午5:05:26
	* @return: Object 
	*/
	@RequestMapping(value = "/takenAmountReq", method = RequestMethod.POST)
	public Object takenAmountReq(@RequestBody TakenInputReqParamVO TakenInputReqParamVO) {
		ResultBO<?> resultBO = PayCommon.validateTakenAmount(TakenInputReqParamVO, false);
		if (resultBO.isError()) {
			logger.info("确认提款金额参数错误【" + TakenInputReqParamVO.toString() + "】，" + resultBO.getMessage());
			return resultBO;
		}
		TakenReqParamVO takenReqParamVO = (TakenReqParamVO) resultBO.getData();
		try {
			resultBO = transTakenConfirmService.taken(takenReqParamVO);
			if (resultBO.isError()) {
				logger.info("确认提款金额失败：" + resultBO.getMessage());
			}
			return resultBO;
		} catch (Exception e) {
			logger.error("确认提款金额时异常，请求参数【" + takenReqParamVO.toString() + "】", e);
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "transTakenService.taken");
		}
	}

	/**  
	* 方法说明: 提款前确认
	* @auth: xiongJinGang
	* @param request
	* @time: 2017年4月21日 下午3:15:11
	* @return: Object 
	*/
	@RequestMapping(value = "/takenConfirm", method = RequestMethod.POST)
	public Object takenConfirm(@RequestBody TakenInputReqParamVO TakenInputReqParamVO) {
		ResultBO<?> resultBO = PayCommon.validateTakenAmount(TakenInputReqParamVO, true);
		if (resultBO.isError()) {
			return resultBO;
		}
		TakenReqParamVO takenReqParamVO = (TakenReqParamVO) resultBO.getData();
		takenReqParamVO.setPlatform(PayConstants.TakenPlatformEnum.WEB.getKey());
		try {
			resultBO = transTakenConfirmService.takenConfirm(takenReqParamVO);
			if (resultBO.isError()) {
				logger.info("确认提款金额失败：" + resultBO.getMessage());
			}
			return resultBO;
		} catch (Exception e) {
			logger.error("确认提款异常，参数【" + takenReqParamVO.toString() + "】", e);
			return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "transTakenService.taken");
		}
	}
}
