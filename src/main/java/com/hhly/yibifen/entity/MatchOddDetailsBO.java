package com.hhly.yibifen.entity;

/**
 * @author lgs on
 * @version 1.0
 * @desc 欧赔详情
 * @date 2017/8/30.
 * @company 益彩网络科技有限公司
 */
public class MatchOddDetailsBO {
    /**
     * time : null
     * score : null
     * homeOdd : 1.03
     * hand : 0
     * guestOdd : 0.85
     */

    private String time;
    private String score;
    private Double homeOdd;
    private Double hand;
    private Double guestOdd;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public Double getHomeOdd() {
        return homeOdd;
    }

    public void setHomeOdd(Double homeOdd) {
        this.homeOdd = homeOdd;
    }

    public Double getHand() {
        return hand;
    }

    public void setHand(Double hand) {
        this.hand = hand;
    }

    public Double getGuestOdd() {
        return guestOdd;
    }

    public void setGuestOdd(Double guestOdd) {
        this.guestOdd = guestOdd;
    }
}
