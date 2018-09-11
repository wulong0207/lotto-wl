package com.hhly.lotto.api.data.operate.v1_0;

import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateFastLottBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateFastVO;

/**
 * @desc 快投管理服务
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface OperateFastV10Data {	

	/**
	 * 查询快投信息+上一期开奖信息
	 * @param operateFastVO
	 * @return
	 */
	ResultBO<List<OperateFastLottBO>> findOperFastIssueDetail(OperateFastVO operateFastVO);
}
