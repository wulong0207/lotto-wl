package com.hhly.lotto.api.common.controller.user.member.v1_0;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.hhly.skeleton.user.vo.PassportVO;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.pay.vo.PayBankcardVO;
import com.hhly.usercore.remote.member.service.IMemberBankcardService;

/**
 * @auth chenkangning
 * @date 2017.03.06
 * @desc 银行卡管理控制类
 * @compay 益彩网络科技有限公司
 * @version 1.0
 */
public class MemberBankCardV10Controller extends BaseController{

    private static final Logger logger = Logger.getLogger(MemberBankCardV10Controller.class);

    @Resource
    private IMemberBankcardService memberBankcardService;
    
    /**
     * @param payBankcardVO
     * @return
     */
        @RequestMapping(value = "/operate/quickPay", method = RequestMethod.POST)
    public Object openOrCloseQuickPayment ( @RequestBody PayBankcardVO payBankcardVO) {
        try {
            return memberBankcardService.openOrCloseQuickPayment(payBankcardVO.getToken(),payBankcardVO.getCardId(), payBankcardVO.getStatus().intValue());

        } catch (Exception e) {
            printException(e, "memberBankcardService.openOrCloseQuickPayment");
            return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.openOrCloseQuickPayment");
        }
    }
    
    /**
     * pc端添加银行卡
     * @param payBankcardVO 数据对象
     * @return Object
     */
    @RequestMapping(value = "/save/bankCard", method = RequestMethod.POST)
    public Object addBandCardPc(HttpServletRequest httpServletRequest,@RequestBody PayBankcardVO payBankcardVO) {
        try {
            payBankcardVO.setIp(getIp(httpServletRequest));
            return memberBankcardService.addBandCardPc(payBankcardVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberBankcardService.addBandCardPc"));
            return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberBankcardService.addBandCardPc");
        }
    }

    /**
     * 激活信用卡
     * @param payBankcardVO 参数
     * @return Object
     */
    @RequestMapping(value = "/activate/creditCard", method = RequestMethod.POST)
    public Object activateCard(HttpServletRequest httpServletRequest,@RequestBody PayBankcardVO payBankcardVO){
        try {
            payBankcardVO.setIp(getIp(httpServletRequest));
            return memberBankcardService.activateCard(payBankcardVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberBankcardService.activateCard"));
            return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberBankcardService.activateCard");
        }
    }

    /**
     * 修改
     *
     * @param payBankcardVO 参数
     * @return Object
     */
    @RequestMapping(value = "/modify/mobile", method = RequestMethod.POST)
    public Object updateOpenMobile (HttpServletRequest httpServletRequest, @RequestBody PayBankcardVO payBankcardVO) {
        try {
            payBankcardVO.setIp(getIp(httpServletRequest));
            return memberBankcardService.updateOpenMobile(payBankcardVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.updateOpenMobile"));
            return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.updateOpenMobile");
        }
    }

    /**
     * 查询用户银行卡信息
     * @param payBankcardVO
     * @return Object
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object selectBankCard(@RequestBody PayBankcardVO payBankcardVO) {
        try {
            return memberBankcardService.selectBankCard(payBankcardVO.getToken());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberBankcardService.selectBankCard"));
            return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberBankcardService.selectBankCard");
        }
    }

    /**
     * 根据id删除银行卡,逻辑删除，修改状态为0
     * @param payBankcardVO
     * @return Object
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object deleteByBankCardId(@RequestBody PayBankcardVO payBankcardVO) {
        try {
            return memberBankcardService.deleteByBankCardId(payBankcardVO.getToken(),payBankcardVO.getId());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberBankcardService.deleteByBankCardId"));
            return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberBankcardService.deleteByBankCardId");
        }
    }
    
    /**
     * 保存银行卡
     *
     * @param payBankcardVO 数组对象
     * @return Object
     */
    @RequestMapping(value = "/add/bankCard", method = RequestMethod.POST)
    public Object addBandCard (HttpServletRequest httpRequest, @RequestBody PayBankcardVO payBankcardVO) {
        try {
            payBankcardVO.setIp(getIp(httpRequest));
            payBankcardVO.setPlatform(getHeaderParam(httpRequest).getPlatformId().shortValue());
            return memberBankcardService.addBankCard(payBankcardVO.getToken(), payBankcardVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.addBankCard"));
            return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.addBankCard");
        }
    }

    /**
     * 查询用户银行卡信息
     *
     * @param payBankcardVO
     * @return Object
     */
    @RequestMapping(value = "/app/list", method = RequestMethod.POST)
    public Object selectBankCardMobile (@RequestBody PayBankcardVO payBankcardVO) {
        try {
            return memberBankcardService.selectCardForModile(payBankcardVO.getToken());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.selectBankCardMobile"));
            return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.selectBankCardMobile");
        }
    }

    /**
     * 处理快捷支付
     *
     * @param payBankcardVO
     * @return Object
     */
    @RequestMapping(value = "/close/quickPay", method = RequestMethod.POST)
    public Object closeOrOpenQuickPayment (@RequestBody PayBankcardVO payBankcardVO) {
        try {
            return memberBankcardService.closenQuickPayment(payBankcardVO.getToken(), payBankcardVO.getCardId(), payBankcardVO.getStatus().intValue());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.closeOrOpenQuickPayment"));
            return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.closeOrOpenQuickPayment");
        }
    }

    /**
     * 根据银行卡号取到银行卡名称，类型
     *
     * @param payBankcardVO
     * @return Object
     */
    @RequestMapping(value = "/get/bankCard/detail", method = RequestMethod.POST)
    public Object getBankName (@RequestBody PayBankcardVO payBankcardVO) {
        try {
            return memberBankcardService.getBankName(payBankcardVO.getToken(), payBankcardVO.getCardcode());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.getBankName"));
            return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.getBankName");
        }
    }

    /**
     * 获取验证码
     *
     * @param payBankcardVO
     * @return object
     */
    @RequestMapping(value = "/get/code", method = RequestMethod.POST)
    public Object getValidateCode (@RequestBody PayBankcardVO payBankcardVO) {
        try {
            return memberBankcardService.getValidateCode(payBankcardVO.getToken(), payBankcardVO.getMobile());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.getValidateCode"));
            return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.getValidateCode");
        }
    }

    @RequestMapping(value = "/getCardLimitDetail", method = RequestMethod.POST)
    public Object getCardLimitDetail (@RequestBody PayBankcardVO payBankcardVO) {
        try {
            Short bankId = payBankcardVO.getBankid().shortValue();
            Short bankType = payBankcardVO.getBanktype().shortValue();
            return memberBankcardService.getCardLimitDetail(payBankcardVO.getToken(), bankId, bankType);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.getCardLimitDetail"));
            return ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberBankcardService.getCardLimitDetail");
        }
    }
}
