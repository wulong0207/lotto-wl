package com.hhly.lotto.api.common.controller.high.k3;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.skeleton.lotto.base.trend.vo.high.K3OmitVO;

/**
 * @desc    PC端快3controller抽象
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class K3PcV10Controller extends K3V10Controller {

	
	@Override
	protected int getIssueCount() {
		// 查询最近开奖详情时PC端查询20期
		return 20;
	}

	@Override
	@RequestMapping(value = "/issue/recent", method = RequestMethod.GET)
	public Object findRecentDrawDetail() {
		logger.debug("高频彩{}:查询最近开奖详情列表", getLottery());
		K3OmitVO vo = new K3OmitVO();
		vo.setLotteryCode(getLottery());
		vo.setShowDrawCode(true);
		vo.setShowIssue(true);
		vo.setType(true);
		vo.setBs(true);
		vo.setQryCount(getIssueCount());
		return highLotteryService2.findRecentIssue(vo);
	}
}
