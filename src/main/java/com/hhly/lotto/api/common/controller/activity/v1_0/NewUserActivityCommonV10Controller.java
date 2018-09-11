package com.hhly.lotto.api.common.controller.activity.v1_0;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.common.HeaderParam;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.lottoactivity.remote.service.IActivityNewUserService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.activity.bo.ActivityNewUserBO;
import com.hhly.skeleton.lotto.base.activity.vo.ActivityNewUserVO;

/**
 * 新用户活动
 *
 * @author huangchengfang1219
 * @date 2018年1月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class NewUserActivityCommonV10Controller extends BaseController {

	@SuppressWarnings("unused")
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	private IActivityNewUserService activityNewUserService;

	/**
	 * 活动基本信息
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/info/base", method = RequestMethod.POST)
	public ResultBO<?> findActivityBaseInfo(@RequestBody ActivityNewUserVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getActivityCode(), "activityCode");
		ActivityNewUserBO newUserBO = activityNewUserService.findActivityBaseInfo(vo);
		return ResultBO.ok(newUserBO);
	}

	/**
	 * 领取
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/take", method = RequestMethod.POST)
	public ResultBO<?> take(@RequestBody ActivityNewUserVO vo, HttpServletRequest request) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getActivityCode(), "activityCode");
		Assert.paramNotNull(vo.getToken(), "token");
		HeaderParam headerParam = getHeaderParam(request);
		vo.setPlatform(headerParam.getPlatformId().shortValue());
		vo.setChannelId(headerParam.getChannelId());
		ActivityNewUserBO newUserBO = activityNewUserService.doTake(vo);
		return ResultBO.ok(newUserBO);
	}

	/**
	 * 下单
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/order/add", method = RequestMethod.POST)
	public ResultBO<?> addOrder(@RequestBody ActivityNewUserVO vo, HttpServletRequest request) throws Exception {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getActivityCode(), "activityCode");
		Assert.paramNotNull(vo.getToken(), "token");
		Assert.paramNotNull(vo.getPlanContent(), "planContent");
		HeaderParam headerParam = getHeaderParam(request);
		vo.setPlatform(headerParam.getPlatformId().shortValue());
		vo.setChannelId(headerParam.getChannelId());
		ActivityNewUserBO newUserBO = activityNewUserService.addOrder(vo);
		return ResultBO.ok(newUserBO);
	}

}
