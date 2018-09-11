package com.hhly.lotto.api.pc.controller.group.v1_1;

import com.hhly.lotto.api.common.controller.ordergroup.v1_1.OrderGroupCommonV11Controller;
import com.hhly.lotto.api.data.group.v1_0.OrderGroupData;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.group.bo.OrderGroupDetailPagingBO;
import com.hhly.skeleton.lotto.base.group.vo.OrderGroupQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/pc/v1.1/order-group")
public class OrderGroupPCV11Controller extends OrderGroupCommonV11Controller{

    @Autowired
    private OrderGroupData orderGroupData;

    @Override
    public ResultBO<?> queryOrderGroupList(@RequestBody OrderGroupQueryVO orderGroupQueryVO, HttpServletRequest request) throws Exception {
        ResultBO<?> result = super.queryOrderGroupList(orderGroupQueryVO, request);
        if(result.isError()){return result;}
        OrderGroupDetailPagingBO orderGroupDetailBOPagingBO = (OrderGroupDetailPagingBO)(result.getData());
        orderGroupData.handleOrderGroupDetailsList(orderGroupDetailBOPagingBO);
        return ResultBO.ok(orderGroupDetailBOPagingBO);
    }
}
