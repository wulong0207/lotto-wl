package com.hhly.lotto.api.common.controller.user.member.v1_1;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.pay.vo.PayBankcardVO;
import com.hhly.usercore.remote.member.service.IMemberBankcardV11Service;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouyang
 * @version 1.0
 * @desc
 * @date 2018/6/23
 * @company 益彩网络科技公司
 */

public class MemberBankcardV11Controller extends BaseController {

    private static final Logger logger = Logger.getLogger(MemberMessageV11Controller.class);

    @Autowired
    private IMemberBankcardV11Service memberBankcardV11Service;

    /**
     * 根据银行卡号取到银行卡名称，类型
     *
     * @param vo
     * @return Object
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public Object getBankName (@RequestBody PayBankcardVO vo, HttpServletRequest httpRequest) {
        vo.setIp(getIp(httpRequest));
        vo.setPlatform(getHeaderParam(httpRequest).getPlatformId().shortValue());
        return memberBankcardV11Service.getBankName(vo);
    }


    /**
     * 获取验证码
     *
     * @param vo
     * @return object
     */
    @RequestMapping(value = "/get/code", method = RequestMethod.POST)
    public Object getValidateCode (@RequestBody PayBankcardVO vo, HttpServletRequest httpRequest) throws Exception {
        vo.setIp(getIp(httpRequest));
        vo.setPlatform(getHeaderParam(httpRequest).getPlatformId().shortValue());
        return memberBankcardV11Service.getValidateCode(vo);
    }

    /**
     * 保存银行卡
     *
     * @param vo 数组对象
     * @return Object
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object addBandCard (@RequestBody PayBankcardVO vo, HttpServletRequest httpRequest) throws Exception {
        vo.setIp(getIp(httpRequest));
        vo.setPlatform(getHeaderParam(httpRequest).getPlatformId().shortValue());
        return memberBankcardV11Service.addBankCard(vo);
    }

}
