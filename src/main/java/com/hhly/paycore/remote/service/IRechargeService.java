package com.hhly.paycore.remote.service;

import java.util.Map;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.pay.vo.AgentPayVO;
import com.hhly.skeleton.pay.vo.CmsRechargeVO;
import com.hhly.skeleton.pay.vo.PayNotifyMockVO;
import com.hhly.skeleton.pay.vo.RechargeParamVO;

/**
 * @desc 充值统一接口
 * @author xiongJinGang
 * @date 2017年4月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IRechargeService {

	/**  
	* 方法说明: 充值
	* @auth: xiongJinGang
	* @param rechargeParam
	* @time: 2017年4月8日 下午3:13:48
	* @return: ResultBO<?> 
	*/
	public ResultBO<?> recharge(RechargeParamVO rechargeParam);

	/**  
	* 方法说明: 充值结果查询
	* @auth: xiongJinGang
	* @param rechargeParam
	* @time: 2017年5月23日 下午9:14:33
	* @return: ResultBO<?> 
	*/
	public ResultBO<?> rechargeResult(RechargeParamVO rechargeParam);

	/**  
	* 方法说明: 充值同步回调
	* @auth: xiongJinGang
	* @param params
	* @time: 2017年4月8日 下午3:14:06
	* @return: ResultBO<?> 
	*/
	public ResultBO<?> rechargeReturn(Map<String, String> params);

	/**  
	* 方法说明: 充值异步回调
	* @auth: xiongJinGang
	* @param params
	* @param Exception
	* @time: 2017年4月8日 下午3:14:08
	* @return: ResultBO<?> 
	*/
	public ResultBO<?> rechargeNotify(Map<String, String> params) throws Exception;

	/**  
	* 方法说明: 模拟充值回调
	* @auth: xiongJinGang
	* @param payNotifyMockVO
	* @throws Exception
	* @time: 2017年5月25日 下午5:42:06
	* @return: ResultBO<?> 
	*/
	ResultBO<?> rechargeNotifyMock(PayNotifyMockVO payNotifyMockVO) throws Exception;
	
	/**  
	* 方法说明: 提供给CMS人工充值（充值现金、充值红包）
	* @auth: xiongJinGang
	* @param cmsRecharge
	* @time: 2017年7月6日 下午5:26:47
	* @return: ResultBO<?> 
	*/
	ResultBO<?> updateRecharge(CmsRechargeVO cmsRecharge);
	/**
	 * 
	 * @Description: 聚合充值异步回调
	 * @param params
	 * @return  ResultBO<?> 
	 * @throws Exception
	 * @author wuLong
	 * @date 2017年7月25日 上午9:21:54
	 */
	public ResultBO<?> juheRechargeNotify(Map<String, String> params) throws Exception;
	
	/**
	 * 代理系统充值
	 * @param agentPayVO
	 * @return
	 * @throws Exception
	 */
	public ResultBO<?> agentRecharge(AgentPayVO agentPayVO) throws Exception;
}
