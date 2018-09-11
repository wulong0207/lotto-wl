package com.hhly.lotto.api.common.controller.num.ssq.v1_0;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.lotto.api.common.controller.num.NumV10Controller;
import com.hhly.lottocore.remote.trend.service.ISsqTrendService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryTrendVO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;

/**
 * @desc 双色球抽象类（定义pc和手机端共有信息）
 * @author huangb
 * @date 2017年11月10日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class SsqV10Controller extends NumV10Controller {
	
	@Autowired
	private ISsqTrendService SsqTrendService;
	
	@Override
	protected Integer getLottery() {
		return Lottery.SSQ.getName();
	}

	@Override
	protected String getLotName() {
		return Lottery.SSQ.getDesc();
	}

	@Override
	public ResultBO<List<TrendBaseBO>> findBaseTrend(LotteryTrendVO vo) throws Exception{
		logger.debug("{}接口：{}：查询走势投注列表",getPlatform().getDesc(),getLotName());
		Assert.notNull(vo);
		vo.setLotteryCode(getLottery());
		//默认30期
		if(vo.getQryCount()==null){
			vo.setQryCount(Constants.NUM_30);
		}
		return SsqTrendService.findBaseTrend(vo);
	}
	
	/***************************开奖信息*******************************/
	
	/**
	 * 
	 * @desc 用户开奖信息的遗漏，冷/热
	 * @create 2018年1月5日
	 * @param param
	 * @return
	 * @throws Exception Object
	 */
	@Override
	public Object findDrawColdHotOmit(LotteryVO param) throws Exception {
		logger.debug("开奖信息{}接口：{}:查询冷热数据",getPlatform().getDesc(),getLotName());
		param.setLotteryCode(getLottery());
		param.setQryCount(Constants.NUM_100);
		return SsqTrendService.findDrawColdHotOmit(param);
	}
}
