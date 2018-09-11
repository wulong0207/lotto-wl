package com.hhly.lotto.api.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hhly.lottoactivity.remote.service.IActivityService;
import com.hhly.lottocore.remote.order.service.IOrderSearchService;
import com.hhly.skeleton.activity.bo.ActivityDynamicBO;
import com.hhly.skeleton.activity.bo.JzstActivityInfo;
import com.hhly.skeleton.activity.bo.JzstActivityRule;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OrderEnum;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.JCLQConstants;
import com.hhly.skeleton.base.constants.JCZQConstants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.FormatConversionJCUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.order.vo.ActivityOrderQueryInfoVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderDetailVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderInfoVO;
import com.hhly.skeleton.user.bo.UserInfoBO;
import com.hhly.skeleton.user.vo.UserActivityVO;
import com.hhly.usercore.remote.activity.service.IMemberActivityService;


/**
 * @author yuanshangbing
 * @version 1.0
 * @desc 活动公共校验
 * @date 2017/8/9 17:17
 * @company 益彩网络科技公司
 */
@Component("activityValid")
public class ActivityValid {

    private static Logger logger = Logger.getLogger(ActivityValid.class);

    @Autowired
    private IMemberActivityService memberActivityService;

    @Autowired
    private IActivityService iActivityService;

    @Autowired
    private IOrderSearchService iOrderSearchService;


    /**
     * 竟足首投立减活动校验
     * @param orderInfoVO
     * @param type 1:订单校验 2：支付校验
     * @return
     * @throws Exception
     */
    public ResultBO validJzstOrderInfo(OrderInfoVO orderInfoVO,Integer type) throws Exception{
        try {
            Integer lotteryCode =Integer.valueOf(
                    String.valueOf(orderInfoVO.getLotteryCode()).substring(Constants.NUM_0, Constants.NUM_3));
            //1.校验活动订单信息（只支持胜平负，2串1,代购）
            ResultBO<?> resultBO = validOrderInfo(orderInfoVO, lotteryCode,type);
            if (resultBO.isError()) {
                return resultBO;
            }
            //2.校验用户信息是否完善
            resultBO =  memberActivityService.verifyPerfectInfo(orderInfoVO.getToken());
            if(resultBO.isError()){
                return resultBO;
            }
            UserInfoBO userInfo = (UserInfoBO)resultBO.getData();
            //3.校验活动信息是否可用(时间，状态等),校验倍数是否合法
            resultBO = validActivityInfo(orderInfoVO);
            if (resultBO.isError()){
                return resultBO;
            }
            //4.当前用户是否购买过竟足/竟篮
            resultBO = currentUserBuyJC(userInfo.getId(),orderInfoVO.getOrderAmount(),type,lotteryCode);
            if(resultBO.isError()){
                return resultBO;
            }
            //5.其他账户是否购买过竟足/竟篮
            resultBO = otherAccountBuyJC(userInfo,orderInfoVO.getOrderAmount(),type,lotteryCode);
            if(resultBO.isError()){
                return resultBO;
            }
            List<Integer> mobileIdCardUserIds = (List<Integer>)resultBO.getData();
            //6.当前用户是否有出票失败的活动订单
            resultBO = currentUserActivityTicketFail(userInfo.getId(),orderInfoVO.getActivityCode(),lotteryCode);
            if(resultBO.isError()){
                return resultBO;
            }
            //7.其他账户是否有出票失败的活动订单
            resultBO = otherAccountActivityTicketFail(mobileIdCardUserIds,orderInfoVO.getActivityCode(),lotteryCode);
            if(resultBO.isError()){
                return resultBO;
            }
            //8.当前用户是否参与过活动
            resultBO = currentUserJoinActivity(userInfo.getId(),orderInfoVO.getActivityCode(),orderInfoVO.getOrderAmount(),lotteryCode);
            if(resultBO.isError()){
                return resultBO;
            }
            //9.当前用户是否有未支付的活动订单
            resultBO = currentUserNoPayActivityOrder(userInfo.getId(),orderInfoVO.getActivityCode(),type,lotteryCode);
            if(resultBO.isError()){
                return resultBO;
            }
            //10.其他账户是否参与过活动
            resultBO = otherAccountJoinActivity(mobileIdCardUserIds,orderInfoVO.getActivityCode(),orderInfoVO.getOrderAmount(),lotteryCode);
            if(resultBO.isError()){
                return resultBO;
            }
            //11.其他账户是否有未支付的活动订单
            resultBO = otherAccountNoPayActivityOrder(mobileIdCardUserIds,orderInfoVO.getActivityCode(),type,lotteryCode);
            if(resultBO.isError()){
                return resultBO;
            }
            //12.真实姓名最多十个账户参与活动
            resultBO =  realNameJoinActivityLimit(userInfo.getRealName(),orderInfoVO.getActivityCode(),lotteryCode);
            if(resultBO.isError()){
                return resultBO;
            }
        }catch (Exception e){
            logger.error("校验竟足/竟篮首单立减活动下单信息失败！",e);
            return ResultBO.err();
        }
        return ResultBO.ok();
    }

