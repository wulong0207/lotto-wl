package com.hhly.lotto.api.common.controller.activity.v1_0;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.hhly.lotto.api.data.cache.v1_0.UserInfoCacheData;
import com.hhly.lotto.base.common.HeaderParam;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottoactivity.remote.service.IAcitivtyCdkeyService;
import com.hhly.lottoactivity.remote.service.IActivityAddedService;
import com.hhly.lottoactivity.remote.service.IActivityAwardService;
import com.hhly.lottoactivity.remote.service.IActivityMutilBetService;
import com.hhly.lottoactivity.remote.service.IActivityService;
import com.hhly.lottocore.remote.order.service.IOrderAddService;
import com.hhly.lottocore.remote.order.service.IOrderService;
import com.hhly.skeleton.activity.bo.ActivityChpUserBO;
import com.hhly.skeleton.activity.bo.ActivityDynamicBO;
import com.hhly.skeleton.activity.vo.ActivityChpVO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.ChaseEnum.ChaseStopType;
import com.hhly.skeleton.base.common.OrderEnum.NumberCode;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.cms.operatemgr.vo.OperateActivityCdkeyVO;
import com.hhly.skeleton.lotto.base.cooperate.vo.CoOperateCdkeyExchangeVO;
import com.hhly.skeleton.lotto.base.order.bo.OrderInfoBO;
import com.hhly.skeleton.lotto.base.order.vo.OrderAddVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderInfoVO;
import com.hhly.skeleton.user.bo.UserInfoBO;
import com.hhly.usercore.remote.activity.service.IMemberActivityService;

/**
 * @author yuanshangbing
 * @version 1.0
 * @desc 活动控制层
 * @date 2017/8/9 10:24
 * @company 益彩网络科技公司
 */
public class ActivityCommonV10Controller extends BaseController {

