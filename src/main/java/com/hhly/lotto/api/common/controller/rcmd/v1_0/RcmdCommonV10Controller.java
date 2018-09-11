package com.hhly.lotto.api.common.controller.rcmd.v1_0;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.rcmd.service.IRcmdService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.recommend.bo.RcmdAttentionBO;
import com.hhly.skeleton.lotto.base.recommend.bo.RcmdAttentionPagingBO;
import com.hhly.skeleton.lotto.base.recommend.bo.RcmdQueryDetailBO;
import com.hhly.skeleton.lotto.base.recommend.bo.RcmdQueryDetailPagingBO;
import com.hhly.skeleton.lotto.base.recommend.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description  新版推单
 * @Author longguoyou
 * @Date  2018/8/14 9:16
 * @Since 1.8
 */

public class RcmdCommonV10Controller extends BaseController {

    @Autowired
    private IRcmdService iRcmdService;

    @RequestMapping(value = "/queryRcmdInfoDetailList",method = RequestMethod.POST)
    public ResultBO<?> queryRcmdInfoDetailPagingBO(@RequestBody RcmdQueryVO rcmdQueryVO) throws Exception{
        ResultBO<?> resultBO = iRcmdService.queryRcmdInfoDetail(rcmdQueryVO);
        if(resultBO.isError()){return resultBO;}
        PagingBO<RcmdQueryDetailBO> rcmdQueryDetailBO = (PagingBO<RcmdQueryDetailBO>)resultBO.getData();
        RcmdQueryDetailPagingBO rcmdQueryDetailPagingBO = new RcmdQueryDetailPagingBO();
        rcmdQueryDetailPagingBO.setTotal(rcmdQueryDetailBO.getTotal());
        rcmdQueryDetailPagingBO.setListRcmdQueryDetailBO(rcmdQueryDetailBO.getData());
        return ResultBO.ok(rcmdQueryDetailPagingBO);
    }

    @RequestMapping(value = "/findRcmdInfoDetail",method = RequestMethod.POST)
    public ResultBO<?> getRcmdInfoDetail(@RequestBody RcmdQueryVO rcmdQueryVO) throws Exception{
        ResultBO<?> resultBO = iRcmdService.findRcmdInfoDetail(rcmdQueryVO);
        return resultBO;
    }

    @RequestMapping(value = "/updateClick",method = RequestMethod.POST)
    public ResultBO<?> updateClick(@RequestBody RcmdQueryVO rcmdQueryVO) throws Exception{
        ResultBO<?> resultBO = iRcmdService.updateClick(rcmdQueryVO.getId());
        return resultBO;
    }

    @RequestMapping(value = "/queryRcmdUser",method = RequestMethod.POST)
    public ResultBO<?> queryRcmdUser(@RequestBody RcmdQueryVO rcmdQueryVO) throws Exception{
        ResultBO<?> resultBO = iRcmdService.queryRcmdUserLikeAccountName(rcmdQueryVO);
        if(resultBO.isError()){return resultBO;}
        PagingBO<RcmdAttentionBO> rcmdAttentionBO = (PagingBO<RcmdAttentionBO>)resultBO.getData();
        RcmdAttentionPagingBO rcmdAttentionPagingBO = new RcmdAttentionPagingBO();
        rcmdAttentionPagingBO.setTotal(rcmdAttentionBO.getTotal());
        rcmdAttentionPagingBO.setListRcmdUserBO(rcmdAttentionBO.getData());
        return ResultBO.ok(rcmdAttentionPagingBO);
    }

