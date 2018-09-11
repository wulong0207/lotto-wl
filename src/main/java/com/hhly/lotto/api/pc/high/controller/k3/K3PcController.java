package com.hhly.lotto.api.pc.high.controller.k3;

import java.text.MessageFormat;

import org.apache.log4j.Logger;
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
public abstract class K3PcController extends K3Controller {

	private static Logger logger = Logger.getLogger(K3PcController.class);

	@Override
	protected int getIssueCount() {
		// 查询最近开奖详情时PC端查询20期
		return 20;
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
		vo.setBs(true);
		vo.setQryCount(20);
		return highLotteryService2.findRecentIssue(vo);
	}

	
//	/**
//	 * @desc   查询遗漏(或近期)
//	 * @author Tony Wang
//	 * @create 2017年3月15日
//	 * @param k3OmitVO
//	 * @return
//	 * @throws Exception 
//	 */
//	@Override
//	public <T extends HighOmitBaseVO> ResultBO<HighOmitDataBO> findOmit(T omitVO) {
//		K3OmitVO k3OmitVO = new K3OmitVO();
//		BeanUtils.copyProperties(omitVO, k3OmitVO);
//		logger.debug("快3：查询遗漏");
//		// 查询所有子玩法,根据不同的遗漏类型(1:N期冷热;2:当前遗漏;3:最大遗漏;4:上次遗漏)查询遗漏
//		Assert.notEmpty(k3OmitVO.getOmitTypes());
//		Assert.isTrue(OmitEnum.HighOmitType.containsAll(k3OmitVO.getOmitTypes()));
//		//List<Integer> subPlays = k3OmitVO.getSubPlays();
//		// PC端快3一次只能查一个子玩法
//		//Assert.isTrue(subPlays != null && subPlays.size()==1 && HighConstants.K3_SUBPLAY.containsAll(subPlays));
//		k3OmitVO.setLotteryCode(getLottery());
//		K3OmitVO recentVO = new K3OmitVO();
//		BeanUtils.copyProperties(k3OmitVO, recentVO);
//		return k3OmitService.findOmit(k3OmitVO, recentVO);
////		QryFlag qryFlag = QryFlag.valueOf(k3OmitVO.getQryFlag());
////		switch (subPlays.get(0)) {
////		// 和值
////		case 1:
////			switch (qryFlag) {
////			case NORMAL:
////				k3OmitVO.showSum(true);
////				break;
////			case RECENT:
////				recentVO.setShowIssue(true);
////				recentVO.showSum(true);
////				recentVO.showSumForm(true);
////				recentVO.setShowDrawcode(true);
////				k3OmitVO.showSum(true);
////				break;
////			default:
////				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
////			}
////			break;
////		// 三同号通选
////		case 2:
////			switch (qryFlag) {
////			case NORMAL:
////				k3OmitVO.setShowT3(true);
////				break;
////			case RECENT:
////				recentVO.showToPC(true);
////				k3OmitVO.setShowT3(true);
////				break;
////			default:
////				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
////			}
////			break;
////		// 三同号单选
////		case 3:
////			switch (qryFlag) {
////			case NORMAL:
////				k3OmitVO.showT3All(true);
////				break;
////			case RECENT:
////				recentVO.showToPC(true);
////				k3OmitVO.showT3All(true);
////				break;
////			default:
////				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
////			}
////			break;
////		// 三连号通选
////		case 5:
////			switch (qryFlag) {
////			case NORMAL:
////				k3OmitVO.setShowL3(true);
////				break;
////			case RECENT:
////				recentVO.showToPC(true);
////				k3OmitVO.setShowL3(true);
////				break;
////			default:
////				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
////			}
////			break;
////		// 二同号复选
////		case 6:
////			switch (qryFlag) {
////			case NORMAL:
////				k3OmitVO.showTf(true);
////				break;
////			case RECENT:
////				recentVO.showToPC(true);
////				k3OmitVO.showTf(true);
////				break;
////			default:
////				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
////			}
////			break;
////		// 二同号单选
////		case 7:
////			switch (qryFlag) {
////			case NORMAL:
////				k3OmitVO.showTd(true);
////				break;
////			case RECENT:
////				recentVO.showToPC(true);
////				k3OmitVO.showTd(true);
////				break;
////			default:
////				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
////			}
////			break;
////		// 三不同号
////		case 4:
////		// 二不同号，一码包中也用8表示
////		case 8:
////			switch (qryFlag) {
////			case NORMAL:
////			case DANTUO:
////				k3OmitVO.show1To6(true);
////				break;
////			case RECENT:
////				recentVO.showToPC(true);
////				k3OmitVO.show1To6(true);
////				break;
////			default:
////				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID,k3OmitVO.getQryFlag()));
////			}
////			break;
////		default:
////			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.LOTTERY_CHILD_CODE_IS_NULL_FIELD,subPlays.get(0)));
////		}
//	}
}
