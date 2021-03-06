package com.hhly.lotto.api.common.controller.area.qyh;

import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.lotto.api.common.controller.high.HighV10Controller;
import com.hhly.lottocore.remote.trend.service.IQyhTrendService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;

/**
 * 
 * @desc 群英会
 * @author chenghougui
 * @Date 2018年1月19日
 * @Company 益彩网络科技公司
 * @version
 */
public abstract class QyhV10Controller extends HighV10Controller {
	@Autowired
	protected IQyhTrendService qyhTrendService;

	@Override
	protected <T extends HighLotteryVO> ResultBO<HighOmitDataBO> findOmit(T omitVO) {
		return null;
	}
	
	@Override
	public Object findDrawColdHotOmit(LotteryVO param) throws Exception {
		logger.debug("开奖信息{}接口：{}:查询遗漏/冷热数据", getPlatform().getDesc(), getLotName());
		Assert.notNull(param);
		param.setLotteryCode(getLottery());
		if (param.getQryCount() == null) {
			param.setQryCount(Constants.NUM_100);
		}
		return qyhTrendService.findDrawColdHotOmit(param);
	}

	
	


}
