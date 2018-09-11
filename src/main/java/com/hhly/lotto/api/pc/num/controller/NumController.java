package com.hhly.lotto.api.pc.num.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.lottocore.remote.trend.service.INumTrendService;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;

/**
 * @desc 数字彩(低频+高频)顶层抽象控制
 * @author huangb
 * @date 2017年10月21日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class NumController extends BaseController {

	//private static Logger logger = Logger.getLogger(NumController.class);

	/**
	 * 远程服务:彩期服务
	 */
	@Autowired
	protected ILotteryIssueService lotteryIssueService;
	/**
	 * 远程服务：遗漏走势
	 */
	@Autowired
	@Qualifier("iNumTrendService")
	protected INumTrendService trendService;
	
	/**
	 * @desc 当前彩种
	 * @author huangb
	 * @date 2017年10月21日
	 * @return
	 */
	protected abstract Integer getLottery();

	/**
	 * @desc 当前彩种名
	 * @author huangb
	 * @date 2017年10月21日
	 * @return
	 */
	protected String getLotName() {
		return null;
	}

	/******************** added to 20171109 彩种发布平台限制，前端接口兼容性处理 （后端统一处理彩种限制状态） **********************/
	/**
	 * @desc 获取api接口调用对应的平台
	 * @author huangb
	 * @date 2017年11月9日
	 * @return 获取api接口调用对应的平台
	 */
	protected PlatformType getPlatform() {
		return PlatformType.WEB;
	}
	
	@RequestMapping(value = "/getChaseIssue", method = RequestMethod.GET)
	@ResponseBody
	public Object findChaseIssue(@RequestParam(value = "curIssue", required = false) String curIssue,
			@RequestParam(value = "issueCount", required = false) Integer issueCount) {
		return lotteryIssueService.findChaseIssue(getLottery(), curIssue, issueCount);
	}
	
}
