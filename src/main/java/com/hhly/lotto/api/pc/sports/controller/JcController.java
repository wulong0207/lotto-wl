package com.hhly.lotto.api.pc.sports.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.lotto.service.IJcDataService;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.common.SportEnum;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.OddsUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.base.valid.MatchPattern;
import com.hhly.skeleton.lotto.base.database.bo.OddsFbEuropeOddsBO;
import com.hhly.skeleton.lotto.base.database.vo.OddsFbEuropeOddsVO;
import com.hhly.skeleton.lotto.base.issue.bo.IssueLottJCBO;
import com.hhly.skeleton.lotto.base.issue.vo.LottoIssueVO;
import com.hhly.skeleton.lotto.base.sport.bo.JczqMainDataBO;
import com.hhly.skeleton.lotto.base.sport.vo.JcParamVO;

/**
 * @version 1.0
 * @auth lgs on
 * @date 2017/2/26.
 * @desc 竞彩数据API接口
 * @compay 益彩网络科技有限公司
 */
@RestController
@RequestMapping("/pc/jc")
public class JcController extends BaseController {

    private static Logger logger = Logger.getLogger(JcController.class);

    @Autowired
    private IJcDataService jcDataService;

    @Autowired
    private ILotteryIssueService iLotteryIssueService;

    /**
     * 竞彩足球数据接口
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/football", method = RequestMethod.GET)
    public Object getJczqData(JcParamVO vo) {
        if (!StringUtil.isBlank(vo.getIssueCode()) && !vo.getIssueCode().matches(MatchPattern.NUM))
            return ResultBO.err("20001");
        return jcDataService.getJczqDataJson(vo);
    }

    /**
     * 竞彩足球数据接口
     *
     * @return
     */
    @RequestMapping(value = "/football", method = RequestMethod.GET, params = "single=1")
    public Object getJczqSingleUpData() {
        return jcDataService.findSingleUpMatchData(new JcParamVO());
    }


    @RequestMapping(value = "/match_info", method = RequestMethod.GET)
    public Object getMatchInfo(JcParamVO vo) {
        ResultBO<JczqMainDataBO> bos = jcDataService.findJczqData(vo);
        return ResultBO.ok(bos.getData().getMatchInfo());
    }

    /**
     * 竞彩足球历史SP值
     *
     * @param id
     * @param type
     * @return
     */
    @RequestMapping(value = "/football/sp/history", method = RequestMethod.GET)
    public Object getJczqSpHistory(@RequestParam(value = "id", required = true) Long id,
                                   @RequestParam(value = "type", required = true, defaultValue = "1") Short type) {
        if (type == null || (type != 1 && type != 2)) {
            type = 1;
        }
        Assert.isTrue(!ObjectUtil.isBlank(id));
        return jcDataService.findJczqWdfSpData(id, type);
    }

    /**
     * 竞彩足球最新sp值
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/football/sp", method = RequestMethod.GET)
    public Object getJczqSp(@RequestParam(value = "id", required = true) Long id) {
        Assert.isTrue(!ObjectUtil.isBlank(id));
        return jcDataService.findFootBallSpById(id);
    }


    /**
     * 竞彩篮球对阵数据VO
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/basketball", method = RequestMethod.GET)
    public Object getJclqData(JcParamVO vo) {
        if (!StringUtil.isBlank(vo.getIssueCode()) && !vo.getIssueCode().matches(MatchPattern.NUM))
            return ResultBO.err("20001");
        return jcDataService.findJclqDataJson(vo);
    }

    /**
     * 竞彩篮球胜负历史SP值
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/basketball/wfSp/history/{id}/{type}", method = RequestMethod.GET)
    public Object getJclqWfSpHistory(@PathVariable(value = "id") Long id,
                                     @PathVariable(value = "type") Short type) {
        if (type == null || (type != 1 && type != 2)) {
            type = 1;
        }
        Assert.isTrue(!ObjectUtil.isBlank(id));
        return jcDataService.findJclqWfHistorySpData(id, type);
    }


    /**
     * 竞彩篮球比分差历史SP值
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/basketball/wsSp/history/{id}", method = RequestMethod.GET)
    public Object getJclqWsSpHistory(@PathVariable(value = "id") Long id) {
        Assert.isTrue(!ObjectUtil.isBlank(id));
        return jcDataService.findJclqWSHistorySpData(id);
    }

    /**
     * 竞彩篮球大小分历史SP值
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/basketball/bsSp/history/{id}", method = RequestMethod.GET)
    public Object getJclqBsSpHistory(@PathVariable(value = "id") Long id) {
        Assert.isTrue(!ObjectUtil.isBlank(id));
        return jcDataService.findJclqSssHistorySpData(id);
    }

    /**
     * 老足彩胜负彩数据接口
     *
     * @param issueCode 彩期
     * @return
     */
    @RequestMapping(value = "/sfc", method = RequestMethod.GET)
    public Object getJcSfcData(@RequestParam(value = "issueCode", required = true) String issueCode) {
        if (!issueCode.matches(MatchPattern.NUM))
            return ResultBO.err("20001");
        return jcDataService.findJcOldData(issueCode, String.valueOf(LotteryEnum.Lottery.SFC.getName()));
    }

