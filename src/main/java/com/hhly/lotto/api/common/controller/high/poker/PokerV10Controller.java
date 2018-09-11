package com.hhly.lotto.api.common.controller.high.poker;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.hhly.lotto.api.common.controller.high.HighV10Controller;
import com.hhly.lottocore.remote.trend.service.IPokeTrendService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OmitEnum;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.PokerOmitVO;

/**
 * @desc    快乐扑克抽象controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class PokerV10Controller extends HighV10Controller {

	@Autowired
	private IPokeTrendService pokeService;
	/**
	 * @desc   PC跟移动端返回的遗漏字段一样，统一实现
	 * @author Tony Wang
	 * @create 2017年6月28日
	 * @param vo
	 * @return
	 */
	@Override
	protected <T extends HighLotteryVO> ResultBO<HighOmitDataBO> findOmit(T omitVO) {
		logger.debug("高频彩{}：查询遗漏",getLottery());
		// 查询所有子玩法的遗漏
		PokerOmitVO pokerOmitVO = new PokerOmitVO();
		BeanUtils.copyProperties(omitVO, pokerOmitVO);
		// 快乐扑克目前只有查询历史遗漏
		pokerOmitVO.setQryFlag(OmitEnum.QryFlag.NORMAL.getRange());
		Assert.notNull(pokerOmitVO);
		Assert.notEmpty(pokerOmitVO.getOmitTypes());
		Assert.isTrue(OmitEnum.HighOmitType.containsAll(pokerOmitVO.getOmitTypes()));
		//List<Integer> subPlays = pokerOmitVO.getSubPlays();
		// 一次只能查一个子玩法
		//Assert.isTrue(subPlays != null && subPlays.size()==1 && HighConstants.POKER_SUBPLAY.containsAll(subPlays));
		pokerOmitVO.setLotteryCode(getLottery());
		//PokerOmitVO recentVO = new PokerOmitVO();
		//BeanUtils.copyProperties(pokerOmitVO, recentVO);
		pokerOmitVO.showRn(true);
		pokerOmitVO.showTh(true);
		pokerOmitVO.showSz(true);
		pokerOmitVO.showDz(true);
		pokerOmitVO.showBz(true);
		pokerOmitVO.setShowFlag(true);
		return highLotteryService2.findResultOmit(pokerOmitVO);
	}
	
	@Override
	public Object findRecentDrawDetail() {
		logger.debug("高频彩{}:查询最近开奖详情列表", getLottery());
		PokerOmitVO vo = new PokerOmitVO();
		vo.setLotteryCode(getLottery());
		vo.setShowDrawCode(true);
		vo.setShowIssue(true);
		vo.setType(true);
		vo.setQryCount(getIssueCount());
		return highLotteryService2.findRecentIssue(vo);
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
		//omitType  在这里的含义 ：1 前三直选 2任五 3任七 4任八
		logger.debug("开奖信息{}接口：{}:查询遗漏/冷热数据",getPlatform().getDesc(),getLotName());
		param.setLotteryCode(getLottery());
		param.setQryCount(Constants.NUM_100);
		return pokeService.findDrawColdHotOmit(param);
	}
	
}

	

