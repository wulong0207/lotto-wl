package com.hhly.lotto.api.common.controller.user.passport.v1_0;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.UserConstants;
import com.hhly.skeleton.base.util.HttpUtil;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.user.vo.OauthVO;
import com.hhly.skeleton.user.vo.TPInfoVO;
import com.hhly.usercore.remote.passport.service.IThirdPartyLoginService;

/**
 * @author zhouyang
 * @version 1.0
 * @desc pc端第三方登录请求接口
 * @date 2017/5/20
 * @company 益彩网络科技公司
 */
public class OauthV10Controller extends BaseController {
    
    
    @Resource
    protected IThirdPartyLoginService thirdPartyLoginService;
    
    /**
     * PC端微信登录
     * @param oauthVO
     * @return Object
     * @throws Exception 异常
     */
    @RequestMapping(value = "/wechat",method = RequestMethod.POST)
    @ResponseBody
    public Object getWXCode(@RequestBody OauthVO oauthVO, HttpServletRequest request) throws Exception {
        //用于保持请求和回调的状态，授权请求后原样带回给第三方。该参数可用于防止csrf攻击（跨站请求伪造攻击），建议第三方带上该参数，可设置为简单的随机数加session进行校验
        //第一步:用户在手机上点击确认登录后，根据code获取access_token
        TPInfoVO tpInfoVO = new TPInfoVO();
        tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
        tpInfoVO.setReqType(oauthVO.getReqType());
        tpInfoVO.setAgentCode(oauthVO.getAgentCode());
        String wxAccessTokenStr = getWxAccessToken(oauthVO.getCode(), oauthVO.getAppId(), oauthVO.getSecret());
        Map<String ,Object > accessTokenMap = JsonUtil.json2Map(wxAccessTokenStr);
        //第二步:根据获取到的access_token获取用户信息
        if (!ObjectUtil.isBlank(accessTokenMap.get("access_token")) && !ObjectUtil.isBlank(accessTokenMap.get("openid"))) {
            String wxUserInfoStr = getWxUserInfo(accessTokenMap.get("access_token").toString(), accessTokenMap.get("openid").toString());
            Map<String, Object> wxUserInfoMap = JsonUtil.json2Map(wxUserInfoStr);
            //第三步:请求本地接口
            tpInfoVO.setCity(wxUserInfoMap.get("city").toString());
            tpInfoVO.setHeadimgurl(wxUserInfoMap.get("headimgurl").toString());
            tpInfoVO.setNickname(wxUserInfoMap.get("nickname").toString());
            tpInfoVO.setProvince(wxUserInfoMap.get("province").toString());
            tpInfoVO.setSex(Integer.valueOf(wxUserInfoMap.get("sex").toString()).shortValue());
            tpInfoVO.setOpenid(wxUserInfoMap.get("unionid").toString());
            tpInfoVO.setIp(getIp(request));
        } else {
            logger.info("无法获取到openid或者access_token"+"----a_t值为:"+accessTokenMap.get("access_token")+"----openid值为:"+accessTokenMap.get("openid"));
            return ResultBO.err("无法获取到openid或者access_token");
        }
        return thirdPartyLoginService.tpWXLogin(tpInfoVO);
    }
    
    /**
     * PC微信，通过code获取access_token
     * @param code code
     * @return String
     * @throws Exception 异常
     */
    protected String getWxAccessToken(String code, String appid, String secret) throws Exception{
        //String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        Map<String, String> params = new HashMap<>();
        params.put("appid", appid);
        params.put("secret",secret);
        params.put("code", code);
        params.put("grant_type", UserConstants.WxLoginConstans.GRANT_TYPE);
        return HttpUtil.doGet(UserConstants.WxLoginConstans.WX_ACCESS_TOKEN_URL,params);
    }
    
    /**
     * PC微信登录，获取微信账号信息
     * @param access_token access_token
     * @param openId openId
     * @return 微信账号jsonStr
     * @throws Exception 异常
     */
    protected String getWxUserInfo(String access_token,String openId) throws Exception{
        //String url = "https://api.weixin.qq.com/sns/userinfo";
        Map<String, String> params = new HashMap<>();
        params.put("access_token", access_token);
        params.put("openid", openId);
        return HttpUtil.doGet(UserConstants.WxLoginConstans.WX_USER_INFO_URL,params);
    }

    @RequestMapping(value = "/qq",method = RequestMethod.POST)
    @ResponseBody
    public Object getQQCode(@RequestBody OauthVO oauthVO, HttpServletRequest request) throws Exception {
        String callback = getOpenID(oauthVO.getAccessToken());
        String data = StringUtils.stripEnd(StringUtils.stripStart(callback, "callback("), ");");
        Map<String, Object> objectMap = JsonUtil.json2Map(data);
        String userInfo = getQQUserInfo(oauthVO.getAccessToken(), objectMap.get("openid").toString());
        Map<String, Object> map = JsonUtil.json2Map(userInfo);
        TPInfoVO tpInfoVO = new TPInfoVO();
        tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
        tpInfoVO.setReqType(oauthVO.getReqType());
        tpInfoVO.setOpenid(objectMap.get("unionid").toString());
        tpInfoVO.setNickname(map.get("nickname").toString());
        tpInfoVO.setCity(map.get("city").toString());
        tpInfoVO.setFigureurl_qq_2(map.get("figureurl_qq_2").toString());
        tpInfoVO.setProvince(map.get("province").toString());
        tpInfoVO.setGender(map.get("gender").toString());
        tpInfoVO.setAgentCode(oauthVO.getAgentCode());
        tpInfoVO.setIp(getIp(request));
        return thirdPartyLoginService.tpQQLogin(tpInfoVO);
    }

