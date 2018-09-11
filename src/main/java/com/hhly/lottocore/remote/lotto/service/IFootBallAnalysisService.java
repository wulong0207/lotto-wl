package com.hhly.lottocore.remote.lotto.service;

import java.io.IOException;
import java.net.URISyntaxException;

import com.hhly.skeleton.lotto.base.ybf.vo.AnalysisVO;

/**
 * @author liguisheng
 * @date 2017年11月10日 下午4:01:10
 * @desc 足球分析数据
 */
public interface IFootBallAnalysisService {

    /**
     * 获取两队近期交战战绩统计
     *
     * @param vo
     * @return
     */
    String getFootBallWarCountBO(AnalysisVO vo) throws IOException, URISyntaxException;


    /**
     * 获取主客队近期战绩走势
     *
     * @param vo
     * @return
     */
    String getFootBallRecentRecordCount(AnalysisVO vo) throws IOException, URISyntaxException;

    /**
     * 球队历史对阵
     *
     * @param vo
     * @return
     */
    String getFootBallMatchAnsBO(AnalysisVO vo) throws IOException, URISyntaxException;

    /**
     * 获取球队双方排名
     *
     * @param vo
     * @return
     */
    String getMatchRankCount(AnalysisVO vo) throws IOException, URISyntaxException;

    /**
     * 获取赛果统计
     *
     * @param vo
     * @return
     */
    String getMatchResultCount(AnalysisVO vo) throws IOException, URISyntaxException;

    /**
     * 获取未来赛事
     *
     * @param vo
     * @return
     */
    String getFootBallFutureMatch(AnalysisVO vo) throws IOException, URISyntaxException;


    /**
     * 足球球队最近对阵信息
     *
     * @param vo
     * @return
     */
    String getFootBallMatchCount(AnalysisVO vo) throws IOException, URISyntaxException;
}
