package com.hhly.lottocore.remote.trend.service;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryTrendVO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.bo.ColdHotOmitBo;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;

/**
 * @desc 各彩种遗漏走势的公共服务接口
 * @author huangb
 * @date 2017年3月8日
 * @company 益彩网络
 * @version v1.0
 */
public interface INumTrendService {

	/*************
	 * 公共的远程服务接口：提供findSingle和findMultiple处理大部分的查询需求，如有其它差异性的查询在上面单独提供
	 ************/
	/**
	 * @desc 前端接口：基础信息遗漏(对应彩种base结尾基础表)：查询遗漏/概率/冷热(按查询标识分别查询)
	 * @author huangb
	 * @date 2017年3月23日
	 * @param param
	 *            参数对象
	 * @return 前端接口：基础信息遗漏(对应彩种base结尾基础表)：查询遗漏/概率/冷热(按查询标识分别查询)
	 * @throws Exception 
	 */
	ResultBO<TrendBaseBO> findOmitChanceColdHot(LotteryVO param) throws Exception;
	
	ResultBO<List<TrendBaseBO>> findTrendRange(LotteryVO param);
	
	
	ResultBO<List<TrendBaseBO>> findBaseTrend(LotteryTrendVO param) throws Exception;
	
	/**
	 * 奇偶走势
	 * @desc 
	 * @create 2017年11月15日
	 * @param param
	 * @return
	 * @throws Exception ResultBO<TrendBaseBO>
	 */
	ResultBO<List<TrendBaseBO>> findOETrend(LotteryTrendVO param) throws Exception;
	
	/**
	 * 大小走势
	 * @desc 
	 * @create 2017年11月15日
	 * @param param
	 * @return
	 * @throws Exception ResultBO<TrendBaseBO>
	 */
	ResultBO<List<TrendBaseBO>> findBSTrend(LotteryTrendVO param) throws Exception;
		
	/**************************开奖信息****************************/
	
	/**
	 * 
	 * @desc 开奖信息中查找冷/热,遗漏数据
	 * @create 2018年1月5日
	 * @param param
	 * @return ResultBO<List<ColdHotOmitBo>>
	 */
	ResultBO<List<ColdHotOmitBo>> findDrawColdHotOmit(LotteryVO param);
	
}
