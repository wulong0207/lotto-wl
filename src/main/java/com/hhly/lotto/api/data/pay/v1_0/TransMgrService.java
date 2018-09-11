package com.hhly.lotto.api.data.pay.v1_0;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.transmgr.bo.TransRemittingBO;


/**
 * @author TonyOne
 * @version 1.0
 * @desc
 * @date 2018/7/4 17:16
 * @company StayReal LTD
 */
public interface TransMgrService {

    /**
     * 提交汇款
     * @param vo
     * @return
     */
    ResultBO<?> insertRemitting(TransRemittingBO vo);
}
