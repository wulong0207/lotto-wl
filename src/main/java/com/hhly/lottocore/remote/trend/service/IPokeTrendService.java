package com.hhly.lottocore.remote.trend.service;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryTrendVO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;
import com.hhly.skeleton.lotto.base.trend.bo.X115ColdHotOmitBo;

/**
 * 
 * @desc 快乐扑克遗漏走势
 * @author chenghougui
 * @Date 2018年1月12日
 * @Company 益彩网络科技公司
 * @version
 */
public interface IPokeTrendService extends IHighTrendService {

	@Override
	ResultBO<List<TrendBaseBO>> findBaseTrend(LotteryTrendVO param);
	
	/**
	 * 
	 * @desc 开奖信息中查找冷/热,遗漏数据
	 * @create 2018年1月5日
	 * @param param
	 * @return ResultBO<List<ColdHotOmitBo>>
	 */
	ResultBO<X115ColdHotOmitBo> findDrawColdHotOmit(LotteryVO param);
}