    /**
     * 老足彩6场半全场数据接口
     *
     * @param issueCode 彩期
     * @return
     */
    @RequestMapping(value = "/hfWdf", method = RequestMethod.GET)
    public Object getJcHfWdfData(@RequestParam(value = "issueCode", required = true) String issueCode) {
        if (!issueCode.matches(MatchPattern.NUM))
            return ResultBO.err("20001");
        return jcDataService.findJcOldData(issueCode, String.valueOf(LotteryEnum.Lottery.ZC6.getName()));
    }

    /**
     * 老足彩4场进球数据接口
     *
     * @param issueCode 彩期
     * @return
     */
    @RequestMapping(value = "/goal", method = RequestMethod.GET)
    public Object getJcGaolData(@RequestParam(value = "issueCode", required = true) String issueCode) {
        if (!issueCode.matches(MatchPattern.NUM))
            return ResultBO.err("20001");
        return jcDataService.findJcOldData(issueCode, String.valueOf(LotteryEnum.Lottery.JQ4.getName()));
    }


    @RequestMapping(value = "/order/{systemCode}", method = RequestMethod.GET)
    public Object getOrderInfo(@PathVariable("systemCode") String systemCode) {
        return jcDataService.findJczqOrderBOBySystemCode(systemCode);
    }


    /**
     * 查询竞彩足球彩种彩期信息
     *
     * @return
     */
    @RequestMapping(value = "/getJczqIssue", method = RequestMethod.GET)
    public Object getJczqIssue(LottoIssueVO vo) {
        //如果没有传则默认第一页 每页7条数据
        if (ObjectUtil.isBlank(vo.getPageIndex())) {
            vo.setPageIndex(0);
        }
        if (ObjectUtil.isBlank(vo.getPageSize())) {
            vo.setPageSize(7);
        }
        vo.setLotteryCode(Lottery.FB.getName());
        return iLotteryIssueService.findIssueListByCode(vo);
    }

    /**
     * 查询竞彩篮球彩种彩期信息
     *
     * @return
     */
    @RequestMapping(value = "/getJclqIssue", method = RequestMethod.GET)
    public Object getJclqIssue(LottoIssueVO vo) {
        //如果没有传则默认第一页 每页7条数据
        if (ObjectUtil.isBlank(vo.getPageIndex())) {
            vo.setPageIndex(0);
        }
        if (ObjectUtil.isBlank(vo.getPageSize())) {
            vo.setPageSize(7);
        }
        vo.setLotteryCode(Lottery.BB.getName());
        return iLotteryIssueService.findIssueListByCode(vo);
    }


    /**
     * 查询竞彩篮球彩种彩期信息
     *
     * @return
     */
    @RequestMapping(value = "/getIssue", method = RequestMethod.GET)
    public Object getIssue(LottoIssueVO vo) {
        //如果没有传则默认第一页 每页7条数据
        if (ObjectUtil.isBlank(vo.getPageIndex())) {
            vo.setPageIndex(0);
        }
        if (ObjectUtil.isBlank(vo.getPageSize())) {
            vo.setPageSize(7);
        }
        if (ObjectUtil.isBlank(vo.getLotteryCode())) {
            return ResultBO.err("20001");
        }
        return iLotteryIssueService.findIssueListByCode(vo);
    }

    /**
     * 查询胜负彩历史彩期
     *
     * @return
     */
    @RequestMapping(value = "/oldLotteryIssue", method = RequestMethod.GET)
    public Object getOldIssue(LottoIssueVO vo) {
        if (ObjectUtil.isBlank(vo.getLotteryCode()) || !Lottery.contain(vo.getLotteryCode())) {
            return ResultBO.err("20001");
        }

        //如果没有传则默认第一页 每页7条数据
        if (ObjectUtil.isBlank(vo.getPageIndex())) {
            vo.setPageIndex(0);
        }
        if (ObjectUtil.isBlank(vo.getPageSize())) {
            vo.setPageSize(7);
        }

        Map<String, Object> map = new HashMap<>();
        List<IssueLottJCBO> afterFiveIssue = iLotteryIssueService.findAfterFiveIssueListByCode(vo).getData();
        PagingBO<IssueLottJCBO> historyIssue = iLotteryIssueService.findIssueListByCode(vo).getData();
        //设置当前期奖池金额
        List<IssueLottJCBO> historyList = historyIssue.getData();
        afterFiveIssue.get(0).setJackpotAmount(historyList.get(1).getJackpotAmount());

        map.put("currentIssue", afterFiveIssue);
        map.put("historyIssue", historyIssue);
        map.put("serverTime", new Date());
        return ResultBO.ok(map);
    }

    /**
     * 根据
     *
     * @param issueCode
     * @return
     */
    @RequestMapping(value = "/getMainJczqData/{issueCode}", method = RequestMethod.GET)
    public Object getMainJczqByIssueCode(@PathVariable(value = "issueCode") String issueCode) {
        if (!issueCode.matches(MatchPattern.NUM))
            return ResultBO.err("20001");
        return jcDataService.findJczqOrderDataByIssueCode(issueCode);
    }

