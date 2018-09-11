package com.hhly.lotto.api.common.controller.num.pl5.v1_0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.api.common.controller.num.NumV10Controller;
import com.hhly.lottocore.remote.trend.service.IPl5TrendService;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;

/**
 * @desc 排列五抽象类（定义pc和手机端共有信息）
 * @author huangb
 * @date 2017年10月24日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class Pl5V10Controller extends NumV10Controller {


	/**
	 * 远程服务：排列五遗漏走势服务
	 */
	@Autowired
	protected IPl5TrendService pl5TrendService;
	
	/**
	 * @desc 公共端接口：排列五：查询走势投注列表
	 * @author huangb
	 * @date 2017年10月24日
	 * @param param(qryCount期数)
	 * @return 公共端接口：排列五：查询走势投注列表
	 */
	@RequestMapping(value = "/trend/betting", method = RequestMethod.GET)
	public Object findTrendBettingInfo(LotteryVO param) {
		logger.debug("{}接口：{}：查询走势投注列表",getPlatform().getDesc(),getLotName());
		param.setLotteryCode(getLottery());
		return pl5TrendService.findTrendBettingInfo(param);
	}
	
	@Override
	protected Integer getLottery() {
		return Lottery.PL5.getName();
	}

	@Override
	protected String getLotName() {
		return Lottery.PL5.getDesc();
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
		return pl5TrendService.findDrawColdHotOmit(param);
	}
	
}
