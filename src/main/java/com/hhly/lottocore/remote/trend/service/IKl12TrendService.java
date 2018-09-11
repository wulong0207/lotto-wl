package com.hhly.lottocore.remote.trend.service;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryTrendVO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.bo.ColdHotOmitBo;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;

/**
 * 
 * @desc 快乐12走势
 * @author chenghougui
 * @Date 2018年1月19日
 * @Company 益彩网络科技公司
 * @version
 */
public interface IKl12TrendService extends IHighTrendService {
	
	
	/******************** 快乐12分的走势服务   ********************/
	
	/**
	 * 基本走势
	 */
	@Override
	ResultBO<List<TrendBaseBO>> findBaseTrend(LotteryTrendVO param);
	
	/**
	 * 
	 * @desc 开奖信息中查找冷/热,遗漏数据
	 * @create 2018年1月5日
	 * @param param
	 * @return ResultBO<List<ColdHotOmitBo>>
	 */
	ResultBO<ColdHotOmitBo> findDrawColdHotOmit(LotteryVO param);
}
