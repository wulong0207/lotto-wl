package com.hhly.lotto.api.data.copyorder.v1_0;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.IssueUtil;
import com.hhly.skeleton.base.util.MathUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.UserInfoBOUtil;
import com.hhly.skeleton.lotto.base.ordercopy.bo.CommissionBO;
import com.hhly.skeleton.lotto.base.ordercopy.bo.QueryBO;
import com.hhly.skeleton.lotto.base.ordercopy.bo.QueryUserIssueInfoBO;

/**
 * @Description 
 * @Author longguoyou
 * @Date  2018/1/10 14:51
 * @Since 1.8
 */
@Component
public class OrderCopyData {


    @Value("${before_file_url}")
    protected String beforeFileUrl;

    /**
     * 处理实单列表
     *
     * @param listQueryBO
     */
    public void handleIssueInfo(List<QueryBO> listQueryBO){
        if (!ObjectUtil.isBlank(listQueryBO)) {
            for (QueryBO bean : listQueryBO) {
                bean.setPassway(IssueUtil.getPasswayFromBetContent(bean.getBetContent()));
                if (!ObjectUtil.isBlank(bean.getMaxRoiDb())) {
                    bean.setMaxRoi(IssueUtil.getOnlyMultiple(bean.getMaxRoiDb()));
                }
                if (!ObjectUtil.isBlank(bean.getHitRateDb())) {
                    bean.setHitRate(IssueUtil.getOnlyPercent(bean.getHitRateDb()));
                }
                bean.setContinueHit(IssueUtil.getContinueHitStr(bean.getContinueHitDb()));
                bean.setFollowNumStr(IssueUtil.replaceMantissa(bean.getFollowNum()));
                bean.setEndTime(IssueUtil.getEndTimeStr(bean.getEndTimeDb()));
                bean.setCreateTimeStr(IssueUtil.getShowDateStr(bean.getCreateTime()));
                bean.setRecentRecord(IssueUtil.getRecentRecordStr(bean.getRecentRecord()));
                bean.setHeadUrl(UserInfoBOUtil.getHeadUrl(bean.getHeadUrl(), beforeFileUrl));
                if(!ObjectUtil.isBlank(bean.getBeginTeamName())){
                    String[] againstNames = bean.getBeginTeamName().split(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR);
                    if(!ObjectUtil.isBlank(againstNames) && againstNames.length == Constants.NUM_2){
                        bean.setHomeTeamName(againstNames[0]);
                        bean.setGuestTeamName(againstNames[1]);
                    }
                }
                if(!ObjectUtil.isBlank(bean.getIssueType()) && bean.getIssueType() == Constants.NUM_1){//实单推荐理由置空
                    bean.setRecommendReason(null);
                }
            }
        }
    }

    /**
     * 处理发单用户列表
     * @param listQueryUserIssueInfo
     */
    public void handleIssueUserInfo(List<QueryUserIssueInfoBO> listQueryUserIssueInfo){
        if(!ObjectUtil.isBlank(listQueryUserIssueInfo)){
            for(QueryUserIssueInfoBO bean : listQueryUserIssueInfo){
                /** 处理显示中文、数字格式*/
                bean.setFocusNumStr(IssueUtil.replaceMantissa(bean.getFocusNum()));
                bean.setRecentRecord(IssueUtil.getRecentRecordStr(bean.getRecentRecord()));
                if(!ObjectUtil.isBlank(bean.getBonusRateDb())){
                    bean.setBonusRate(IssueUtil.getOnlyPercent(Double.valueOf(bean.getBonusRateDb()+ SymbolConstants.ENPTY_STRING)));
                }
                bean.setHitRate(IssueUtil.getOnlyPercent(bean.getHitRateDb()));
                bean.setContinueHit(IssueUtil.getContinueHitStr(bean.getContinueHitDb()));
                bean.setHeadUrl(UserInfoBOUtil.getHeadUrl(bean.getHeadUrl(), beforeFileUrl));
            }
        }
    }

    /**
     * 处理返佣情况信息列表
     * @param listCommissions
     */
    public void handleCommissions(List<CommissionBO> listCommissions){
        if(!ObjectUtil.isBlank(listCommissions)){
            for(CommissionBO bean : listCommissions){
                if(!ObjectUtil.isBlank(bean.getCommissionAmount())){
                    bean.setCommissionAmount(MathUtil.round(bean.getCommissionAmount(), 2));
                }
                bean.setCreateTimeStr(DateUtil.convertDateToStr(bean.getCreateTime(), DateUtil.FORMAT_MM_DD));
                bean.setCreateTime(null);
            }
        }
    }

    /**
     * 处理返佣明细信息列表
     * @param listCommissionDetails
     */
    public void handleCommissionDetails(List<CommissionBO> listCommissionDetails){
        if(!ObjectUtil.isBlank(listCommissionDetails)){
            for(CommissionBO bean : listCommissionDetails){
                if(!ObjectUtil.isBlank(bean.getCommissionAmount())){
                    bean.setCommissionAmount(MathUtil.round(bean.getCommissionAmount(), 2));
                }
            }
        }
    }
}
