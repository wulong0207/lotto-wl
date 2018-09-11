package com.hhly.paycore.remote.service;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.pay.vo.TransParamVO;

/**
 * @desc 用户交易接口
 * @author xiongjingang
 * @date 2017年3月3日 上午10:32:26
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ITransUserLogService {
	/**  
	* 方法说明: 根据条件获取用户的交易记录详情
	* @param token
	* @param transCode
	* @throws Exception
	* @time: 2017年3月7日 下午2:40:18
	* @return: ResultBO<?> 
	*/
	ResultBO<?> findTransUserByCode(String token, String transCode) throws Exception;

	/**  
	* 方法说明: 根据条件获取用户的交易记录，默认为交易明细
	* @param transParamVO
	* @time: 2017年3月3日 下午2:15:27
	* @return: ResultBO<?> 
	* @throws Exception 
	*/
	public ResultBO<?> findTransUserByPage(TransParamVO transParamVO) throws Exception;

	/**  
	* 方法说明: 给移动端提供接口
	* @auth: xiongJinGang
	* @param transParamVO
	* @throws Exception
	* @time: 2017年4月26日 下午3:25:18
	* @return: ResultBO<?> 
	*/
	ResultBO<?> findAppTransUserByPage(TransParamVO transParamVO) throws Exception;

}