    /**
     * 校验活动信息是否可用(时间，状态等),校验倍数是否合法
     * @param orderInfoVO
     * @return
     */
    private ResultBO validActivityInfo(OrderInfoVO orderInfoVO) {
        ResultBO<?> resultBO = iActivityService.findJzstActivityInfo(orderInfoVO.getActivityCode());
        if(resultBO.isError()){
            return resultBO;
        }
        JzstActivityInfo jzstActivityInfo = (JzstActivityInfo)resultBO.getData();
        resultBO = jzstActivityInfo.getVerifyActivityInfo();
        if(resultBO.isError()){
            return resultBO;
        }
        //校验活动订单倍数是否合法
        List<JzstActivityRule> jzstActivityRules =  jzstActivityInfo.getJzstActivityRules();
        boolean isMatch = false;
        for(JzstActivityRule jzstActivityRule : jzstActivityRules){
            if(jzstActivityRule.getMultipleNum() == orderInfoVO.getMultipleNum()){//校验五倍，二十倍
                isMatch = true;
                break;
            }
            if(orderInfoVO.getMultipleNum() >= jzstActivityRule.getMultipleNum() && jzstActivityRule.getMultipleNum()==Constants.NUM_50){//校验大于等于五十倍
                isMatch = true;
                break;
            }
        }
        if(!isMatch){
            return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_ORDER_MULTIPLENUM_IS_NOT_SUPPORT);
        }
        return ResultBO.ok();
    }

    /**
     * 校验活动订单信息（只支持胜平负，2串1）
     * @param orderInfoVO
     * @param lotteryCode
     * @param type type 1:订单校验 2：支付校验
     * @return
     */
    private ResultBO validOrderInfo(OrderInfoVO orderInfoVO, Integer lotteryCode,Integer type) {
        if(Constants.NUM_2 == type){ //支付不需要重复校验订单信息
            return ResultBO.ok();
        }
        //校验是否是竟足/竟篮胜平负
        if(JCZQConstants.ID_JCZQ_B==lotteryCode){
            if(JCZQConstants.ID_JCZQ!=orderInfoVO.getLotteryCode()){
                return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_ORDER_LOTTERY_CODE_IS_NOT_SUPPORT);
            }
        }
        if(JCLQConstants.ID_JCLQ_B==lotteryCode){
            if(JCLQConstants.ID_JCLQ_SF!=orderInfoVO.getLotteryCode()){
                return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_ORDER_LOTTERY_CODE_IS_NOT_SUPPORT);
            }
        }
        //校验是否是2串1
        for(OrderDetailVO orderDetailVO : orderInfoVO.getOrderDetailList()){
            String[] betContent = FormatConversionJCUtil
                    .singleBetContentAnalysis(orderDetailVO.getPlanContent());
            String bunchStr = betContent[1];
            String bunchstrs [] =bunchStr.split(SymbolConstants.UNDERLINE);
            if(bunchstrs.length!= Constants.NUM_2 || !bunchstrs[0].equals("2") || !bunchstrs[1].equals("1")){
                return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_ORDER_BUNCH_IS_NOT_SUPPORT);
            }
        }
        //校验是否是代购
        if(OrderEnum.BuyType.BUY.getValue() != orderInfoVO.getBuyType()){
            return ResultBO.err(MessageCodeConstants. FOOTBALL_FIRST_ORDER_BUY_TYPE_IS_NOT_SUPPORT);
        }
        //校验是否是两场赛事
        String buyScreens[] = orderInfoVO.getBuyScreen().split(SymbolConstants.COMMA);
        if(buyScreens.length!=2){
            return ResultBO.err(MessageCodeConstants. FOOTBALL_FIRST_ORDER_TWO_MATCH);
        }
        //校验是否只有一个详情
        if(orderInfoVO.getOrderDetailList().size()!=1){
            return ResultBO.err(MessageCodeConstants. FOOTBALL_FIRST_ORDER_DETAIL);
        }
        //不能传categoryId
        if(!ObjectUtil.isBlank(orderInfoVO.getCategoryId())){
            return ResultBO.err(MessageCodeConstants. BET_CONTENT_ILLEGAL_SERVICE);
        }
        return ResultBO.ok();
    }

    /**
     * 等前用户是否购买过竟足/竟篮
     * @param userId
     * @param orderAmount
     * @return
     * @throws Exception
     */
    private ResultBO<?> currentUserBuyJC(Integer userId,Double orderAmount,Integer type,Integer lotteryCode) throws Exception{
        if(Constants.NUM_2 == type){ //支付不需要重复校验订单信息
            return ResultBO.ok();
        }
        ActivityOrderQueryInfoVO activityOrderQueryInfoVO = new ActivityOrderQueryInfoVO();
        activityOrderQueryInfoVO.setUserId(userId);
        activityOrderQueryInfoVO.setQueryType(Constants.NUM_1);
        activityOrderQueryInfoVO.setLotteryCode(lotteryCode);
        ResultBO<?> resultBO = iOrderSearchService.queryJoinActivityOrderCount(activityOrderQueryInfoVO);
        if(resultBO.isError()){
            return resultBO;
        }
        Integer count = (Integer) resultBO.getData();
        if(count > Constants.NUM_0){
            String lotteryName = "";
            if(JCZQConstants.ID_JCZQ_B == lotteryCode){
                lotteryName =  "竞彩足球";
            }else if(JCLQConstants.ID_JCLQ_B == lotteryCode){
                lotteryName =  "竞彩篮球";
            }
            String orderAmounts [] = {lotteryName,String.valueOf(orderAmount)};
            ActivityDynamicBO activityDynamicBO = new ActivityDynamicBO();
            activityDynamicBO.setOrderAmount(orderAmount);
            return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_DISCOUNT_HAVE_BUY,activityDynamicBO,orderAmounts);
        }
        return ResultBO.ok();
    }

    /**
     * 其他账户是否购买过竟足/竟篮
     * @param userInfo
     * @param orderAmount
     * @return
     * @throws Exception
     */
    private ResultBO<?> otherAccountBuyJC(UserInfoBO userInfo,Double orderAmount,Integer type,Integer lotteryCode)throws Exception{
        if(Constants.NUM_2 == type){ //支付不需要重复校验订单信息
            return ResultBO.ok();
        }
        List<Integer> userIds = new ArrayList<Integer>();
        UserActivityVO userActivityVO = new UserActivityVO();
        //手机号去校验
        userActivityVO.setType(Short.valueOf("1"));
        userActivityVO.setQueryField(userInfo.getMobile());
        ResultBO<?> resultBO = memberActivityService.findUserList(userActivityVO);
        if(resultBO.isError()){
            return resultBO;
        }
        List<Integer> mobileUserIds = (List<Integer>)resultBO.getData();
        userActivityVO.setType(Short.valueOf("2"));
        userActivityVO.setQueryField(userInfo.getIdCard());
        resultBO = memberActivityService.findUserList(userActivityVO);
        if(resultBO.isError()){
            return resultBO;
        }
        List<Integer> idCardUserIds = (List<Integer>)resultBO.getData();
        if(!ObjectUtil.isBlank(mobileUserIds)){
            userIds.addAll(mobileUserIds);
        }
        if(!ObjectUtil.isBlank(idCardUserIds)){
            userIds.addAll(idCardUserIds);
        }
        if(!ObjectUtil.isBlank(userIds)){
            ActivityOrderQueryInfoVO activityOrderQueryInfoVO = new ActivityOrderQueryInfoVO();
            activityOrderQueryInfoVO.setUserIds(userIds);
            activityOrderQueryInfoVO.setQueryType(Constants.NUM_2);
            activityOrderQueryInfoVO.setLotteryCode(lotteryCode);
            resultBO = iOrderSearchService.queryJoinActivityOrderCount(activityOrderQueryInfoVO);
            if(resultBO.isError()){
                return resultBO;
            }
            Integer count = (Integer) resultBO.getData();
            if(count > Constants.NUM_0){
                String lotteryName = "";
                if(JCZQConstants.ID_JCZQ_B == lotteryCode){
                    lotteryName =  "竞彩足球";
                }else if(JCLQConstants.ID_JCLQ_B == lotteryCode){
                    lotteryName =  "竞彩篮球";
                }
                String params [] = {lotteryName,String.valueOf(orderAmount)};
                ActivityDynamicBO activityDynamicBO = new ActivityDynamicBO();
                activityDynamicBO.setOrderAmount(orderAmount);
                return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_DISCOUNT_HAVA_ACCOUNT_BUY,activityDynamicBO,params);
            }
        }
        return ResultBO.ok(userIds);
    }

    /**
     * 当前用户是否参与过活动
     * @param userId
     * @param activityCode
     * @param orderAmount
     * @return
     * @throws Exception
     */
    private ResultBO<?> currentUserJoinActivity(Integer userId,String activityCode,Double orderAmount,Integer lotteryCode) throws Exception{
        ActivityOrderQueryInfoVO activityOrderQueryInfoVO = new ActivityOrderQueryInfoVO();
        activityOrderQueryInfoVO.setUserId(userId);
        activityOrderQueryInfoVO.setQueryType(Constants.NUM_3);
        activityOrderQueryInfoVO.setActivityCode(activityCode);
        activityOrderQueryInfoVO.setLotteryCode(lotteryCode);
        ResultBO<?> resultBO = iOrderSearchService.queryJoinActivityOrderCount(activityOrderQueryInfoVO);
        if(resultBO.isError()){
            return resultBO;
        }
        Integer count = (Integer) resultBO.getData();
        if(count > Constants.NUM_0){
            Double orderAmounts [] = {orderAmount};
            ActivityDynamicBO activityDynamicBO = new ActivityDynamicBO();
            activityDynamicBO.setOrderAmount(orderAmount);
            return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_DISCOUNT_IS_JOIN_ONE,activityDynamicBO,orderAmounts);
        }
        return ResultBO.ok();
    }

    /**
     * 当前用户是否有未支付的活动订单
     * @param userId
     * @param activityCode
     * @return
     * @throws Exception
     */
    private ResultBO<?> currentUserNoPayActivityOrder(Integer userId,String activityCode,Integer type,Integer lotteryCode) throws Exception{
        if(Constants.NUM_2 == type){ //支付不需要重复校验订单信息
            return ResultBO.ok();
        }
        ActivityOrderQueryInfoVO activityOrderQueryInfoVO = new ActivityOrderQueryInfoVO();
        activityOrderQueryInfoVO.setUserId(userId);
        activityOrderQueryInfoVO.setQueryType(Constants.NUM_4);
        activityOrderQueryInfoVO.setActivityCode(activityCode);
        activityOrderQueryInfoVO.setLotteryCode(lotteryCode);
        ResultBO<?> resultBO = iOrderSearchService.queryJoinActivityOrderCount(activityOrderQueryInfoVO);
        if(resultBO.isError()){
            return resultBO;
        }
        Integer count = (Integer) resultBO.getData();
        if(count > Constants.NUM_0){
            activityOrderQueryInfoVO.setQueryType(null);
            resultBO = iOrderSearchService.queryNoPayActivityOrderNo(activityOrderQueryInfoVO);
            List<String> orderCodes = (List<String>)resultBO.getData();
            ActivityDynamicBO activityDynamicBO = new ActivityDynamicBO();
            activityDynamicBO.setOrderCode(orderCodes.get(0));
            return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_DISCOUNT_HAVE_NO_PAY,activityDynamicBO,null);
        }
        return ResultBO.ok();
    }

    /**
     * 其他账户是否参与过活动
     * @param mobileIdCardUserIds
     * @param activityCode
     * @param orderAmount
     * @return
     * @throws Exception
     */
    private ResultBO<?> otherAccountJoinActivity(List<Integer> mobileIdCardUserIds,String activityCode,Double orderAmount,Integer lotteryCode) throws Exception{
        if(!ObjectUtil.isBlank(mobileIdCardUserIds)){
            ActivityOrderQueryInfoVO activityOrderQueryInfoVO = new ActivityOrderQueryInfoVO();
            activityOrderQueryInfoVO.setUserIds(mobileIdCardUserIds);
            activityOrderQueryInfoVO.setQueryType(Constants.NUM_5);
            activityOrderQueryInfoVO.setActivityCode(activityCode);
            activityOrderQueryInfoVO.setLotteryCode(lotteryCode);
            ResultBO<?> resultBO = iOrderSearchService.queryJoinActivityOrderCount(activityOrderQueryInfoVO);
            if(resultBO.isError()){
                return resultBO;
            }
            Integer count = (Integer) resultBO.getData();
            if(count > Constants.NUM_0){
                Double orderAmounts [] = {orderAmount};
                ActivityDynamicBO activityDynamicBO = new ActivityDynamicBO();
                activityDynamicBO.setOrderAmount(orderAmount);
                return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_DISCOUNT_HAVA_ACCOUNT_JOIN,activityDynamicBO,orderAmounts);
            }
        }
        return ResultBO.ok();
    }

    /**
     * 其他账户是否有未支付的活动订单
     * @param mobileIdCardUserIds
     * @param activityCode
     * @return
     * @throws Exception
     */
    private ResultBO<?> otherAccountNoPayActivityOrder(List<Integer> mobileIdCardUserIds,String activityCode,Integer type,Integer lotteryCode) throws Exception{
        if(Constants.NUM_2 == type){ //支付不需要重复校验订单信息
            return ResultBO.ok();
        }
        if(!ObjectUtil.isBlank(mobileIdCardUserIds)){
            ActivityOrderQueryInfoVO activityOrderQueryInfoVO = new ActivityOrderQueryInfoVO();
            activityOrderQueryInfoVO.setUserIds(mobileIdCardUserIds);
            activityOrderQueryInfoVO.setQueryType(Constants.NUM_6);
            activityOrderQueryInfoVO.setActivityCode(activityCode);
            activityOrderQueryInfoVO.setLotteryCode(lotteryCode);
            ResultBO<?> resultBO = iOrderSearchService.queryJoinActivityOrderCount(activityOrderQueryInfoVO);
            if(resultBO.isError()){
                return resultBO;
            }
            Integer count = (Integer) resultBO.getData();
            if(count > Constants.NUM_0){
                return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_DISCOUNT_OTHER_ACCOUNT_NO_PAY);
            }
        }
        return ResultBO.ok();
    }

    /**
     * 真实姓名最多十个账户参与活动
     * @param realName
     * @param activityCode
     * @return
     * @throws Exception
     */
    private ResultBO<?> realNameJoinActivityLimit(String realName,String activityCode,Integer lotteryCode) throws Exception{
        UserActivityVO userActivityVO = new UserActivityVO();
        userActivityVO.setType(Short.valueOf("3"));
        userActivityVO.setQueryField(realName);
        ResultBO<?> resultBO = memberActivityService.findUserList(userActivityVO);
        if(resultBO.isError()){
            return resultBO;
        }
        List<Integer> realNameUserIds = (List<Integer>)resultBO.getData();
        if(!ObjectUtil.isBlank(realNameUserIds)){
            ActivityOrderQueryInfoVO activityOrderQueryInfoVO = new ActivityOrderQueryInfoVO();
            activityOrderQueryInfoVO.setUserIds(realNameUserIds);
            activityOrderQueryInfoVO.setQueryType(Constants.NUM_7);
            activityOrderQueryInfoVO.setActivityCode(activityCode);
            activityOrderQueryInfoVO.setLotteryCode(lotteryCode);
            resultBO = iOrderSearchService.queryJoinActivityOrderCount(activityOrderQueryInfoVO);
            if(resultBO.isError()){
                return resultBO;
            }
            Integer count = (Integer) resultBO.getData();
            if(count >= Constants.NUM_10){
                String realNames[] = {realName};
                ActivityDynamicBO activityDynamicBO = new ActivityDynamicBO();
                activityDynamicBO.setRealName(realName);
                return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_DISCOUNT_OTHER_ACCOUNT_JOIN_IN,activityDynamicBO,realNames);
            }
        }
        return ResultBO.ok();
    }

    /**
     * 当前用户是否有出票失败的活动订单
     * @param userId
     * @param activityCode
     * @return
     * @throws Exception
     */
    private ResultBO<?> currentUserActivityTicketFail(Integer userId,String activityCode,Integer lotteryCode) throws Exception{
        ActivityOrderQueryInfoVO activityOrderQueryInfoVO = new ActivityOrderQueryInfoVO();
        activityOrderQueryInfoVO.setUserId(userId);
        activityOrderQueryInfoVO.setQueryType(Constants.NUM_8);
        activityOrderQueryInfoVO.setActivityCode(activityCode);
        activityOrderQueryInfoVO.setLotteryCode(lotteryCode);
        ResultBO<?> resultBO = iOrderSearchService.queryJoinActivityOrderCount(activityOrderQueryInfoVO);
        if(resultBO.isError()){
            return resultBO;
        }
        Integer count = (Integer) resultBO.getData();
        if(count > Constants.NUM_0){
            return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_ORDER_TICKET_FAIL);
        }
        return ResultBO.ok();
    }


    /**
     * 其他用户是否有出票失败的活动订单
     * @param mobileIdCardUserIds
     * @param activityCode
     * @return
     * @throws Exception
     */
    private ResultBO<?> otherAccountActivityTicketFail(List<Integer> mobileIdCardUserIds,String activityCode,Integer lotteryCode) throws Exception{
        if(!ObjectUtil.isBlank(mobileIdCardUserIds)){
            ActivityOrderQueryInfoVO activityOrderQueryInfoVO = new ActivityOrderQueryInfoVO();
            activityOrderQueryInfoVO.setUserIds(mobileIdCardUserIds);
            activityOrderQueryInfoVO.setQueryType(Constants.NUM_9);
            activityOrderQueryInfoVO.setActivityCode(activityCode);
            activityOrderQueryInfoVO.setLotteryCode(lotteryCode);
            ResultBO<?> resultBO = iOrderSearchService.queryJoinActivityOrderCount(activityOrderQueryInfoVO);
            if(resultBO.isError()){
                return resultBO;
            }
            Integer count = (Integer) resultBO.getData();
            if(count > Constants.NUM_0){
                return ResultBO.err(MessageCodeConstants.FOOTBALL_FIRST_HAVE_ACCOUNT_ORDER_TICKET_FAIL);
            }
        }
        return ResultBO.ok();
    }


}
