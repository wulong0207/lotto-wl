package com.hhly.lottocore.remote.trend.service;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;

/**
 * @desc    七星彩遗漏走势的服务接口
 * @author  Tony Wang
 * @date    2017年7月31日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IQxcTrendService extends INumTrendService {

	/**
	 * @desc   查询最近开奖
	 * @author Tony Wang
	 * @create 2017年7月31日
	 * @param vo
	 * @return 
	 */
	ResultBO<List<TrendBaseBO>> findRecentDrawIssue(LotteryVO vo);
}
