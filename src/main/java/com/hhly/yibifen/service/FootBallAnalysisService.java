package com.hhly.yibifen.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hhly.skeleton.base.util.HttpUtil;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.yibifen.config.Constants;
import com.hhly.yibifen.entity.FootBallAnalysisBO;
import com.hhly.yibifen.entity.FootBallAnalysisOverviewBO;
import com.hhly.yibifen.entity.FootBallMatchOddBO;
import com.hhly.yibifen.entity.FootBallMatchOddDetailBO;

/**
 * @author lgs on
 * @version 1.0
 * @desc 移动端分析数据逻辑处理层
 * @date 2017/8/11.
 * @company 益彩网络科技有限公司
 */
@Component
public class FootBallAnalysisService {

    private static final Logger logger = Logger.getLogger(FootBallAnalysisService.class);

    @Value("${yibifen_api_url_head}")
    private String headUrl;

    /**
     * 获取分析数据。
     *
     * @param matchId
     * @return
     */
    public FootBallAnalysisBO findFootBallAnalysis(String matchId) {
        FootBallAnalysisBO bo = null;
        String url = headUrl + Constants.FOOTBALL_ANALYSIS_DETAIL;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("thirdId", matchId);
            params.put("lang", "zh");
            String json = HttpUtil.doGet(url, params);
            bo = JsonUtil.jsonToJcakObject(json, FootBallAnalysisBO.class);
            if (ObjectUtil.isBlank(bo) || !bo.getResult().equals("200")) {
                logger.error("yibifen FootBallAnalysis matchId= " + matchId + "is null");
                return null;
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return bo;
    }


    /**
     * 获取分析数据。
     *
     * @param matchId
     * @return
     */
    public FootBallAnalysisOverviewBO findFootBallAnalysisOverview(String matchId) {
        FootBallAnalysisOverviewBO bo = null;
        String url = headUrl + Constants.FOOTBALL_ANALYSIS_OVERVIEW;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("thirdId", matchId);
            params.put("lang", "zh");
            String json = HttpUtil.doGet(url, params);
            bo = JsonUtil.jsonToJcakObject(json, FootBallAnalysisOverviewBO.class);
            if (ObjectUtil.isBlank(bo) || !bo.getResult().equals("200")) {
                logger.error("yibifen FootBallAnalysisOverview matchId = " + matchId + "is null");
                return null;
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return bo;
    }

    /**
     * 获取足球指数亚盘大小球指数
     *
     * @param matchId
     * @return
     */
    public FootBallMatchOddBO findFootBallMatchOdd(String matchId, String oddType) {
        FootBallMatchOddBO bo = null;
        String url = headUrl + Constants.FOOTBALL_MATCHODD;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("thirdId", matchId);
            params.put("oddType", oddType);
            params.put("lang", "zh");
            String json = HttpUtil.doGet(url, params);
            bo = JsonUtil.jsonToJcakObject(json, FootBallMatchOddBO.class);
            if (ObjectUtil.isBlank(bo) || !bo.getResult().equals("200")) {
                logger.error("yibifen findFootBallMatchOdd matchId = " + matchId + "is null");
                return null;
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return bo;
    }


    /**
     * 获取足球指数亚盘大小球欧赔详情
     *
     * @param matchId
     * @return
     */
    public FootBallMatchOddDetailBO findFootBallMatchOddDetail(String matchId, String oddType, String companyId) {
        FootBallMatchOddDetailBO bo = null;
        String url = headUrl + Constants.FOOTBALL_MATCHODD_DETAIL;
        try {
            Map<String, String> params = new HashMap<>();
            params.put("thirdId", matchId);
            params.put("companyId", companyId);
            params.put("oddType", oddType);
            params.put("lang", "zh");
            String json = HttpUtil.doGet(url, params);
            bo = JsonUtil.jsonToJcakObject(json, FootBallMatchOddDetailBO.class);
            if (ObjectUtil.isBlank(bo) || !bo.getResult().equals("200")) {
                logger.error("yibifen findFootBallMatchOddDetail matchId = " + matchId + "is null");
                return null;
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        return bo;
    }

}
