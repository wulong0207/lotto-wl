package com.hhly.lottoactivity.remote.service;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityAddedBO;
import com.hhly.skeleton.lotto.base.order.vo.OrderAddVO;

/**
 * @desc 追号活动接口
 * @author lidecheng
 * @date 2017年10月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IActivityAddedService {
	
	/**
	 * @desc   查询追号送活动信息
	 * @author lidecheng
	 * @create 2017年11月14日
	 * @return 
	 */
	ResultBO<List<OperateActivityAddedBO>> findActivityAddedInfo(String lotteryCode);
	
	/**
	 * 检测追号送活动信息
	 * @param activityCode
	 * @return
	 */
	ResultBO<?> checkAdded(OrderAddVO orderVo,Integer addForm);
}
