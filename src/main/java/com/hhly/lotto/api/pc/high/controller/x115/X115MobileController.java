package com.hhly.lotto.api.pc.high.controller.x115;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.HighEnum;
import com.hhly.skeleton.base.common.OmitEnum.HighOmitType;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryIssueBaseBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitBaseBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;
import com.hhly.skeleton.lotto.mobile.num.bo.LotteryIssueBaseMobileBO;

/**
 * @desc    十一选五移动端抽象controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class X115MobileController extends X115Controller {

	private static Logger logger = Logger.getLogger(X115MobileController.class);
	
	/**
	 * @desc   首页信息：查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号
	 * @author Tony Wang
	 * @create 2017年3月28日
	 * @return 
	 */
	@Override
	public Object info() {
		logger.info("十一选五:查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号");
		// 移动端首页默认返回任选5当前遗漏
		List<HighOmitBaseBO> omitList = c11x5TrendService.findResultOmit(new HighLotteryVO(getLottery(), HighEnum.X115Subplay.RX.getValue(), HighOmitType.CURRENT.getOmitType())).getData().getBaseOmit();
		// 任选5的当前遗漏只有一条记录
//		Assert.isTrue(!CollectionUtils.isEmpty(omitList) && omitList.size()==1);
		if(CollectionUtils.isEmpty(omitList)){
			return ResultBO.err("40713");
		}
		HighOmitBaseBO rx5Omit = omitList.get(0);
		// 把omitType和subPlay设置为null,不返回到前端
		rx5Omit.setOmitType(null);
		rx5Omit.setSubPlay(null);
		LotteryIssueBaseBO issueBase = lotteryIssueService.findLotteryIssueBase(getLottery(), getPlatform().getValue()).getData();
		return new ResultBO<LotteryIssueBaseMobileBO>(new LotteryIssueBaseMobileBO(issueBase, rx5Omit));
	}
	
	@Override
	public Object findLimit(LotteryVO vo) {
		logger.info("十一选五：查询限号");
		Assert.notNull(vo);
		List<Integer> subPlays = vo.getSubPlays();
		Assert.isTrue(!CollectionUtils.isEmpty(subPlays));
		List<Integer> completeSubPlays = new ArrayList<>();
		for(Integer subPlay : subPlays) {
			Assert.isTrue(subPlay != null && subPlay>0);
			// 加前缀0,如2-->"21502"
			Integer x115SubPlay = getLottery() * 100 + subPlay;
			Assert.isTrue(isSubPlay(x115SubPlay));
			completeSubPlays.add(x115SubPlay);
		}
		// 设置完整的子玩法
		vo.setSubPlays(completeSubPlays);
		// 设置彩种
		vo.setLotteryCode(getLottery());
		Map<String, Object> ret = new HashMap<>();
		ret.put("currentDateTime", DateUtil.convertDateToStr(new Date()));
		ret.put("limitInfoList", lotteryIssueService.findLimit(vo).getData());
		return new ResultBO<>(ret);
	}

	@Override
	protected int getIssueCount() {
		return 10;
	}
}
