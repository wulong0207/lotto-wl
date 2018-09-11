package com.hhly.lotto.api.common.controller.num.f3d.v1_0;

import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.lotto.api.common.controller.num.NumV10Controller;
import com.hhly.lottocore.remote.trend.service.IF3dTrendService;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;

/**
 * @desc 福彩3d抽象类（定义pc和手机端共有信息）
 * @author huangb
 * @date 2017年11月10日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class F3dV10Controller extends NumV10Controller {

	/**
	 * 远程服务：福彩3D遗漏走势的服务接口
	 */
	@Autowired
	protected IF3dTrendService f3dTrendService;
	
	@Override
	protected Integer getLottery() {
		return Lottery.F3D.getName();
	}

	@Override
	protected String getLotName() {
		return Lottery.F3D.getDesc();
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
		return f3dTrendService.findDrawColdHotOmit(param);
	}
	
}
