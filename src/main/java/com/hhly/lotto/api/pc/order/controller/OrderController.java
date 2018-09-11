package com.hhly.lotto.api.pc.order.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.ActivityValid;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottoactivity.remote.service.IActivityService;
import com.hhly.lottocore.remote.order.service.IOrderAddService;
import com.hhly.lottocore.remote.order.service.IOrderSearchService;
import com.hhly.lottocore.remote.order.service.IOrderService;
import com.hhly.paycore.remote.service.IOperateCouponService;
import com.hhly.paycore.remote.service.ITransUserService;
import com.hhly.skeleton.activity.bo.ActivityDynamicBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OrderEnum;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.PayConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.lotto.base.order.bo.OrderAddBO;
import com.hhly.skeleton.lotto.base.order.bo.OrderBaseInfoBO;
import com.hhly.skeleton.lotto.base.order.bo.OrderFullDetailInfoBO;
import com.hhly.skeleton.lotto.base.order.bo.OrderInfoBO;
import com.hhly.skeleton.lotto.base.order.bo.OrderInfoSingleBO;
import com.hhly.skeleton.lotto.base.order.vo.OrderInfoSingleUploadVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderInfoVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderQueryVo;
import com.hhly.skeleton.lotto.base.order.vo.OrderSingleQueryVo;
import com.hhly.skeleton.lotto.base.order.vo.OrderStatisticsQueryVo;
import com.hhly.skeleton.lotto.base.order.vo.UserChaseDetailQueryVO;
import com.hhly.skeleton.lotto.base.order.vo.UserNumOrderDetailQueryVO;
import com.hhly.skeleton.lotto.base.order.vo.UserSportOrderDetailQueryVO;
import com.hhly.skeleton.lotto.base.recommend.vo.RcmdInfoVO;
import com.hhly.skeleton.pay.bo.OperateCouponBO;
import com.hhly.skeleton.pay.bo.TransUserBO;
import com.hhly.skeleton.pay.vo.TransUserVO;

/**
 * @author yuanshangbing
 * @version 1.0
 * @desc
 * @date 2017/3/15 17:13
 * @company 益彩网络科技公司
 */
