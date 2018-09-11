package com.hhly.lotto.api.h5.order.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.order.service.IOrderAddService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.ChaseEnum.ClientType;
import com.hhly.skeleton.lotto.base.order.vo.OrderAddVO;

/**
 * @desc h5端：追号计划控制器
 * @author huangb
 * @date 2017年3月29日
 * @company 益彩网络
 * @version v1.0
 */
@RestController
@RequestMapping("/h5/chase")
public class ChaseH5Controller extends BaseController {

	private static Logger logger = Logger.getLogger(ChaseH5Controller.class);
	/**
	 * 远程服务:追号服务(包含追号)
	 */
	@Autowired
	private IOrderAddService orderAddService;

	/**
	 * @desc h5端接口：追号计划
	 * @author huangb
	 * @date 2017年3月29日
	 * @return h5端接口：追号计划
	 */
	@RequestMapping(value = "/addChase", method = RequestMethod.POST)
	//@AccessRequired(required = true)
	public ResultBO<?> addChase(@RequestBody OrderAddVO orderAdd) {
		logger.debug("h5端接口：追号计划");
		// 正常页面的追号下单，不会有活动id
		orderAdd.setActivityId(null);
		orderAdd.setSource(ClientType.H5.getValue()); // pc与移动端验证参数有所区别
		return orderAddService.userChase(orderAdd);
	}
}
