package com.hhly.lotto.api.android.order.controller;

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
 * @desc Android端：追号计划控制器
 * @author huangb
 * @date 2017年3月29日
 * @company 益彩网络
 * @version v1.0
 */
@RestController
@RequestMapping("/android/chase")
public class ChaseAndrController extends BaseController {

	private static Logger logger = Logger.getLogger(ChaseAndrController.class);
	/**
	 * 远程服务:追号服务(包含追号)
	 */
	@Autowired
	private IOrderAddService orderAddService;

	/**
	 * @desc Android端接口：追号计划
	 * @author huangb
	 * @date 2017年3月29日
	 * @return PC端接口：追号计划
	 */
	@RequestMapping(value = "/addChase", method = RequestMethod.POST)
	//@AccessRequired(required = true)
	public ResultBO<?> addChase(@RequestBody OrderAddVO orderAdd) {
		logger.debug("Android端接口：追号计划");
		// 正常页面的追号下单，不会有活动id
		orderAdd.setActivityId(null);
		orderAdd.setSource(ClientType.ANDROID.getValue()); // pc与移动端验证参数有所区别
		return orderAddService.userChase(orderAdd);
	}
}
