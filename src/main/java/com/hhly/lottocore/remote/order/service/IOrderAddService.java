package com.hhly.lottocore.remote.order.service;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.order.bo.OrderAddBO;
import com.hhly.skeleton.lotto.base.order.vo.OrderAddQueryVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderAddVO;
import com.hhly.skeleton.lotto.base.order.vo.UserChaseDetailQueryVO;

/**
 * @desc  追号服务
 * @author huangb
 * @date 2017年11月8日
 * @company 益彩网络
 * @version v1.0
 */
public interface IOrderAddService {

	/**
	 * @param orderAdd
	 *            追号对象
	 * @return
	 * @Desc 添加追号计划
	 */
	ResultBO<?> addChase(OrderAddVO orderAdd);
	
	/**
	 * 不走验证的追号下单
	 * @desc 
	 * @create 2018年1月9日
	 * @param orderAdd
	 * @return ResultBO<?>
	 */
	ResultBO<?> addChaseWithOutVerify(OrderAddVO orderAdd);
	
	/**
	 * @desc 前端接口：用户追号计划下单调用中转接口(为避免事务处理与mq消息处理冲突)
	 * @author huangb
	 * @date 2017年8月21日
	 * @param orderAdd
	 *            追号对象
	 * @return 前端接口：用户追号计划下单调用中转接口(为避免事务处理与mq消息处理冲突)
	 */
	ResultBO<?> userChase(OrderAddVO orderAdd);
	
	/**
	 * @desc 前端接口：用户中心-用户终止追号任务 （查询追号退款组成）
	 * @author huangb
	 * @date 2017年6月23日
	 * @param queryVO
	 *            参数对象
	 * @return 前端接口：用户中心-用户终止追号任务 （查询追号退款组成）
	 */
	ResultBO<?> findChaseRefundAsUserCancel(UserChaseDetailQueryVO queryVO);
	
	/**
	 * @desc 前端接口：用户中心-用户终止追号任务 （用户撤单：追号期号中所有等待追号的全撤）
	 * @author huangb
	 * @date 2017年4月13日
	 * @param queryVO
	 *            参数对象
	 * @return 前端接口：用户中心-用户终止追号任务 （用户撤单：追号期号中所有等待追号的全撤）
	 */
	ResultBO<?> updChaseStatusAsUserCancel(UserChaseDetailQueryVO queryVO);

	/**
	 * @desc 前端接口：用户撤单调用中转接口(为避免事务处理与mq消息处理冲突)
	 * @author huangb
	 * @date 2017年7月3日
	 * @param queryVO
	 *            参数对象
	 * @return 前端接口：用户撤单调用中转接口(为避免事务处理与mq消息处理冲突)
	 */
	ResultBO<?> userCancel(UserChaseDetailQueryVO queryVO);

	/**
	 * @desc   查询符合条件的追号单数
	 * @author Tony Wang
	 * @create 2017年8月14日
	 * @param vo
	 * @return 
	 */
	int countOrderAdd(OrderAddQueryVO vo);

	/**
	 * @desc   查询符合条件的追号单
	 * @author Tony Wang
	 * @create 2017年8月29日
	 * @param vo
	 * @return 
	 */
	List<OrderAddBO> findOrderAdd(OrderAddQueryVO vo);
	
	/**
	 * @desc 前端接口：用户中心-用户终止追号任务 （查询目标追号信息,主要是验证目标订单是否存在，及目标订单是否与活动有关）
	 * @author huangb
	 * @date 2018年1月2日
	 * @param queryVO
	 *            查询对象
	 * @return 前端接口：用户中心-用户终止追号任务 （查询目标追号信息,主要是验证目标订单是否存在，及目标订单是否与活动有关）
	 */
	ResultBO<OrderAddBO> findChaseAsUserCancel(UserChaseDetailQueryVO queryVO);
}
