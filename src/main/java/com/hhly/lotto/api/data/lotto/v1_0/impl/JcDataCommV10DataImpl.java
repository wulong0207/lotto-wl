package com.hhly.lotto.api.data.lotto.v1_0.impl;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.commoncore.remote.lotto.service.IJcDataCommService;
import com.hhly.lotto.api.data.lotto.v1_0.JcDataCommV10Data;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.lotto.base.sport.bo.JcMathSPBO;
import com.hhly.skeleton.lotto.base.sport.bo.JcMathWinSPBO;

/**
 * @desc 广告管理服务实现类
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class JcDataCommV10DataImpl  implements JcDataCommV10Data{
	@Autowired
	private IJcDataCommService JcDataCommService;
	@Autowired
    private RedisUtil redisUtil;
	 /**
     * 查询快投单场致胜信息
     *
     * @param lotteryCode
     * @param issueCode
     * @param saleEndTime
     * @return
     */
    public JcMathWinSPBO findSportFBMatchDCZSInfo(long id, int lotteryCode, String issueCode, Date saleEndTime, Date startTime, String systemCode) {
        String key = CacheConstants.getFBMatchSpCacheKey(lotteryCode, systemCode, saleEndTime);
        JcMathWinSPBO jcMathWinSPBO = (JcMathWinSPBO) redisUtil.getObj(key);
        if (jcMathWinSPBO != null) {
            if (DateUtil.isOver(new Timestamp(jcMathWinSPBO.getSaleEndTime().getTime()))) {
                //时间过去清理缓存
                jcMathWinSPBO = null;
            }
        }
        if (jcMathWinSPBO == null || Objects.equals(systemCode, jcMathWinSPBO.getSystemCode())) {
            //查询单场至胜时间相同情况下配对赛事信息（首页接口）
            jcMathWinSPBO = JcDataCommService.findSportFBMatchDCZSInfo(id,  lotteryCode, issueCode, saleEndTime,  startTime,  systemCode).getData();
            redisUtil.addObj(key, jcMathWinSPBO, (long) Constants.DAY_1);
        }   
        return jcMathWinSPBO;
    }
    
    /**
     * 查询竞彩足球对阵信息
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<JcMathSPBO> findSportMatchFBSPInfo(int lotteryCode, String issueCode, String queryDate,Integer saleStatus) {
        String key = CacheConstants.C_COMM_MATCH_FB_RECOMMEND_LIST + SymbolConstants.UNDERLINE + lotteryCode + SymbolConstants.UNDERLINE + queryDate;
        List<JcMathSPBO> list = (List<JcMathSPBO>) redisUtil.getObj(key);
        if (list != null) {
            //删除过期数据
            Iterator<JcMathSPBO> it = list.iterator();
            while (it.hasNext()) {
                JcMathSPBO bo = it.next();
                if (DateUtil.isOver(new Timestamp(bo.getSaleEndTime().getTime()))) {
                    it.remove();
                }
            }
        }
        if (list == null || list.size() == 0) {
            list = JcDataCommService.findSportMatchFBSPInfo(lotteryCode, issueCode, queryDate, saleStatus).getData();
            redisUtil.addObj(key, list, (long) Constants.DAY_1);
        }
        return list;
    }
    
    /**
     * 查询竞彩篮球对阵信息
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<JcMathSPBO> findSportMatchBBSPInfo(int lotteryCode, String issueCode, String queryDate,Integer saleStatus) {
        String key = CacheConstants.getBBMatchSpCacheKeyBySaleStatus(lotteryCode, queryDate, saleStatus);
        List<JcMathSPBO> list = (List<JcMathSPBO>) redisUtil.getObj(key);
        if (list != null) {
            //删除过期数据
            Iterator<JcMathSPBO> it = list.iterator();
            while (it.hasNext()) {
                JcMathSPBO bo = it.next();
                if (DateUtil.isOver(new Timestamp(bo.getSaleEndTime().getTime()))) {
                    it.remove();
                }
            }
        }
        if (list == null || list.size() == 0) {
            list = JcDataCommService.findSportMatchBBSPInfo(lotteryCode, issueCode, queryDate, saleStatus).getData();
            redisUtil.addObj(key, list, (long) Constants.DAY_1);
        }
        return list;
    }    

	
}