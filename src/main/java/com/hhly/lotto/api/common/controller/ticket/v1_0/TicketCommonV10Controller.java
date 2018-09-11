package com.hhly.lotto.api.common.controller.ticket.v1_0;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.order.service.ITicketDetailService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.order.vo.OrderQueryVo;


/**
 * @author yuanshangbing
 * @version 1.0
 * @desc 出票明细控制层
 * @date 2017/11/4 9:17
 * @company 益彩网络科技公司
 */
public class TicketCommonV10Controller extends BaseController {
    private static Logger logger = Logger.getLogger(TicketCommonV10Controller.class);

    @Autowired
    private ITicketDetailService iTicketDetailService;


    /**
     * 查询出票明细列表
     * @param orderQueryVo
     * @return
     */
    @RequestMapping(value = "/ticketlist" ,method = RequestMethod.POST)
    public ResultBO<?> queryOrderInfoList(@RequestBody OrderQueryVo orderQueryVo) throws Exception{
        if(ObjectUtil.isBlank(orderQueryVo) || ObjectUtil.isBlank(orderQueryVo.getToken())
                ||ObjectUtil.isNull(orderQueryVo.getPageIndex()) || ObjectUtil.isBlank(orderQueryVo.getPageSize()) || ObjectUtil.isBlank(orderQueryVo.getOrderCode())){
            return ResultBO.err(MessageCodeConstants.PARAM_IS_NULL_FIELD);
        }
        if(orderQueryVo.getPageSize()> Constants.NUM_10){//每页最大显示10条
            orderQueryVo.setPageSize(Constants.NUM_10);
        }
        return iTicketDetailService.queryTicketDetailInfo(orderQueryVo);

    }


}
