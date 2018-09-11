package com.hhly.lotto.api.common.controller.user.passport.v1_0;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.user.bo.UserInfoBO;
import com.hhly.skeleton.user.vo.PassportVO;
import com.hhly.usercore.remote.activity.service.IMemberActivityService;

/**
 * @author zhouyang
 * @version 1.0
 * @desc 用户活动详情
 * @date 2017/8/8
 * @company 益彩网络科技公司
 */
public class MemberActivityV10Controller extends BaseController {

    @Autowired
    private IMemberActivityService memberActivityService;

    /**
     * 注册 - 验证并绑定手机号或email地址（1）
     * @param passportVO
     * @param request
     * @return
     */
    @RequestMapping(value="/register", method= RequestMethod.POST)
    public Object regFromActivity(@RequestBody PassportVO passportVO, HttpServletRequest request) {
        try {
            passportVO.setChannelId(getHeaderParam(request).getChannelId());
            passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            passportVO.setIp(getIp(request));
            return memberActivityService.regFromActivity(passportVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "userMgrService.regFromActivity"), e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "userMgrService.regFromActivity");
        }
    }

    /**
     * 验证账户是否完善资料
     * @param passportVO
     * @return
     */
    @RequestMapping(value="/verifyPerfectInfo", method= RequestMethod.POST)
    public Object verifyPerfectInfo(@RequestBody PassportVO passportVO, HttpServletRequest request) {
        passportVO.setChannelId(getHeaderParam(request).getChannelId());
        passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        passportVO.setIp(getIp(request));
        try {
            ResultBO<?> resultBO = memberActivityService.verifyPerfectInfo(passportVO.getToken());
            if (resultBO.getSuccess() == 0) {
                return resultBO;
            }
            UserInfoBO userInfoBO = (UserInfoBO) resultBO.getData();
            if (!ObjectUtil.isBlank(userInfoBO.getIdCard())) {
                userInfoBO.setIdCard(StringUtil.hideString(userInfoBO.getIdCard(), (short)1));
            }
            if (!ObjectUtil.isBlank(userInfoBO.getMobile())) {
                userInfoBO.setMobile(StringUtil.hideString(userInfoBO.getMobile(), (short)3));
            }
            if (!ObjectUtil.isBlank(userInfoBO.getEmail())) {
                userInfoBO.setEmail(StringUtil.hideString(userInfoBO.getEmail(), (short)4));
            }
            if (!ObjectUtil.isBlank(userInfoBO.getRealName())) {
                userInfoBO.setRealName(StringUtil.hideString(userInfoBO.getRealName(), (short)5));
            }
            return resultBO;
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "userMgrService.verifyPerfectInfo"), e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "userMgrService.verifyPerfectInfo");
        }
    }

    /**
     * 验证帐户名
     * @param vo
     * @return
     */
    @RequestMapping(value = "/check/account", method = RequestMethod.POST)
    public Object checkAccount(@RequestBody PassportVO vo) {
        return memberActivityService.checkAccount(vo);
    }

}
