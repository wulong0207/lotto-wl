package com.hhly.lotto.api.pc.activity.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottoactivity.remote.service.IActivityAddedService;
import com.hhly.lottoactivity.remote.service.IActivityAwardService;
import com.hhly.lottoactivity.remote.service.IActivityMutilBetService;
import com.hhly.lottoactivity.remote.service.IActivityService;
import com.hhly.lottocore.remote.order.service.IOrderAddService;
import com.hhly.lottocore.remote.order.service.IOrderService;
import com.hhly.skeleton.activity.bo.ActivityDynamicBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.ChaseEnum.ChaseStopType;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.order.bo.OrderInfoBO;
import com.hhly.skeleton.lotto.base.order.vo.OrderAddVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderInfoVO;
import com.hhly.usercore.remote.activity.service.IMemberActivityService;

/**
 * @author yuanshangbing
 * @version 1.0
 * @desc 活动控制层
 * @date 2017/8/9 10:24
 * @company 益彩网络科技公司
 */
@RestController
@RequestMapping("/activity")
public class ActivityController extends BaseController {

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
    @RequestMapping(value = "/queryFirstRecSend" ,method = { RequestMethod.GET, RequestMethod.POST })
    public ResultBO<?> queryFirstRecSendInfo(String token,HttpServletRequest request){
	     String channelId =getHeaderParam(request).getChannelId();
        return iActivityService.getFirstRecSend(token,channelId);
    }
    /**
     * 查询抽奖滚动信息
     * @param token
     * @return
     */
    @RequestMapping(value = "/awardRoll" ,method = { RequestMethod.GET, RequestMethod.POST })
    public ResultBO<?> queryAwardRollInfo(String activityCode){
    	  if(ObjectUtil.isBlank(activityCode)){
              return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
          }
        return iActivityAwardService.findAwardRollInfo(activityCode);
    }
    /**
     * 查询抽奖信息
     * @param token
     * @return
     */
    @RequestMapping(value = "/awardInfo" ,method = { RequestMethod.GET, RequestMethod.POST })
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
    @RequestMapping(value = "/awardExcute" ,method = { RequestMethod.GET, RequestMethod.POST })
    public ResultBO<?> excuteAwardWinInfo(String activityCode,String channelId,String token){
    	  if(ObjectUtil.isBlank(activityCode)||ObjectUtil.isBlank(channelId)||ObjectUtil.isBlank(token)){
              return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
          }
        return iActivityAwardService.excuteAwardWinInfo(activityCode,channelId,token);
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
    	// 活动信息各种校验
    	iActivityAddedService.checkAdded(chase,Constants.NUM_1);
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
    public ResultBO<?> findAddedInfo(String activityCode) throws Exception{    	
		return iActivityAddedService.findActivityAddedInfo(activityCode);
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
}
