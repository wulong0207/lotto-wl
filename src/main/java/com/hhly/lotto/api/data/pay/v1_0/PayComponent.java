package com.hhly.lotto.api.data.pay.v1_0;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.PayConstants.TakenPlatformEnum;
import com.hhly.skeleton.pay.vo.PayInputParamVO;
import com.hhly.skeleton.pay.vo.PayNotifyMockVO;
import com.hhly.skeleton.pay.vo.PayParamVO;
import com.hhly.skeleton.pay.vo.PayQueryParamVO;
import com.hhly.skeleton.pay.vo.ToPayInputParamVO;

public interface PayComponent {

	/**  
	* 方法说明: 跳转到支付页面
	* @auth: xiongJinGang
	* @param request
	* @param TakenPlatformEnum
	* @time: 2017年5月8日 上午11:03:36
	* @return: ResultBO<?> 
	*/
	ResultBO<?> toPay(HttpServletRequest request, TakenPlatformEnum platform);

	/**  
	* 方法说明: 批量支付
	* @auth: xiongJinGang
	* @param ToPayInputParamVO
	* @param TakenPlatformEnum
	* @time: 2017年5月8日 下午5:52:36
	* @return: ResultBO<?> 
	*/
	ResultBO<?> toBatchPay(ToPayInputParamVO toPayInputParamVO, TakenPlatformEnum platform);

	/**  
	* 方法说明: 查看推单详情支付请求
	* @auth: xiongJinGang
	* @param request
	* @param platform
	* @time: 2018年1月2日 下午4:47:10
	* @return: ResultBO<?> 
	*/
	ResultBO<?> toPushPay(HttpServletRequest request, TakenPlatformEnum platform);

	/**  
	* 方法说明: 判断是否为测试环境 true是,false否
	* @auth: xiongJinGang
	* @time: 2017年4月13日 上午11:59:32
	* @return: boolean 
	*/
	boolean isTestProject();

	/**  
	* 方法说明: 订单支付
	* @auth: xiongJinGang
	* @param request
	* @param payInputParam
	* @param clientIp
	* @param TakenPlatformEnum
	* @time: 2017年5月8日 上午11:15:00
	* @return: ResultBO<?> 
	*/
	ResultBO<?> pay(PayInputParamVO payInputParam, String clientIp, TakenPlatformEnum platform);

	/**  
	* 方法说明: 模拟支付回调，只有在测试环境才可以使用【目前只对测试环境开放】
	* @auth: xiongJinGang
	* @param payNotifyMockVO
	* @time: 2017年6月27日 下午3:34:13
	* @return: ResultBO<?> 
	*/
	ResultBO<?> payNotifyMock(PayNotifyMockVO payNotifyMockVO);

	/**  
	* 方法说明: 申请退款【目前只对测试环境开放】
	* @auth: xiongJinGang
	* @param request
	* @time: 2017年10月16日 下午5:12:15
	* @return: ResultBO<?> 
	*/
	ResultBO<?> insideRefund(HttpServletRequest request);

	/**  
	* 方法说明: 交易记录查询接口【目前只对测试环境开放】
	* @auth: xiongJinGang
	* @param payQueryParamVO
	* @param request
	* @time: 2017年11月4日 上午10:31:38
	* @return: ResultBO<?> 
	*/
	ResultBO<?> payQuery(PayQueryParamVO payQueryParamVO, HttpServletRequest request);

	/**  
	* 方法说明: 退款查询接口【目前只对测试环境开放】
	* @auth: xiongJinGang
	* @param payQueryParamVO
	* @param request
	* @time: 2017年11月4日 上午10:56:50
	* @return: ResultBO<?> 
	*/
	ResultBO<?> refundQuery(PayQueryParamVO payQueryParamVO, HttpServletRequest request);

	/**  
	* 方法说明: 支付结果
	* @auth: xiongJinGang
	* @param payParam
	* @time: 2017年12月7日 下午6:34:56
	* @return: ResultBO<?> 
	*/
	ResultBO<?> payResult(PayParamVO payParam);

	/**  
	* 方法说明: 支付异步通知
	* @auth: xiongJinGang
	* @param notifyMap
	* @time: 2017年12月7日 下午6:35:45
	* @return: ResultBO<?> 
	*/
	ResultBO<?> payNotify(Map<String, String> notifyMap);

	/**  
	* 方法说明: 退款异步通知
	* @auth: xiongJinGang
	* @param notifyMap
	* @time: 2017年12月7日 下午6:36:26
	* @return: ResultBO<?> 
	*/
	ResultBO<?> refundNotify(Map<String, String> notifyMap);

	/**  
	* 方法说明: 推单支付
	* @auth: xiongJinGang
	* @param payInputParam
	* @param clientIp
	* @param platform
	* @time: 2018年1月11日 下午4:25:59
	* @return: ResultBO<?> 
	*/
	ResultBO<?> pushPay(PayInputParamVO payInputParam, String clientIp, TakenPlatformEnum platform);
}
