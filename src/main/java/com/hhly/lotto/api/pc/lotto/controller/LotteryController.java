package com.hhly.lotto.api.pc.lotto.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.commoncore.remote.lotto.service.ILotteryBuyService;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.lottocore.remote.lotto.service.ILotteryService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.operate.vo.OperateAdVO;

/**
 * @author lgs on
 * @version 1.0
 * @desc 彩种信息Controller
 * @date 2017/4/28.
 * @company 益彩网络科技有限公司
 */
@RestController
@RequestMapping(value = "/lottery")
public class LotteryController {

    @Autowired
    private ILotteryService iLotteryService;

    @Autowired
    private ILotteryIssueService iLotteryIssueService;

    @Autowired
    private ILotteryBuyService iLotteryBuyService;


    /**
     * 获取彩种信息
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Object getLottery() {
        Map<String, String> lotteryMap = new HashMap<>();
        for (LotteryEnum.Lottery temp : LotteryEnum.Lottery.values()) {
            lotteryMap.put(String.valueOf(temp.getName()), temp.getDesc());
        }
        return ResultBO.ok(lotteryMap);
    }

    /**
     * 获取彩种投注规则
     * @param lotteryCode
     * @return
     */
    @RequestMapping(value = "/bettingMul/{lotteryCode}")
    public Object getLotteryBettingMul(@PathVariable(value = "lotteryCode") Integer lotteryCode){
        if (ObjectUtil.isBlank(lotteryCode) || !LotteryEnum.Lottery.contain(lotteryCode)) {
            return ResultBO.err("20001");
        }

        return iLotteryService.findLotteryDettingMul(lotteryCode);
    }

    /**
     * 获取投注规则(旧,需保留支持旧客户端)
     *
     * @param lotteryCode 彩种编号
     * @return
     */
    @RequestMapping(value = "/betRule/{lotteryCode}")
    public Object getLotteryBetRule(@PathVariable(value = "lotteryCode") Integer lotteryCode) {
        if (ObjectUtil.isBlank(lotteryCode) || !LotteryEnum.Lottery.contain(lotteryCode)) {
            return ResultBO.err("20001");
        }

        boolean flag = LotteryEnum.Lottery.contain(lotteryCode);
        if (!flag) {
            return ResultBO.err();
        }
        return iLotteryIssueService.findLotteryIssueBase(lotteryCode);
    }
    
    /**
     * 获取投注规则(新,分平台)
     *
     * @param lotteryCode 彩种编号
     * @return
     */
    @RequestMapping(value = "/betRule/{platform}/{lotteryCode}")
    public Object getLotteryBetRuleByPlatform(@PathVariable(value = "lotteryCode") Integer lotteryCode,
    		@PathVariable(value = "platform") Short platform) {
        if (ObjectUtil.isBlank(lotteryCode) || !LotteryEnum.Lottery.contain(lotteryCode)) {
            return ResultBO.err("20001");
        }
        boolean flag = LotteryEnum.Lottery.contain(lotteryCode);
        if (!flag) {
            return ResultBO.err();
        }
        return iLotteryIssueService.findLotteryIssueBase(lotteryCode,platform);
    }

    /**
     * 查询彩种集合
     * @return
     */
    @RequestMapping(value = "/list")
    public Object getLotteryList(OperateAdVO vo) {
        return iLotteryBuyService.findLotteryList(vo);
    }

    /**
     * 查询开奖集合
     * @return
     */
    @RequestMapping(value = "/draw/list")
    public Object getDrawList() {
        return iLotteryBuyService.findDrawList();
    }


}
