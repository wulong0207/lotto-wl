package com.hhly.lotto.api.pc.high.controller.ssc;

import java.util.List;
import java.util.Objects;

import com.hhly.lotto.api.pc.high.controller.HighController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryTrendVO;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitBaseBO;
import com.hhly.skeleton.lotto.base.trend.high.bo.SscOmitBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.SscOmitVO;

/**
 * @desc    时时彩抽象controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class SscController extends HighController {
	
	@Override
	public Object findRecentDrawDetail() {
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
		for(HighOmitBaseBO issue : list) {
			String stype=null, gtype=null, h3type=null;
			sscIssue = (SscOmitBO)issue;
			if(sscIssue.getSdxds() != null) {
				stype =  (Objects.equals(sscIssue.getSdxds().get(0).toString(), "0") ? "大":"小") + 
						(Objects.equals(sscIssue.getSdxds().get(2).toString(), "0") ? "单":"双");
			}
			if(sscIssue.getGdxds() != null) {
				gtype =  (Objects.equals(sscIssue.getGdxds().get(0).toString(), "0") ? "大":"小") + 
						(Objects.equals(sscIssue.getGdxds().get(2).toString(), "0") ? "单":"双");
			}
			if(sscIssue.getH3z() != null) {
				h3type = Objects.equals(sscIssue.getH3z().get(0).toString(), "0") ? 
						"组三": Objects.equals(sscIssue.getH3z().get(1).toString(), "0") ? "组六":"豹子";
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
		Assert.paramNotNull(vo);
		Integer lotteryCode = vo.getLotteryCode();
		Assert.paramNotNull(lotteryCode,"lotteryCode");
		Assert.paramNotNull(vo.getStartIssue(),"开始彩期");
		Assert.paramNotNull(vo.getEndIssue(),"结束彩期");
		Assert.paramLegal(getLottery().equals(lotteryCode),"重庆时时彩");
		return highLotteryService2.findBaseTrend(vo);
	}
}

	

