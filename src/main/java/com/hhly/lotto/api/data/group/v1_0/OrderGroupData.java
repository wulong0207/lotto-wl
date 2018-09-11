package com.hhly.lotto.api.data.group.v1_0;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.group.bo.OrderGroupDetailBO;
import com.hhly.skeleton.lotto.base.group.bo.OrderGroupDetailPagingBO;
import org.springframework.stereotype.Component;

import java.util.List;
/**
 * @Description 合买数据处理
 * @Author longguoyou
 * @Date  2018/5/3 14:26
 * @Since 1.8
 */
@Component
public class OrderGroupData {
    /**
     * 处理合买大厅列表数据
     * @param list
     */
    public void handleOrderGroupList(List<OrderGroupDetailBO> list){
        if (!ObjectUtil.isBlank(list)) {
            for (OrderGroupDetailBO bean : list) {
                bean.setEndTime(DateUtil.convertDateToStr(DateUtil.convertStrToDate(bean.getEndTime(),DateUtil.DEFAULT_FORMAT), DateUtil.FORMAT_M_D_H_M));
            }
        }
    }

    public void handleOrderGroupList(PagingBO<OrderGroupDetailBO> pagingBO){
        if(!ObjectUtil.isBlank(pagingBO)){
            List<OrderGroupDetailBO> list = pagingBO.getData();
            this.handleOrderGroupList(list);
        }
    }

    public void handleOrderGroupDetailsList(OrderGroupDetailPagingBO detailPagingBO){
        if (!ObjectUtil.isBlank(detailPagingBO)) {
            for (OrderGroupDetailBO bean : detailPagingBO.getListOrderGroupDetailBO()) {
                bean.setEndTime(DateUtil.convertDateToStr(DateUtil.convertStrToDate(bean.getEndTime(),DateUtil.DEFAULT_FORMAT), DateUtil.FORMAT_M_D_H_M));
            }
        }
    }
}
