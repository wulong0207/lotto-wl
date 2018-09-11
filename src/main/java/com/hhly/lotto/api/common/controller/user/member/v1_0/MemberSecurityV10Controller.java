package com.hhly.lotto.api.common.controller.user.member.v1_0;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.user.vo.UserModifyLogVO;
import com.hhly.usercore.remote.member.service.IMemberSecurityService;

/**
 * @author zhouyang
 * @version 1.0
 * @desc 账户安全中心控制层
 * @date 2017/12/8
 * @company 益彩网络科技公司
 */
public class MemberSecurityV10Controller extends BaseController {

    @Autowired
    private IMemberSecurityService memberSecurityService;

    /**
     * 展示账户动态及个人信息
     * @param vo
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public Object showSafeCenter(@RequestBody UserModifyLogVO vo, HttpServletRequest request) {
        try {
            vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            vo.setChannelId(getHeaderParam(request).getChannelId());
            return memberSecurityService.findUserModifyLogByUserId(vo);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "userModifyLogService.findUserModifyLogByUserId"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "userModifyLogService.findUserModifyLogByUserId");
        }
    }

    /**
     * 查询账户动态
     *
     * @param vo uservo查询条件
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object selectAccountDynamic(@RequestBody UserModifyLogVO vo, HttpServletRequest request) {
        vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        vo.setChannelId(getHeaderParam(request).getChannelId());
        vo.setUserIp(getIp(request));
        return memberSecurityService.selectByUserId(vo);
    }
}
