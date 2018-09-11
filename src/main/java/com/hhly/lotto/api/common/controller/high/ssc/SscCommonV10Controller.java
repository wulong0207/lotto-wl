package com.hhly.lotto.api.common.controller.high.ssc;

import org.springframework.util.Assert;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;

public class SscCommonV10Controller extends SscV10Controller {

	@Override
	protected int getIssueCount() {
		return 20;
	}

	@Override
	protected <T extends HighLotteryVO> ResultBO<HighOmitDataBO> findOmit(T omitVO) {
		return null;
	}

	@Override
	protected Integer getLottery() {
		return null;
	}
	
	
	@Override
	public Object findDrawColdHotOmit(LotteryVO param) throws Exception {
		Assert.notNull(param);
		Integer lotteryCode = param.getLotteryCode();
		Assert.notNull(lotteryCode,"彩种编码不能为空");
		logger.debug("开奖信息{}接口：{}:查询遗漏/冷热数据",getPlatform().getDesc(),lotteryCode);
		param.setLotteryCode(lotteryCode);
		param.setQryCount(Constants.NUM_100);
		return sscTrendService.findDrawColdHotOmit(param);
	}
	

}
