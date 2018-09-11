package com.hhly.lotto.api.common.lotto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.lottocore.remote.lotto.service.ILotteryService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;


/**
 * 彩种基本查询
 * @desc    
 * @author  cheng chen
 * @date    2017年9月23日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/common/lotto")
public class LotteryCommonController extends BaseController {

    @Autowired
    private ILotteryService iLotteryService;

    @Autowired
    private ILotteryIssueService iLotteryIssueService;
    
    @RequestMapping(value = "queryLotterySelectList", method = {RequestMethod.POST, RequestMethod.GET})
    public Object queryLotterySelectList(LotteryVO vo) {
    	if(ObjectUtil.isBlank(vo))
    		return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
    	
    	Assert.paramNotNull(vo.getDrawType(), "drawType");
    	
        return ResultBO.ok(iLotteryService.queryLotterySelectList(vo));
    }
    
    @RequestMapping(value = "queryIssueByLottery", method = {RequestMethod.POST, RequestMethod.GET})
    public Object queryIssueByLottery(LotteryVO lotteryVO) {
    	if(ObjectUtil.isBlank(lotteryVO))
    		return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
    	Assert.paramNotNull(lotteryVO.getLotteryCode(), "lotteryCode");
    	return ResultBO.ok(iLotteryIssueService.queryIssueByLottery(lotteryVO));
    }
    
}
