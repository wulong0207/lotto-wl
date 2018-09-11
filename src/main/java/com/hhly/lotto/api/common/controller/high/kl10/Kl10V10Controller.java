package com.hhly.lotto.api.common.controller.high.kl10;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hhly.lotto.api.common.controller.high.HighV10Controller;
import com.hhly.lottocore.remote.trend.service.IKl10TrendService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OmitEnum;
import com.hhly.skeleton.base.common.OmitEnum.QryFlag;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.trend.high.bo.HighOmitDataBO;
import com.hhly.skeleton.lotto.base.trend.vo.high.HighLotteryVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.Kl103CodeVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.Kl10BaseVO;
import com.hhly.skeleton.lotto.base.trend.vo.high.Kl10OmitVO;


/**
 * @desc 快乐10分抽象controller
 * @author Tony Wang
 * @date 2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class Kl10V10Controller extends HighV10Controller {
	@Autowired
	protected IKl10TrendService k10TrendService;

	@Override
	public <T extends HighLotteryVO> ResultBO<HighOmitDataBO> findOmit(T omitVO) {
		logger.debug("高频彩{}:查询遗漏", getLottery());
		Kl10OmitVO klOmitVO = new Kl10OmitVO();
		BeanUtils.copyProperties(omitVO, klOmitVO);
		// 查询所有子玩法、所有类型的遗漏
		Assert.notEmpty(klOmitVO.getOmitTypes());
		Assert.isTrue(OmitEnum.HighOmitType.containsAll(klOmitVO.getOmitTypes()));
		Assert.isTrue(QryFlag.contains(klOmitVO.getQryFlag()));
		klOmitVO.setLotteryCode(getLottery());
		// 快乐10分现在只提供查询普通遗漏
		klOmitVO.setQryFlag(QryFlag.NORMAL.getRange());
		// QryFlag qryFlag = QryFlag.valueOf(klOmitVO.getQryFlag());
		// 1前一数投,2前一红投,3任选二,4选二连组,5选二连直,6任选三,7选三前组,8选三前直,9任选四,10任选五
		Kl10BaseVO base = new Kl10BaseVO();
		Kl103CodeVO threeCode = new Kl103CodeVO();
		// 同时查询base表和3code表的遗漏
		// klOmitVO.setShowFlag(true);
		base.showAll(true);
		base.setFlag(true);
		threeCode.showAll(true);
		klOmitVO.setBase(base);
		klOmitVO.setThreeCode(threeCode);
		return highLotteryService2.findResultOmit(klOmitVO);
	}
	
	@Override
	public Object findDrawColdHotOmit(LotteryVO param) throws Exception {
		logger.debug("开奖信息{}接口：{}:查询遗漏/冷热数据",getPlatform().getDesc(),getLotName());
		param.setLotteryCode(getLottery());
		param.setQryCount(Constants.NUM_100);
		return k10TrendService.findDrawColdHotOmit(param);
	}
	
}
