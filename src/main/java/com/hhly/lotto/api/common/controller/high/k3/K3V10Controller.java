package com.hhly.lotto.api.common.controller.high.k3;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.api.common.controller.high.HighV10Controller;
import com.hhly.lottocore.remote.trend.service.IK3TrendService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OmitEnum;
import com.hhly.skeleton.base.common.OmitEnum.QryFlag;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryTrendVO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.bo.TrendBaseBO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.K3OmitVO;

/**
 * @desc 快3controller抽象
 * @author Tony Wang
 * @date 2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class K3V10Controller extends HighV10Controller {
	
	@Autowired
	protected IK3TrendService k3TrendService;

	/**
	 * @desc 查询遗漏(或近期)
	 * @author Tony Wang
	 * @create 2017年3月15日
	 * @param k3OmitVO
	 * @return
	 * @throws Exception
	 */
	@Override
	@RequestMapping(value = "/omit", method = RequestMethod.GET)
	public <T extends HighLotteryVO> ResultBO<HighOmitDataBO> findOmit(T omitVO) {
		logger.debug("高频彩{}：查询遗漏", getLottery());
		K3OmitVO k3OmitVO = new K3OmitVO();
		BeanUtils.copyProperties(omitVO, k3OmitVO);
		// 查询所有子玩法,根据不同的遗漏类型(1:N期冷热;2:当前遗漏;3:最大遗漏;4:上次遗漏)查询遗漏
		Assert.notEmpty(k3OmitVO.getOmitTypes());
		Assert.isTrue(OmitEnum.HighOmitType.containsAll(k3OmitVO.getOmitTypes()));
		Assert.isTrue(QryFlag.contains(k3OmitVO.getQryFlag()));
		// List<Integer> subPlays = k3OmitVO.getSubPlays();
		// PC端快3一次只能查一个子玩法
		// Assert.isTrue(subPlays != null && subPlays.size()==1 &&
		// HighConstants.K3_SUBPLAY.containsAll(subPlays));
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
			throw new ResultJsonException(
					ResultBO.err(MessageCodeConstants.QRY_FLAG_NOT_INVALID, k3OmitVO.getQryFlag()));
		}
	}

	@Override
	public ResultBO<List<TrendBaseBO>> findBaseTrend(LotteryTrendVO vo) throws Exception {
		logger.debug("{}接口：{}：查询走势", getPlatform().getDesc(), getLotName());
		Assert.notNull(vo);
		vo.setLotteryCode(getLottery());
		// 默认30期
		if (vo.getQryCount() == null) {
			vo.setQryCount(Constants.NUM_30);
		}
		return k3TrendService.findBaseTrend(vo);
	}
	
	
	@Override
	public Object findDrawColdHotOmit(LotteryVO param) throws Exception {
		logger.debug("开奖信息{}接口：{}:查询遗漏/冷热数据",getPlatform().getDesc(),getLotName());
		param.setLotteryCode(getLottery());
		param.setQryCount(Constants.NUM_100);
		return k3TrendService.findDrawColdHotOmit(param);
	}
}
