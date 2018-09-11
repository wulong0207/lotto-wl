package com.hhly.lotto.api.pc.high.controller.x115;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hhly.lotto.api.pc.high.controller.HighController;
import com.hhly.lottocore.remote.trend.service.IC11x5TrendService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OmitEnum;
import com.hhly.skeleton.base.common.OmitEnum.QryFlag;
import com.hhly.skeleton.base.constants.HighConstants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.lotto.base.trend.bo.TreadStatistics;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighOmitBetVO;

/**
 * @desc    十一选五抽象controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class X115Controller extends HighController {
	private static Logger logger = Logger.getLogger(X115Controller.class);
	
	@Autowired
	protected IC11x5TrendService c11x5TrendService;
	/**
	 * @desc   判断是否为该彩种的子玩法
	 * @author Tony Wang
	 * @create 2017年6月28日
	 * @param subPlay
	 * @return 
	 */
	protected abstract boolean isSubPlay(Integer subPlay);
	
	/**
	 * @desc   查询遗漏，同时返回N期冷热、当前遗漏、最大遗漏、上次遗漏
	 * @author Tony Wang
	 * @create 2017年3月13日
	 * @param vo
	 * @return PC端要求一次性返回所有子玩法的所有遗漏数据
	 * @throws Exception 
	 */
	@Override
	public <T extends HighLotteryVO> ResultBO<HighOmitDataBO> findOmit(T vo) {
		logger.info("十一选五：查询遗漏");
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
			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,vo.getQryFlag()));
		}
	}
	
	@Override
	protected ResultBO<List<TreadStatistics>> findOmitBet(HighOmitBetVO omitVO) {
		Assert.notNull(omitVO);
		Assert.notNull(omitVO.getLotteryCode());
		Assert.notNull(omitVO.getFatypes());
		Assert.notNull(omitVO.getSontypes());
		Assert.isTrue(HighConstants.X115_FA_TYPES.containsAll(omitVO.getFatypes()));
		Assert.isTrue(HighConstants.X115_SON_TYPES.containsAll(omitVO.getSontypes()));
		return c11x5TrendService.findOmitBet(omitVO);
	}	
}
