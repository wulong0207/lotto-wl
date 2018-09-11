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
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;

/**
 * @desc    PC端十一选五抽象controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class X115PcController extends X115Controller {

	private static Logger logger = Logger.getLogger(X115PcController.class);
	
	@Override
	protected int getIssueCount() {
		return 20;
	}
	
	@Override
	public Object info() {
		logger.info("PC端接口:十一选五:查询彩种、当彩期、最新开奖详情、注/倍数配置、子玩法、限号");
		return lotteryIssueService.findLotteryIssueBase(getLottery(), getPlatform().getValue());
	}
	
	/**
	 * @desc   查询遗漏统计(遗漏投注)
	 * @author Tony Wang
	 * @create 2017年3月9日
	 * @return 
	 
	@RequestMapping(value = "/omitstatis", method = RequestMethod.GET)
	@ResponseBody
	public ResultBO<List<OmitStatisBaseBO>> findOmitStatis(OmitStatisBaseVO vo) {
		logger.info("PC端接口：十一选五：查询遗漏数据");
		vo.setLotteryCode(LotteryEnum.Lottery.SD11X5.getName());
		return x115OmitService.find(vo);
	}*/
	
	@Override
	public Object findLimit(LotteryVO vo) {
		logger.info("PC端接口：十一选五：查询限号");
		Assert.notNull(vo);
		// 默认查询所有子玩法的限号
		if(!CollectionUtils.isEmpty(vo.getSubPlays())) {
			List<Integer> completeSubPlays = new ArrayList<>();
			for(Integer subPlay : vo.getSubPlays()) {
				Assert.isTrue(subPlay != null && subPlay>0);
				// 加前缀0,如2-->"21502"
				Integer x115SubPlay = getLottery() * 100 + subPlay;
				Assert.isTrue(isSubPlay(x115SubPlay));
				completeSubPlays.add(x115SubPlay);
			}
			// 设置完整的子玩法
			vo.setSubPlays(completeSubPlays);
		}
		// 设置彩种
		vo.setLotteryCode(getLottery());
		Map<String, Object> ret = new HashMap<>();
		ret.put("currentDateTime", DateUtil.convertDateToStr(new Date()));
		ret.put("limitInfoList", lotteryIssueService.findLimit(vo).getData());
		return new ResultBO<>(ret);
	}
}
