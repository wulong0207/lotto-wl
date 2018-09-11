package com.hhly.yibifen.entity;

/**
 * @author lgs on
 * @version 1.0
 * @desc 未来赛事对阵
 * @date 2017/8/21.
 * @company 益彩网络科技有限公司
 */
public class FutureMatchTeamBO {

    /**
     * date : 2017-08-26
     * leagueName : 西甲
     * homeName : 皇家社会
     * guestName : 比利亚雷亚尔
     * diffDays : 4
     */

    private String date;
    private String leagueName;
    private String homeName;
    private String guestName;
    private int diffDays;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getDiffDays() {
        return diffDays;
    }

    public void setDiffDays(int diffDays) {
        this.diffDays = diffDays;
    }
}
