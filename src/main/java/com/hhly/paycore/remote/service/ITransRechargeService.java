package com.hhly.paycore.remote.service;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.pay.vo.TransParamVO;
import com.hhly.skeleton.pay.vo.TransRechargeVO;

public interface ITransRechargeService {

	/**  
	* 方法说明: 根据充值流水号查找用户充值记录详情
	* @param userId 用户Id
	* @param rechargeCode 充值流水号
	* @time: 2017年3月2日 下午12:25:27
	* @return: ResultBO<?> 
	* @throws Exception 
	*/
	public ResultBO<?> findRechargeByCode(String token, String rechargeCode) throws Exception;

	/**  
	* 方法说明: 根据订单号、状态、平台获取用户充值记录
	* @auth: xiongjingang
	* @param transRecharge
	* @time: 2017年3月20日 上午10:48:23
	* @return: ResultBO<?> 
	*/
	public ResultBO<?> findRechargeByOrderCode(TransRechargeVO transRecharge);

	/**  
	* 方法说明: 分页查找充值列表
	* @param transParamVO 交易列表查询
	* @throws Exception
	* @time: 2017年3月7日 上午10:47:30
	* @return: ResultBO<?> 
	*/
	public ResultBO<?> findRechargeListByPage(TransParamVO transParamVO) throws Exception;
}