    @Autowired
    private IActivityService iActivityService;
    @Autowired
    private IActivityAwardService iActivityAwardService;
    @Autowired
    private IActivityAddedService iActivityAddedService;
    @Autowired
    private IActivityMutilBetService iActivityMutilBetService;
    @Autowired
	private IOrderAddService orderAddService;
    @Autowired
    private IMemberActivityService memberActivityService;
    @Autowired
    private IOrderService iOrderService;
    @Autowired
    private IAcitivtyCdkeyService acitivtyCdkeyService;
    @Autowired
    private UserInfoCacheData userInfoCacheData;
    @Autowired
	private RestTemplate restTemplate;	
    @Value("${lotto_activity_url}")
	private String lottoActivityUrl;
	@Value("${lotto_common_core_url}")
	private String lottoCommonCoreUrl;
    /**
     * 查询竟足/竞蓝首单立减活动信息
     * @param activityCode
     * @return
     */
    @RequestMapping(value = "/queryJzsdActivityInfo" ,method = RequestMethod.GET)
    public ResultBO<?> queryJzsdActivityInfo(String activityCode){
        if(ObjectUtil.isBlank(activityCode)){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        return iActivityService.findJzstActivityInfo(activityCode);
    }
    /**
     * 查询首冲活动信息
     * @param token
     * @return
     */
    @RequestMapping(value = "/queryFirstRecSend" ,method = RequestMethod.GET)
    public ResultBO<?> queryFirstRecSendInfo(String token,HttpServletRequest request){
	     String channelId =getHeaderParam(request).getChannelId();
        return iActivityService.getFirstRecSend(token,channelId);
    }
    /**
     * 查询抽奖滚动信息
     * @return
     */
    @RequestMapping(value = "/awardRoll" ,method = RequestMethod.GET)
    public ResultBO<?> queryAwardRollInfo(String activityCode){
    	  if(ObjectUtil.isBlank(activityCode)){
              return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
          }
        return iActivityAwardService.findAwardRollInfo(activityCode);
    }
    /**
     * 查询抽奖信息
     * @return
     */
    @RequestMapping(value = "/awardInfo" ,method = RequestMethod.GET)
    public ResultBO<?> queryAwardInfo(String activityCode){
    	  if(ObjectUtil.isBlank(activityCode)){
              return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
          }
        return iActivityAwardService.findAwardInfo(activityCode);
    }
    /**
     * 执行抽奖
     * @param token
     * @return
     */
    @RequestMapping(value = "/awardExcute" ,method =  RequestMethod.GET)
    public ResultBO<?> excuteAwardWinInfo(String activityCode,String token,HttpServletRequest request){
    	  if(ObjectUtil.isBlank(activityCode)||ObjectUtil.isBlank(token)){
              return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
          }
    	  HeaderParam headerParam = getHeaderParam(request);
        return iActivityAwardService.excuteAwardWinInfo(activityCode,headerParam.getChannelId(),token);
    }
    
    
    
    /**
     * @desc   执行追号送活动
     * @author lidecheng
     * @create 2017年11月15日
     * @return 
     * @throws Exception 
     */
    @RequestMapping(value = "/excuteAdded" ,method = RequestMethod.POST)
    public ResultBO<?> excuteAdded(@RequestBody OrderAddVO chase) throws Exception{
    	if(ObjectUtil.isBlank(chase.getActivityId())||ObjectUtil.isBlank(chase.getAddIssues())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
    	// 活动信息各种校验
    	ResultBO<?> addRet =iActivityAddedService.checkAdded(chase,Constants.NUM_1);
    	if(addRet.isError()){
    		throw new ResultJsonException(addRet);
    	}
    	//验证用户信息
    	ResultBO<?> ret = memberActivityService.verifyPerfectInfo(chase.getToken());
    	if(ret.isError())
    		throw new ResultJsonException(ret);
    	// 设置为永追
     	chase.setStopType(ChaseStopType.ALWAYS.getValue());
		return orderAddService.addChase(chase);
    }
    
    /**
     * @desc   验证追号送活动
     * @author lidecheng
     * @create 2017年11月15日
     * @return 
     * @throws Exception 
     */
    @RequestMapping(value = "/validateAdded" ,method = { RequestMethod.GET, RequestMethod.POST })
    public ResultBO<?> validateAddedInfo(@RequestBody OrderAddVO chase) throws Exception{  
    	//验证用户信息
    	ResultBO<?> ret = memberActivityService.verifyPerfectInfo(chase.getToken());
    	if(ret.isError())
    		throw new ResultJsonException(ret);
		return iActivityAddedService.checkAdded(chase,Constants.NUM_1);
    }
    
    /**
     * @desc   查询追号送活动
     * @author lidecheng
     * @create 2017年11月15日
     * @return 
     * @throws Exception 
     */
    @RequestMapping(value = "/addedInfo" ,method = { RequestMethod.GET, RequestMethod.POST })
    public ResultBO<?> findAddedInfo(String lotteryCode) throws Exception{    	
		return iActivityAddedService.findActivityAddedInfo(lotteryCode);
    }
    
    /**
     * @desc   查询倍投立减活动
     * @author lidecheng
     * @create 2017年11月15日
     * @return 
     * @throws Exception 
     */
    @RequestMapping(value = "/mutilBetInfo" ,method = { RequestMethod.GET, RequestMethod.POST })
    public ResultBO<?> findMutilBetInfo(String activityCode) throws Exception{    	
		return iActivityMutilBetService.findActivityMutilBetInfo(activityCode);
    }
    
    /**
     * 倍投立减下订单
     * @author lidecheng
     * @date 2017年11月17日
     * @return
     */
    @RequestMapping(value = "/addMutilBet", method = RequestMethod.POST)
    public ResultBO<?> addJzsdOrder(@RequestBody OrderInfoVO orderInfoVO) throws Exception{
        if(ObjectUtil.isBlank(orderInfoVO) || ObjectUtil.isBlank(orderInfoVO.getToken()) || ObjectUtil.isBlank(orderInfoVO.getActivityCode())
                || ObjectUtil.isBlank(orderInfoVO.getMultipleNum()) || ObjectUtil.isBlank(orderInfoVO.getOrderAmount())
                || ObjectUtil.isBlank(orderInfoVO.getOrderDetailList()) || ObjectUtil.isBlank(orderInfoVO.getBuyScreen())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        //验证用户信息
    	ResultBO<?> ret = memberActivityService.verifyPerfectInfo(orderInfoVO.getToken());
    	if(ret.isError())
    		throw new ResultJsonException(ret);
    	//验证活动信息
    	iActivityMutilBetService.validateMutilBet(orderInfoVO);
    	ResultBO<?>  result = iOrderService.addOrder(orderInfoVO);
        if(result.isError()){
            return result;
        }
        OrderInfoBO orderInfoBO = (OrderInfoBO)result.getData();
        ActivityDynamicBO activityDynamicBO = new ActivityDynamicBO();
        activityDynamicBO.setOrderCode(orderInfoBO.getOrderCode());
        return ResultBO.ok(activityDynamicBO);      
    } 
    
	
	/**
	 * 年会派奖活动
	 * @return
	 */
	@RequestMapping(value = "/findAnnualMeet", method = RequestMethod.GET)
	public Object findAnnualMeetCountDown(String token){
		if(ObjectUtil.isBlank(token)){
			 return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
		}
		return iActivityService.findAnnualMeetCountDown(token);
	}

    /**
     * cdk兑换红包接口
     * @param vo
     * @return
     */
    @RequestMapping(value = "/exchange", method = RequestMethod.POST)
    public Object exchange(@RequestBody CoOperateCdkeyExchangeVO vo, HttpServletRequest request) {
    	Assert.paramNotNull(vo.getCdkey(), "cdkey");
        vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
        vo.setChannelId(getHeaderParam(request).getChannelId());
        String cdkey = vo.getCdkey().toUpperCase().trim();
        vo.setCdkey(cdkey);
        if(cdkey.startsWith(NumberCode.CDKEY_INTEGRAL.getCode())) {
        	return restTemplate.postForEntity(lottoCommonCoreUrl + "cooperate/cdkey/exchange", vo, ResultBO.class).getBody();
        }else if(cdkey.startsWith(NumberCode.CDKEY_COUNPN.getCode())){
        	OperateActivityCdkeyVO cdkeyVO = new OperateActivityCdkeyVO();
        	cdkeyVO.setToken(vo.getToken());
        	cdkeyVO.setCdkey(vo.getCdkey());
        	cdkeyVO.setChannelId(vo.getChannelId());
        	cdkeyVO.setPlatform(vo.getPlatform());
        	return acitivtyCdkeyService.exchangeCdkey(cdkeyVO);
        }else{
        	return ResultBO.err(MessageCodeConstants.CDK_FORMAT_IS_ERROR);
        }
    }
    
    /**
	 * 加奖活动,查询加奖信息
	 * @return
	 */
	@RequestMapping(value = "/findAddAward", method = RequestMethod.GET)
	public Object findActivityAddAwardInfo(Integer lotteryCode,HttpServletRequest request){
		 int platform = getHeaderParam(request).getPlatformId().shortValue();
	     String channelId =getHeaderParam(request).getChannelId();
		return iActivityService.findActivityAddAwardInfo(lotteryCode,channelId,platform);
	}
	

	 /**
	 * 查询用户竞猜信息
	 * @return
	 */
	@RequestMapping(value = "/chpUserInfo", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findChpUserInfo(String token){
		if (StringUtil.isBlank(token)) {
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		UserInfoBO userInfo =(UserInfoBO) userInfoCacheData.checkToken(token).getData();
        if (ObjectUtil.isBlank(userInfo)) {
            throw new ResultJsonException(ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE));
        }
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userInfo.getId());
		ResultBO<?> result =  restTemplate.getForObject(
				lottoActivityUrl + "activityChp/chpUserInfo?userId={userId}", ResultBO.class,
				params);
		if(result.getSuccess()==0){
			return result;
		}
		ActivityChpUserBO chpBO = JSON.parseObject(JSON.toJSONString(result.getData()), ActivityChpUserBO.class);			
		chpBO.setNickName(userInfo.getNickname());
		chpBO.setHeadUrl(userInfo.getHeadUrl());
		return ResultBO.ok(chpBO);
	}
		
	/**
	 * 查询球队信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/chpUserTeam", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findChpUserTeam(String token){
		Integer userId =null;
		if (!StringUtil.isBlank(token)) {
			UserInfoBO userInfo =(UserInfoBO) userInfoCacheData.checkToken(token).getData();
	        if (!ObjectUtil.isBlank(userInfo)) {
		        userId = userInfo.getId();

	        }
		}		
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return  restTemplate.getForObject(
				lottoActivityUrl + "activityChp/chpUserTeam?userId={userId}", ResultBO.class,
				params);
	}
	
	/**
	 * 查询球队信息，将球队信息分组
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/chpTeamByGroup", method = { RequestMethod.GET, RequestMethod.POST })
	public Object  findChpTeamByGroup(String token){
		Integer userId =null;
		if (!StringUtil.isBlank(token)) {
			UserInfoBO userInfo =(UserInfoBO) userInfoCacheData.checkToken(token).getData();
	        if (!ObjectUtil.isBlank(userInfo)) {
		        userId = userInfo.getId();

	        }
		}		
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		return  restTemplate.getForObject(
				lottoActivityUrl + "activityChp/chpTeamByGroup?userId={userId}", ResultBO.class,
				params);
	}
	
	
	/**
	 * 查询积分排行信息
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/chpRank", method = { RequestMethod.GET, RequestMethod.POST })
	public Object  findChpRank(String token, HttpServletRequest request){
		Integer userId =null;
		if (!StringUtil.isBlank(token)) {
			UserInfoBO userInfo =(UserInfoBO) userInfoCacheData.checkToken(token).getData();
	        if (!ObjectUtil.isBlank(userInfo)) {
		        userId = userInfo.getId();

	        }
		}	
		HeaderParam headerParam = getHeaderParam(request);
        Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("pId",headerParam.getPlatformId());
		return  restTemplate.getForObject(
				lottoActivityUrl + "activityChp/chpRank?userId={userId}&pId={pId}", ResultBO.class,
				params);
	}    
	
	/**
	 * 世界杯竞猜活动，执行竞猜前判断
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/checkChpTeam", method = {  RequestMethod.POST })
	public Object checkChpTeam(@RequestBody ActivityChpVO vo, HttpServletRequest request ) throws Exception{
		if(ObjectUtil.isBlank(vo) || ObjectUtil.isBlank(vo.getToken()) || ObjectUtil.isBlank(vo.getActivityCode())
	                || ObjectUtil.isBlank(vo.getRuleId())){
	        return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        //验证用户信息
    	ResultBO<?> ret = memberActivityService.verifyPerfectInfo(vo.getToken());
    	if(ret.isError())
    		throw new ResultJsonException(ret);
		UserInfoBO userInfo =(UserInfoBO) userInfoCacheData.checkToken(vo.getToken()).getData();	
		vo.setUserId(userInfo.getId());
		HeaderParam headerParam = getHeaderParam(request);
		vo.setChannelId(headerParam.getChannelId());
		return  restTemplate.postForEntity(lottoActivityUrl + "activityChp/checkChpTeam", vo, String.class).getBody();
	}    
	
	/**
	 * 世界杯竞猜活动，执行竞猜
	 * @param userId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/addChpTeam", method = {  RequestMethod.POST })
	public Object addChpTeam(@RequestBody ActivityChpVO vo, HttpServletRequest request ) throws Exception{
		if(ObjectUtil.isBlank(vo) || ObjectUtil.isBlank(vo.getToken()) || ObjectUtil.isBlank(vo.getActivityCode())
	                || ObjectUtil.isBlank(vo.getRuleId())){
	        return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        //验证用户信息
    	ResultBO<?> ret = memberActivityService.verifyPerfectInfo(vo.getToken());
    	if(ret.isError())
    		throw new ResultJsonException(ret);
		UserInfoBO userInfo =(UserInfoBO) userInfoCacheData.checkToken(vo.getToken()).getData();	
		vo.setUserId(userInfo.getId());
		HeaderParam headerParam = getHeaderParam(request);
		vo.setChannelId(headerParam.getChannelId());
		return  restTemplate.postForEntity(lottoActivityUrl + "activityChp/addChpTeam", vo, String.class).getBody();
	}    
}
