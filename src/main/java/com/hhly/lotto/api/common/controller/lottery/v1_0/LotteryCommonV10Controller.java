package com.hhly.lotto.api.common.controller.lottery.v1_0;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hhly.commoncore.remote.dicdata.service.IDicDataDetailService;
import com.hhly.commoncore.remote.lotto.service.ILotteryBuyService;
import com.hhly.lotto.base.common.HeaderParam;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottoactivity.remote.service.IActivityService;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.lottocore.remote.lotto.service.ILotteryService;
import com.hhly.skeleton.activity.bo.ActivityBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.model.DicDataEnum;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.dic.bo.DicDataDetailBO;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryIssueBaseBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateAdVO;

/**
 * @author lgs on
 * @version 1.0
 * @desc 彩种信息Controller
 * @date 2017/4/28.
 * @company 益彩网络科技有限公司
 */
public class LotteryCommonV10Controller extends BaseController {

    private static Logger logger = LoggerFactory.getLogger(LotteryCommonV10Controller.class);

    @Autowired
    private ILotteryService iLotteryService;

    @Autowired
    private ILotteryIssueService iLotteryIssueService;

    @Autowired
    private ILotteryBuyService iLotteryBuyService;

    @Autowired
    private IActivityService iActivityService;

    @Autowired
    private IDicDataDetailService iDicDataDetailService;
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
     *
     * @param lotteryCode
     * @return
     */
    @RequestMapping(value = "/bettingMul/{lotteryCode}")
    public Object getLotteryBettingMul(@PathVariable(value = "lotteryCode") Integer lotteryCode) {
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
                                              @PathVariable(value = "platform") Short platform,HttpServletRequest request) {
        if (ObjectUtil.isBlank(lotteryCode) || !LotteryEnum.Lottery.contain(lotteryCode)) {
            return ResultBO.err("20001");
        }
        boolean flag = LotteryEnum.Lottery.contain(lotteryCode);
        if (!flag) {
            return ResultBO.err();
        }
        String channelId = getHeaderParam(request).getChannelId();
        List<DicDataDetailBO> dicDataList = iDicDataDetailService.findSimple(DicDataEnum.LOTTO_LIMIT_CHANNEL.getDicCode());
        ResultBO<LotteryIssueBaseBO> lotteryIssueBaseBOResultBO= iLotteryIssueService.findLotteryIssueBase(lotteryCode, platform,channelId,dicDataList);
        return getLotteryAddAwardInfo(lotteryIssueBaseBOResultBO,lotteryCode,platform,channelId);
    }
    
    /**
     * 设置彩种加奖信息
     * @param resultBO
     */
    private ResultBO<LotteryIssueBaseBO> getLotteryAddAwardInfo(ResultBO<LotteryIssueBaseBO> resultBO,Integer lotteryCode,int platform,String channelId){
        LotteryIssueBaseBO lotteryIssueBaseBO = null;
        if(resultBO.isOK()){
            if(!ObjectUtil.isBlank(resultBO.getData())){
                lotteryIssueBaseBO = resultBO.getData();
                try {
                    ResultBO<ActivityBO> activityBOResultBO = iActivityService.findActivityAddAwardInfo(lotteryCode,channelId,platform);
                    if(activityBOResultBO.isOK()){
                        if(!ObjectUtil.isBlank(activityBOResultBO.getData())){
                            ActivityBO activityBO = activityBOResultBO.getData();
                            lotteryIssueBaseBO.setActivityBO(activityBO);
                        }
                    }
                }catch (Exception e){
                    logger.error("获取彩种加奖信息失败！",e);
                }
            }
        }
        return ResultBO.ok(lotteryIssueBaseBO);
    }


    /**
     * 获取投注规则(新,分平台)
     *
     * @param lotteryCode 彩种编号
     * @return
     */
    @RequestMapping(value = "/betRule/")
    public Object getLotteryBetRule(@RequestParam(value = "lotteryCode") Integer lotteryCode,
                                              @RequestParam(value = "platform") Short platform) {
        if (ObjectUtil.isBlank(lotteryCode) || !LotteryEnum.Lottery.contain(lotteryCode)) {
            return ResultBO.err("20001");
        }
        boolean flag = LotteryEnum.Lottery.contain(lotteryCode);
        if (!flag) {
            return ResultBO.err();
        }
        return iLotteryIssueService.findLotteryIssueBase(lotteryCode, platform);
    }



    /**
     * 查询彩种集合
     *
     * @return
     */
    @RequestMapping(value = "/list")
    public Object getLotteryList(OperateAdVO vo,HttpServletRequest request) {
    	HeaderParam headerParam = getHeaderParam(request);
    	vo.setChannelId(headerParam.getChannelId());
        return iLotteryBuyService.findLotteryList(vo);
    }

    /**
     * 查询开奖集合
     *
     * @return
     */
    @RequestMapping(value = "/draw/list")
    public Object getDrawList() {
        return iLotteryBuyService.findDrawList();
    }


}