    /**
     * 查看sp值变化
     *
     * @param type
     * @return
     */
    @RequestMapping(value = "/getTrendSp", method = RequestMethod.GET)
    public Object getTrendSp(@RequestParam("type") String type) {
        return jcDataService.findJcTrendSP(type);
    }

    /**
     * 获取北京单场数据
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/bj", method = RequestMethod.GET)
    public Object getBjData(JcParamVO vo) {
        return jcDataService.findBjMainData(vo);
    }


    /**
     * 获取北京单场数据
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/bjMatch", method = RequestMethod.GET)
    public Object getBjMatchData(JcParamVO vo) {
        return jcDataService.findBjMatch(vo);
    }


    /**
     * 获取北京单场单式上传数据
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/bjSingle", method = RequestMethod.GET)
    public Object getBjSingleData(JcParamVO vo) {
        return jcDataService.findBjSingleData(vo);
    }


    /**
     * 获取竞彩足球推荐赛事信息
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/fbRecom", method = RequestMethod.GET)
    public Object getFbRecomData(JcParamVO vo) {
        return ResultBO.ok(jcDataService.findSportMatchFBSPInfo(Lottery.FB.getName(), vo.getIssueCode(), null));
    }

    /**
     * 获取竞彩篮球推荐赛事信息
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/bbRecom", method = RequestMethod.GET)
    public Object getBbRecomData(JcParamVO vo) {
        return ResultBO.ok(jcDataService.findSportMatchBBSPInfo(Lottery.BB.getName(), vo.getIssueCode(), null));
    }

    /**
     * 获取一比分即时比分数据
     *
     * @return
     */
    @RequestMapping(value = "/ybf/immediate-score", method = RequestMethod.GET)
    public Object getYbfImmediateScore() {
        return jcDataService.getYbfJishiBifen();
    }

    /**
     * 获取篮球即时比分
     *
     * @return
     */
    @RequestMapping(value = "/ybf/basketball-score", method = RequestMethod.GET)
    public Object getBasketBallImmediateScore() {
        return jcDataService.getBasketBallImmediateScore();
    }

    /**
     * 获取平均欧赔
     *
     * @return
     */
    @RequestMapping(value = "/odds", method = RequestMethod.GET)
    public Object findOdds(@RequestParam(value = "indexId", required = true) Integer indexId,
                           @RequestParam(value = "itemId", required = true) Integer itemId,
                           @RequestParam(value = "lotteryCode", required = true) String lotteryCode,
                           @RequestParam(value = "issueCode", required = false) String issueCode) {
        List<OddsFbEuropeOddsBO> list = null;
        if (ObjectUtil.isBlank(lotteryCode)) {
            return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
        }
        OddsFbEuropeOddsVO vo = new OddsFbEuropeOddsVO();
        vo.setLotteryCode(lotteryCode);
        vo.setIssueCode(issueCode);
        vo.setMatchStatus(String.valueOf(SportEnum.JcMatchStatusEnum.SALE.getCode()));
        vo.setSourceType(1);
        vo.setType(1);
        vo.setEuropeId(itemId);
        switch (itemId) {
            case -1:
                list = jcDataService.findAvgOdds(vo);
                break;
            default:
                list = jcDataService.findOddsByEuropeId(vo);
                break;
        }

        List<Map<String, Map<String, Object>>> resultList = new ArrayList<>();

        switch (indexId) {
            case 2://概率
                for (OddsFbEuropeOddsBO bo : list) {
                    Map<String, Map<String, Object>> map = new HashMap<>();
                    Map<String, Object> spMap = new HashMap<>();
                    spMap.put("w", bo.getProbabilityHN());
                    spMap.put("d", bo.getProbabilityDN());
                    spMap.put("f", bo.getProbabilityGN());
                    map.put(bo.getSourceId(), spMap);
                    resultList.add(map);
                }
                break;
            case 3://凯利
                for (OddsFbEuropeOddsBO bo : list) {
                    Map<String, Map<String, Object>> map = new HashMap<>();
                    Map<String, Object> spMap = new HashMap<>();
                    spMap.put("w", OddsUtil.getKelly(bo.getHomewinN(), bo.getProbabilityTN()));
                    spMap.put("d", OddsUtil.getKelly(bo.getDrawN(), bo.getProbabilityTN()));
                    spMap.put("f", OddsUtil.getKelly(bo.getGuestwinN(), bo.getProbabilityTN()));
                    map.put(bo.getSourceId(), spMap);
                    resultList.add(map);
                }
                break;
            case 1://欧赔
            default:
                for (OddsFbEuropeOddsBO bo : list) {
                    Map<String, Map<String, Object>> map = new HashMap<>();
                    Map<String, Object> spMap = new HashMap<>();
                    spMap.put("w", bo.getHomewinN());
                    spMap.put("d", bo.getDrawN());
                    spMap.put("f", bo.getGuestwinN());
                    map.put(bo.getSourceId(), spMap);
                    resultList.add(map);
                }
                break;
        }
        return ResultBO.ok(resultList);
    }


}
