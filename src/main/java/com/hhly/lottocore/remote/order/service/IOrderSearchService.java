package com.hhly.lottocore.remote.order.service;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.lotto.base.order.vo.ActivityOrderQueryInfoVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderQueryVo;
import com.hhly.skeleton.lotto.base.order.vo.OrderSingleQueryVo;
import com.hhly.skeleton.lotto.base.order.vo.OrderStatisticsQueryVo;
import com.hhly.skeleton.lotto.base.order.vo.UserChaseDetailQueryVO;
import com.hhly.skeleton.lotto.base.order.vo.UserNumOrderDetailQueryVO;
import com.hhly.skeleton.lotto.base.order.vo.UserSportOrderDetailQueryVO;

/**
 * @author yuanshangbing
 * @version 1.0
 * @desc 订单查询服务类
 * @date 2017/3/15 16:58
 * @company 益彩网络科技公司
 */
public interface IOrderSearchService {

    /**
     * 分页查询投注列表
     * @return
     * @throws Exception
     */
    ResultBO<?> queryOrderListInfo(OrderQueryVo orderQueryVo)throws Exception;

    /**
     * 查询追号活动订单列表
     * @param orderQueryVo
     * @return
     * @throws Exception
     */
    ResultBO<?> queryAddOrderListInfo(final OrderQueryVo orderQueryVo) throws Exception;

    /**
     * 查询订单详情
     * @param orderCode
     * @param token
     * @param source 1 Pc端； 2 移动端，默认PC端
     * @param orderGroupContentId 合买跟单表主键ID，只有合买跟单的单才有，其他没有，前端传过来
     * @return
     * @throws Exception
     */
    ResultBO<?> queryOrderDetailInfo(String orderCode,String token,Integer source,Integer userId,Integer orderGroupContentId) throws Exception;

    /**
     * 订单统计信息
     * @param orderStatisticsQueryVo
     * @return
     * @throws Exception
     */
    ResultBO<?> queryOrderStatisInfo(OrderStatisticsQueryVo orderStatisticsQueryVo) throws Exception;

    /**
     * 用户首页订单
     * @param orderQueryVo
     * @return
     * @throws Exception
     */
    ResultBO<?> queryHomeOrderList(OrderQueryVo orderQueryVo) throws Exception;

    /**
     * 查询订单，包括追号订单
     * @param orderCode 可能是订单编号，可能是追号编号
     * @param token
     * @return
     */
    ResultBO<?> queryOrderInfo(String orderCode,String token) throws Exception;


    /**
     * 查询出票失败原因
     * @param orderSingleQueryVo
     * @return
     * @throws Exception
     */
    ResultBO<?> queryLotteryFailReason(OrderSingleQueryVo orderSingleQueryVo)throws Exception;


    /**
     * 查询追号中奖追停原因
     * @param orderSingleQueryVo
     * @return
     * @throws Exception
     */
    ResultBO<?> queryAddOrderStopReason(OrderSingleQueryVo orderSingleQueryVo) throws Exception;



    /**
     * @desc 查询用户数字彩方案详情
     * @author huangb
     * @date 2017年4月11日
     * @param queryVO
     *            查询对象
     * @return 查询用户数字彩方案详情
     * @throws com.hhly.skeleton.base.exception.ResultJsonException
     */
    ResultBO<?> queryUserNumOrderDetail(UserNumOrderDetailQueryVO queryVO) throws ResultJsonException;
    
    /**
     * 查询用户竞技彩奖金优化, 单场致胜方案详情
     * @param queryVO
     * @return
     * @throws ResultJsonException
     * @date 2017年7月21日下午4:20:46
     * @author cheng.chen
     */
    ResultBO<?> queryUserSportOrderDetail(UserSportOrderDetailQueryVO queryVO) throws ResultJsonException;    

    /**
     * @desc 前端接口：用户中心-查询用户追号内容详情
     * @author huangb
     * @date 2017年4月11日
     * @param queryVO
     *            查询对象
     * @return 前端接口：用户中心-查询用户追号内容详情
     * @throws ResultJsonException
     */
    ResultBO<?> queryUserChaseContent(UserChaseDetailQueryVO queryVO) throws ResultJsonException;

    /**
     * @desc 查询用户追号明细详情
     * @author huangb
     * @date 2017年4月11日
     * @param queryVO
     *            查询对象
     * @return 查询用户追号明细详情
     * @throws ResultJsonException
     */
    ResultBO<?> queryUserChaseDetail(UserChaseDetailQueryVO queryVO) throws ResultJsonException;

    /**
     * 查询订单流程信息
     * @param orderCode 订单编号或者追号编号
     * @return
     */
    ResultBO<?> queryOrderFlowInfoList(String orderCode,String token) throws Exception;

    /**
     * @desc 前端接口：用户中心-查询追号计划中奖金额（税前或税后）的组成明细，追号彩期关联追号订单获取
     * @author huangb
     * @date 2017年4月13日
     * @param queryVO
     *            查询对象
     * @return 前端接口：用户中心-查询追号计划中奖金额（税前或税后）的组成明细，追号彩期关联追号订单获取
     */
    ResultBO<?> queryUserChaseWinningDetail(UserChaseDetailQueryVO queryVO)throws ResultJsonException;

    /**
	 * 查询未支付订单详情列表
	 * @author longguoyou
	 * @date 2017年4月28日
	 * @param orderQueryVO
	 * @return
	 */
	ResultBO<?> queryNoPayOrderDetailList(OrderQueryVo orderQueryVO);

    /**
     * 根据订单编号集合查询订单信息（orderCode，buyType）
     * @return
     */
    ResultBO<?> queryOrderListForOrderCodes(List<OrderQueryVo> orderQueryVoList,String token);

    /**
     * 查询活动参与数量
     * @param activityOrderQueryInfoVO
     * @return
     */
    ResultBO<?> queryJoinActivityOrderCount(ActivityOrderQueryInfoVO activityOrderQueryInfoVO) throws Exception;

    /**
     * 查询当前用户没有支付的活动订单
     * @param activityOrderQueryInfoVO
     * @return
     */
    ResultBO<?> queryNoPayActivityOrderNo(ActivityOrderQueryInfoVO activityOrderQueryInfoVO) throws Exception;

    /**
     * 用户中奖轮播信息
     * @return
     * @throws Exception
     */
    ResultBO<?> queryUserWinInfo(String lotteryCodes) throws Exception;

	ResultBO<?> queryYearOrderInfoDetail(OrderQueryVo orderQueryVo);

}
