package com.hhly.lotto.api.pc.controller.num.dlt.v1_0;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.num.dlt.v1_0.DltV10Controller;
import com.hhly.skeleton.activity.bo.ActivityBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.NUMConstants;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryIssueBaseBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;

/**
 * @desc PC端：大乐透页面控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@RestController
@RequestMapping("/pc/v1.0/dlt")
public class DltPcV10Controller extends DltV10Controller {


	/**
	 * @desc PC端接口：大乐透：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号
	 * @author huangb
	 * @date 2017年3月6日
	 * @return PC端接口：大乐透：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Object findDlt(HttpServletRequest request) {
		logger.debug("PC端接口:大乐透:查LotteryIssueBaseBO数配置、子玩法、限号");
		ResultBO<LotteryIssueBaseBO> result = lotteryIssueService.findLotteryIssueBase(getLottery(), getPlatform().getValue());
		LotteryIssueBaseBO data = result.getData();
		ActivityBO activityBO = iActivityService.findActivityAddAwardInfo(getLottery(), getHeaderParam(request).getChannelId(), (int)getPlatform().getValue()).getData();
		data.setActivityBO(activityBO);
		return result;
	}

	/**
	 * @desc PC端接口：大乐透：查询最近开奖详情列表
	 * @author huangb
	 * @date 2017年3月6日
	 * @return PC端接口：大乐透：查询最近开奖详情列表
	 */
	@RequestMapping(value = "/recent/drawissue", method = RequestMethod.GET)
	public Object findRecentDrawDetail() {
		logger.debug("PC端接口：大乐透：查询最近开奖详情列表");
		return lotteryIssueService.findRecentDrawIssue(new LotteryVO(getLottery(), NUMConstants.NUM_8));
	}

	/**
	 * @desc PC端接口：大乐透：查询遗漏/冷热（统计期数内出现最多或最少的号码）/概率（出号概率=号码当前遗漏值/号码历史平均遗漏值）数据
	 * @author huangb
	 * @date 2017年3月6日
	 * @return PC端接口：大乐透：查询遗漏/冷热（统计期数内出现最多或最少的号码）/概率（出号概率=号码当前遗漏值/号码历史平均遗漏值）数据
	 * @throws Exception
	 */
	@RequestMapping(value = "/omit", method = RequestMethod.GET)
	public Object findOmitChanceColdHot(LotteryVO param) throws Exception {
		logger.debug("PC端接口：大乐透：查询遗漏/冷热/概率数据");
		param.setLotteryCode(getLottery());
		return trendService.findOmitChanceColdHot(param);
	}
	
}
