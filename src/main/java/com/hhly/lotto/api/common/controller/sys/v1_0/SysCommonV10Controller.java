
package com.hhly.lotto.api.common.controller.sys.v1_0;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.commoncore.remote.dicdata.service.IDicDataDetailService;
import com.hhly.lotto.api.data.sys.v1_0.SysV10Data;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.model.DicDataEnum;
import com.hhly.skeleton.lotto.base.dic.bo.DicDataDetailBO;
import com.hhly.skeleton.lotto.base.dic.vo.DicDataDetailVO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2017年12月21日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class SysCommonV10Controller extends BaseController {

	@Autowired
	protected IDicDataDetailService dicDataDetailService;
	
	@Autowired
	protected SysV10Data sysV10Data;

	@RequestMapping(value = "/agent/adFlag", method = RequestMethod.GET)
	public Object findCustomerTel() {
		DicDataDetailVO dicDataDetailVO = new DicDataDetailVO();
		dicDataDetailVO.setDicCode(DicDataEnum.OPEN_STATUS.getDicCode());
		DicDataDetailBO dicDataDetailBO = dicDataDetailService.findSingle(dicDataDetailVO);
		return ResultBO.ok(dicDataDetailBO.getDicDataValue());
	}
}
