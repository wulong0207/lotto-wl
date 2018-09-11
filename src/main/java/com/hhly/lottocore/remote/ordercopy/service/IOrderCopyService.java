package com.hhly.lottocore.remote.ordercopy.service;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.ordercopy.bo.MUserIssueInfoBO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.MUserIssueInfoVO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.MUserIssueLinkVO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.OrderFollowedInfoVO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.OrderIssueInfoVO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.QueryVO;

/**
 * @author lgs on
 * @version 1.0
 * @desc 抄单 RPC通信Service
 * @date 2017/9/19.
 * @company 益彩网络科技有限公司
 */
public interface IOrderCopyService {

    /**
     * 新增发单记录
     *
     * @param vo
     * @return
     */
    ResultBO<?> insertOrderCopy(OrderIssueInfoVO vo) throws Exception;

    /**
     * 根据发单用户id查询发单用户信息
     *
     * @param id
     * @return
     */
    ResultBO<MUserIssueInfoBO> findUserIssueInfoBoById(Long id, String token);


    /**
     * 判断是否已经关注
     * @author longguoyou
     * @date 2017年9月29日
     * @param userIssueId
     * @param token
     * @return
     */
    ResultBO<?> findIfFocus(Integer userIssueId, String token);

    /**
     * 新增关注/取消关注
     * @author longguoyou
     * @date 2017年9月29日
     * @param mUserIssueLinkVO
     * @return
     */
    ResultBO<?> updateFocus(MUserIssueLinkVO mUserIssueLinkVO);

    /**
     * 查询关注列表(已分页)
     * @author longguoyou
     * @date 2017年9月29日
     * @param mUserIssueLinkVO
     * @return
     */
    ResultBO<?> queryFocusByMUserIssueLinkVO(MUserIssueLinkVO mUserIssueLinkVO);

    /**
     * 查询实单列表(已分页)
     * @author longguoyou
     * @date 2017年9月29日
     * @param queryVO
     * @return
     */
    ResultBO<?> queryIssueInfo(QueryVO queryVO);
    /**
     * 查询实单总记录数
     * @author longguoyou
     * @date 2017年9月30日
     * @param queryVO
     * @return
     */
    ResultBO<?> queryIssueInfoCount(QueryVO queryVO);
    /**
     * 
     * @author longguoyou
     * @date 2017年10月13日
     * @param queryVO
     * @return
     */
    ResultBO<?> queryUserIssueInfo(QueryVO queryVO);
    /**
     * 
     * @author longguoyou
     * @date 2017年10月13日
     * @param queryVO
     * @return
     */
    ResultBO<?> queryUserIssueInfoCount(QueryVO queryVO);
    
    /**
     * 返佣情况
     * @author longguoyou
     * @date 2017年10月11日
     * @param queryVO
     * @return
     */
    ResultBO<?> queryCommissions(QueryVO queryVO);
    /**
     * 返佣明细
     * @author longguoyou
     * @date 2017年10月11日
     * @param queryVO
     * @return
     */
    ResultBO<?> queryCommissionsDetails(QueryVO queryVO);
    
    /**
     * 获取总返佣
     * @author longguoyou
     * @date 2017年11月20日
     * @param queryVO
     * @return
     */
    ResultBO<?> getCommissionDetailsSumCommission(QueryVO queryVO);

    /**
     * 查询发单用户中奖统计
     *
     * @param vo
     * @return
     */
    ResultBO<?> findUserIssuePrizeCount(MUserIssueInfoVO vo);


    /**
     * 用户跟单
     *
     * @param vo
     * @return
     */
    ResultBO<?> orderFollowed(OrderFollowedInfoVO vo) throws Exception;
    
    /**
     * 查询抄单明细
     * @author longguoyou
     * @date 2017年10月14日
     * @param queryVO
     * @return
     */
    ResultBO<?> queryFollowedDetails(QueryVO queryVO);


    /**
     * 查询方案详情
     *
     * @param id
     * @param token
     * @return
     */
    ResultBO<?> findOrderCopyIssueInfoBOById(Long id, String token);


    /**
     * 验证订单是否能够发单
     *
     * @param orderCode
     * @param token
     * @return
     */
    ResultBO<?> validateOrderCopy(String orderCode, String token) throws Exception;

    /**
     * 获取篮球盘口变化数据
     * @param issueOrderId
     * @param token
     * @return
     * @throws Exception
     */
    ResultBO<?> getHandicapChange(Long issueOrderId,String token)throws Exception;

    /**
     * 根据发单编号查询支付需要的抄单信息
     * @param orderIssueId
     * @return
     * @throws Exception
     */
    ResultBO<?> getCopyOrderInfoForPay(Integer orderIssueId,String token)throws Exception;
}
