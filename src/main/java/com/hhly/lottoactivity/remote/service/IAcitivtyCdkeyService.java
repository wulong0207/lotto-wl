package com.hhly.lottoactivity.remote.service;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateActivityCdkeyVO;

/**
 * @author zhouy478
 * @version 1.0
 * @desc 兑换码管理hessian接口
 * @date 2018/1/12
 * @company 益彩网络科技公司
 */
public interface IAcitivtyCdkeyService {

    ResultBO<?> exchangeCdkey(OperateActivityCdkeyVO vo);
}
