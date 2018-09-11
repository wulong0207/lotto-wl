package com.hhly.lotto.api.pc.controller.user.passwport.v1_0;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.user.passport.v1_0.OauthV10Controller;
import com.hhly.skeleton.base.constants.UserConstants.WxLoginConstans;
import com.hhly.skeleton.user.vo.OauthVO;

/**
 * @author zhouyang
 * @version 1.0
 * @desc pc端第三方登录请求接口
 * @date 2017/12/7
 * @company 益彩网络科技公司
 */
@RestController
@RequestMapping("/pc/v1.0/oauth")
public class OauthPcV10Controller extends OauthV10Controller {

	@Override
	public Object getWXCode(@RequestBody OauthVO oauthVO, HttpServletRequest request) throws Exception {
		oauthVO.setAppId(WxLoginConstans.APPID);
		oauthVO.setSecret(WxLoginConstans.APPSECRET);
		return super.getWXCode(oauthVO, request);
	}


}
