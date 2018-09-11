package com.hhly.lotto.api.pc.high.controller.poker;

import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.hhly.lotto.api.pc.high.controller.HighController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OmitEnum;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.PokerOmitVO;

/**
 * @desc    快乐扑克抽象controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class PokerController extends HighController {

	private static Logger logger = Logger.getLogger(PokerController.class);

	/**
	 * @desc   PC跟移动端返回的遗漏字段一样，统一实现
	 * @author Tony Wang
	 * @create 2017年6月28日
	 * @param vo
	 * @return
	 */
	@Override
	protected <T extends HighLotteryVO> ResultBO<HighOmitDataBO> findOmit(T omitVO) {
		logger.debug("快乐扑克：查询遗漏");
		// 查询所有子玩法的遗漏
		PokerOmitVO pokerOmitVO = new PokerOmitVO();
		BeanUtils.copyProperties(omitVO, pokerOmitVO);
		// 快乐扑克目前只有查询历史遗漏
		pokerOmitVO.setQryFlag(OmitEnum.QryFlag.NORMAL.getRange());
		Assert.notNull(pokerOmitVO);
		Assert.notEmpty(pokerOmitVO.getOmitTypes());
		Assert.isTrue(OmitEnum.HighOmitType.containsAll(pokerOmitVO.getOmitTypes()));
		//List<Integer> subPlays = pokerOmitVO.getSubPlays();
		// 一次只能查一个子玩法
		//Assert.isTrue(subPlays != null && subPlays.size()==1 && HighConstants.POKER_SUBPLAY.containsAll(subPlays));
		pokerOmitVO.setLotteryCode(getLottery());
		//PokerOmitVO recentVO = new PokerOmitVO();
		//BeanUtils.copyProperties(pokerOmitVO, recentVO);
		pokerOmitVO.showRn(true);
		pokerOmitVO.showTh(true);
		pokerOmitVO.showSz(true);
		pokerOmitVO.showDz(true);
		pokerOmitVO.showBz(true);
		pokerOmitVO.setShowFlag(true);
		return highLotteryService2.findResultOmit(pokerOmitVO);
//		switch (subPlays.get(0)) {
//		// 1任1,2任2,3任3,4任4,5任5,6任6,7同花,8顺子,9对子,10豹子
//		case 1:
//		case 2:
//		case 3:
//		case 4:
//		case 5:
//		case 6:
//			pokerOmitVO.showRn(true);
//			break;
//		case 7:
//			pokerOmitVO.showTh(true);
//			break;
//		case 8:
//			pokerOmitVO.showSz(true);
//			break;
//		case 9:
//			pokerOmitVO.showDz(true);
//			break;
//		case 10:
//			pokerOmitVO.showBz(true);
//			break;
//		default:
//			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.LOTTERY_CHILD_CODE_IS_NULL_FIELD,subPlays.get(0)));
//		}
		// 把近期空数据设置为null，则前端不会显示
		//ret.getData().setRecentOmit(null);
		//return ret;
	}
	
	@Override
	public Object findRecentDrawDetail() {
		logger.debug(MessageFormat.format("高频彩{0}:查询最近开奖详情列表", getLottery()));
		PokerOmitVO vo = new PokerOmitVO();
		vo.setLotteryCode(getLottery());
		vo.setShowDrawCode(true);
		vo.setShowIssue(true);
		vo.setType(true);
		vo.setQryCount(getIssueCount());
		return highLotteryService2.findRecentIssue(vo);
	}
}

	

