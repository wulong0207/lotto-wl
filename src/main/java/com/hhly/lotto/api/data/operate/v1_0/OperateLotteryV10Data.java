package com.hhly.lotto.api.data.operate.v1_0;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateLottBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateLotteryDetailBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateLotteryLottVO;

/**
 * @desc 彩种管理服务
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface OperateLotteryV10Data {	

	/**
	 * 获取PC主页彩种运营管理信息
	 * @return
	 */
	ResultBO<List<OperateLotteryDetailBO>> findHomeOperLottery(OperateLotteryLottVO vo);
	/**
	 * 根据平台查询彩种运营信息
	 * @param platform
	 * @return
	 */
	List<OperateLotteryDetailBO> findOperLottery(short platform);
	/**
	 * 手机端首页写死数据
	 * @return
	 */
	List<OperateLottBO> getMobailLottList();
}
