package com.hhly.paycore.remote.service;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.pay.bo.TransRedBO;
import com.hhly.skeleton.pay.vo.TransRedVO;

/**
 * @desc 红包交易记录service
 * @author xiongJinGang
 * @date 2017年3月24日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ITransRedService {

	/**  
	* 方法说明: 添加红包交易记录
	* @auth: xiongJinGang
	* @param record
	* @time: 2017年3月24日 上午11:42:20
	* @return: int 
	*/
	int addTransRed(TransRedBO record);

	/**  
	* 方法说明: 根据红包code获取用户红包交易记录 
	* @auth: xiongJinGang
	* @param token 用户登录token
	* @param redCode 红包code
	* @time: 2017年3月24日 上午11:42:32
	* @return: ResultBO<?
	*/
	ResultBO<?> findUserTransRedByCode(String token, Integer redCode);
	
	/** 
	* @Title: findUserTransRedByPage 
	* @Description: 用户红包交易分页查询
	*  @param vo
	*  @return
	* @time 2017年5月6日 下午4:33:27
	*/
	 ResultBO<?> findUserTransRedByPage(TransRedVO vo);

}
