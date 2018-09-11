package com.hhly.lotto.api.pc.num.controller.pl5;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.lotto.api.pc.num.controller.NumController;
import com.hhly.lottocore.remote.trend.service.IPl5TrendService;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;

/**
 * @desc 排列五抽象类（定义pc和手机端共有信息）
 * @author huangb
 * @date 2017年10月24日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class Pl5Controller extends NumController {

	private static Logger logger = Logger.getLogger(Pl5Controller.class);

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
	@ResponseBody
	public Object findTrendBettingInfo(LotteryVO param) {
		logger.debug(getPlatform().getDesc() + "接口：" + getLotName() + " ：查询走势投注列表");
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
}
