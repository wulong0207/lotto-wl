package com.hhly.lottocore.remote.trend.service;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.num.bo.pl3.Pl3OmitOutBO;
import com.hhly.skeleton.lotto.base.trend.num.bo.pl3.Pl3RecentTrendBO;
import com.hhly.skeleton.lotto.base.trend.num.bo.pl3.Pl3RecentTrendOutBO;

/**
 * @desc 排列三遗漏走势的服务接口
 * @author huangb
 * @date 2017年6月27日
 * @company 益彩网络
 * @version v1.0
 */
public interface IPl3TrendService extends INumTrendService {
	/*************
	 * 公共的远程服务接口：提供findSingle和findMultiple处理大部分的查询需求，如有其它差异性的查询在上面单独提供
	 ************/
	
	/*****************************排列三首页-遗漏、冷热、概率数据接口 *********************************/
	/**
	 * @desc 前端接口：所有遗漏(1：直选-普通遗漏；2：组三-包号遗漏|组六-普通遗漏|组六-胆拖遗漏；3：直选-和值遗漏；4：组三-和值遗漏；5
	 *       ：组六-和值遗漏 )：查询遗漏/概率/冷热(按查询标识分别查询)
	 * @author huangb
	 * @date 2017年3月23日
	 * @param param
	 *            参数对象
	 * @return 前端接口：所有遗漏(1：直选-普通遗漏；2：组三-包号遗漏|组六-普通遗漏|组六-胆拖遗漏；3：直选-和值遗漏；4：组三-和值遗漏；5
	 *       ：组六-和值遗漏 )：查询遗漏/概率/冷热(按查询标识分别查询)
	 * @throws Exception
	 */
	ResultBO<List<Pl3OmitOutBO>> findOmitChanceColdHotAll(LotteryVO param) throws Exception;
	
	/**
	 * @desc 前端接口：近期开奖：查询近期遗漏走势
	 * @author huangb
	 * @date 2017年6月30日
	 * @param param
	 *            参数对象(qryCount)
	 * @return 前端接口：近期开奖：查询近期遗漏走势
	 */
	ResultBO<List<Pl3RecentTrendOutBO>> findRecentTrend(LotteryVO param);

	/**
	 * @desc 前端接口：近期开奖：查询近期遗漏走势(简易版-手机端使用)
	 * @author huangb
	 * @date 2017年6月30日
	 * @param param
	 *            参数对象(qryCount)
	 * @return 前端接口：近期开奖：查询近期遗漏走势(简易版-手机端使用)
	 */
	ResultBO<List<Pl3RecentTrendBO>> findRecentTrendSimple(LotteryVO param);
	
	
	/*****************************排列三走势图数据接口 *********************************/
	
}
