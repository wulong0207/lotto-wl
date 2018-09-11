package com.hhly.lotto.api.common.controller.user.member.v1_0;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.user.bo.UserMsgInfoBO;
import com.hhly.skeleton.user.vo.PassportVO;
import com.hhly.skeleton.user.vo.UserInfoVO;
import com.hhly.skeleton.user.vo.UserMsgConfigVO;
import com.hhly.skeleton.user.vo.UserMsgInfoVO;
import com.hhly.usercore.remote.member.service.IMemberMessageService;

/**
 * @author zhouyang
 * @version 1.0
 * @desc 消息中心
 * @date 2017/11/9
 * @company 益彩网络科技公司
 */
public class MemberMessageV10Controller extends BaseController {

    private static final Logger logger = Logger.getLogger(MemberMessageV10Controller.class);

    @Autowired
    private IMemberMessageService memberMessageService;

    /**
     * 查询消息中心菜单
     * @param vo
     * @return
     */
    @RequestMapping(value = "/menu", method = RequestMethod.POST)
    public Object quertMsgMenu(@RequestBody UserMsgInfoVO vo, HttpServletRequest request) {
        vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        vo.setChannelId(getHeaderParam(request).getChannelId());
        return memberMessageService.findMsgMenu(vo);
    }

    /**
     * 查询消息中心集合
     * @param vo
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object queryMsgList(@RequestBody UserMsgInfoVO vo, HttpServletRequest request) {
        vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        vo.setChannelId(getHeaderParam(request).getChannelId());
        ResultBO<?> result = memberMessageService.findList(vo);
        if(result == null || result.isError() || ObjectUtil.isBlank(result.getData())) {
        	return result;
        }
        PagingBO<UserMsgInfoBO> pageData = (PagingBO<UserMsgInfoBO>) result.getData();
        for(UserMsgInfoBO userMsgInfoBO : pageData.getData()) {
        	Integer toBuyLotteryCode = userMsgInfoBO.getToBuyLotteryCode();
			if (userMsgInfoBO.getLotteryCode() == null && toBuyLotteryCode != null && toBuyLotteryCode.intValue() >= Constants.NUM_100) {
        		userMsgInfoBO.setLotteryCode(userMsgInfoBO.getToBuyLotteryCode());
        	}
        }
        return result;
    }

    /**
     * 查询消息中心开关
     * @param vo
     * @return
     */
    @RequestMapping(value = "/switch", method = RequestMethod.POST)
    public Object queryMsgSwitch(@RequestBody UserMsgInfoVO vo, HttpServletRequest request) {
        vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        vo.setChannelId(getHeaderParam(request).getChannelId());
        return memberMessageService.findMsgSwitch(vo);
    }

    /**
     * 查询消息中心子开关
     * @param vo
     * @return
     */
    @RequestMapping(value = "/switch/node", method = RequestMethod.POST)
    public Object queryMsgNodeSwitch(@RequestBody UserMsgInfoVO vo, HttpServletRequest request) {
        vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        vo.setChannelId(getHeaderParam(request).getChannelId());
        return memberMessageService.findMsgNodeSwitch(vo);
    }

    /**
     * 修改消息状态
     * @param vo
     * @return
     */
    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public Object updateMsgInfo(@RequestBody UserMsgInfoVO vo, HttpServletRequest request) {
        vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        vo.setChannelId(getHeaderParam(request).getChannelId());
        return memberMessageService.updateMsgInfoStatus(vo);
    }

    /**
     * 修改消息开关状态
     * @param vo
     * @return
     */
    @RequestMapping(value = "/set/switch", method = RequestMethod.POST)
    public Object updateSwitchStatus(@RequestBody UserMsgConfigVO vo, HttpServletRequest request) {
        vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        vo.setChannelId(getHeaderParam(request).getChannelId());
        return memberMessageService.updateSwitchStatus(vo);
    }

    /**
     * 重置消息开关，恢复默认
     * @param vo
     * @return
     */
    @RequestMapping(value = "/reset/switch", method = RequestMethod.POST)
    public Object recoverDefault(@RequestBody UserMsgConfigVO vo, HttpServletRequest request) {
        vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        vo.setChannelId(getHeaderParam(request).getChannelId());
        return memberMessageService.recoverDefault(vo);
    }

    /**
     * 修改彩种开关状态
     * @param vo
     * @return
     */
    @RequestMapping(value = "/set/lottery/switch", method = RequestMethod.POST)
    public Object updateLotterySwitchStatus(@RequestBody UserMsgConfigVO vo, HttpServletRequest request) {
        vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        vo.setChannelId(getHeaderParam(request).getChannelId());
        return memberMessageService.updateLotterySwitchStatus(vo);
    }

    /***
     * 删除消息
     * @param vo
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Object deleteMsgInfoById(@RequestBody UserMsgInfoVO vo, HttpServletRequest request) {
        vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        vo.setChannelId(getHeaderParam(request).getChannelId());
        return memberMessageService.deleteMsgInfoById(vo);
    }

    /**
     * 清空列表
     * @param vo
     * @return
     */
    @RequestMapping(value = "/empty", method = RequestMethod.POST)
    public Object empty(@RequestBody UserMsgInfoVO vo, HttpServletRequest request) {
        vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        vo.setChannelId(getHeaderParam(request).getChannelId());
        return memberMessageService.deleteMsgInfo(vo);
    }


    @RequestMapping(value="/getDisturb", method=RequestMethod.POST)
    public Object getDisturb (@RequestBody UserInfoVO userInfoVO) {
        try {
            return memberMessageService.getDisturb(userInfoVO.getToken());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberMessageService.getDisturb"), e);
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberMessageService.getDisturb");
        }
    }

    /**
     *  消息设置-勿扰模式，设置勿扰时间段
     * @return object
     */
    @RequestMapping(value="/doNotDisturb", method=RequestMethod.POST)
    public Object doNotDisturb(@RequestBody PassportVO passportVO) {
        try {
            return memberMessageService.doNotDisturb(passportVO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberMessageService.doNotDisturb"), e);
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberMessageService.doNotDisturb");
        }
    }



}
