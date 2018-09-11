package com.hhly.lotto.api.pc.high.controller.kl10;

import org.springframework.beans.BeanUtils;

import com.hhly.lotto.api.pc.high.controller.HighController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OmitEnum;
import com.hhly.skeleton.base.common.OmitEnum.QryFlag;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.Kl103CodeVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.Kl10BaseVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.Kl10OmitVO;

/**
 * @desc    快乐10分抽象controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class Kl10Controller extends HighController {
	@Override
	public <T extends HighLotteryVO> ResultBO<HighOmitDataBO> findOmit(T omitVO) {
		Kl10OmitVO klOmitVO = new Kl10OmitVO();
		BeanUtils.copyProperties(omitVO, klOmitVO);
		// 查询所有子玩法、所有类型的遗漏
		Assert.notEmpty(klOmitVO.getOmitTypes());
		Assert.isTrue(OmitEnum.HighOmitType.containsAll(klOmitVO.getOmitTypes()));
		Assert.isTrue(QryFlag.contains(klOmitVO.getQryFlag()));
		klOmitVO.setLotteryCode(getLottery());
		// 快乐10分现在只提供查询普通遗漏
		klOmitVO.setQryFlag(QryFlag.NORMAL.getRange());
//		QryFlag qryFlag = QryFlag.valueOf(klOmitVO.getQryFlag());
		//1前一数投,2前一红投,3任选二,4选二连组,5选二连直,6任选三,7选三前组,8选三前直,9任选四,10任选五
		Kl10BaseVO base = new Kl10BaseVO();
		Kl103CodeVO threeCode = new Kl103CodeVO();
		// 同时查询base表和3code表的遗漏
		//klOmitVO.setShowFlag(true);
		base.showAll(true);
		base.setFlag(true);
		threeCode.showAll(true);
		klOmitVO.setBase(base);
		klOmitVO.setThreeCode(threeCode);
		return highLotteryService2.findResultOmit(klOmitVO);
//		switch (qryFlag) {
//		case NORMAL:
//			switch (subPlays.get(0)) {
//			case 1:
//				threeCode.showSt(true);
//				klOmitVO.setThreeCode(threeCode);;
//				break;
//			case 2:
//				base.showHt(true);
//				klOmitVO.setBase(base);
//				break;
//			case 3:
//			case 4:
//			case 5:
//			case 6:
//			case 9:
//			case 10:
//				base.showNums(true);
//				klOmitVO.setBase(base);
//				break;
//			case 7:
//				base.showG3(true);
//				klOmitVO.setBase(base);
//				break;
//			case 8:
//				threeCode.showAll(true);
//				klOmitVO.setThreeCode(threeCode);;
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.LOTTERY_CHILD_CODE_IS_NULL_FIELD,subPlays.get(0)));
//			}
//			return highLotteryService2.findResultOmit(klOmitVO);
//		case RECENT:
//			Kl10OmitVO recentVO = new Kl10OmitVO();
//			BeanUtils.copyProperties(klOmitVO, recentVO);
//			return highLotteryService2.findRecentOmit(recentVO);
//		default:
//			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,klOmitVO.getQryFlag()));
//		}
	}
}

	

