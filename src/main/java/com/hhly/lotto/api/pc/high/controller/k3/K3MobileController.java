package com.hhly.lotto.api.pc.high.controller.k3;

import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OmitEnum.HighOmitType;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryIssueBaseBO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitBaseBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.K3OmitVO;
import com.hhly.skeleton.lotto.mobile.num.bo.LotteryIssueBaseMobileBO;

/**
 * @desc    移动端快3controller抽象
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class K3MobileController extends K3Controller {

	
	private static Logger logger = Logger.getLogger(K3MobileController.class);
	
	protected abstract Integer getLottery();
	
	@Override
	protected int getIssueCount() {
		// 查询最近开奖详情时移动端查询10期
		return 10;
	}

	@Override
	@RequestMapping(value = "/issue/recent", method = RequestMethod.GET)
	public Object findRecentDrawDetail() {
		logger.debug(MessageFormat.format("高频彩{0}:查询最近开奖详情列表", getLottery()));
		K3OmitVO vo = new K3OmitVO();
		vo.setLotteryCode(getLottery());
		vo.setShowDrawCode(true);
		vo.setShowIssue(true);
		vo.setType(true);
		vo.setShowS(true);
		vo.setQryCount(10);
		return highLotteryService2.findRecentIssue(vo);
	}
	
	@Override
	public Object info() {
		MessageFormat.format("高频彩{0}:查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号", getLottery());
		// 移动端首页默认显示和值遗漏数据(当前遗漏)
		// 和值1
		K3OmitVO vo = new K3OmitVO(getLottery(), 1, HighOmitType.CURRENT.getOmitType());
		vo.showSum(true);
		List<HighOmitBaseBO> omitList = highLotteryService2.findResultOmit(vo).getData().getBaseOmit();
		// 和值遗漏数据的当前遗漏只有一条记录
//		Assert.isTrue(!CollectionUtils.isEmpty(omitList) && omitList.size()==1);
		if(CollectionUtils.isEmpty(omitList)){
			return	ResultBO.err("40713");
		}
		HighOmitBaseBO omit = omitList.get(0);
		// 把omitType和subPlay设置为null,不返回到前端
		omit.setOmitType(null);
		omit.setSubPlay(null);
		LotteryIssueBaseBO issueBase = lotteryIssueService.findLotteryIssueBase(getLottery(), getPlatform().getValue()).getData();
		return new ResultBO<LotteryIssueBaseMobileBO>(new LotteryIssueBaseMobileBO(issueBase, omit));
	}
}
