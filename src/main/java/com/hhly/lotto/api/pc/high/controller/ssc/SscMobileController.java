package com.hhly.lotto.api.pc.high.controller.ssc;

import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OmitEnum;
import com.hhly.skeleton.base.common.OmitEnum.QryFlag;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.SscOmitVO;

/**
 * @desc    时时彩移动端抽象controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class SscMobileController extends SscController {

	@Override
	protected int getIssueCount() {
		return 10;
	}

	@Override
	protected <T extends HighLotteryVO> ResultBO<HighOmitDataBO> findOmit(T omitVO) {
		SscOmitVO sscOmitVO = new SscOmitVO();
		BeanUtils.copyProperties(omitVO, sscOmitVO);
		// 查询所有子玩法的遗漏
		Assert.notNull(sscOmitVO);
		Assert.notEmpty(sscOmitVO.getOmitTypes());
		Assert.isTrue(OmitEnum.HighOmitType.containsAll(sscOmitVO.getOmitTypes()));
		Assert.isTrue(QryFlag.contains(sscOmitVO.getQryFlag()));
		//List<Integer> subPlays = sscOmitVO.getSubPlays();
		// 一次只能查一个子玩法
		//Assert.isTrue(subPlays != null && subPlays.size()==1 && HighConstants.SSC_SUBPLAY.containsAll(subPlays));
		sscOmitVO.setLotteryCode(getLottery());
		QryFlag qryFlag = QryFlag.valueOf(sscOmitVO.getQryFlag());
		// 1五星直选,2五星通选,3三星直选,4三星组三,5三星组六,6二星直选,7二星组选,8一星,9大小单双
		switch (qryFlag) {
		case NORMAL:
		case DANTUO:
		case SUM:
			sscOmitVO.showAll(true);
			sscOmitVO.setShowFlag(true);
			return highLotteryService2.findResultOmit(sscOmitVO);
		case RECENT:
			SscOmitVO recentVO = new SscOmitVO();
			BeanUtils.copyProperties(sscOmitVO, recentVO);
			recentVO.showAll(true);
			recentVO.setShowIssue(true);
			recentVO.setShowDrawCode(true);
			recentVO.setShowIssue(true);
			recentVO.showNum(true);
			sscOmitVO.showNum(true);
			return highLotteryService2.findRecentOmit(recentVO);
		default:
			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
		}
		
//		switch (subPlays.get(0)) {
//		case 1:
//		case 2:
//			switch (qryFlag) {
//			case NORMAL:
//				sscOmitVO.showWqbsg(true);
//				break;
//			case RECENT:
//				recentVO.setShowIssue(true);
//				recentVO.showWqbsg(true);
//				sscOmitVO.showWqbsg(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
//			}
//			break;
//		case 3:
//			switch (qryFlag) {
//			case NORMAL:
//				sscOmitVO.showBsg(true);
//				break;
//			case SUM:
//				sscOmitVO.showBsg(true);
//				break;
//			case RECENT:
//				recentVO.setShowIssue(true);
//				recentVO.showBsg(true);
//				sscOmitVO.showBsg(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
//			}
//			break;
//		case 4:
//		case 5:
//			switch (qryFlag) {
//			case NORMAL:
//			case DANTUO:
//				sscOmitVO.showNum(true);
//				break;
//			case RECENT:
//				recentVO.setShowIssue(true);
//				recentVO.showNum(true);
//				recentVO.showH3(true);
//				sscOmitVO.showNum(true);
//				sscOmitVO.showH3(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
//			}
//			break;
//		// 6二星直选
//		case 6:
//			switch (qryFlag) {
//			case NORMAL:
//				sscOmitVO.showS(true);
//				sscOmitVO.showG(true);
//				break;
//			case SUM:
//				sscOmitVO.showSum2(true);
//			case RECENT:
//				recentVO.setShowIssue(true);
//				recentVO.showS(true);
//				recentVO.showG(true);
//				sscOmitVO.showS(true);
//				sscOmitVO.showG(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
//			}
//			break;
//		// 7二星组选
//		case 7:
//			switch (qryFlag) {
//			case NORMAL:
//			case DANTUO:
//				sscOmitVO.showNum(true);
//				break;
//			case SUM:
//				sscOmitVO.showSum2(true);
//				break;
//			case RECENT:
//				recentVO.setShowIssue(true);
//				recentVO.showNum(true);
//				sscOmitVO.showNum(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
//			}
//			break;
//		case 8:
//			switch (qryFlag) {
//			case NORMAL:
//				sscOmitVO.showG(true);
//				break;
//			case RECENT:
//				recentVO.setShowIssue(true);
//				recentVO.showG(true);
//				sscOmitVO.showG(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
//			}
//			break;
//		case 9:
//			switch (qryFlag) {
//			case NORMAL:
//				sscOmitVO.showSdxds(true);
//				sscOmitVO.showGdxds(true);
//				break;
//			case RECENT:
//				recentVO.setShowDrawCode(true);
//				recentVO.showSdxds(true);
//				recentVO.showGdxds(true);
//				sscOmitVO.showSdxds(true);
//				sscOmitVO.showGdxds(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,sscOmitVO.getQryFlag()));
//			}
//			break;
//		default:
//			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.LOTTERY_CHILD_CODE_IS_NULL_FIELD,subPlays.get(0)));
//		}
	}
}

	

