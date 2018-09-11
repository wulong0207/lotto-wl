package com.hhly.lotto.api.common.controller.user.member.v1_0;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.pay.vo.OperateCouponVO;
import com.hhly.usercore.remote.member.service.IMemberCouponService;

/**
 * @author lgs on
 * @version 1.0
 * @desc 我的礼品Controller
 * @date 2017/4/14.
 * @company 益彩网络科技有限公司
 */
public class MemberCouponV10Controller extends BaseController {

    @Autowired
    private IMemberCouponService memberCouponService;

    /**
     * 查看用户礼品
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Object findCoupone(@RequestBody OperateCouponVO vo, HttpServletRequest request) {
        vo.setChannelId(getHeaderParam(request).getChannelId());
        vo.setLimitPlatform(getHeaderParam(request).getPlatformId().toString());
        return memberCouponService.findCoupone(vo);
    }

    /**
     * 查看用户礼品数量
     *
     * @return
     */
    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public Object findCouponGroup(@RequestBody OperateCouponVO vo, HttpServletRequest request) {
        vo.setChannelId(getHeaderParam(request).getChannelId());
        vo.setLimitPlatform(getHeaderParam(request).getPlatformId().toString());
        return memberCouponService.findCouponGroup(vo);
    }

    /**
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/typeGroup", method = RequestMethod.POST)
    public Object couponTypeGroup(@RequestBody OperateCouponVO vo, HttpServletRequest request){
        vo.setChannelId(getHeaderParam(request).getChannelId());
        vo.setLimitPlatform(getHeaderParam(request).getPlatformId().toString());
    	return memberCouponService.getCouponTypeGroup(vo);
    }
}
