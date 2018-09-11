package com.hhly.lotto.api.h5.controller.user.passport.v1_0;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hhly.lotto.api.common.controller.user.passport.v1_0.OauthV10Controller;
import com.hhly.lotto.api.data.sys.v1_0.SysV10Data;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.hhly.skeleton.base.util.HttpUtil;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.user.vo.OauthVO;
import com.hhly.skeleton.user.vo.TPInfoVO;

/**
 * @author zhouyang
 * @version 1.0
 * @desc pc端第三方登录请求接口
 * @date 2017/12/7
 * @company 益彩网络科技公司
 */
@RestController
@RequestMapping("/h5/v1.0/oauth")
public class OauthH5V10Controller extends OauthV10Controller {
	
	@Value("${wx_mp_get_api_accesstoken_url}")
	private String wx_mp_get_api_accesstoken_url;
	
	@Value("${wx_mp_get_jsapi_ticket_url}")
	private String wx_mp_get_jsapi_ticket_url;	
	
	@Autowired
	SysV10Data sysV10Data;
	
	@Autowired
	RedisUtil redisUtil;
	
	@Override
	public Object getWXCode(@RequestBody OauthVO oauthVO, HttpServletRequest request) throws Exception {
		Map<String, String> map = sysV10Data.findWxAccList();
		if(ObjectUtil.isBlank(map))
			throw new ServiceRuntimeException("H5微信公众号权限查询为null");
		if(!map.containsKey(oauthVO.getAppId()) || ObjectUtil.isBlank(map.get(oauthVO.getAppId())))
			return ResultBO.err("20001");
		oauthVO.setSecret(map.get(oauthVO.getAppId()));
		
        //用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
        //第一步:用户在手机上点击确认登录后，根据code获取access_token
        String wxAccessTokenStr = getWxAccessToken(oauthVO.getCode(), oauthVO.getAppId(), oauthVO.getSecret());
        Map<String ,Object > accessTokenMap = JsonUtil.json2Map(wxAccessTokenStr);
        //第二步:根据获取到的access_token获取用户信息
        String wxUserInfoStr = getWxUserInfo(accessTokenMap.get("access_token").toString(),accessTokenMap.get("openid").toString());
        Map<String, Object> wxUserInfoMap = JsonUtil.json2Map(wxUserInfoStr);
        //第三步:请求本地接口
        TPInfoVO tpInfoVO = new TPInfoVO();
        tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
        tpInfoVO.setReqType(oauthVO.getReqType());
        tpInfoVO.setCity(wxUserInfoMap.get("city").toString());
        tpInfoVO.setHeadimgurl(wxUserInfoMap.get("headimgurl").toString());
        tpInfoVO.setNickname(wxUserInfoMap.get("nickname").toString());
        tpInfoVO.setProvince(wxUserInfoMap.get("province").toString());
        tpInfoVO.setSex(Integer.valueOf(wxUserInfoMap.get("sex").toString()).shortValue());
        tpInfoVO.setOpenid(wxUserInfoMap.get("openid").toString());
        tpInfoVO.setUnionid(wxUserInfoMap.get("unionid").toString());
        tpInfoVO.setAgentCode(oauthVO.getAgentCode());
        tpInfoVO.setIp(getIp(request));
        return thirdPartyLoginService.tpV11WXLogin(tpInfoVO);		
	}
	
    @RequestMapping(value = "/wechat/getShareToken",method = RequestMethod.POST)
    @ResponseBody	
	public Object getShareToken(@RequestBody OauthVO oauthVO, HttpServletRequest request) throws Exception {
		Map<String, String> map = sysV10Data.findWxAccList();
		if(ObjectUtil.isBlank(map))
			throw new ServiceRuntimeException("H5微信公众号权限查询为null");
		if(!map.containsKey(oauthVO.getAppId()) || ObjectUtil.isBlank(map.get(oauthVO.getAppId())))
			return ResultBO.err("20001");
		oauthVO.setSecret(map.get(oauthVO.getAppId()));
		
        //第一步:获取公众号接口请求权限access_token
		String api_access_token = redisUtil.getObj(oauthVO.getAppId(), new String());
		if(ObjectUtil.isBlank(api_access_token)){
			String tokenResult = HttpUtil.getRemotePage(MessageFormat.format(wx_mp_get_api_accesstoken_url,oauthVO.getAppId(), oauthVO.getSecret()));
			api_access_token = JSONObject.parseObject(tokenResult).getString("access_token");
			redisUtil.addObj(oauthVO.getAppId(), api_access_token, CacheConstants.TWO_HOURS);
		}
        //第二步:根据获取到的access_token获取用户分享权限
		Map<String, String> dataMap= redisUtil.getObj(api_access_token, new HashMap<String, String>());
		if(ObjectUtil.isBlank(dataMap)){
	        String result = HttpUtil.getRemotePage(MessageFormat.format(wx_mp_get_jsapi_ticket_url,api_access_token));
	        dataMap = new HashMap<String, String>();
	        dataMap.put("shareToken", JSONObject.parseObject(result).getString("ticket"));
	        redisUtil.addObj(api_access_token, dataMap, CacheConstants.TWO_HOURS);
		}
        return ResultBO.ok(dataMap);		
	}
}
