package com.hhly.lotto.api.data.lotto.v1_0;
import java.util.Date;
import java.util.List;

import com.hhly.skeleton.lotto.base.sport.bo.JcMathSPBO;
import com.hhly.skeleton.lotto.base.sport.bo.JcMathWinSPBO;

/**
 * 
 * @author lidecheng

 * @date 2017年2月6日 下午4:01:10

 * @desc  竞彩足球前端数据接口
 *
 */
public interface JcDataCommV10Data {
	
    /**
     * 查询竞彩蓝球推荐赛事信息
     * @param lotteryCode
     * @param issueCode
     * @return
     */
    List<JcMathSPBO>  findSportMatchBBSPInfo(int lotteryCode,String issueCode,String queryDate,Integer saleStatus);
    
    /**
     * 查询快投单场致胜信息
     * @param lotteryCode
     * @param issueCode
     * @param saleEndTime
     * @return
     */
    JcMathWinSPBO findSportFBMatchDCZSInfo(long id,int lotteryCode,String issueCode,Date saleEndTime,Date startTime, String systemCode);
    /**
     * 查询竞彩足球推荐赛事信息
     * @param lotteryCode
     * @param issueCode
     * @param saleStatus 查询状态
     * @return
     */
    List<JcMathSPBO>  findSportMatchFBSPInfo(int lotteryCode,String issueCode,String queryDate,Integer saleStatus);
   
}
