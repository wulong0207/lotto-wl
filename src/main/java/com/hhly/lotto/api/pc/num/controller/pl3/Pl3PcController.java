package com.hhly.lotto.api.pc.num.controller.pl3;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;

/**
 * @desc PC端：排列三页面控制器
 * @author huangb
 * @date 2017年6月28日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
@RequestMapping("/pc/pl3")
public class Pl3PcController extends Pl3Controller {

	private static Logger logger = Logger.getLogger(Pl3PcController.class);

	/**
	 * @desc PC端接口：排列三：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号
	 * @author huangb
	 * @date 2017年6月28日
	 * @return PC端接口：排列三：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	@ResponseBody
	public Object findPl3() {
		logger.debug("PC端接口：排列三：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号");
		return lotteryIssueService.findLotteryIssueBase(getLottery(), getPlatform().getValue());
	}

	/**
	 * @desc PC端接口：排列三：查询最近开奖详情列表
	 * @author huangb
	 * @date 2017年3月6日
	 * @param param(qryCount期数)
	 * @return PC端接口：排列三：查询最近开奖详情列表
	 */
	@RequestMapping(value = "/recent/drawissue", method = RequestMethod.GET)
	@ResponseBody
	public Object findRecentDrawDetail(LotteryVO param) {
		logger.debug("PC端接口：排列三：查询最近开奖详情列表");
		param.setLotteryCode(getLottery());
		return pl3TrendService.findRecentTrend(param);
	}

	/**
	 * @desc PC端接口：排列三：查询遗漏/冷热（统计期数内出现最多或最少的号码）/概率（出号概率=号码当前遗漏值/号码历史平均遗漏值）数据
	 * @author huangb
	 * @date 2017年3月6日
	 * @return PC端接口：排列三：查询遗漏/冷热（统计期数内出现最多或最少的号码）/概率（出号概率=号码当前遗漏值/号码历史平均遗漏值）数据
	 */
	@RequestMapping(value = "/omit", method = RequestMethod.GET)
	@ResponseBody
	public Object findOmitChanceColdHot(LotteryVO param) throws Exception {
		logger.debug("PC端接口：排列三：查询遗漏/冷热/概率数据");
		param.setLotteryCode(getLottery());
		return pl3TrendService.findOmitChanceColdHotAll(param);
	}
}
