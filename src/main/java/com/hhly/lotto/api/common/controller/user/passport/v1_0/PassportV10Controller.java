package com.hhly.lotto.api.common.controller.user.passport.v1_0;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.UserConstants.MessageTypeEnum;
import com.hhly.skeleton.user.vo.PassportVO;
import com.hhly.usercore.remote.member.service.IVerifyCodeService;
import com.hhly.usercore.remote.passport.service.IMemberLoginService;
import com.hhly.usercore.remote.passport.service.IMemberRegisterService;


/**
 * 
 * @desc 账号密码管理控制层
 * @author zhouyang
 * @date 2017年2月21日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class PassportV10Controller extends BaseController {
	
	private static final Logger logger = Logger.getLogger(PassportV10Controller.class);
	
	@Autowired
	private IMemberLoginService memberLoginService;
	
	@Autowired
	private IMemberRegisterService memberRegisterService;
	
	@Autowired
	private IVerifyCodeService verifyCodeService;

	/**
	 * 获取验证码
	 * @param passportVO 手机号/邮箱
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/get/code", method = RequestMethod.POST)
	public Object sendCode(@RequestBody PassportVO passportVO, HttpServletRequest request) throws Exception {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			return verifyCodeService.sendVerifyCode(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS,"verifyCodeService.sendVerifyCode"), e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"verifyCodeService.sendVerifyCode");
		}
	}

	/**
	 * 注册 - 验证并绑定手机号或email地址（1）
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public Object register(@RequestBody PassportVO passportVO, HttpServletRequest request) {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberRegisterService.regUser(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.regUser"), e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.regUser");
		}
	}

	/**
	 * 注册 - 单独验证帐户名
	 * @param passportVO
	 * @return
	 */
	@RequestMapping(value = "/validate/account", method = RequestMethod.POST)
	public Object validateAccount(@RequestBody PassportVO passportVO) {
		try {
			return memberRegisterService.validateAccount(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.validateAccount"), e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.validateAccount");
		}
	}

	/**
	 * 注册 - 设置帐户名跟密码（2）
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/set/account", method=RequestMethod.POST)
	public Object perfectAccountAndPassword(@RequestBody PassportVO passportVO, HttpServletRequest request) {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberRegisterService.perfectAccountAndPassword(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.perfectAccountAndPassword"), e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.perfectAccountAndPassword");
		}
	}

	/**
	 * 注册 - 完善真实身份信息（3）
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/set/realname", method=RequestMethod.POST)
	public Object perfectRealName(@RequestBody PassportVO passportVO, HttpServletRequest request) {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberRegisterService.perfectRealName(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.regUser"), e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.regUser");
		}
	}

	/**
	 * 注册 -  点击跳过，成功页面显示帐户名
	 * @param passportVO
	 * @return
	 */
	@RequestMapping(value = "/continue", method = RequestMethod.POST)
	public Object registerSuccess(@RequestBody PassportVO passportVO, HttpServletRequest request) {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			return memberRegisterService.registerSuccess(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.registerSuccess"),
					e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.registerSuccess");
		}
	}

	/**
	 * 登记手机号
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/add/mobile", method=RequestMethod.POST)
	public Object addMobile(@RequestBody PassportVO passportVO, HttpServletRequest request) {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberRegisterService.addMobile(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.addMobile"), e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.addMobile");
		}
	}

	/**
	 * 登记邮箱
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/add/email", method=RequestMethod.POST)
	public Object addEmail(@RequestBody PassportVO passportVO, HttpServletRequest request) {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberRegisterService.addEmail(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.addEmail"), e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRegisterService.addEmail");
		}
	}
	
    /**
	 * 登录 - 通过账号是否设置密码判断帐号登录方式
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login/validate/username", method=RequestMethod.POST)
	public Object loginValidate(@RequestBody PassportVO passportVO, HttpServletRequest request) throws Exception {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			return memberLoginService.loginValidate(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberLoginService.loginValidate"), e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberLoginService.loginValidate");
		}
	}

	/**
	 * 验证帐号是否有效
	 * @param passportVO
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/register/validate/username", method=RequestMethod.POST)
	public Object validateUser(@RequestBody PassportVO passportVO, HttpServletRequest request) throws Exception {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			return memberLoginService.validateUser(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberLoginService.validateUser"), e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberLoginService.validateUser");
		}
	}
	
	/**
	 * 登陆 - 手机验证码登录
	 * 
	 * @param passportVO
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login/code", method = RequestMethod.POST)
	public Object loginUserByVerifyCode(@RequestBody PassportVO passportVO, HttpServletRequest request) throws Exception {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberLoginService.loginUserByCode(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberLoginService.loginUserByCode"),
					e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberLoginService.loginUserByCode");
		}
	}
	
	/**
	 * 快速登录 - 手机验证码登录，手机号不存在或未开启手机号登录则注册后登录
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/login/fast", method = RequestMethod.POST)
	public Object loginFast(@RequestBody PassportVO passportVO, HttpServletRequest request) {
		passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
		passportVO.setChannelId(getHeaderParam(request).getChannelId());
		passportVO.setIp(getIp(request));
		passportVO.setSendType(MessageTypeEnum.FAST_LOGIN_MSG.getKey());
		return memberLoginService.loginFastByMobile(passportVO);
	}

	/**
	 * 登录 - 帐号(帐户名/手机号/邮箱地址)密码登录
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public Object loginUserByUserName(@RequestBody PassportVO passportVO, HttpServletRequest request) {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberLoginService.loginUserByUserName(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberLoginService.loginUserByUserName"), e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberLoginService.loginUserByUserName");
		}
	}
	
	/**
	 * 注销登录
	 * @return
	 */
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public Object logout(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			return memberLoginService.exitUser(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberLoginService.exitUser"), e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberLoginService.exitUser");
		}
	}

}
