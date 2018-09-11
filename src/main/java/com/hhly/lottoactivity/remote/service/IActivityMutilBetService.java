package com.hhly.lottoactivity.remote.service;

import java.util.List;

import com.hhly.skeleton.activity.bo.ActivityMutilBetInfoBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.order.vo.OrderInfoVO;

/**
 * @desc 追号活动接口
 * @author lidecheng
 * @date 2017年10月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IActivityMutilBetService {
	
	/**
	 * @desc   查询倍投立减活动信息
	 * @author lidecheng
	 * @create 2017年11月14日
	 * @return 
	 */
	ResultBO<List<ActivityMutilBetInfoBO>> findActivityMutilBetInfo(String activityCode);
	
	/**
	 * 检测倍投立减送活动信息
	 * @param activityCode
	 * @return
	 */
	ResultBO<?> validateMutilBet(OrderInfoVO orderVo);
}
