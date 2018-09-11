package com.hhly.lotto.api.common.controller.ordergroup.v1_0;


import com.hhly.lotto.base.common.HeaderParam;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.ordergroup.service.IOrderGroupService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.group.bo.OrderGroupDetailBO;
import com.hhly.skeleton.lotto.base.group.bo.OrderGroupDetailPagingBO;
import com.hhly.skeleton.lotto.base.group.vo.OrderGroupContentVO;
import com.hhly.skeleton.lotto.base.group.vo.OrderGroupInfoVO;
import com.hhly.skeleton.lotto.base.group.vo.OrderGroupQueryVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderSingleQueryVo;
import com.hhly.skeleton.lotto.base.ordercopy.vo.QueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

public class OrderGroupCommonV10Controller extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(OrderGroupCommonV10Controller.class);

    @Autowired
    private IOrderGroupService iOrderGroupService;

    @RequestMapping(method = RequestMethod.POST)
    public Object insertOrderGroup(@RequestBody OrderGroupInfoVO orderGroupInfoVO) throws Exception {
        return iOrderGroupService.addOrderGroup(orderGroupInfoVO);
    }

    @RequestMapping(value = "/queryOrderGroupList",method = RequestMethod.POST)
    public ResultBO<?> queryOrderGroupList(@RequestBody OrderGroupQueryVO orderGroupQueryVO, HttpServletRequest request)throws Exception{
        HeaderParam headerParam = getHeaderParam(request);
        if(!ObjectUtil.isBlank(headerParam)){
            orderGroupQueryVO.setPlatform(headerParam.getPlatformId());
        }
        ResultBO<?> result = iOrderGroupService.queryOrderGroupList(orderGroupQueryVO);
        if(result.isError()){return result;}
        PagingBO<OrderGroupDetailBO> orderGroupDetailBO = (PagingBO<OrderGroupDetailBO>)result.getData();
        OrderGroupDetailPagingBO orderGroupDetailPagingBO = new OrderGroupDetailPagingBO();
        orderGroupDetailPagingBO.setTotal(orderGroupDetailBO.getTotal());
        orderGroupDetailPagingBO.setListOrderGroupDetailBO(orderGroupDetailBO.getData());
        return ResultBO.ok(orderGroupDetailPagingBO);//iOrderGroupService.queryOrderGroupList(queryVO);
    }

    @RequestMapping(value = "/queryOrderGroupContentList",method = RequestMethod.POST)
    public ResultBO<?> queryOrderGroupContentList(@RequestBody OrderGroupContentVO orderGroupContentVO)throws Exception{
        if(ObjectUtil.isBlank(orderGroupContentVO) || ObjectUtil.isBlank(orderGroupContentVO.getOrderCode())
                || ObjectUtil.isNull(orderGroupContentVO.getPageIndex()) || ObjectUtil.isBlank(orderGroupContentVO.getPageSize())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
           return iOrderGroupService.queryOrderGroupContentList(orderGroupContentVO);
        }catch (Exception e){
            logger.error("查询全部合买用户信息失败！",e);
            return ResultBO.err();
        }
    }


    @RequestMapping(value = "/validOrderGroupContent",method = RequestMethod.POST)
    public ResultBO<?> validOrderGroupContent(@RequestBody OrderSingleQueryVo orderSingleQueryVo)  {
        if(ObjectUtil.isBlank(orderSingleQueryVo) || ObjectUtil.isBlank(orderSingleQueryVo.getOrderCode())
                || ObjectUtil.isNull(orderSingleQueryVo.getToken()) ){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            return iOrderGroupService.validOrderGroupContent(orderSingleQueryVo);
        }catch (Exception e){
            logger.error("验证是否可以参与合买信息失败！",e);
            return ResultBO.err();
        }

    }

    @RequestMapping(value = "queryOrderGroupPersonInfo",method = RequestMethod.POST)
    public ResultBO<?> queryOrderGroupPersonInfo(@RequestBody OrderSingleQueryVo orderSingleQueryVo){
        if(ObjectUtil.isBlank(orderSingleQueryVo.getSeeType()) || ObjectUtil.isNull(orderSingleQueryVo.getSource())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        if(orderSingleQueryVo.getSeeType().intValue()== Constants.NUM_1){//合买大厅查看个人主页，不需要登录 传userId
           if(ObjectUtil.isBlank(orderSingleQueryVo.getUserId())){
               return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
           }
        }
        if(orderSingleQueryVo.getSeeType().intValue()== Constants.NUM_2){//2我的个人主页，传token
            if(ObjectUtil.isBlank(orderSingleQueryVo.getToken())){
                return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
            }
        }
        try {
            return iOrderGroupService.queryOrderGroupPersonInfo(orderSingleQueryVo);
        }catch (Exception e){
            logger.error("查询合买个人首页信息失败！",e);
            return ResultBO.err();
        }
    }

    @RequestMapping(value = "/queryOrderStandingList",method = RequestMethod.POST)
    public ResultBO<?> queryOrderStandingList(@RequestBody OrderSingleQueryVo orderSingleQueryVo){
        if(ObjectUtil.isBlank(orderSingleQueryVo.getSeeType()) || ObjectUtil.isNull(orderSingleQueryVo.getSource()) || ObjectUtil.isBlank(orderSingleQueryVo.getLotteryCode()) || ObjectUtil.isNull(orderSingleQueryVo.getPageIndex()) || ObjectUtil.isBlank(orderSingleQueryVo.getPageSize())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        if(orderSingleQueryVo.getSeeType().intValue()== Constants.NUM_1){//合买大厅查看个人主页，不需要登录 传userId
            if(ObjectUtil.isBlank(orderSingleQueryVo.getUserId())){
                return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
            }
        }
        if(orderSingleQueryVo.getSeeType().intValue()== Constants.NUM_2){//2我的个人主页，传token
            if(ObjectUtil.isBlank(orderSingleQueryVo.getToken())){
                return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
            }
        }
        try {
            return iOrderGroupService.queryOrderStandingList(orderSingleQueryVo);
        }catch (Exception e){
            logger.error("查询合买个人首页战绩详情列表！",e);
            return ResultBO.err();
        }
    }

    @RequestMapping(value = "/getOrderStandingList",method = RequestMethod.GET)
    public ResultBO<?> queryGroupRankingList(){
        ResultBO<?> resultBO = null;
        try {
            resultBO = iOrderGroupService.queryGroupRankingList();
        } catch (Exception e) {
            logger.error("查询合买排行榜信息出错：" + e);
        }
        return resultBO;
    }


    @RequestMapping(value = "/getGroupFamousList",method = RequestMethod.GET)
    public ResultBO<?> queryGroupFamousList(){
        ResultBO<?> resultBO = null;
        try{
            resultBO = iOrderGroupService.queryGroupFamousList();
        }catch(Exception e){
            logger.error("查询合买名人信息出错：" + e);
        }
        return resultBO;
    }

}
