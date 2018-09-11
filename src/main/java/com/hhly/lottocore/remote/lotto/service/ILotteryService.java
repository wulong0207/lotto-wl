package com.hhly.lottocore.remote.lotto.service;


import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryTypeBO;
import com.hhly.skeleton.lotto.base.lottery.bo.LotBettingMulBO;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;

/**
 * @author Administrator on
 * @version 1.0
 * @desc 彩种service
 * @date 2017/4/28.
 * @company 益彩网络科技有限公司
 */
public interface ILotteryService {
    /**
     * 查询 投注注数，倍数截止时间信息表
     * @param lotteryCode
     * @return
     */
    ResultBO<List<LotBettingMulBO>> findLotteryDettingMul(Integer lotteryCode);
    /**
     * 查询彩种信息
     * @return
     */
    ResultBO<List<LotteryTypeBO>> findAllLotteryType();
    
    LotteryBO findSingleFront(LotteryVO lotteryVO);
    
    /**
     * 通过开奖彩种类型查询彩种集合
     * @param
     * @return
     * @date 2017年9月23日上午11:27:42
     * @author cheng.chen
     */
    List<LotteryBO> queryLotterySelectList(LotteryVO vo);
}
