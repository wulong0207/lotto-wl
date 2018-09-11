
package com.hhly.lotto.api.data.sys.v1_0;

import java.util.Map;

import com.hhly.skeleton.base.bo.ResultBO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2018年1月12日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface SysV10Data {

	/**
	 * 查询H5公众号appid和secret权限管理
	 * @return
	 * @date 2018年1月12日下午4:16:02
	 * @author cheng.chen
	 */
	Map<String, String> findWxAccList();
	
	/**
	 * 查询H5公众号appid和secret权限管理
	 * @return
	 * @date 2018年1月12日下午4:16:02
	 * @author cheng.chen
	 */
	ResultBO<?> getWxAcc();
}
