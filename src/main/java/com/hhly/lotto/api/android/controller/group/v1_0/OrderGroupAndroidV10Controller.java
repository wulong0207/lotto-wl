package com.hhly.lotto.api.android.controller.group.v1_0;


import com.hhly.lotto.api.common.controller.ordergroup.v1_0.OrderGroupCommonV10Controller;
import com.hhly.lotto.api.data.group.v1_0.OrderGroupData;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.group.bo.OrderGroupDetailBO;
import com.hhly.skeleton.lotto.base.group.bo.OrderGroupDetailPagingBO;
import com.hhly.skeleton.lotto.base.group.vo.OrderGroupQueryVO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.QueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/android/v1.0/order-group")
public class OrderGroupAndroidV10Controller extends OrderGroupCommonV10Controller{

    @Autowired
    private OrderGroupData orderGroupData;

    @Override
    public ResultBO<?> queryOrderGroupList(@RequestBody OrderGroupQueryVO orderGroupQueryVO,HttpServletRequest request) throws Exception {
        ResultBO<?> result = super.queryOrderGroupList(orderGroupQueryVO,request);
        if(result.isError()){return result;}
        OrderGroupDetailPagingBO orderGroupDetailBOPagingBO = (OrderGroupDetailPagingBO)(result.getData());
        orderGroupData.handleOrderGroupDetailsList(orderGroupDetailBOPagingBO);
        return ResultBO.ok(orderGroupDetailBOPagingBO);
    }
}
