package com.hhly.lotto.api.common.controller.high.ssc;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.lotto.api.common.controller.high.HighV10Controller;
import com.hhly.lottocore.remote.trend.service.ISscTrendService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryTrendVO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitBaseBO;
import com.hhly.skeleton.lotto.base.trend.high.bo.SscOmitBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.SscOmitVO;

/**
 * @desc 时时彩抽象controller
 * @author Tony Wang
 * @date 2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class SscV10Controller extends HighV10Controller {
	@Autowired
	protected ISscTrendService sscTrendService;

	@Override
	public Object findRecentDrawDetail() {
		logger.debug("高频彩{}:查询最近中奖");
		SscOmitVO vo = new SscOmitVO();
		vo.setLotteryCode(getLottery());
		vo.setShowDrawCode(true);
		vo.setShowIssue(true);
		vo.showSdxds(true);
		vo.showGdxds(true);
		vo.showH3(true);
		vo.setQryCount(getIssueCount());
		List<HighOmitBaseBO> list = highLotteryService2.findRecentIssue(vo).getData();
		SscOmitBO sscIssue;
		for (HighOmitBaseBO issue : list) {
			String stype = null, gtype = null, h3type = null;
			sscIssue = (SscOmitBO) issue;
			if (sscIssue.getSdxds() != null) {
				stype = (Objects.equals(sscIssue.getSdxds().get(0).toString(), "0") ? "大" : "小")
						+ (Objects.equals(sscIssue.getSdxds().get(2).toString(), "0") ? "单" : "双");
			}
			if (sscIssue.getGdxds() != null) {
				gtype = (Objects.equals(sscIssue.getGdxds().get(0).toString(), "0") ? "大" : "小")
						+ (Objects.equals(sscIssue.getGdxds().get(2).toString(), "0") ? "单" : "双");
			}
			if (sscIssue.getH3z() != null) {
				h3type = Objects.equals(sscIssue.getH3z().get(0).toString(), "0") ? "组三"
						: Objects.equals(sscIssue.getH3z().get(1).toString(), "0") ? "组六" : "豹子";
			}
			sscIssue.setStype(stype);
			sscIssue.setGtype(gtype);
			sscIssue.setH3type(h3type);
			sscIssue.setSdxds(null);
			sscIssue.setGdxds(null);
			sscIssue.setH3z(null);
		}
		return ResultBO.ok(list);
	}

	@Override
	public ResultBO<List<TrendBaseBO>> findBaseTrend(LotteryTrendVO vo) {
		logger.debug("高频彩{}:基本走势", getLottery());
		Assert.paramNotNull(vo);
		vo.setLotteryCode(getLottery());
		if (vo.getQryCount() == null) {
			vo.setQryCount(Constants.NUM_30);
		}
		return sscTrendService.findBaseTrend(vo);
	}

	@Override
	public Object findDrawColdHotOmit(LotteryVO param) throws Exception {
		logger.debug("开奖信息{}接口：{}:查询遗漏/冷热数据", getPlatform().getDesc(), getLotName());
		param.setLotteryCode(getLottery());
		param.setQryCount(Constants.NUM_100);
		return sscTrendService.findDrawColdHotOmit(param);
	}

	@Override
	public ResultBO<TrendBaseBO> findTrendBet(LotteryTrendVO vo){
		logger.debug("走势投注,彩种{}", getLotName());
		if(vo==null){
			vo = new LotteryTrendVO();
		  }
		if(vo.getQryCount()==null){
			vo.setQryCount(getIssueCount());
		}	
		vo.setLotteryCode(getLottery());
		return sscTrendService.findTrendBetting(vo);
	}

}
