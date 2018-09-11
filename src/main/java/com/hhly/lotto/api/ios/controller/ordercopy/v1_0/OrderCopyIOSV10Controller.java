package com.hhly.lotto.api.ios.controller.ordercopy.v1_0;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.ordercopy.v1_0.OrderCopyCommonV10Controller;
import com.hhly.lotto.api.data.copyorder.v1_0.OrderCopyData;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.ordercopy.bo.CommissionBO;
import com.hhly.skeleton.lotto.base.ordercopy.bo.QueryBO;
import com.hhly.skeleton.lotto.base.ordercopy.bo.QueryUserIssueInfoBO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.QueryVO;

/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2017/9/28.
 * @company 益彩网络科技有限公司
 */
@RestController
@RequestMapping("/ios/v1.0/order-copy")
public class OrderCopyIOSV10Controller extends OrderCopyCommonV10Controller {

	@Autowired
	private OrderCopyData orderCopyData;

	@SuppressWarnings("unchecked")
	@Override
	public ResultBO<?> queryIssueInfo(@RequestBody QueryVO queryVO) throws Exception {
		List<QueryBO> listQueryBO = (List<QueryBO>) super.queryIssueInfo(queryVO).getData();
		orderCopyData.handleIssueInfo(listQueryBO);
		return ResultBO.ok(listQueryBO);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultBO<?> queryUserIssueInfo(@RequestBody QueryVO queryVO) throws Exception {
		List<QueryUserIssueInfoBO> list = (List<QueryUserIssueInfoBO>)super.queryUserIssueInfo(queryVO).getData();
		orderCopyData.handleIssueUserInfo(list);
		return ResultBO.ok(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultBO<?> listCommissions(@RequestBody QueryVO queryVO) throws Exception {
		List<CommissionBO> list = (List<CommissionBO>)super.listCommissions(queryVO).getData();
		orderCopyData.handleCommissions(list);
		return ResultBO.ok(list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ResultBO<?> listCommissionDetails(@RequestBody QueryVO queryVO)throws Exception{
		List<CommissionBO> list = (List<CommissionBO>)super.listCommissionDetails(queryVO).getData();
		orderCopyData.handleCommissionDetails(list);
		return ResultBO.ok(list);
	}
}
