package com.hhly.lottocore.remote.trend.service;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.num.bo.f3d.F3dOmitOutBO;
import com.hhly.skeleton.lotto.base.trend.num.bo.f3d.F3dRecentTrendBO;
import com.hhly.skeleton.lotto.base.trend.num.bo.f3d.F3dRecentTrendOutBO;

/**
 * @desc 福彩3d遗漏走势的服务接口
 * @author huangb
 * @date 2017年6月27日
 * @company 益彩网络
 * @version v1.0
 */
public interface IF3dTrendService extends INumTrendService {
	/*************
	 * 公共的远程服务接口：提供findSingle和findMultiple处理大部分的查询需求，如有其它差异性的查询在上面单独提供
	 ************/
	
	/*****************************福彩3D首页-遗漏、冷热、概率数据接口 *********************************/
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
	ResultBO<List<F3dOmitOutBO>> findOmitChanceColdHotAll(LotteryVO param) throws Exception;
	
	/**
	 * @desc 前端接口：近期开奖：查询近期遗漏走势
	 * @author huangb
	 * @date 2017年6月30日
	 * @param param
	 *            参数对象(qryCount)
	 * @return 前端接口：近期开奖：查询近期遗漏走势
	 */
	ResultBO<List<F3dRecentTrendOutBO>> findRecentTrend(LotteryVO param);

	/**
	 * @desc 前端接口：近期开奖：查询近期遗漏走势(简易版-手机端使用)
	 * @author huangb
	 * @date 2017年6月30日
	 * @param param
	 *            参数对象(qryCount)
	 * @return 前端接口：近期开奖：查询近期遗漏走势(简易版-手机端使用)
	 */
	ResultBO<List<F3dRecentTrendBO>> findRecentTrendSimple(LotteryVO param);
	
	
	/*****************************福彩3D走势图数据接口 *********************************/
	
}
