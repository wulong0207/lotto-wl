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
import com.hhly.skeleton.user.vo.TPInfoVO;
import com.hhly.usercore.remote.passport.service.IThirdPartyLoginService;

/**
 * @author zhouyang
 * @version 1.0
 * @desc 第三方登录
 * @date 2017/5/8
 * @company 益彩网络科技公司
 */
public class ThirdPartyLoginV10Controller extends BaseController {

    private static final Logger logger = Logger.getLogger(ThirdPartyLoginV10Controller.class);

    @Autowired
    private IThirdPartyLoginService thirdPartyLoginService;

    /**
     * 第三方登录 - qq登录
     * @param tpInfoVO
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/qq", method = RequestMethod.POST)
    public Object qqLogin(@RequestBody TPInfoVO tpInfoVO, HttpServletRequest request) throws Exception {
        try {
            tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
            if (tpInfoVO.getChannelId().equals("159")) {
                return ResultBO.err(MessageCodeConstants.QQ_CAN_NOT_LOGIN);
            }
            tpInfoVO.setIp(getIp(request));
            return thirdPartyLoginService.tpQQLogin(tpInfoVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.tpQQLogin"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.tpQQLogin");
        }
    }

    /**
     * 第三方 - wx登录
     * @param tpInfoVO
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/wechat", method = RequestMethod.POST)
    public Object wxLogin(@RequestBody TPInfoVO tpInfoVO, HttpServletRequest request) throws Exception {
        try {
            tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
            tpInfoVO.setIp(getIp(request));
            return thirdPartyLoginService.tpWXLogin(tpInfoVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.tpWXLogin"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.tpWXLogin");
        }
    }

    /**
     * 第三方 - 新浪微博登陆
     * @param tpInfoVO
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/weibo", method = RequestMethod.POST)
    public Object wbLogin(@RequestBody TPInfoVO tpInfoVO, HttpServletRequest request) throws Exception {
        try {
            tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
            tpInfoVO.setIp(getIp(request));
            return thirdPartyLoginService.tpWBLogin(tpInfoVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.tpWBLogin"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.tpWBLogin");
        }
    }

    /**
     * 绑定QQ
     * @param tpInfoVO
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bind/qq", method = RequestMethod.POST)
    public Object bingQQ(@RequestBody TPInfoVO tpInfoVO, HttpServletRequest request) throws Exception {
        try {
            tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
            if (tpInfoVO.getChannelId().equals("159")) {
                return ResultBO.err(MessageCodeConstants.QQ_CAN_NOT_LOGIN);
            }
            tpInfoVO.setIp(getIp(request));
            return thirdPartyLoginService.bindQQ(tpInfoVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.bindQQ"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.bindQQ");
        }
    }

    /**
     * 绑定微信
     * @param tpInfoVO
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bind/wechat", method = RequestMethod.POST)
    public Object bindWX(@RequestBody TPInfoVO tpInfoVO, HttpServletRequest request) throws Exception {
        try {
            tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
            tpInfoVO.setIp(getIp(request));
            return thirdPartyLoginService.bindWX(tpInfoVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.bindWX"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.bindWX");
        }
    }

    /**
     * 绑定微博
     * @param tpInfoVO
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bind/weibo", method = RequestMethod.POST)
    public Object bindWB(@RequestBody TPInfoVO tpInfoVO, HttpServletRequest request) throws Exception {
        try {
            tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
            tpInfoVO.setIp(getIp(request));
            return thirdPartyLoginService.bindWB(tpInfoVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.bindWB"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.bindWB");
        }
    }

    /**
     * 解除QQ绑定
     * @param tpInfoVO
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/remove/qq" , method = RequestMethod.POST)
    public Object removeQQ(@RequestBody TPInfoVO tpInfoVO, HttpServletRequest request) throws Exception {
        try {
            tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
            tpInfoVO.setIp(getIp(request));
            return thirdPartyLoginService.removeQQ(tpInfoVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.removeQQ"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.removeQQ");
        }
    }

    /**
     * 解除微信绑定
     * @param tpInfoVO
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/remove/wechat", method = RequestMethod.POST)
    public Object removeWX(@RequestBody TPInfoVO tpInfoVO, HttpServletRequest request) throws Exception {
        try {
            tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
            tpInfoVO.setIp(getIp(request));
            return thirdPartyLoginService.removeWX(tpInfoVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.removeWX"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.removeWX");
        }
    }

    /**
     * 解除微博绑定
     * @param tpInfoVO
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/remove/weibo", method = RequestMethod.POST)
    public Object removeWB(@RequestBody TPInfoVO tpInfoVO, HttpServletRequest request) throws Exception {
        try {
            tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
            tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
            tpInfoVO.setIp(getIp(request));
            return thirdPartyLoginService.removeWB(tpInfoVO);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.removeWB"),
                    e);
            e.printStackTrace();
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "thirdPartyLoginService.removeWB");
        }
    }

}
