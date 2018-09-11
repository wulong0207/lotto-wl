package com.hhly.lottocore.remote.order.service;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.order.vo.OrderQueryVo;

/**
 * @author yuanshangbing
 * @version 1.0
 * @desc 出票明细
 * @date 2017/11/4 9:16
 * @company 益彩网络科技公司
 */
public interface ITicketDetailService {

    /**
     * 获取出票明细信息
     * @param orderQueryVo
     * @return
     */
    ResultBO<?> queryTicketDetailInfo(OrderQueryVo orderQueryVo) throws Exception;
}
