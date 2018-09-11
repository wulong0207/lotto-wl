package com.hhly.lotto.api.pc.high.controller.ssc;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OmitEnum;
import com.hhly.skeleton.base.common.OmitEnum.QryFlag;
import com.hhly.skeleton.base.constants.HighConstants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.SscOmitVO;

/**
 * @desc    时时彩PC端抽象controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class SscPcController extends SscController {

	@Override
	protected int getIssueCount() {
		return 20;
	}

	@Override
	public <T extends HighLotteryVO> ResultBO<HighOmitDataBO> findOmit(T omitVO) {
		SscOmitVO sscOmitVO = new SscOmitVO();
		BeanUtils.copyProperties(omitVO, sscOmitVO);
		// 查询所有子玩法、所有类型的遗漏
		Assert.notNull(sscOmitVO);
		Assert.notEmpty(sscOmitVO.getOmitTypes());
		Assert.isTrue(OmitEnum.HighOmitType.containsAll(sscOmitVO.getOmitTypes()));
		Assert.isTrue(QryFlag.contains(sscOmitVO.getQryFlag()));
		List<Integer> subPlays = sscOmitVO.getSubPlays();
		// 一次只能查一个子玩法
		Assert.isTrue(subPlays != null && subPlays.size()==1 && HighConstants.SSC_SUBPLAY.containsAll(subPlays));
		sscOmitVO.setLotteryCode(getLottery());
		SscOmitVO recentVO = new SscOmitVO();
		BeanUtils.copyProperties(sscOmitVO, recentVO);
		sscOmitVO.setShowFlag(true);
		QryFlag qryFlag = QryFlag.valueOf(sscOmitVO.getQryFlag());
		// 1五星直选,2五星通选,3三星直选,4三星组三,5三星组六,6二星直选,7二星组选,8一星,9大小单双
		switch (subPlays.get(0)) {
		case 1:
		case 2:
			switch (qryFlag) {
			case NORMAL:
				sscOmitVO.showWqbsg(true);
				return highLotteryService2.findResultOmit(sscOmitVO);
			case RECENT:
				recentVO.setShowIssue(true);
				recentVO.showWqbsg(true);
				recentVO.showWdxds(true);
				recentVO.showQdxds(true);
				recentVO.showBdxds(true);
				recentVO.showSdxds(true);
				recentVO.showGdxds(true);
				recentVO.showQ3(true);
				recentVO.showH3(true);
				recentVO.setSpan(true);
				sscOmitVO.showWqbsg(true);
				return highLotteryService2.findRecentOmit(recentVO);
			default:
				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
			}
		case 3:
			switch (qryFlag) {
			case NORMAL:
			case SUM:
				// 前端要求一次性返回
				sscOmitVO.showBsg(true);
				sscOmitVO.showSum3(true);
				return highLotteryService2.findResultOmit(sscOmitVO);
			case RECENT:
				recentVO.setShowIssue(true);
				recentVO.showBsg(true);
				recentVO.showBdxds(true);
				recentVO.showSdxds(true);
				recentVO.showGdxds(true);
				recentVO.showH3(true);
				sscOmitVO.showBsg(true);
				sscOmitVO.showSum3(true);
				return highLotteryService2.findRecentOmit(recentVO);
			default:
				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
			}
		case 4:
		case 5:
			switch (qryFlag) {
			case NORMAL:
			case DANTUO:
				sscOmitVO.showNum(true);
				return highLotteryService2.findResultOmit(sscOmitVO);
			case RECENT:
				recentVO.setShowIssue(true);
				recentVO.showNum(true);
				recentVO.showH3(true);
				sscOmitVO.showNum(true);
				return highLotteryService2.findRecentOmit(recentVO);
			default:
				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
			}
		// 6二星直选
		case 6:
			switch (qryFlag) {
			case NORMAL:
			case SUM:
				// 前端要求一次性返回
				sscOmitVO.showS(true);
				sscOmitVO.showG(true);
				sscOmitVO.showSum2(true);
				return highLotteryService2.findResultOmit(sscOmitVO);
			case RECENT:
				recentVO.setShowIssue(true);
				recentVO.showS(true);
				recentVO.showG(true);
				recentVO.showSdxds(true);
				recentVO.showGdxds(true);
				recentVO.setSpan(true);
				sscOmitVO.showS(true);
				sscOmitVO.showG(true);
				sscOmitVO.showSum2(true);
				return highLotteryService2.findRecentOmit(recentVO);
			default:
				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
			}
		// 7二星组选
		case 7:
			switch (qryFlag) {
			case NORMAL:
			case DANTUO:
			case SUM:
				// 前端要求一次性返回
				sscOmitVO.showNum(true);
				sscOmitVO.showSum2(true);
				return highLotteryService2.findResultOmit(sscOmitVO);
			case RECENT:
				recentVO.setShowIssue(true);
				recentVO.showS(true);
				recentVO.showG(true);
				recentVO.showSdxds(true);
				recentVO.showGdxds(true);
				recentVO.setSpan(true);
				sscOmitVO.showNum(true);
				return highLotteryService2.findRecentOmit(recentVO);
			default:
				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
			}
		case 8:
			switch (qryFlag) {
			case NORMAL:
				sscOmitVO.showG(true);
				return highLotteryService2.findResultOmit(sscOmitVO);
			case RECENT:
				recentVO.setShowIssue(true);
				recentVO.showG(true);
				recentVO.showGdxds(true);
				sscOmitVO.showG(true);
				return highLotteryService2.findRecentOmit(recentVO);
			default:
				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
			}
		case 9:
			switch (qryFlag) {
			case NORMAL:
				sscOmitVO.showSdxds(true);
				sscOmitVO.showGdxds(true);
				return highLotteryService2.findResultOmit(sscOmitVO);
			case RECENT:
				recentVO.setShowIssue(true);
				recentVO.showS(true);
				recentVO.showSdxds(true);
				recentVO.showG(true);
				recentVO.showGdxds(true);
				recentVO.setSpan(true);
				sscOmitVO.showSdxds(true);
				sscOmitVO.showGdxds(true);
				return highLotteryService2.findRecentOmit(recentVO);
			default:
				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
			}
		default:
			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.LOTTERY_CHILD_CODE_IS_NULL_FIELD,subPlays.get(0)));
		}
	}
}

	

