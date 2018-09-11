package com.hhly.lottoactivity.remote.service;

import java.util.List;

import com.hhly.skeleton.activity.bo.AwardRollBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityAwardBO;
import com.hhly.skeleton.lotto.base.operate.bo.PrizeAwardBO;

/**
 * @desc 抽奖活动接口
 * @author lidecheng
 * @date 2017年10月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IActivityAwardService {
	/**
	 * 查询抽奖活动轮播信息
	 * @param activityCode
	 * @return
	 */
	ResultBO<List<AwardRollBO>> findAwardRollInfo(String activityCode);
	/**
	 * 查询抽奖活动
	 * @param activityCode
	 * @return
	 */
	ResultBO<List<OperateActivityAwardBO>> findAwardInfo(String activityCode);
	
	/**
	 * 执行抽奖
	 * @param activityCode
	 * @return
	 */
	ResultBO<PrizeAwardBO> excuteAwardWinInfo(String activityCode,String channelId,String token);
}
