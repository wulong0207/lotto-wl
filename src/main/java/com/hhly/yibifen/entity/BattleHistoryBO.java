package com.hhly.yibifen.entity;

/**
 * @author lgs on
 * @version 1.0
 * @desc 交战历史BO
 * @date 2017/8/11.
 * @company 益彩网络科技有限公司
 */
public class BattleHistoryBO {

    /**
     * time : 2017-03-25
     * matchType : 日职乙
     * home : 水户蜀葵
     * guest : 爱嫒FC
     * homeScore : 1
     * guestScore : 0
     * markTeam : 2
     * homeGround : false
     * result : -1
     * teamColor : 0
     * ctotScore : null
     * tot : null
     * casLetGoal : null
     * let : null
     */

    private String time;
    private String matchType;
    private String home;
    private String guest;
    private String homeScore;
    private String guestScore;
    private String markTeam;
    private Boolean homeGround;
    private String result;
    private String teamColor;
    private String ctotScore;
    private String tot;
    private String casLetGoal;
    private String let;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(String homeScore) {
        this.homeScore = homeScore;
    }

    public String getGuestScore() {
        return guestScore;
    }

    public void setGuestScore(String guestScore) {
        this.guestScore = guestScore;
    }

    public String getMarkTeam() {
        return markTeam;
    }

    public void setMarkTeam(String markTeam) {
        this.markTeam = markTeam;
    }

    public Boolean getHomeGround() {
        return homeGround;
    }

    public void setHomeGround(Boolean homeGround) {
        this.homeGround = homeGround;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(String teamColor) {
        this.teamColor = teamColor;
    }

    public String getCtotScore() {
        return ctotScore;
    }

    public void setCtotScore(String ctotScore) {
        this.ctotScore = ctotScore;
    }

    public String getTot() {
        return tot;
    }

    public void setTot(String tot) {
        this.tot = tot;
    }

    public String getCasLetGoal() {
        return casLetGoal;
    }

    public void setCasLetGoal(String casLetGoal) {
        this.casLetGoal = casLetGoal;
    }

    public String getLet() {
        return let;
    }

    public void setLet(String let) {
        this.let = let;
    }
}
