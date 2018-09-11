package com.hhly.lotto.api.common.controller.num;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottoactivity.remote.service.IActivityService;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.lottocore.remote.trend.service.INumTrendService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryTrendVO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;

/**
 * @desc 数字彩(低频+高频)顶层抽象控制
 * @author huangb
 * @date 2017年10月21日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class NumV10Controller extends BaseController {


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
	 * 远程服务：活动相关接口
	 */
	@Autowired
	protected IActivityService iActivityService;
	
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
	public Object findChaseIssue(@RequestParam(value = "curIssue", required = false) String curIssue,
			@RequestParam(value = "issueCount", required = false) Integer issueCount) {
		logger.debug("{}：查询追号期数",getLottery());
		return lotteryIssueService.findChaseIssue(getLottery(), curIssue, issueCount);
	}
	
	@RequestMapping(value = "/baseTrend", method = RequestMethod.GET)
	public ResultBO<List<TrendBaseBO>> findBaseTrend(LotteryTrendVO vo) throws Exception{
		logger.debug("{}接口：{}：查询走势投注列表",getPlatform().getDesc(),getLotName());
		Assert.notNull(vo);
		vo.setLotteryCode(getLottery());
		//默认30期
		if(vo.getQryCount()==null){
			vo.setQryCount(Constants.NUM_30);
		}
		return trendService.findBaseTrend(vo);
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
	@RequestMapping(value = "/drawColdHotOmit", method = RequestMethod.GET)
	public Object findDrawColdHotOmit(LotteryVO param) throws Exception {
		logger.debug("开奖信息{}接口：{}:查询冷热数据",getPlatform().getDesc(),getLotName());
		return null;
	}
}
