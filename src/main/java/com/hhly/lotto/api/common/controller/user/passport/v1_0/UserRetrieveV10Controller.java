package com.hhly.lotto.api.common.controller.user.passport.v1_0;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.user.vo.PassportVO;
import com.hhly.usercore.remote.member.service.IVerifyCodeService;
import com.hhly.usercore.remote.passport.service.IMemberRetrieveService;

/**
 * @author zhouyang
 * @version 1.0
 * @desc 用户找回密码控制层
 * @date 2017/12/8
 * @company 益彩网络科技公司
 */
public class UserRetrieveV10Controller extends BaseController {
    
    @Autowired
    private IMemberRetrieveService memberRetrieveService;
    
    @Autowired
    private IVerifyCodeService verifyCodeService;

    /**
     * 找回密码 - 验证帐号
     * @return
     */
    @RequestMapping(value="/validate/username", method= RequestMethod.POST)
    public Object checkUserName(@RequestBody PassportVO passportVO, HttpServletRequest request) {
        try {
            passportVO.setIp(getIp(request));
            passportVO.setChannelId(getHeaderParam(request).getChannelId());
            passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            return memberRetrieveService.checkUserName(passportVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.checkUserName"), e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.checkUserName");
        }
    }

    /**
     * 找回密码，获取手机验证码
     * @param passportVO
     * @return
     */
    @RequestMapping(value = "/get/mobile/code", method = RequestMethod.POST)
    public Object sendFdMobileCode(@RequestBody PassportVO passportVO, HttpServletRequest request) {
        try {
            passportVO.setChannelId(getHeaderParam(request).getChannelId());
            passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            return verifyCodeService.sendFdMobileVerifyCode(passportVO);
        } catch (Exception e) {
            logger.error(
                    ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.sendFdMobileVerifyCode"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.sendFdMobileVerifyCode");
        }
    }

    /**
     * 找回密码，获取邮箱验证码
     * @param passportVO
     * @return
     */
    @RequestMapping(value = "/get/email/code", method = RequestMethod.POST)
    public Object sendFdEmailCode(@RequestBody PassportVO passportVO, HttpServletRequest request) {
        try {
            passportVO.setChannelId(getHeaderParam(request).getChannelId());
            passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            return verifyCodeService.sendFdEmailVerifyCode(passportVO);
        } catch (Exception e) {
            logger.error(
                    ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.sendFdEmailVerifyCode"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.sendFdEmailVerifyCode");
        }
    }

    /**
     * 找回密码 - 手机号验证
     * @param passportVO
     * @return
     */
    @RequestMapping(value = "/validate/mobile", method = RequestMethod.POST)
    public Object checkFdMobileCode(@RequestBody PassportVO passportVO, HttpServletRequest request) {
        try {
        	passportVO.setIp(getIp(request));
            passportVO.setChannelId(getHeaderParam(request).getChannelId());
            passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            return memberRetrieveService.checkFdMobileCode(passportVO);
        } catch (Exception e) {
            logger.error(
                    ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.checkFdMobileCode"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.checkFdMobileCode");
        }
    }

    /**
     * 找回密码 - 邮箱验证
     * @param passportVO
     * @return
     */
    @RequestMapping(value = "/validate/email", method = RequestMethod.POST)
    public Object checkFdEmailCode(@RequestBody PassportVO passportVO, HttpServletRequest request) {
        try {
        	passportVO.setIp(getIp(request));
            passportVO.setChannelId(getHeaderParam(request).getChannelId());
            passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            return memberRetrieveService.checkFdEmailCode(passportVO);
        } catch (Exception e) {
            logger.error(
                    ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.checkFdEmailCode"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.checkFdEmailCode");
        }
    }

    /**
     * 找回密码 - 验证银行卡号
     * @param passportVO
     * @param request
     * @return
     */
    @RequestMapping(value = "/validate/bankcard", method = RequestMethod.POST)
    public Object checkBankCard(@RequestBody PassportVO passportVO, HttpServletRequest request) {
        try {
            passportVO.setIp(getIp(request));
            passportVO.setChannelId(getHeaderParam(request).getChannelId());
            passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            return memberRetrieveService.checkBankCard(passportVO);
        } catch (Exception e) {
            logger.error(
                    ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.checkBankCard"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.checkBankCard");
        }
    }

    /**
     * 找回密码 - 重置密码
     * @param passportVO
     * @param request
     * @return
     */
    @RequestMapping(value="/reset/password", method=RequestMethod.POST)
    public Object reSetPassword(@RequestBody PassportVO passportVO, HttpServletRequest request) {
        try {
            passportVO.setChannelId(getHeaderParam(request).getChannelId());
            passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            passportVO.setIp(getIp(request));
            return memberRetrieveService.updatePassword(passportVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.changePasswordFound"), e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberRetrieveService.changePasswordFound");
        }
    }

    /**
     * 获取4位随机验证码 - android、ios使用
     * @return
     */
    @RequestMapping(value="/get/code", method=RequestMethod.GET)
    public Object sendCommonCode() {
        try {
            return verifyCodeService.sendCommonVerifyCode();
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "verifyCodeService.sendCommonVerifyCode"), e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "verifyCodeService.sendCommonVerifyCode");
        }
    }

    /**
     * 验证4位随机验证码和帐号 - android、ios使用
     * @param passportVO
     * @return
     */
    @RequestMapping(value="/validate/code", method=RequestMethod.POST)
    public Object checkCommonCode(@RequestBody PassportVO passportVO, HttpServletRequest request) {
        try {
            passportVO.setChannelId(getHeaderParam(request).getChannelId());
            passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            passportVO.setIp(getIp(request));
            return verifyCodeService.checkCommonVerifyCode(passportVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "verifyCodeService.checkCommonVerifyCode"), e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "verifyCodeService.checkCommonVerifyCode");
        }
    }
}
