package com.hhly.lotto.api.pc.high.controller.k3;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.api.pc.high.controller.HighController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OmitEnum;
import com.hhly.skeleton.base.common.OmitEnum.QryFlag;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.K3OmitVO;

/**
 * @desc    快3controller抽象
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class K3Controller extends HighController {
	
	private static Logger logger = Logger.getLogger(K3Controller.class);
	
	/**
	 * @desc   查询遗漏(或近期)
	 * @author Tony Wang
	 * @create 2017年3月15日
	 * @param k3OmitVO
	 * @return
	 * @throws Exception 
	 */
	@Override
	@RequestMapping(value = "/omit", method = RequestMethod.GET)
	public <T extends HighLotteryVO> ResultBO<HighOmitDataBO> findOmit(T omitVO) {
		K3OmitVO k3OmitVO = new K3OmitVO();
		BeanUtils.copyProperties(omitVO, k3OmitVO);
		logger.debug("快3：查询遗漏");
		// 查询所有子玩法,根据不同的遗漏类型(1:N期冷热;2:当前遗漏;3:最大遗漏;4:上次遗漏)查询遗漏
		Assert.notEmpty(k3OmitVO.getOmitTypes());
		Assert.isTrue(OmitEnum.HighOmitType.containsAll(k3OmitVO.getOmitTypes()));
		Assert.isTrue(QryFlag.contains(k3OmitVO.getQryFlag()));
		//List<Integer> subPlays = k3OmitVO.getSubPlays();
		// PC端快3一次只能查一个子玩法
		//Assert.isTrue(subPlays != null && subPlays.size()==1 && HighConstants.K3_SUBPLAY.containsAll(subPlays));
		k3OmitVO.setLotteryCode(getLottery());
		k3OmitVO.showAll(true);
		K3OmitVO recentVO = new K3OmitVO();
		BeanUtils.copyProperties(k3OmitVO, recentVO);
		QryFlag qryFlag = QryFlag.valueOf(k3OmitVO.getQryFlag());
		switch (qryFlag) {
		case NORMAL:
			return highLotteryService2.findResultOmit(k3OmitVO);
		case RECENT:
			return highLotteryService2.findRecentOmit(recentVO);
		default:
			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
		}
//		QryFlag qryFlag = QryFlag.valueOf(k3OmitVO.getQryFlag());
//		switch (subPlays.get(0)) {
//		// 和值
//		case 1:
//			switch (qryFlag) {
//			case NORMAL:
//				k3OmitVO.showSum(true);
//				break;
//			case RECENT:
//				recentVO.setShowIssue(true);
//				recentVO.showSum(true);
//				recentVO.showSumForm(true);
//				recentVO.setShowDrawcode(true);
//				k3OmitVO.showSum(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
//			}
//			break;
//		// 三同号通选
//		case 2:
//			switch (qryFlag) {
//			case NORMAL:
//				k3OmitVO.setShowT3(true);
//				break;
//			case RECENT:
//				recentVO.showToPC(true);
//				k3OmitVO.setShowT3(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
//			}
//			break;
//		// 三同号单选
//		case 3:
//			switch (qryFlag) {
//			case NORMAL:
//				k3OmitVO.showT3All(true);
//				break;
//			case RECENT:
//				recentVO.showToPC(true);
//				k3OmitVO.showT3All(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
//			}
//			break;
//		// 三连号通选
//		case 5:
//			switch (qryFlag) {
//			case NORMAL:
//				k3OmitVO.setShowL3(true);
//				break;
//			case RECENT:
//				recentVO.showToPC(true);
//				k3OmitVO.setShowL3(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
//			}
//			break;
//		// 二同号复选
//		case 6:
//			switch (qryFlag) {
//			case NORMAL:
//				k3OmitVO.showTf(true);
//				break;
//			case RECENT:
//				recentVO.showToPC(true);
//				k3OmitVO.showTf(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
//			}
//			break;
//		// 二同号单选
//		case 7:
//			switch (qryFlag) {
//			case NORMAL:
//				k3OmitVO.showTd(true);
//				break;
//			case RECENT:
//				recentVO.showToPC(true);
//				k3OmitVO.showTd(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
//			}
//			break;
//		// 三不同号
//		case 4:
//		// 二不同号，一码包中也用8表示
//		case 8:
//			switch (qryFlag) {
//			case NORMAL:
//			case DANTUO:
//				k3OmitVO.show1To6(true);
//				break;
//			case RECENT:
//				recentVO.showToPC(true);
//				k3OmitVO.show1To6(true);
//				break;
//			default:
//				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
//			}
//			break;
//		default:
//			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.LOTTERY_CHILD_CODE_IS_NULL_FIELD,subPlays.get(0)));
//		}
	}
}