    private String getOpenID(String accessToken) throws Exception {
        String url = MessageFormat.format(UserConstants.WxLoginConstans.QQ_OPEN_ID_URL,accessToken);
        return HttpUtil.getRemotePage(url);
    }

    private String getQQUserInfo(String accessToken,String openid) throws Exception {
        //https://graph.qq.com/user/get_user_info?access_token=xxx&oauth_consumer_key=xxx&openid=xxx
        Map<String, String> params = new HashMap<>();
        params.put("access_token", accessToken);
        params.put("oauth_consumer_key", UserConstants.WxLoginConstans.QQ_APPID);
        params.put("openid", openid);
        return HttpUtil.doGet(UserConstants.WxLoginConstans.QQ_USER_INFO_URL,params);
    }

    /**********************************************************************************************新浪微博登陆*************************************************************************************************/
    @RequestMapping(value = "/weibo",method = RequestMethod.POST)
    @ResponseBody
    private Object weiboLogin(@RequestBody OauthVO oauthVO, HttpServletRequest request) throws Exception {
    	Map<String, Object> map = JsonUtil.json2Map(getWeiboAccessToken(oauthVO.getCode()));
    	JSONObject userData = JSONObject.parseObject(getWeiboUserInfo(map.get("uid").toString(), map.get("access_token").toString()));
        TPInfoVO tpInfoVO = new TPInfoVO();
        tpInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        tpInfoVO.setChannelId(getHeaderParam(request).getChannelId());
        tpInfoVO.setNickname(userData.getString("name"));
        tpInfoVO.setReqType(oauthVO.getReqType());
        tpInfoVO.setAgentCode(oauthVO.getAgentCode());
        tpInfoVO.setOpenid(userData.getString("id"));
        String location = userData.getString("location");
        if (!ObjectUtil.isBlank(location)) {
            if (location.trim().contains(" ")) {
                tpInfoVO.setProvince(location.split(" ")[0].toString());
                tpInfoVO.setCity(location.split(" ")[1].toString());
            } else {
                tpInfoVO.setProvince(location);
                tpInfoVO.setCity("");
            }
        } else {
            tpInfoVO.setProvince("");
            tpInfoVO.setCity("");
        }
        tpInfoVO.setHeadimgurl(userData.getString("profile_image_url"));
        tpInfoVO.setGender(userData.getString("gender"));
        tpInfoVO.setIp(getIp(request));
        return thirdPartyLoginService.tpWBLogin(tpInfoVO);
    }
    
    /**
     * PC微信，通过code获取access_token
     * @param code code
     * @return String
     * @throws Exception 异常
     */
    protected static String getWeiboAccessToken(String code) throws Exception{
        Map<String, String> params = new HashMap<>();
        params.put("client_id", UserConstants.WEIBO_CLIENT_ID);
        params.put("client_secret",UserConstants.WEIBO_CLIENT_SECRET);
        params.put("code", code);
        params.put("grant_type", UserConstants.WEIBO_GRANT_TYPE);
        params.put("redirect_uri", "http://cp.2ncai.com/oauth2.html");
        return HttpUtil.doPost(UserConstants.WEIBO_ACCESS_TOKEN_URL, params);
    }
    
//    protected static String getWeiboUid(String accessToken) throws Exception{
//    	String url = "https://api.weibo.com/2/account/get_uid.json";
//    	Map<String, String> headers = new HashMap<>();
//    	headers.put("Authorization", "OAuth2 " + accessToken);
//    	
//        return HttpUtil.doGet(url, null, headers);
//    }
    
    protected static String getWeiboUserInfo(String uid, String accessToken) throws Exception{
    	Map<String, String> params = new HashMap<>();
    	params.put("uid", uid);
    	
    	Map<String, String> headers = new HashMap<>();
    	headers.put("Authorization", "OAuth2 " + accessToken);
    	
        return HttpUtil.doGet(UserConstants.WEIBO_USER_INFO_URL, params, headers);
    }    
    
//    public static void main(String[] args) {
//		try {
//			Map<String, Object> dataMap = JsonUtil.json2Map(getWeiboAccessToken("c5c5a216ffd19fd8d4fa64ed2bafd660"));
//			System.out.println(JSONObject.toJSONString(dataMap));
//			System.out.println(getWeiboAccessToken("4fb1977e236e4e5a2aede9774da5c803"));
//			System.out.println(getWeiboUid("2.00lpjhLC0o9AeN462c2c248aRVT_vC"));
//			System.out.println(getWeiboUserInfo(dataMap.get("uid").toString(), dataMap.get("access_token").toString()));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
