package com.hhly.lottoactivity.remote.service;

import java.util.List;

import com.hhly.skeleton.activity.bo.ActivityAnnualMeetMsgBO;
import com.hhly.skeleton.activity.bo.ActivityBO;
import com.hhly.skeleton.activity.bo.FirstRecSendBO;
import com.hhly.skeleton.activity.bo.YfgcActivityBO;
import com.hhly.skeleton.base.bo.ResultBO;

/**
 * @author yuanshangbing
 * @version 1.0
 * @desc 活动服务接口
 * @date 2017/8/8 16:59
 * @company 益彩网络科技公司
 */
public interface IActivityService {

    /**
     * 获取竟足/竟篮首投立减活动信息
     * @return
     */
    ResultBO<?> findJzstActivityInfo(String activityCode);
    /**
     * 获取首冲活动信息
     * @param token
     * @return
     */
    ResultBO<FirstRecSendBO> getFirstRecSend(String token,String channelId);
    
	/**
	 * @desc   查询1分钱购彩活动信息
	 * @author Tony Wang
	 * @create 2017年8月11日
	 * @return 
	 */
	ResultBO<YfgcActivityBO> findYfgcActivityInfo(int lotteryCode);
	/**
     * @desc   查询活动是否允许彻单
     * @author lidecheng
     * @create 2017年11月15日
     * @return 
     * @throws Exception 
     */
	ResultBO<Integer> findCancelActivity(String activityCode);
	
	/**
     * @desc   查询年会活动信息，查询时间段当天的起始时间00：00：00和当天的结束时间23：59：59
     * @author lidecheng
     * @create 2017年11月15日
     * @return 
     * @throws Exception 
     */
	ResultBO<List<ActivityAnnualMeetMsgBO>> findAnnualMeetCountDown(String token);
	
	/**
     * @desc   查询（本站，官方）加奖信息
     * @param  lotteryCode 彩种编号
     * @param  channelId 渠道号
     * @param  platform 平台
     * @author lidecheng
     * @create 2018年03月13日
     * @return 
     */
	ResultBO<ActivityBO> findActivityAddAwardInfo(Integer lotteryCode,String channelId,Integer platform);
}
