package com.hhly.lotto.api.common.controller.sport.v1_0;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.lotto.service.IFootBallAnalysisService;
import com.hhly.lottocore.remote.lotto.service.IJcDataService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.database.vo.OddsFbEuropeOddsMeanVO;
import com.hhly.skeleton.lotto.base.ybf.vo.AnalysisVO;
import com.hhly.yibifen.entity.FootBallMatchOddBO;
import com.hhly.yibifen.entity.FootBallMatchOddDetailBO;
import com.hhly.yibifen.service.FootBallAnalysisService;

/**
 * @author lgs on
 * @version 1.0
 * @desc 足球分析数据
 * @date 2017/8/21.
 * @company 益彩网络科技有限公司
 */
public class FootBallAnalysisCommonController extends BaseController {

    @Autowired
    private FootBallAnalysisService footBallAnalysisService;

    @Autowired
    private IFootBallAnalysisService iFootBallAnalysisService;

    @Autowired
    private IJcDataService iJcDataService;

    /**
     * 获取对阵未来赛事
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/future-match")
    public String findFutureMatchById(AnalysisVO vo) throws IOException, URISyntaxException {
        return iFootBallAnalysisService.getFootBallFutureMatch(vo);
    }

    /**
     * 获取两个球队的排名
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/match-rank")
    public String findScoreRankById(AnalysisVO vo) throws IOException, URISyntaxException {
        return iFootBallAnalysisService.getMatchRankCount(vo);
    }


    /**
     * 双方交战战绩统计
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/war", method = RequestMethod.GET)
    public String getFootBallWarCountBO(AnalysisVO vo) throws IOException, URISyntaxException {
        return iFootBallAnalysisService.getFootBallWarCountBO(vo);
    }

    /**
     * 统计近期战绩
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/recent-record", method = RequestMethod.GET)
    public String FootBallRecentRecordCount(AnalysisVO vo) throws IOException, URISyntaxException {
        return iFootBallAnalysisService.getFootBallRecentRecordCount(vo);
    }


    /**
     * 球队历史对阵
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/history-match", method = RequestMethod.GET)
    public String getFootBallMatchAnsBO(AnalysisVO vo) throws IOException, URISyntaxException {
        return iFootBallAnalysisService.getFootBallMatchAnsBO(vo);
    }

    /**
     * 获取赛果统计
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/result-count", method = RequestMethod.GET)
    public String getMatchResultCount(AnalysisVO vo) throws IOException, URISyntaxException {
        return iFootBallAnalysisService.getMatchResultCount(vo);
    }


    /**
     * 获取对阵亚盘 大小球 胜平负欧赔 即时盘和初盘
     *
     * @param matchId
     * @param oddType 1.亚盘，2。胜平负 欧赔， 3。大小球
     * @return
     */
    @RequestMapping(value = "/match-odd/{matchId}/{oddType}")
    public Object findFootBallMatchOddBOById(@PathVariable("matchId") String matchId,
                                             @PathVariable("oddType") String oddType) {
        FootBallMatchOddBO bo = footBallAnalysisService.findFootBallMatchOdd(matchId, oddType);
        if (ObjectUtil.isBlank(bo)) {
            return ResultBO.err();
        }
        return ResultBO.ok(bo.getListOdd());
    }

    /**
     * 获取对阵亚盘 大小球 胜平负欧赔 赔率变化
     *
     * @param matchId
     * @param oddType 1.亚盘，2。胜平负 欧赔， 3。大小球
     * @return
     */
    @RequestMapping(value = "/match-odd/detail/{matchId}/{companyId}/{oddType}")
    public Object findFootBallMatchOddDetailBOById(@PathVariable("matchId") String matchId,
                                                   @PathVariable("oddType") String oddType,
                                                   @PathVariable("companyId") String companyId) {
        FootBallMatchOddDetailBO bo = footBallAnalysisService.findFootBallMatchOddDetail(matchId, oddType, companyId);
        if (ObjectUtil.isBlank(bo)) {
            return ResultBO.err();
        }
        return ResultBO.ok(bo.getDetails());
    }

    /**
     * 平均欧赔
     *
     * @param vo
     * @return
     */
    @RequestMapping(value = "/match-odds/avg")
    public Object findFootBallAvgOdds(AnalysisVO vo) {
        OddsFbEuropeOddsMeanVO oddsFbEuropeOddsMeanVO = new OddsFbEuropeOddsMeanVO();
        oddsFbEuropeOddsMeanVO.setSourceId(String.valueOf(vo.getMatchId()));
        oddsFbEuropeOddsMeanVO.setSourceType(1);
        oddsFbEuropeOddsMeanVO.setType(1);
        return iJcDataService.findAvgOddsBySourceId(oddsFbEuropeOddsMeanVO);
    }

    @RequestMapping(value = "/match-kelly/avg")
    public Object findFootBallKellyOdds(AnalysisVO vo) {
        OddsFbEuropeOddsMeanVO oddsFbEuropeOddsMeanVO = new OddsFbEuropeOddsMeanVO();
        oddsFbEuropeOddsMeanVO.setSourceId(String.valueOf(vo.getMatchId()));
        oddsFbEuropeOddsMeanVO.setSourceType(1);
        oddsFbEuropeOddsMeanVO.setType(1);
        return iJcDataService.findAvgKellyBySourceId(oddsFbEuropeOddsMeanVO);
    }

    /**
     * 球队近期战绩
     *
     * @param vo
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    @RequestMapping(value = "/match-count")
    public Object getFootBallMatchCount(AnalysisVO vo) throws IOException, URISyntaxException {
        return iFootBallAnalysisService.getFootBallMatchCount(vo);
    }
}
