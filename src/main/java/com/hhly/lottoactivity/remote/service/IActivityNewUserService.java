package com.hhly.lottoactivity.remote.service;

import com.hhly.skeleton.lotto.base.activity.bo.ActivityNewUserBO;
import com.hhly.skeleton.lotto.base.activity.vo.ActivityNewUserVO;

/**
 * 新用户活动
 *
 * @author huangchengfang1219
 * @date 2018年1月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IActivityNewUserService {

	/**
	 * 活动基础信息查询
	 * 
	 * @param vo
	 * @return
	 */
	ActivityNewUserBO findActivityBaseInfo(ActivityNewUserVO vo);

	/**
	 * 领取
	 * 
	 * @return
	 */
	ActivityNewUserBO doTake(ActivityNewUserVO vo);

	/**
	 * 活动详细信息
	 * 
	 * @param vo
	 * @return
	 */
	ActivityNewUserBO findActivityDetailInfo(ActivityNewUserVO vo);

	/**
	 * 下单
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	ActivityNewUserBO addOrder(ActivityNewUserVO vo) throws Exception;
}
