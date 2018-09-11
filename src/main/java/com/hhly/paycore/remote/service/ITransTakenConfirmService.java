package com.hhly.paycore.remote.service;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.pay.vo.TakenReqParamVO;
import com.hhly.skeleton.pay.vo.TransParamVO;

/**
 * @desc 提款交易服务接口
 * @author xiongjingang
 * @date 2017年3月2日 上午10:44:46
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ITransTakenConfirmService {

	/**    
	* 方法说明： 根据交易流水号查找用户的交易详情
	* @param userId 用户ID
	* @param transCode 交易流水号
	* @time: 2017年3月2日 下午12:17:44
	* @return: ResultBO<?> 
	* @throws Exception 
	*/
	public ResultBO<?> findTakenByCode(String token, String transCode) throws Exception;

	/**  
	* 方法说明: 分页查询提款记录
	* @param transParamVO
	* @throws Exception
	* @time: 2017年3月7日 上午11:49:29
	* @return: ResultBO<?> 
	*/
	public ResultBO<?> findTakenListByPage(TransParamVO transParamVO) throws Exception;

	/**  
	* 方法说明: 用户提款操作
	* @auth: xiongJinGang
	* @param takenValidateTypeVO
	* @throws Exception
	* @time: 2017年4月19日 上午10:24:22
	* @return: ResultBO<?> 
	*/
	public ResultBO<?> taken(TakenReqParamVO takenReqParamVO) throws Exception;

	/**  
	* 方法说明: 提款确认
	* @auth: xiongJinGang
	* @param takenReqParamVO
	* @throws Exception
	* @time: 2017年4月21日 下午12:17:57
	* @return: ResultBO<?> 
	*/
	public ResultBO<?> takenConfirm(TakenReqParamVO takenReqParamVO) throws Exception;

	/**  
	* 方法说明: 移动端提款请求
	* @auth: xiongJinGang
	* @param token
	* @throws Exception
	* @time: 2017年5月4日 下午3:50:18
	* @return: ResultBO<?> 
	*/
	ResultBO<?> takenReqForApp(String token) throws Exception;

	/**  
	* 方法说明: 提款前，用户输入提款金额后异步加载提示
	* @auth: xiongJinGang
	* @param takenReqParamVO
	* @throws Exception
	* @time: 2017年8月22日 下午6:28:26
	* @return: ResultBO<?> 
	*/
	ResultBO<?> takenCount(TakenReqParamVO takenReqParamVO) throws Exception;

	/**  
	* 方法说明: 验证用户提款次数是否超过当日最高提款次数，如有超过，返回正在处理中的所有提款记录
	* @auth: xiongJinGang
	* @param takenReqParamVO
	* @throws Exception
	* @time: 2017年11月4日 下午2:57:28
	* @return: ResultBO<?> 
	*/
	ResultBO<?> validateTakenCount(TakenReqParamVO takenReqParamVO) throws Exception;
}
