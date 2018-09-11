package com.hhly.lottocore.remote.order.service;

import java.util.List;
import java.util.Map;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.singleupload.bo.SingleUploadLogBO;
import com.hhly.skeleton.lotto.base.singleupload.vo.SingleUploadJCVO;
import com.hhly.skeleton.lotto.base.singleupload.vo.SingleUploadLogVO;

public interface ISingleOrderService {
	/**
	 * @param orderInfo
	 *            订单信息
	 * @return
	 * @throws Exception
	 * @Desc 添加订单信息
	 */
	ResultBO<?> validateOrder(String originalFilename, SingleUploadJCVO singleUploadJCVO, Map<String,Object> map);
	/**
	 * 通过用户编号，获取第二次上传生成文件名
	 * @author longguoyou
	 * @date 2017年6月27日
	 * @param userId
	 * @return
	 */
	String getRedisFileName(Integer userId);
	
	/**
	 * 查询单式上传日志信息
	 * @author longguoyou
	 * @date 2017年6月28日
	 * @param singleUploadLogVO
	 * @return
	 */
	List<SingleUploadLogBO> findSingleUploadLogInfo(SingleUploadLogVO singleUploadLogVO);
}
