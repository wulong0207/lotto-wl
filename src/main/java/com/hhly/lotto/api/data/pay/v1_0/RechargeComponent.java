package com.hhly.lotto.api.data.pay.v1_0;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.PayConstants.TakenPlatformEnum;
import com.hhly.skeleton.pay.vo.PayNotifyMockVO;
import com.hhly.skeleton.pay.vo.RechargeInputParamVO;
import com.hhly.skeleton.pay.vo.RechargeParamVO;

public interface RechargeComponent {

	/**  
	* 方法说明: 跳转去充值
	* @auth: xiongJinGang
	* @param token
	* @param platform
	* @throws Exception
	* @time: 2017年5月12日 下午12:00:34
	* @return: ResultBO<?> 
	*/
	ResultBO<?> toRecharge(HttpServletRequest request, TakenPlatformEnum platform) throws Exception;

	/**  
	* 方法说明: 获取充值时可用优惠券
	* @auth: xiongJinGang
	* @param rechargeInputParamVO
	* @time: 2017年5月18日 上午10:00:01
	* @return: Result<?> 
	* @throws Exception 
	*/
	ResultBO<?> findRechargeRed(RechargeInputParamVO rechargeInputParamVO) throws Exception;

	/**  
	* 方法说明: 统一充值
	* @auth: xiongJinGang
	* @param rechargeInputParam
	* @param clientIp
	* @param platform
	* @time: 2017年5月12日 下午12:03:11
	* @return: ResultBO<?> 
	*/
	ResultBO<?> recharge(RechargeInputParamVO rechargeInputParam, String clientIp, TakenPlatformEnum platform);

	/**  
	* 方法说明: 测试环境模拟充值回调
	* @auth: xiongJinGang
	* @param payNotifyMockVO
	* @throws Exception
	* @time: 2017年6月27日 下午3:38:22
	* @return: ResultBO<?> 
	*/
	ResultBO<?> rechargeNotifyMock(PayNotifyMockVO payNotifyMockVO) throws Exception;

	/**  
	* 方法说明: 充值结果
	* @auth: xiongJinGang
	* @param rechargeParam
	* @time: 2017年12月7日 下午6:41:41
	* @return: ResultBO<?> 
	*/
	ResultBO<?> rechargeResult(RechargeParamVO rechargeParam) throws Exception;

	/**  
	* 方法说明: 充值异步通知
	* @auth: xiongJinGang
	* @param notifyMap
	* @time: 2017年12月7日 下午6:35:45
	* @return: ResultBO<?> 
	* @throws Exception 
	*/
	ResultBO<?> rechargeNotify(Map<String, String> notifyMap) throws Exception;

}
