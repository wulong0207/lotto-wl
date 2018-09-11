
package com.hhly.lotto.api.common.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.user.vo.UserWinInfoVO;
import com.hhly.usercore.remote.member.service.IMemberWinningService;

/**
 * @desc
 * @author cheng chen
 * @date 2017年9月23日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/common/user")
public class UserCommonController extends BaseController {

	@Autowired
	IMemberWinningService memberWinningService;

	@RequestMapping(value = "queryUserWinByLottery", method = RequestMethod.POST)
	public Object queryUserWinByLottery(UserWinInfoVO vo) {
	   	Assert.paramNotNull(vo.getLotteryCode(), "lotteryCode");
	   	
	   	if(ObjectUtil.isBlank(vo.getPageIndex()))
	   		vo.setPageIndex(0);
	   	if(ObjectUtil.isBlank(vo.getPageSize()))
	   		vo.setPageSize(6);	   	
	   	return ResultBO.ok(memberWinningService.queryUserWinByLottery(vo));
	}
}
