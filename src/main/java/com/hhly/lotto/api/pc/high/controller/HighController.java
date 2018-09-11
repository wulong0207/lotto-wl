package com.hhly.lotto.api.pc.high.controller;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.api.pc.num.controller.NumController;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.lottocore.remote.trend.service.IHighTrendService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryTrendVO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.bo.TreadStatistics;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighOmitBetVO;

/**
 * @desc    高频彩抽象controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class HighController extends NumController {

//	@Autowired
//	protected IHighLotteryService highLotteryService;
	
	private static Logger logger = Logger.getLogger(HighController.class);
	
	/**
	 * 远程服务：遗漏走势
	 */
	@Autowired
	@Qualifier("iHighTrendService")
	protected IHighTrendService highLotteryService2;
	
//	@Autowired
//	@Qualifier("iHighTrendService")
//	protected IHighTrendService highLotteryService;
	
	/**
	 * 远程服务:彩期服务
	 */
	@Autowired
	protected ILotteryIssueService lotteryIssueService;

	/**
	 * @desc   首页信息：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号
	 * @author Tony Wang
	 * @create 2017年3月28日
	 * @return 
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Object info() {
		logger.debug(MessageFormat.format("高频彩{0}:查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号", getLottery()));
		return lotteryIssueService.findLotteryIssueBase(getLottery(), getPlatform().getValue());
	}
	
	/**
	 * @desc  查询最近开奖详情列表时指定的期数
	 * @author Tony Wang
	 * @create 2017年6月28日 
	 */
	protected abstract int getIssueCount();
	
	/**
	 * @desc   有些彩种PC跟移动端返回的遗漏字段不一样，分开实现
	 * @author Tony Wang
	 * @create 2017年6月28日
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/omit", method = RequestMethod.GET)
	protected abstract <T extends HighLotteryVO>  ResultBO<HighOmitDataBO> findOmit(T omitVO);

	/**
	 * @desc   查询最近开奖详情列表
	 * @author Tony Wang
	 * @create 2017年3月9日
	 * @return 
	 */
	@RequestMapping(value = "/issue/recent", method = RequestMethod.GET)
	public Object findRecentDrawDetail() {
		logger.debug(MessageFormat.format("高频彩{0}:查询最近开奖详情列表", getLottery()));
		return lotteryIssueService.findRecentDrawIssue(new LotteryVO(getLottery(), getIssueCount()));
	}
	
	@RequestMapping(value = "/limit", method = RequestMethod.GET)
	public Object findLimit(LotteryVO vo) {
		logger.debug(MessageFormat.format("高频彩{0}:查询限号", getLottery()));
		Assert.notNull(vo);
		// 江苏快3查询所有子玩法的限号信息
		Assert.isNull(vo.getSubPlays());
		vo.setLotteryCode(getLottery());
		Map<String, Object> ret = new HashMap<>();
		ret.put("currentDateTime", DateUtil.convertDateToStr(new Date()));
		ret.put("limitInfoList", lotteryIssueService.findLimit(vo).getData());
		return new ResultBO<>(ret);
	}
	
	/**
	 * 遗漏投注
	 * @desc 
	 * @create 2017年11月7日
	 * @param omitVO
	 * @return ResultBO<HighOmitDataBO>
	 */
	@RequestMapping(value="/omitBet",method = RequestMethod.GET)
	protected ResultBO<List<TreadStatistics>> findOmitBet(HighOmitBetVO omitVO){
		return null;
	}
	
	@RequestMapping(value="/baseTrend", method = RequestMethod.GET)
	public ResultBO<List<TrendBaseBO>> findBaseTrend(LotteryTrendVO vo){
		return null;
	}
}
