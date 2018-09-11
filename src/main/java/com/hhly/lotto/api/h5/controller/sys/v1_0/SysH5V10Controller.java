
package com.hhly.lotto.api.h5.controller.sys.v1_0;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.sys.v1_0.SysCommonV10Controller;

/**
 * @desc    
 * @author  cheng chen
 * @date    2017年12月21日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/h5/v1.0/sys")
public class SysH5V10Controller extends SysCommonV10Controller {
	
	@RequestMapping(value = "/getWxAcc", method = RequestMethod.GET)
	public Object getWxAcc() {
		return sysV10Data.getWxAcc();
	}
}