    /**
     * 推单主页
     * @param rcmdPersonVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryRecommendPersonInfo",method = RequestMethod.POST)
    public ResultBO<?> queryRecommendPersonInfo(@RequestBody RcmdPersonVO rcmdPersonVO){
        if(ObjectUtil.isBlank(rcmdPersonVO.getSeeType())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        if(rcmdPersonVO.getSeeType().intValue() == Constants.NUM_1){
            if(ObjectUtil.isBlank(rcmdPersonVO.getToken())){
                return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
            }
        }
        if(rcmdPersonVO.getSeeType().intValue() == Constants.NUM_2){
            if(ObjectUtil.isBlank(rcmdPersonVO.getUserId())){
                return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
            }
        }
        try {
            return iRcmdService.queryRecommendPersonInfo(rcmdPersonVO);
        }catch (Exception e){
            logger.error("查询推单主页信息失败！",e);
            return ResultBO.err();
        }
    }

    /**
     * 我的关注列表
     * @param rcmdSingleVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryMyAttentionList",method = RequestMethod.POST)
    public ResultBO<?> queryMyAttentionList(@RequestBody RcmdSingleVO rcmdSingleVO) {
         if(ObjectUtil.isBlank(rcmdSingleVO.getToken()) || ObjectUtil.isNull(rcmdSingleVO.getPageIndex()) || ObjectUtil.isBlank(rcmdSingleVO.getPageSize())){
             return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
         }
        try {
            return iRcmdService.queryMyAttentionList(rcmdSingleVO);
        }catch (Exception e){
            logger.error("查询我的关注列表失败！",e);
            return ResultBO.err();
        }

    }

    /**
     * 我的推单/已付费单
     * @param rcmdSingleVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryPersonRcmdList",method = RequestMethod.POST)
    public ResultBO<?> queryPersonRcmdList(@RequestBody RcmdSingleVO rcmdSingleVO) {
        if(ObjectUtil.isBlank(rcmdSingleVO.getToken()) || ObjectUtil.isNull(rcmdSingleVO.getPageIndex())
                || ObjectUtil.isBlank(rcmdSingleVO.getPageSize()) || ObjectUtil.isBlank(rcmdSingleVO.getQueryType())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            return iRcmdService.queryPersonRcmdList(rcmdSingleVO);
        }catch (Exception e){
            logger.error("查询我的推单/已付费单失败！",e);
            return ResultBO.err();
        }
    }

    /**
     * 申请成为发单人
     * @param rcmdUserCheckVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/applyRcmdPerson",method = RequestMethod.POST)
    public ResultBO<?> applyRcmdPerson(@RequestBody RcmdUserCheckVO rcmdUserCheckVO) {
         if(ObjectUtil.isBlank(rcmdUserCheckVO.getToken()) || ObjectUtil.isBlank(rcmdUserCheckVO.getApplySource()) || ObjectUtil.isBlank(rcmdUserCheckVO.getLotteryCode()) ||
                 ObjectUtil.isBlank(rcmdUserCheckVO.getAdeptMatch()) || ObjectUtil.isBlank(rcmdUserCheckVO.getSummary())){
             return  ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
         }
        try {
            return iRcmdService.applyRcmdPerson(rcmdUserCheckVO);
        }catch (Exception e){
            logger.error("申请成为发单人失败！",e);
            return ResultBO.err();
        }
    }

    /**
     * 推单校验
     * @param rcmdValidVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/validRcmdInfo",method = RequestMethod.POST)
    public ResultBO<?> validRcmdInfo(@RequestBody RcmdValidVO rcmdValidVO){
        if(ObjectUtil.isBlank(rcmdValidVO.getToken()) || ObjectUtil.isBlank(rcmdValidVO.getScreens()) || ObjectUtil.isBlank(rcmdValidVO.getPassWay())){
            return  ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        if(rcmdValidVO.getPassWay().intValue() == Constants.NUM_1){//二串一
            if(ObjectUtil.isBlank(rcmdValidVO.getLowestBonus()) || ObjectUtil.isBlank(rcmdValidVO.getPlanAmount())){
                return  ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
            }
        }
        if(rcmdValidVO.getPassWay().intValue() == Constants.NUM_2){//单关
            if(ObjectUtil.isBlank(rcmdValidVO.getSps()) ){
                return  ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
            }
        }
        try {
            return iRcmdService.validRcmdInfo(rcmdValidVO);
        }catch (Exception e){
            logger.error("推单校验失败！",e);
            return ResultBO.err();
        }
    }

    /**
     * 校验是否是分析师
     * @param rcmdValidVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/validIsRcmdPerson",method = RequestMethod.POST)
    public ResultBO<?> validIsRcmdPerson(@RequestBody RcmdValidVO rcmdValidVO){
        if(ObjectUtil.isBlank(rcmdValidVO.getToken()) ){
            return  ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        try {
            return iRcmdService.validIsRcmdPerson(rcmdValidVO.getToken());
        }catch (Exception e){
            logger.error("校验是否是分析师失败！",e);
            return ResultBO.err();
        }
    }
}
