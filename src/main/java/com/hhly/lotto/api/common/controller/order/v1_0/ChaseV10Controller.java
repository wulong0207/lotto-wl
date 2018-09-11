package com.hhly.lotto.api.common.controller.order.v1_0;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.order.service.IOrderAddService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.ChaseEnum.ClientType;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;
import com.hhly.skeleton.lotto.base.order.vo.OrderAddVO;

/**
 * @desc 追号计划公共控制器
 * @author huangb
 * @date 2017年12月12日
 * @company 益彩网络
 * @version v1.0
 */
public abstract class ChaseV10Controller extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(ChaseV10Controller.class);
	/**
	 * 远程服务:追号服务(包含追号)
	 */
	@Autowired
	protected IOrderAddService orderAddService;

	/**
	 * @desc 获取api接口调用对应的平台类型
	 * @author huangb
	 * @date 2017年12月12日
	 * @return 获取api接口调用对应的平台类型
	 */
	protected abstract PlatformType getPlatform() ;
	
	/**
	 * @desc 获取api接口调用对应的客户端类型  (跟平台类型是一个意思；历史原因，先保留，后续优化后再删除该方法)
	 * @author huangb
	 * @date 2017年12月12日
	 * @return 获取api接口调用对应的客户端类型  (跟平台类型是一个意思；历史原因，先保留，后续优化后再删除该方法)
	 */
	protected abstract ClientType getClientType() ;
	
	/**
	 * @desc PC端接口：追号计划
	 * @author huangb
	 * @date 2017年3月29日
	 * @return PC端接口：追号计划
	 */
	@RequestMapping(value = "/addChase", method = RequestMethod.POST)
	public ResultBO<?> addChase(@RequestBody OrderAddVO orderAdd) {
		logger.debug("{}接口：追号计划",getPlatform().getDesc());
		// 正常页面的追号下单，不会有活动id
		orderAdd.setActivityId(null);
		// pc与移动端验证参数有所区别
		orderAdd.setSource(getClientType().getValue());
		return orderAddService.userChase(orderAdd);
	}
	
//	@RequestMapping(value = "/addChaseWithOutVerify", method = RequestMethod.POST)
//	public ResultBO<?> addChaseWithOutVerify(@RequestBody OrderAddVO orderAdd) {
//		logger.debug("{}接口：追号计划",getPlatform().getDesc());
//		// 正常页面的追号下单，不会有活动id
//		orderAdd.setActivityId(null);
//		// pc与移动端验证参数有所区别
//		orderAdd.setSource(getClientType().getValue());
//		return orderAddService.userChaseWithOutVerify(orderAdd);
//	}
}
