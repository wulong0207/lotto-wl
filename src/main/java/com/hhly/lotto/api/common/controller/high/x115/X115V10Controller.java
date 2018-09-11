package com.hhly.lotto.api.common.controller.high.x115;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hhly.lotto.api.common.controller.high.HighV10Controller;
import com.hhly.lottocore.remote.trend.service.IC11x5TrendService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OmitEnum;
import com.hhly.skeleton.base.common.OmitEnum.QryFlag;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.HighConstants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryTrendVO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.bo.TreadStatistics;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighOmitBetVO;

/**
 * @desc 十一选五抽象controller
 * @author Tony Wang
 * @date 2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class X115V10Controller extends HighV10Controller {
	
	@Autowired
	protected IC11x5TrendService c11x5TrendService;
	/**
	 * @desc 判断是否为该彩种的子玩法
	 * @author Tony Wang
	 * @create 2017年6月28日
	 * @param subPlay
	 * @return
	 */
	protected abstract boolean isSubPlay(Integer subPlay);

	/**
	 * @desc 查询遗漏，同时返回N期冷热、当前遗漏、最大遗漏、上次遗漏
	 * @author Tony Wang
	 * @create 2017年3月13日
	 * @param vo
	 * @return PC端要求一次性返回所有子玩法的所有遗漏数据
	 * @throws Exception
	 */
	@Override
	public <T extends HighLotteryVO> ResultBO<HighOmitDataBO> findOmit(T vo) {
		logger.debug("十一选五{}：查询遗漏",getLottery());
		Assert.notNull(vo);
		Assert.notNull(vo.getOmitTypes());
		Assert.notNull(vo.getSubPlays());
		Assert.isTrue(OmitEnum.HighOmitType.containsAll(vo.getOmitTypes()));
		Assert.isTrue(HighConstants.X115_SUBPLAY.containsAll(vo.getSubPlays()));
		Assert.isTrue(OmitEnum.QryFlag.contains(vo.getQryFlag()));
		// 设置彩种
		vo.setLotteryCode(getLottery());
		QryFlag qryFlag = QryFlag.valueOf(vo.getQryFlag());
		switch (qryFlag) {
		case NORMAL:
		case DANTUO:
			return c11x5TrendService.findResultOmit(vo);
		case RECENT:
			return c11x5TrendService.findRecentOmit(vo);
		default:
			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID, vo.getQryFlag()));
		}
	}

	@Override
	protected ResultBO<List<TreadStatistics>> findOmitBet(HighOmitBetVO omitVO) {
		logger.debug("十一选五{}:遗漏投注",getLottery());
		Assert.notNull(omitVO);
		omitVO.setLotteryCode(getLottery());
		Assert.notNull(omitVO.getFatypes());
		Assert.notNull(omitVO.getSontypes());
		Assert.isTrue(HighConstants.X115_FA_TYPES.containsAll(omitVO.getFatypes()));
		Assert.isTrue(HighConstants.X115_SON_TYPES.containsAll(omitVO.getSontypes()));
		return c11x5TrendService.findOmitBet(omitVO);
	}

	@Override
	public ResultBO<List<TrendBaseBO>> findBaseTrend(LotteryTrendVO vo) {
		logger.debug("十一选五{}:基本走势",getLottery());
		Assert.notNull(vo);
		vo.setLotteryCode(getLottery());
		//基本走势   子玩法 默认任选
		vo.setBtype(1);
		if(vo.getQryCount()==null){
			vo.setQryCount(Constants.NUM_30);
		}
		return c11x5TrendService.findBaseTrend(vo);
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
		logger.debug("开奖信息{}接口：{}:查询遗漏/冷热数据",getPlatform().getDesc(),getLotName());
		param.setLotteryCode(getLottery());
		param.setQryCount(Constants.NUM_100);
		return c11x5TrendService.findDrawColdHotOmit(param);
	}

	@Override
	public ResultBO<TrendBaseBO> findTrendBet(LotteryTrendVO vo) {
		logger.debug("走势投注,彩种{}",getLottery());
		if(vo==null){
			vo = new LotteryTrendVO();
		}
		vo.setLotteryCode(getLottery());
		if(vo.getQryCount()==null){
			vo.setQryCount(getIssueCount());
		}
		return c11x5TrendService.findTrendBetting(vo);
	}
	
	
}