@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

    private static Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    private IOrderSearchService iorderSearchService;

    @Autowired
    private IOrderService iOrderService;
    
    /**
	 * 远程服务:追号服务(包含追号)
	 */
	@Autowired
	private IOrderAddService orderAddService;

    @Autowired
    private ITransUserService iTransUserService;

    @Autowired
    private IOperateCouponService operateCouponService;

    @Autowired
    private ActivityValid activityValid;
   
    @Autowired
    private IActivityService activityService;
    
    @Value("${redcent.activity.code}")
    private String gdYfgcActivityCode;
    
    @Value("${jx.redcent.activity.code}")
    private String jxYfgcActivityCode;
    /**
     * 查询订单列表
     * @param orderQueryVo
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/orderlist" ,method = RequestMethod.POST)
    public ResultBO<?> queryOrderInfoList(@RequestBody OrderQueryVo orderQueryVo){
         if(ObjectUtil.isBlank(orderQueryVo) || ObjectUtil.isBlank(orderQueryVo.getToken())
                 ||ObjectUtil.isNull(orderQueryVo.getPageIndex()) || ObjectUtil.isBlank(orderQueryVo.getPageSize())
                 || ObjectUtil.isBlank(orderQueryVo.getBeginDate()) || ObjectUtil.isBlank(orderQueryVo.getEndDate())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
         }
         try {
             Date startDate = DateUtil.convertStrToDate(orderQueryVo.getBeginDate(),DateUtil.DATE_FORMAT);
             Date finishDate = DateUtil.convertStrToDate(orderQueryVo.getEndDate(),DateUtil.DATE_FORMAT);
             if(DateUtil.judgmentDate(startDate,finishDate)){//最大查询一年的数据
                 return ResultBO.err(MessageCodeConstants.MAX_ORDER_LIST_NUM);
             }
             setDateForOrderList(orderQueryVo, startDate, finishDate);
             if(Constants.NUM_1==orderQueryVo.getSource()){//移动端
                 if(orderQueryVo.getPageSize()> Constants.NUM_10){//每页最大显示10条
                     orderQueryVo.setPageSize(Constants.NUM_10);
                 }
             }else {
                 if(orderQueryVo.getPageSize()> Constants.NUM_20){//每页最大显示20条
                     orderQueryVo.setPageSize(Constants.NUM_20);
                 }
             }
             ResultBO<?> resultBO = iorderSearchService.queryOrderListInfo(orderQueryVo);
             if(resultBO.isError()){
                 return resultBO;
             }
             PagingBO<OrderBaseInfoBO> orderBaseInfoBOPagingBO = (PagingBO<OrderBaseInfoBO>) resultBO.getData();
             List<OrderBaseInfoBO> result = orderBaseInfoBOPagingBO.getData();
             if(!ObjectUtil.isBlank(result)){
                 //取支付使用的红包金额和加奖红包奖金
                 for(OrderBaseInfoBO orderBaseInfoBO : result){
                     //取支付红包金额
                     if(OrderEnum.PayStatus.SUCCESS_PAY.getValue()==orderBaseInfoBO.getPayStatus().shortValue()
                             || OrderEnum.PayStatus.REFUNDMENT.getValue()==orderBaseInfoBO.getPayStatus().shortValue()){//支付了的订单才取支付红包金额
                         TransUserVO transUser = new TransUserVO();
                         transUser.setOrderCode(orderBaseInfoBO.getOrderCode());
                         transUser.setToken(orderQueryVo.getToken());
                         transUser.setTransType(PayConstants.TransTypeEnum.LOTTERY.getKey());
                         List<TransUserBO> list = (List<TransUserBO>)iTransUserService.findUserTransRecordByOrderCode(transUser).getData();
                         if(!ObjectUtil.isBlank(list)){
                             orderBaseInfoBO.setRedAmount(list.get(0).getRedTransAmount());
                         }
                     }
                     //加奖红包
                     if(Integer.valueOf(OrderEnum.OrderWinningStatus.WINNING.getValue())==orderBaseInfoBO.getWinningStatus()
                             || Integer.valueOf(OrderEnum.OrderWinningStatus.GET_WINNING.getValue())==orderBaseInfoBO.getWinningStatus()){//已中奖或者已派奖取加奖红包金额
                         TransUserVO transUser = new TransUserVO();
                         transUser.setOrderCode(orderBaseInfoBO.getOrderCode());
                         transUser.setToken(orderQueryVo.getToken());
                         transUser.setTransType(PayConstants.TransTypeEnum.RETURN_AWARD.getKey());
                         List<TransUserBO> getList = (List<TransUserBO>)iTransUserService.findUserTransRecordByOrderCode(transUser).getData();
                         if(!ObjectUtil.isBlank(getList)){
                             orderBaseInfoBO.setGetRedAmount(getList.get(0).getRedTransAmount());
                         }
                     }
                 }
             }
             return ResultBO.ok(orderBaseInfoBOPagingBO);
         }catch (Exception e){
             logger.error("查询投注列表失败！",e);
             return ResultBO.err();
         }
    }


    /**
     * 查询追号活动订单列表
     * @param orderQueryVo
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/queryAddOrderListInfo" ,method = RequestMethod.POST)
    public ResultBO<?> queryAddOrderListInfo(@RequestBody OrderQueryVo orderQueryVo){
        if(ObjectUtil.isBlank(orderQueryVo) || ObjectUtil.isBlank(orderQueryVo.getToken())
                ||ObjectUtil.isNull(orderQueryVo.getPageIndex()) || ObjectUtil.isBlank(orderQueryVo.getPageSize())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        if(orderQueryVo.getAddQueryType()==Constants.NUM_2 && ObjectUtil.isBlank(orderQueryVo.getActivityCode())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            ResultBO<?> resultBO = iorderSearchService.queryAddOrderListInfo(orderQueryVo);
            if(resultBO.isError()){
                return resultBO;
            }
            PagingBO<OrderBaseInfoBO> orderBaseInfoBOPagingBO = (PagingBO<OrderBaseInfoBO>) resultBO.getData();
            List<OrderBaseInfoBO> result = orderBaseInfoBOPagingBO.getData();
            if(!ObjectUtil.isBlank(result)){
                //取支付使用的红包金额和加奖红包奖金
                for(OrderBaseInfoBO orderBaseInfoBO : result){
                    //取支付红包金额
                    if(OrderEnum.PayStatus.SUCCESS_PAY.getValue()==orderBaseInfoBO.getPayStatus().shortValue()
                            || OrderEnum.PayStatus.REFUNDMENT.getValue()==orderBaseInfoBO.getPayStatus().shortValue()){//支付了的订单才取支付红包金额
                        TransUserVO transUser = new TransUserVO();
                        transUser.setOrderCode(orderBaseInfoBO.getOrderCode());
                        transUser.setToken(orderQueryVo.getToken());
                        transUser.setTransType(PayConstants.TransTypeEnum.LOTTERY.getKey());
                        List<TransUserBO> list = (List<TransUserBO>)iTransUserService.findUserTransRecordByOrderCode(transUser).getData();
                        if(!ObjectUtil.isBlank(list)){
                            orderBaseInfoBO.setRedAmount(list.get(0).getRedTransAmount());
                        }
                    }
                }
            }
            return ResultBO.ok(orderBaseInfoBOPagingBO);
        }catch (Exception e){
            logger.error("查询追号活动订单列表！",e);
            return ResultBO.err();
        }
    }


    private void setDateForOrderList(OrderQueryVo orderQueryVo, Date startDate, Date finishDate) {
        //结束时间取当前时间最大的。比如2016-04-27 23:59:59
        Date addHourDate = DateUtil.addHour(finishDate, Constants.NUM_23);
        Date addMinuteDate = DateUtil.addMinute(addHourDate,Constants.NUM_59);
        Date addSencodeDate = DateUtil.addSecond(addMinuteDate,Constants.NUM_59);
        orderQueryVo.setStartDate(startDate);
        orderQueryVo.setFinishDate(addSencodeDate);
    }

    /**
     * 订单详情-竞技彩和数字彩和老足彩和北京单场
     * @param orderSingleQueryVo
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/queryOrderDetailInfo",method = RequestMethod.POST)
    public ResultBO<?> queryOrderDetailInfo(@RequestBody OrderSingleQueryVo orderSingleQueryVo){
        if(ObjectUtil.isBlank(orderSingleQueryVo) || ObjectUtil.isBlank(orderSingleQueryVo.getOrderCode()) || ObjectUtil.isBlank(orderSingleQueryVo.getToken()) ){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            ResultBO<?> resultBO = iorderSearchService.queryOrderDetailInfo(orderSingleQueryVo.getOrderCode(), orderSingleQueryVo.getToken(),orderSingleQueryVo.getSource(),0,orderSingleQueryVo.getOrderGroupContentId());
            if(resultBO.isOK()){
                OrderFullDetailInfoBO orderFullDetailInfoBO =(OrderFullDetailInfoBO)resultBO.getData();
                OrderBaseInfoBO orderBaseInfoBO = orderFullDetailInfoBO.getOrderBaseInfoBO();
                //取支付现金和红包金额
                if(OrderEnum.PayStatus.SUCCESS_PAY.getValue()==orderBaseInfoBO.getPayStatus().shortValue()
                        || OrderEnum.PayStatus.REFUNDMENT.getValue()==orderBaseInfoBO.getPayStatus().shortValue()){//支付了的订单才取支付红包金额
                    TransUserVO transUser = new TransUserVO();
                    transUser.setOrderCode(orderSingleQueryVo.getOrderCode());
                    transUser.setToken(orderSingleQueryVo.getToken());
                    transUser.setTransType(PayConstants.TransTypeEnum.LOTTERY.getKey());
                    List list = (List) iTransUserService.findUserTransRecordByOrderCode(transUser).getData();
                    if(!ObjectUtil.isBlank(list)){
                        orderBaseInfoBO.setCashAmount(((TransUserBO) list.get(0)).getCashAmount());
                        orderBaseInfoBO.setRedAmount(((TransUserBO) list.get(0)).getRedTransAmount());
                    }
                    //红包名称
                    if(!ObjectUtil.isBlank(orderBaseInfoBO.getRedCode())){//支付使用了红包
                        OperateCouponBO operateCouponBO =(OperateCouponBO)operateCouponService.findUserCouponByRedCode(orderSingleQueryVo.getToken(),
                                orderBaseInfoBO.getRedCode()).getData();
                        if(!ObjectUtil.isBlank(operateCouponBO)){
                            orderBaseInfoBO.setRedName(operateCouponBO.getRedName());
                        }
                    }
                }
                //返奖金额
                if(Integer.valueOf(OrderEnum.OrderWinningStatus.WINNING.getValue())==orderBaseInfoBO.getWinningStatus()
                        || Integer.valueOf(OrderEnum.OrderWinningStatus.GET_WINNING.getValue())==orderBaseInfoBO.getWinningStatus()){//已中奖或者已派奖取返奖金额
                    TransUserVO transUser = new TransUserVO();
                    transUser.setOrderCode(orderSingleQueryVo.getOrderCode());
                    transUser.setToken(orderSingleQueryVo.getToken());
                    transUser.setTransType(PayConstants.TransTypeEnum.RETURN_AWARD.getKey());
                    List getList = (List) iTransUserService.findUserTransRecordByOrderCode(transUser).getData();
                    if(!ObjectUtil.isBlank(getList)){
                        orderBaseInfoBO.setGetRedAmount(((TransUserBO) getList.get(0)).getRedTransAmount());
                    }
                    //红包名称
                    if(!ObjectUtil.isBlank(orderBaseInfoBO.getRedCodeGet())){//加奖获得的红包
                        OperateCouponBO operateCouponBO =(OperateCouponBO)operateCouponService.findUserCouponByRedCode(orderSingleQueryVo.getToken(),
                                orderBaseInfoBO.getRedCodeGet()).getData();
                        if(!ObjectUtil.isBlank(operateCouponBO)){
                            orderBaseInfoBO.setRedNameGet(operateCouponBO.getRedName());
                        }
                    }
                }
            }
            return resultBO;
        }catch (Exception e){
            logger.error("查询订单详情失败！",e);
            return ResultBO.err();
        }
    }

    /**
     * 订单详情-追号计划
     * @param orderSingleQueryVo
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/queryAddOrderDetailInfo",method = RequestMethod.POST)
    public ResultBO<?> queryAddOrderDetailInfo(@RequestBody OrderSingleQueryVo orderSingleQueryVo){
        if(ObjectUtil.isBlank(orderSingleQueryVo) || ObjectUtil.isBlank(orderSingleQueryVo.getOrderCode()) || ObjectUtil.isBlank(orderSingleQueryVo.getToken())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            ResultBO<?> resultBO = iorderSearchService.queryOrderDetailInfo(orderSingleQueryVo.getOrderCode(), orderSingleQueryVo.getToken(), orderSingleQueryVo.getSource(),0,orderSingleQueryVo.getOrderGroupContentId());
            if(resultBO.isOK()){
                OrderFullDetailInfoBO orderFullDetailInfoBO =(OrderFullDetailInfoBO)resultBO.getData();
                OrderBaseInfoBO orderBaseInfoBO = orderFullDetailInfoBO.getOrderBaseInfoBO();
                //取支付现金和红包金额
                if(OrderEnum.PayStatus.SUCCESS_PAY.getValue()==orderBaseInfoBO.getPayStatus().shortValue()
                        || OrderEnum.PayStatus.REFUNDMENT.getValue()==orderBaseInfoBO.getPayStatus().shortValue()) {//支付了的订单才取支付红包金额
                    TransUserVO transUser = new TransUserVO();
                    transUser.setOrderCode(orderSingleQueryVo.getOrderCode());
                    transUser.setTransType(PayConstants.TransTypeEnum.LOTTERY.getKey());
                    transUser.setToken(orderSingleQueryVo.getToken());
                    List<TransUserBO> list = (List<TransUserBO>)iTransUserService.findUserTransRecordByOrderCode(transUser).getData();
                    if(!ObjectUtil.isBlank(list)){
                        orderBaseInfoBO.setCashAmount(list.get(0).getCashAmount());
                        orderBaseInfoBO.setRedAmount(list.get(0).getRedTransAmount());
                    }
                    //红包名称
                    if(!ObjectUtil.isBlank(orderBaseInfoBO.getRedCode())){//支付使用了红包
                        OperateCouponBO operateCouponBO =(OperateCouponBO)operateCouponService.findUserCouponByRedCode(orderSingleQueryVo.getToken(),
                                orderBaseInfoBO.getRedCode()).getData();
                        if(!ObjectUtil.isBlank(operateCouponBO)){
                            orderBaseInfoBO.setRedName(operateCouponBO.getRedName());
                        }
                    }
                }
            }
            return resultBO;
        }catch (Exception e){
            logger.error("查询订单详情失败！",e);
            return ResultBO.err();
        }
    }


    /**
     * 数字彩订单详情里面的分页
     * @param queryVO
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/queryUserNumOrderList",method = RequestMethod.POST)
    public ResultBO<?> queryUserNumOrderList(@RequestBody UserNumOrderDetailQueryVO queryVO){
        if(ObjectUtil.isBlank(queryVO) || ObjectUtil.isBlank(queryVO.getOrderCode()) || ObjectUtil.isBlank(queryVO.getToken())
                || ObjectUtil.isNull(queryVO.getPageIndex()) || ObjectUtil.isBlank(queryVO.getPageSize())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        if(Constants.NUM_1 == queryVO.getSource()){//移动端五条
            if(queryVO.getPageSize()> Constants.NUM_5){
                queryVO.setPageSize(Constants.NUM_5);
            }
        }else{//PC
            //每页最大显示10条
            queryVO.setPageSize(Constants.NUM_10);
        }
        try {
            ResultBO<?> resultBO = iorderSearchService.queryUserNumOrderDetail(queryVO);
            return resultBO;
        }catch (Exception e){
            logger.error("查询订单详情失败！",e);
            return ResultBO.err();
        }
    }
    
    /**
     * 奖金优化, 单场致胜订单详情接口
     */
    @RequestMapping(value = "/queryUserSportOrderList", method = RequestMethod.POST)
    public ResultBO<?> queryUserSportOrderList(@RequestBody UserSportOrderDetailQueryVO queryVO){
    	
        if(ObjectUtil.isBlank(queryVO) || ObjectUtil.isBlank(queryVO.getOrderCode()) || ObjectUtil.isBlank(queryVO.getToken())
                || ObjectUtil.isNull(queryVO.getPageIndex()) || ObjectUtil.isBlank(queryVO.getPageSize())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
			return iorderSearchService.queryUserSportOrderDetail(queryVO);
		} catch (Exception e) {
            logger.error("查询竞技彩奖金优化, 单场致胜订单详情失败！",e);
            return ResultBO.err();
		}
    }


    /**
     * 追号计划订单内容里面的分页（现在需求移动端追号内容需要分页。PC端不需要）
     * @param queryVO
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/queryUserChaseContentList",method = RequestMethod.POST)
    public ResultBO<?> queryUserChaseContentList(@RequestBody UserChaseDetailQueryVO queryVO){
        if(ObjectUtil.isBlank(queryVO) || ObjectUtil.isBlank(queryVO.getOrderAddCode()) || ObjectUtil.isBlank(queryVO.getToken())
                || ObjectUtil.isNull(queryVO.getPageIndex()) || ObjectUtil.isBlank(queryVO.getPageSize())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        if(Constants.NUM_1 == queryVO.getSource()){//移动端五条
            if(queryVO.getPageSize()> Constants.NUM_5){
                queryVO.setPageSize(Constants.NUM_5);
            }
        }
        try {
            ResultBO<?> resultBO = iorderSearchService.queryUserChaseContent(queryVO);
            return resultBO;
        }catch (Exception e){
            logger.error("查询追号订单内容详情失败！",e);
            return ResultBO.err();
        }
    }


    /**
     * 追号计划订单详情里面的分页
     * @param queryVO
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/queryUserChaseOrderList",method = RequestMethod.POST)
    public ResultBO<?> queryUserChaseOrderList(@RequestBody UserChaseDetailQueryVO queryVO){
        if(ObjectUtil.isBlank(queryVO) || ObjectUtil.isBlank(queryVO.getOrderAddCode()) || ObjectUtil.isBlank(queryVO.getToken())
                || ObjectUtil.isNull(queryVO.getPageIndex()) || ObjectUtil.isBlank(queryVO.getPageSize())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        if(Constants.NUM_1 == queryVO.getSource()){//移动端五条
            if(queryVO.getPageSize()> Constants.NUM_5){
                queryVO.setPageSize(Constants.NUM_5);
            }
        }else{//PC
            if(queryVO.getPageSize()> Constants.NUM_10){//每页最大显示10条
                queryVO.setPageSize(Constants.NUM_10);
            }
        }
        try {
            ResultBO<?> resultBO = iorderSearchService.queryUserChaseDetail(queryVO);
            return resultBO;
        }catch (Exception e){
            logger.error("查询追号订单详情失败！",e);
            return ResultBO.err();
        }
    }

    /**
     * 追号计划订单详情的每期中奖明细列表
     * @param queryVO
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/queryUserChaseWinningDetail",method = RequestMethod.POST)
    public ResultBO<?> queryUserChaseWinningDetail(@RequestBody UserChaseDetailQueryVO queryVO){
        if(ObjectUtil.isBlank(queryVO) || ObjectUtil.isBlank(queryVO.getOrderAddCode()) || ObjectUtil.isBlank(queryVO.getToken())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            ResultBO<?> resultBO = iorderSearchService.queryUserChaseWinningDetail(queryVO);
            return resultBO;
        }catch (Exception e){
            logger.error("查询追号订单详情中奖明细失败！",e);
            return ResultBO.err();
        }
    }


    /***
     * 终止追号计划 - 查询追号退款组成
     * @param queryVO
     * @return
     */
    @RequestMapping(value = "/queyChaseRefund", method = RequestMethod.POST)
    public ResultBO<?> findChaseRefundAsUserCancel(@RequestBody UserChaseDetailQueryVO queryVO) {
        if(ObjectUtil.isBlank(queryVO) || ObjectUtil.isBlank(queryVO.getOrderAddCode()) || ObjectUtil.isBlank(queryVO.getToken())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
        	// 撤单前验证（有些活动的追号单禁止撤）
        	verifyActivityAsUserCancel(queryVO);
            return orderAddService.findChaseRefundAsUserCancel(queryVO);
        } catch (ResultJsonException e) {
            logger.error("终止追号计划失败！",e);
            return e.getResult();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "iOrderService.findChaseRefundAsUserCancel"), e);
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"iOrderService.findChaseRefundAsUserCancel");
        }

    }
     
    /**
	 * @desc 追号订单-用户撤单前验证活动相关（有些活动是禁止撤单的）
	 * @author huangb
	 * @date 2018年1月2日
	 * @param queryVO
	 *            查询对象
	 */
	private void verifyActivityAsUserCancel(UserChaseDetailQueryVO queryVO) {
		// 1.目标追号是否存在，且是否与活动有关
		ResultBO<OrderAddBO> chaseInfo = orderAddService.findChaseAsUserCancel(queryVO);
		if (chaseInfo.getData() == null || StringUtil.isBlank(chaseInfo.getData().getActivityId())) {
			// 活动编号不存在，不需要判断是否禁止撤单
			return;
		}
		// 2.如果跟活动有关，则判断是否禁止撤单
		String activityCode = chaseInfo.getData().getActivityId(); // 活动编号
		// 调用活动是否禁止撤单的接口： 0:不容许撤单 1:容许撤单 null:没有找到活动 （公共组提供-陈丞组）
		ResultBO<Integer> cancelStatus = activityService.findCancelActivity(activityCode);
		if (cancelStatus.getData() != null) {
			logger.info("是否禁止撤单的接口返回：" + cancelStatus.getData());
			Assert.isTrue(Constants.NUM_0 != cancelStatus.getData(), MessageCodeConstants.HD_NOT_ALLOW_CANCEL);
		}

		// 3.一分钱购彩也不容许撤单 (由于1分钱活动不支持活动配置，所以编号是写死判断的，若后面支持可配了，则可删除，仅保留第2点即可)
		Assert.isTrue(!activityCode.equals(gdYfgcActivityCode), MessageCodeConstants.YFGC_NOT_ALLOW_CANCEL); // 广东11选5的1分钱活动
		Assert.isTrue(!activityCode.equals(jxYfgcActivityCode), MessageCodeConstants.YFGC_NOT_ALLOW_CANCEL); // 江西11选5的1分钱活动
	}

    /**
     * 终止追号计划
     * @param queryVO
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/stopAddOrderPlan",method = RequestMethod.POST)
    public ResultBO<?> stopAddPlan(@RequestBody UserChaseDetailQueryVO queryVO){
        if(ObjectUtil.isBlank(queryVO) || ObjectUtil.isBlank(queryVO.getOrderAddCode()) || ObjectUtil.isBlank(queryVO.getToken())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
        	// 撤单前验证（有些活动的追号单禁止撤）
        	verifyActivityAsUserCancel(queryVO);
            ResultBO<?> resultBO = orderAddService.userCancel(queryVO);
            return resultBO;
        } catch (ResultJsonException e){
            logger.error("终止追号计划失败！",e);
            return e.getResult();
        } catch (Exception e){
            logger.error("终止追号计划失败！",e);
            return ResultBO.err();
        }
    }



    /**
     * 查询订单统计信息
     * 订单列表和订单详情页面公用此方法，
     * @param orderStatisticsQueryVo
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/queryOrderStatisInfo",method = RequestMethod.POST)
    public ResultBO<?> queryOrderStatisInfo(@RequestBody OrderStatisticsQueryVo orderStatisticsQueryVo){
        if(ObjectUtil.isBlank(orderStatisticsQueryVo) || ObjectUtil.isBlank(orderStatisticsQueryVo.getToken())
                || ObjectUtil.isBlank(orderStatisticsQueryVo.getSource())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try{
            if(orderStatisticsQueryVo.getSource()==Constants.NUM_1){
                //订单列表只查询最近三个月的购彩统计
                setDateForStatis(orderStatisticsQueryVo);
            }
            return iorderSearchService.queryOrderStatisInfo(orderStatisticsQueryVo);
        }catch (Exception e){
            logger.error("查询订单统计信息失败！",e);
            return ResultBO.err();
        }
    }

    /**
     * 订单列表的统一只统计最近三个月内的数据
     * @param orderStatisticsQueryVo
     */
    private void setDateForStatis(OrderStatisticsQueryVo orderStatisticsQueryVo) {
        Date now = DateUtil.convertStrToDate(DateUtil.getNow(DateUtil.DATE_FORMAT), DateUtil.DATE_FORMAT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(calendar.MONTH, -3);  //设置为前3月
        Date beforeDate = calendar.getTime();
        orderStatisticsQueryVo.setBeginDate(beforeDate);
        orderStatisticsQueryVo.setEndDate(new Date());
    }

    /**
     * 下订单
     * @author longguoyou
     * @date 2017年3月24日
     * @return
     */
//    @AccessRequired(required = true)
    @RequestMapping(value = "/addOrder", method = RequestMethod.POST)
    public ResultBO<?> addOrder(@RequestBody OrderInfoVO orderInfoVO){
    	if(ObjectUtil.isBlank(orderInfoVO)){
    		return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
    	}
    	//普通订单下单,清空活动编号操作(避免活动漏洞)
    	if(!ObjectUtil.isBlank(orderInfoVO.getActivityCode())){
    		orderInfoVO.setActivityCode(null);
    		logger.error("订单入库参数异常-活动编号不为空(activityCode) :"+orderInfoVO.getActivityCode());
    	}
    	return iOrderService.addOrder(orderInfoVO);
    }
    
    /**
     * 分析师推荐方案
     * @author houxiangbao
     * @return
     */
//  @AccessRequired(required = true)
    @RequestMapping(value = "/addRcmdOrder", method = RequestMethod.POST)
    public ResultBO<?> addRcmdOrder(@RequestBody RcmdInfoVO rcmdInfoVO){
    	if(ObjectUtil.isBlank(rcmdInfoVO)){
    		return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
    	}
    	return iOrderService.addRcmdOrder(rcmdInfoVO);
    }

    /**
     * 取消订单j
     * @param orderSingleQueryVo
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/cancelOrder" ,method = RequestMethod.POST)
    public ResultBO<?> cancelOrder(@RequestBody OrderSingleQueryVo orderSingleQueryVo){
        if(ObjectUtil.isBlank(orderSingleQueryVo) || ObjectUtil.isBlank(orderSingleQueryVo.getOrderCode()) || ObjectUtil.isBlank(orderSingleQueryVo.getToken())
                || ObjectUtil.isBlank(orderSingleQueryVo.getLotteryCode())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            ResultBO<?> resultBO = iorderSearchService.queryOrderInfo(orderSingleQueryVo.getOrderCode(), orderSingleQueryVo.getToken());
            if(resultBO.isError()){
                return resultBO;
            }
            OrderBaseInfoBO orderBaseInfoBO = (OrderBaseInfoBO)resultBO.getData();
            if (ObjectUtil.isBlank(orderBaseInfoBO)) {
                return  ResultBO.err(MessageCodeConstants.ORDER_IS_NOT_EXIST);
            }
            if(OrderEnum.PayStatus.WAITING_PAY.getValue() != orderBaseInfoBO.getPayStatus().shortValue()){
                return  ResultBO.err(MessageCodeConstants.ORDER_CAN_NOT_CANCEL);
            }
            orderSingleQueryVo.setPayStatus(Integer.valueOf(OrderEnum.PayStatus.USER_CANCLE_PAY.getValue()));
            orderSingleQueryVo.setBuyType(orderBaseInfoBO.getBuyType());
            return iOrderService.updateOrderStatus(orderSingleQueryVo);
        } catch (Exception e) {
            logger.error("更新订单状态失败！",e);
            return ResultBO.err();
        }
    }


    /**
     * 查询订单 type :1.支付明细（现金+红包）2.奖金明细 奖金+加奖红包
     * @param orderSingleQueryVo
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/queryOrderAmountDetail" ,method = RequestMethod.POST)
    public ResultBO<?> queryOrderAmountDetail(@RequestBody OrderSingleQueryVo orderSingleQueryVo){
        if(ObjectUtil.isBlank(orderSingleQueryVo) || ObjectUtil.isBlank(orderSingleQueryVo.getOrderCode()) || ObjectUtil.isBlank(orderSingleQueryVo.getToken())
                || ObjectUtil.isBlank(orderSingleQueryVo.getType())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            OrderInfoSingleBO orderInfoSingleBO = new OrderInfoSingleBO();
            ResultBO<?> result = iorderSearchService.queryOrderInfo(orderSingleQueryVo.getOrderCode(), orderSingleQueryVo.getToken());
            if(result.isError()){
                return result;
            }
            OrderBaseInfoBO orderBaseInfoBO = (OrderBaseInfoBO)result.getData();
            if(ObjectUtil.isBlank(orderBaseInfoBO)){
                return ResultBO.err(MessageCodeConstants.ORDER_IS_NOT_EXIST);
            }
            if(orderSingleQueryVo.getType()==Constants.NUM_1){//购彩
                TransUserVO transUser = new TransUserVO();
                transUser.setOrderCode(orderSingleQueryVo.getOrderCode());
                transUser.setTransType(PayConstants.TransTypeEnum.LOTTERY.getKey());
                transUser.setToken(orderSingleQueryVo.getToken());
                List<TransUserBO> list = (List<TransUserBO>)iTransUserService.findUserTransRecordByOrderCode(transUser).getData();
                if(!ObjectUtil.isBlank(list)){
                    orderInfoSingleBO.setTotalAmount(orderBaseInfoBO.getOrderAmount());
                    orderInfoSingleBO.setCashAmount(list.get(0).getCashAmount());
                    orderInfoSingleBO.setRedAmount(list.get(0).getRedTransAmount());
                }
            }else if(orderSingleQueryVo.getType()==Constants.NUM_2){//返奖
                TransUserVO transUser = new TransUserVO();
                transUser.setOrderCode(orderSingleQueryVo.getOrderCode());
                transUser.setTransType(PayConstants.TransTypeEnum.RETURN_AWARD.getKey());
                transUser.setToken(orderSingleQueryVo.getToken());
                List<TransUserBO> list = (List<TransUserBO>)iTransUserService.findUserTransRecordByOrderCode(transUser).getData();
                if(!ObjectUtil.isBlank(list)){
                    orderInfoSingleBO.setPreBonus(orderBaseInfoBO.getPreBonus());
                    orderInfoSingleBO.setAddRedAmount(list.get(0).getRedTransAmount());
                }
            }
            return ResultBO.ok(orderInfoSingleBO);
        }catch (Exception e){
            logger.error("查询支付，奖金明细异常",e);
            return ResultBO.err();
        }
    }


    /**
     * 查询彩票投注失败，未出票原因
     * @param orderSingleQueryVo
     * @return
     */
    //@AccessRequired(required = true)
    /*@RequestMapping(value = "/queryLotteryFailReason" ,method = RequestMethod.POST)
    public ResultBO<?> queryLotteryFailReason(@RequestBody OrderSingleQueryVo orderSingleQueryVo){
        if(ObjectUtil.isBlank(orderSingleQueryVo) || ObjectUtil.isBlank(orderSingleQueryVo.getOrderCode()) || ObjectUtil.isBlank(orderSingleQueryVo.getToken())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            return iorderSearchService.queryLotteryFailReason(orderSingleQueryVo);
        }catch (Exception e){
            logger.error("查询出票失败原因异常",e);
            return ResultBO.err();
        }
    }*/

    /**
     * 查询追号订单，中奖追停的原因
     * @param orderSingleQueryVo
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/queryAddOrderStopReason" ,method = RequestMethod.POST)
    public ResultBO<?> queryAddOrderStopReason(@RequestBody OrderSingleQueryVo orderSingleQueryVo){
        if(ObjectUtil.isBlank(orderSingleQueryVo) || ObjectUtil.isBlank(orderSingleQueryVo.getOrderCode()) || ObjectUtil.isBlank(orderSingleQueryVo.getToken())
                || ObjectUtil.isBlank(orderSingleQueryVo.getLotteryCode())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            return iorderSearchService.queryAddOrderStopReason(orderSingleQueryVo);
        }catch (Exception e){
            logger.error("查询追号中奖追停原因异常",e);
            return ResultBO.err();
        }
    }

    /**
     * 批量取消订单
     * @param orderSingleQueryVo
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/batchCancelOrderList" ,method = RequestMethod.POST)
    public ResultBO<?> batchCancelOrderList(@RequestBody OrderSingleQueryVo orderSingleQueryVo){
        if(ObjectUtil.isBlank(orderSingleQueryVo) || ObjectUtil.isBlank(orderSingleQueryVo.getOrderCodes()) || ObjectUtil.isBlank(orderSingleQueryVo.getToken())
                || ObjectUtil.isBlank(orderSingleQueryVo.getLotteryCode())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            return iOrderService.batchCancelOrderList(orderSingleQueryVo.getOrderCodes(),orderSingleQueryVo.getToken(), Integer.valueOf(orderSingleQueryVo.getLotteryCode())) ;
        }catch (Exception e){
            logger.error("批量取消订单异常",e);
            return ResultBO.err();
        }
    }

    /**
     *  查询订单详情的流程信息
     * @param orderSingleQueryVo
     * @return
     */
    //@AccessRequired(required = true)
    @RequestMapping(value = "/queryOrderFlowInfoList" ,method = RequestMethod.POST)
    public ResultBO<?> queryOrderFlowInfoList(@RequestBody OrderSingleQueryVo orderSingleQueryVo){
        if(ObjectUtil.isBlank(orderSingleQueryVo) || ObjectUtil.isBlank(orderSingleQueryVo.getOrderCode()) || ObjectUtil.isBlank(orderSingleQueryVo.getToken())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            return iorderSearchService.queryOrderFlowInfoList(orderSingleQueryVo.getOrderCode(), orderSingleQueryVo.getToken());
        }catch (Exception e){
            logger.error("查询订单详情的流程信息失败",e);
            return ResultBO.err();
        }
    }

    /**
     * 查询未支付订单详情列表
     * @author longguoyou
     * @date 2017年4月28日
     * @param orderQueryVO
     * @return
     */
    @RequestMapping(value = "/queryNoPayOrderDetailList" ,method = RequestMethod.POST)
    public ResultBO<?> queryNoPayOrderDetailList(@RequestBody OrderQueryVo orderQueryVO){
    	if(ObjectUtil.isBlank(orderQueryVO.getLotteryCode()) || ObjectUtil.isBlank(orderQueryVO.getToken())){
    		return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
    	}
    	ResultBO<?> result = null;
    	try{
    		result = iorderSearchService.queryNoPayOrderDetailList(orderQueryVO);
    		return result;
    	}catch (Exception e){
            logger.error("查询订单详情信息失败",e);
            return ResultBO.err();
        }
    }

    /**
     * 单式上传 下订单
     * @author yuanshangbing
     * @date 2017年6月14日
     * @return
     */
//    @AccessRequired(required = true)
    @RequestMapping(value = "/addSingleUploadOrder", method = RequestMethod.POST)
    public ResultBO<?> addSingleUploadOrder(@RequestBody OrderInfoSingleUploadVO orderInfoSingleUploadVO){
        if(ObjectUtil.isBlank(orderInfoSingleUploadVO)){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        ResultBO<?> result = null;
        try {
            result =  iOrderService.addSingleUploadOrder(orderInfoSingleUploadVO);
        }catch (Exception e){
            logger.error("单式上传下订单失败：",e);
            return ResultBO.err();
        }
        return result;
    }

    /**
     * 竟足/竞篮首单立减 下订单
     * @author yuanshangbing
     * @date 2017年6月14日
     * @return
     */
//    @AccessRequired(required = true)
    @RequestMapping(value = "/addJzsdOrder", method = RequestMethod.POST)
    public ResultBO<?> addJzsdOrder(@RequestBody OrderInfoVO orderInfoVO){
        if(ObjectUtil.isBlank(orderInfoVO) || ObjectUtil.isBlank(orderInfoVO.getToken()) || ObjectUtil.isBlank(orderInfoVO.getActivityCode())
                || ObjectUtil.isBlank(orderInfoVO.getMultipleNum()) || ObjectUtil.isBlank(orderInfoVO.getOrderAmount())
                || ObjectUtil.isBlank(orderInfoVO.getOrderDetailList()) || ObjectUtil.isBlank(orderInfoVO.getBuyScreen())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            ResultBO<?> result = activityValid.validJzstOrderInfo(orderInfoVO,Constants.NUM_1);
            if(result.isError()){
                return result;
            }
            result = iOrderService.addOrder(orderInfoVO);
            if(result.isError()){
                return result;
            }
            OrderInfoBO orderInfoBO = (OrderInfoBO)result.getData();
            ActivityDynamicBO activityDynamicBO = new ActivityDynamicBO();
            activityDynamicBO.setOrderCode(orderInfoBO.getOrderCode());
            return ResultBO.ok(activityDynamicBO);
        }catch (Exception e){
            logger.error("竟足/竟篮首单立减活动下订单失败：",e);
            return ResultBO.err();
        }
    }

    @RequestMapping(value = "/winInfo", method = RequestMethod.GET)
    public ResultBO<?> queryUserWinInfo(String lotteryCodes) {
        try {
            return iorderSearchService.queryUserWinInfo(lotteryCodes);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultBO.err();
        }
    }





}
