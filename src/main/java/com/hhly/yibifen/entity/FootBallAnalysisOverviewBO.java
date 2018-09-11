package com.hhly.yibifen.entity;

import java.util.List;
import java.util.Map;

/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2017/8/21.
 * @company 益彩网络科技有限公司
 */
public class FootBallAnalysisOverviewBO {


    /**
     * result : 200
     * lineUp : {"result":"200","homeLineUp":[],"guestLineUp":[]}
     * attackDefense : {"guestFieldGoal":"1.67","guestFieldLose":"1.50","sizeHandicap":"2.5","homeFieldGoal":"1.50","homeFieldLose":"1.67"}
     * sizeTrend : {}
     * scoreRank : {"home":{"rank":"10","team":"弗鲁米嫩塞","vsCount":"20","win":"6","draw":"9","lose":"5","integral":"27","goalDiff":"2","goal":"29","miss":"27"},"guest":{"rank":"13","team":"米内罗竞技","vsCount":"20","win":"7","draw":"5","lose":"8","integral":"26","goalDiff":"-2","goal":"21","miss":"23"}}
     * bothRecord : {"home":{"historyWin":2,"historyDraw":2,"recentRecord":[0,0,1,0,1,2],"futureMatch":{"team":"利加大学","logoUrl":"http://pic.13322.com/icons/teams/100/1949.png","diffDays":1}},"guest":{"historyWin":2,"historyDraw":2,"recentRecord":[1,0,2,2,1,2],"futureMatch":{"team":"庞特普雷塔","logoUrl":"http://pic.13322.com/icons/teams/100/381.png","diffDays":4}}}
     * recommend : <p><span style="font-size: 16px;">六★品球师：弗鲁米嫩塞上场巴甲赛事客场对阵桑托斯，球队全场威胁攻势寥寥无几，而且连续作客出征，体能有所影响。米内罗竞技上场巴甲赛事主场迎战弗拉门戈，在场面上占优势，最终以2-0轻松击败对手，终止联赛2连败，士气有所提升故此本场就算客场征战弗鲁米嫩塞，米内罗竞技还是具备一定的抗衡能力。</span><span style="font-size: 12px;">（免责声明：个人观点并不代表一比分官方网站）</span></p><p><br/></p>
     * leagueId : 4
     * fullScoreRank : 1
     * asiaTrend : {"homeRecent":{"trendList":[{"let":1,"homeGround":false},{"let":1,"homeGround":false},{"let":1,"homeGround":true},{"let":1,"homeGround":false},{"let":1,"homeGround":false},{"let":2,"homeGround":true}],"statistics":{"draw":0,"lose":1,"winPercent":"83%","vsCount":6,"win":5,"losePercent":"17%","drawPercent":"0%"}},"battleHistory":{"pointList":[{"point1":"1-2-2","point2":"4-2-1","point3":"1-1-2","point4":"1-2-1","point5":"4-1-2","point6":"0-0-1"},{"point1":"1","point2":"0","point3":"0.5","point4":"0/0.5","point5":"0/0.5","point6":"0/0.5"},{"point1":1,"point2":1,"point3":1,"point4":2,"point5":2,"point6":2}],"statistics":{"draw":0,"lose":3,"winPercent":"50%","vsCount":6,"losePercent":"50%","win":3,"drawPercent":"0%"},"letList":[{"let":1,"homeGround":false},{"let":1,"homeGround":true},{"let":1,"homeGround":false},{"let":2,"homeGround":true},{"let":2,"homeGround":false},{"let":2,"homeGround":true}]},"guestRecent":{"trendList":[{"let":1,"homeGround":true},{"let":2,"homeGround":true},{"let":2,"homeGround":false},{"let":2,"homeGround":true},{"let":1,"homeGround":false},{"let":2,"homeGround":false}],"statistics":{"draw":0,"lose":4,"winPercent":"33%","vsCount":6,"win":2,"losePercent":"67%","drawPercent":"0%"}}}
     * homeTeam : 弗鲁米嫩塞
     * guestTeam : 米内罗竞技
     * leagueType : 0
     */

    private String result;
    private LineUpBean lineUp;
    private AttackDefenseBean attackDefense;
    private SizeTrendBean sizeTrend;
    private ScoreRankBean scoreRank;
    private BothRecordBean bothRecord;
    private String recommend;
    private String leagueId;
    private String fullScoreRank;
    private AsiaTrendBean asiaTrend;
    private String homeTeam;
    private String guestTeam;
    private String leagueType;

    public LineUpBean getLineUp() {
        return lineUp;
    }

    public void setLineUp(LineUpBean lineUp) {
        this.lineUp = lineUp;
    }

    public AttackDefenseBean getAttackDefense() {
        return attackDefense;
    }

    public void setAttackDefense(AttackDefenseBean attackDefense) {
        this.attackDefense = attackDefense;
    }

    public SizeTrendBean getSizeTrend() {
        return sizeTrend;
    }

    public void setSizeTrend(SizeTrendBean sizeTrend) {
        this.sizeTrend = sizeTrend;
    }

    public ScoreRankBean getScoreRank() {
        return scoreRank;
    }

    public void setScoreRank(ScoreRankBean scoreRank) {
        this.scoreRank = scoreRank;
    }

    public BothRecordBean getBothRecord() {
        return bothRecord;
    }

    public void setBothRecord(BothRecordBean bothRecord) {
        this.bothRecord = bothRecord;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getFullScoreRank() {
        return fullScoreRank;
    }

    public void setFullScoreRank(String fullScoreRank) {
        this.fullScoreRank = fullScoreRank;
    }

    public AsiaTrendBean getAsiaTrend() {
        return asiaTrend;
    }

    public void setAsiaTrend(AsiaTrendBean asiaTrend) {
        this.asiaTrend = asiaTrend;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getGuestTeam() {
        return guestTeam;
    }

    public void setGuestTeam(String guestTeam) {
        this.guestTeam = guestTeam;
    }

    public String getLeagueType() {
        return leagueType;
    }

    public void setLeagueType(String leagueType) {
        this.leagueType = leagueType;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static class LineUpBean {
        /**
         * result : 200
         * homeLineUp : []
         * guestLineUp : []
         */

        private String result;
        private List<Map<String, String>> homeLineUp;
        private List<Map<String, String>> guestLineUp;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public List<Map<String, String>> getHomeLineUp() {
            return homeLineUp;
        }

        public void setHomeLineUp(List<Map<String, String>> homeLineUp) {
            this.homeLineUp = homeLineUp;
        }

        public List<Map<String, String>> getGuestLineUp() {
            return guestLineUp;
        }

        public void setGuestLineUp(List<Map<String, String>> guestLineUp) {
            this.guestLineUp = guestLineUp;
        }
    }

    public static class AttackDefenseBean {

        /**
         * guestFieldGoal : 1.33
         * guestFieldLose : 1.17
         * sizeHandicap : 3
         * homeFieldGoal : 1.17
         * homeFieldLose : 1.33
         */

        private String guestFieldGoal;
        private String guestFieldLose;
        private String sizeHandicap;
        private String homeFieldGoal;
        private String homeFieldLose;

        public String getGuestFieldGoal() {
            return guestFieldGoal;
        }

        public void setGuestFieldGoal(String guestFieldGoal) {
            this.guestFieldGoal = guestFieldGoal;
        }

        public String getGuestFieldLose() {
            return guestFieldLose;
        }

        public void setGuestFieldLose(String guestFieldLose) {
            this.guestFieldLose = guestFieldLose;
        }

        public String getSizeHandicap() {
            return sizeHandicap;
        }

        public void setSizeHandicap(String sizeHandicap) {
            this.sizeHandicap = sizeHandicap;
        }

        public String getHomeFieldGoal() {
            return homeFieldGoal;
        }

        public void setHomeFieldGoal(String homeFieldGoal) {
            this.homeFieldGoal = homeFieldGoal;
        }

        public String getHomeFieldLose() {
            return homeFieldLose;
        }

        public void setHomeFieldLose(String homeFieldLose) {
            this.homeFieldLose = homeFieldLose;
        }
    }

    public static class SizeTrendBean {

        /**
         * homeRecent : {"trendList":[{"tot":2,"homeGround":false},{"tot":0,"homeGround":true},{"tot":1,"homeGround":true},{"tot":2,"homeGround":false}],"statistics":{"draw":1,"bigPercent":"25%","smallPercent":"50%","vsCount":4,"big":1,"drawPercent":"25%","small":2}}
         * battleHistory : {"totList":[{"tot":1,"homeGround":false},{"tot":2,"homeGround":true},{"tot":1,"homeGround":true},{"tot":2,"homeGround":true},{"tot":1,"homeGround":false},{"tot":2,"homeGround":false}],"pointList":[{"point4":"0-0-1","point5":"2-1-2","point6":"0-2-2","point1":"4-0-2","point2":"1-1-1","point3":"3-1-1"},{"point4":"3","point5":"2.5/3","point6":"3","point1":"2.5/3","point2":"3","point3":"3"},{"point4":2,"point5":1,"point6":2,"point1":1,"point2":2,"point3":1}],"statistics":{"draw":0,"bigPercent":"50%","smallPercent":"50%","vsCount":6,"big":3,"drawPercent":"0%","small":3}}
         * guestRecent : {"trendList":[{"tot":2,"homeGround":true},{"tot":2,"homeGround":true},{"tot":1,"homeGround":true},{"tot":2,"homeGround":false},{"tot":2,"homeGround":true},{"tot":2,"homeGround":false}],"statistics":{"draw":0,"bigPercent":"17%","smallPercent":"83%","vsCount":6,"big":1,"drawPercent":"0%","small":5}}
         */

        private HomeRecentBean homeRecent;
        private BattleHistoryBean battleHistory;
        private GuestRecentBean guestRecent;

        public HomeRecentBean getHomeRecent() {
            return homeRecent;
        }

        public void setHomeRecent(HomeRecentBean homeRecent) {
            this.homeRecent = homeRecent;
        }

        public BattleHistoryBean getBattleHistory() {
            return battleHistory;
        }

        public void setBattleHistory(BattleHistoryBean battleHistory) {
            this.battleHistory = battleHistory;
        }

        public GuestRecentBean getGuestRecent() {
            return guestRecent;
        }

        public void setGuestRecent(GuestRecentBean guestRecent) {
            this.guestRecent = guestRecent;
        }

        public static class HomeRecentBean {
            /**
             * trendList : [{"tot":2,"homeGround":false},{"tot":0,"homeGround":true},{"tot":1,"homeGround":true},{"tot":2,"homeGround":false}]
             * statistics : {"draw":1,"bigPercent":"25%","smallPercent":"50%","vsCount":4,"big":1,"drawPercent":"25%","small":2}
             */

            private StatisticsBean statistics;
            private List<TrendListBean> trendList;

            public StatisticsBean getStatistics() {
                return statistics;
            }

            public void setStatistics(StatisticsBean statistics) {
                this.statistics = statistics;
            }

            public List<TrendListBean> getTrendList() {
                return trendList;
            }

            public void setTrendList(List<TrendListBean> trendList) {
                this.trendList = trendList;
            }

            public static class StatisticsBean {
                /**
                 * draw : 1
                 * bigPercent : 25%
                 * smallPercent : 50%
                 * vsCount : 4
                 * big : 1
                 * drawPercent : 25%
                 * small : 2
                 */

                private int draw;
                private String bigPercent;
                private String smallPercent;
                private int vsCount;
                private int big;
                private String drawPercent;
                private int small;

                public int getDraw() {
                    return draw;
                }

                public void setDraw(int draw) {
                    this.draw = draw;
                }

                public String getBigPercent() {
                    return bigPercent;
                }

                public void setBigPercent(String bigPercent) {
                    this.bigPercent = bigPercent;
                }

                public String getSmallPercent() {
                    return smallPercent;
                }

                public void setSmallPercent(String smallPercent) {
                    this.smallPercent = smallPercent;
                }

                public int getVsCount() {
                    return vsCount;
                }

                public void setVsCount(int vsCount) {
                    this.vsCount = vsCount;
                }

                public int getBig() {
                    return big;
                }

                public void setBig(int big) {
                    this.big = big;
                }

                public String getDrawPercent() {
                    return drawPercent;
                }

                public void setDrawPercent(String drawPercent) {
                    this.drawPercent = drawPercent;
                }

                public int getSmall() {
                    return small;
                }

                public void setSmall(int small) {
                    this.small = small;
                }
            }

            public static class TrendListBean {
                /**
                 * tot : 2
                 * homeGround : false
                 */

                private int tot;
                private boolean homeGround;

                public int getTot() {
                    return tot;
                }

                public void setTot(int tot) {
                    this.tot = tot;
                }

                public boolean isHomeGround() {
                    return homeGround;
                }

                public void setHomeGround(boolean homeGround) {
                    this.homeGround = homeGround;
                }
            }
        }

        public static class BattleHistoryBean {
            /**
             * totList : [{"tot":1,"homeGround":false},{"tot":2,"homeGround":true},{"tot":1,"homeGround":true},{"tot":2,"homeGround":true},{"tot":1,"homeGround":false},{"tot":2,"homeGround":false}]
             * pointList : [{"point4":"0-0-1","point5":"2-1-2","point6":"0-2-2","point1":"4-0-2","point2":"1-1-1","point3":"3-1-1"},{"point4":"3","point5":"2.5/3","point6":"3","point1":"2.5/3","point2":"3","point3":"3"},{"point4":2,"point5":1,"point6":2,"point1":1,"point2":2,"point3":1}]
             * statistics : {"draw":0,"bigPercent":"50%","smallPercent":"50%","vsCount":6,"big":3,"drawPercent":"0%","small":3}
             */

            private StatisticsBeanX statistics;
            private List<TotListBean> totList;
            private List<PointListBean> pointList;

            public StatisticsBeanX getStatistics() {
                return statistics;
            }

            public void setStatistics(StatisticsBeanX statistics) {
                this.statistics = statistics;
            }

            public List<TotListBean> getTotList() {
                return totList;
            }

            public void setTotList(List<TotListBean> totList) {
                this.totList = totList;
            }

            public List<PointListBean> getPointList() {
                return pointList;
            }

            public void setPointList(List<PointListBean> pointList) {
                this.pointList = pointList;
            }

            public static class StatisticsBeanX {
                /**
                 * draw : 0
                 * bigPercent : 50%
                 * smallPercent : 50%
                 * vsCount : 6
                 * big : 3
                 * drawPercent : 0%
                 * small : 3
                 */

                private int draw;
                private String bigPercent;
                private String smallPercent;
                private int vsCount;
                private int big;
                private String drawPercent;
                private int small;

                public int getDraw() {
                    return draw;
                }

                public void setDraw(int draw) {
                    this.draw = draw;
                }

                public String getBigPercent() {
                    return bigPercent;
                }

                public void setBigPercent(String bigPercent) {
                    this.bigPercent = bigPercent;
                }

                public String getSmallPercent() {
                    return smallPercent;
                }

                public void setSmallPercent(String smallPercent) {
                    this.smallPercent = smallPercent;
                }

                public int getVsCount() {
                    return vsCount;
                }

                public void setVsCount(int vsCount) {
                    this.vsCount = vsCount;
                }

                public int getBig() {
                    return big;
                }

                public void setBig(int big) {
                    this.big = big;
                }

                public String getDrawPercent() {
                    return drawPercent;
                }

                public void setDrawPercent(String drawPercent) {
                    this.drawPercent = drawPercent;
                }

                public int getSmall() {
                    return small;
                }

                public void setSmall(int small) {
                    this.small = small;
                }
            }

            public static class TotListBean {
                /**
                 * tot : 1
                 * homeGround : false
                 */

                private int tot;
                private boolean homeGround;

                public int getTot() {
                    return tot;
                }

                public void setTot(int tot) {
                    this.tot = tot;
                }

                public boolean isHomeGround() {
                    return homeGround;
                }

                public void setHomeGround(boolean homeGround) {
                    this.homeGround = homeGround;
                }
            }

            public static class PointListBean {
                /**
                 * point4 : 0-0-1
                 * point5 : 2-1-2
                 * point6 : 0-2-2
                 * point1 : 4-0-2
                 * point2 : 1-1-1
                 * point3 : 3-1-1
                 */

                private String point4;
                private String point5;
                private String point6;
                private String point1;
                private String point2;
                private String point3;

                public String getPoint4() {
                    return point4;
                }

                public void setPoint4(String point4) {
                    this.point4 = point4;
                }

                public String getPoint5() {
                    return point5;
                }

                public void setPoint5(String point5) {
                    this.point5 = point5;
                }

                public String getPoint6() {
                    return point6;
                }

                public void setPoint6(String point6) {
                    this.point6 = point6;
                }

                public String getPoint1() {
                    return point1;
                }

                public void setPoint1(String point1) {
                    this.point1 = point1;
                }

                public String getPoint2() {
                    return point2;
                }

                public void setPoint2(String point2) {
                    this.point2 = point2;
                }

                public String getPoint3() {
                    return point3;
                }

                public void setPoint3(String point3) {
                    this.point3 = point3;
                }
            }
        }

        public static class GuestRecentBean {
            /**
             * trendList : [{"tot":2,"homeGround":true},{"tot":2,"homeGround":true},{"tot":1,"homeGround":true},{"tot":2,"homeGround":false},{"tot":2,"homeGround":true},{"tot":2,"homeGround":false}]
             * statistics : {"draw":0,"bigPercent":"17%","smallPercent":"83%","vsCount":6,"big":1,"drawPercent":"0%","small":5}
             */

            private StatisticsBeanXX statistics;
            private List<TrendListBeanX> trendList;

            public StatisticsBeanXX getStatistics() {
                return statistics;
            }

            public void setStatistics(StatisticsBeanXX statistics) {
                this.statistics = statistics;
            }

            public List<TrendListBeanX> getTrendList() {
                return trendList;
            }

            public void setTrendList(List<TrendListBeanX> trendList) {
                this.trendList = trendList;
            }

            public static class StatisticsBeanXX {
                /**
                 * draw : 0
                 * bigPercent : 17%
                 * smallPercent : 83%
                 * vsCount : 6
                 * big : 1
                 * drawPercent : 0%
                 * small : 5
                 */

                private int draw;
                private String bigPercent;
                private String smallPercent;
                private int vsCount;
                private int big;
                private String drawPercent;
                private int small;

                public int getDraw() {
                    return draw;
                }

                public void setDraw(int draw) {
                    this.draw = draw;
                }

                public String getBigPercent() {
                    return bigPercent;
                }

                public void setBigPercent(String bigPercent) {
                    this.bigPercent = bigPercent;
                }

                public String getSmallPercent() {
                    return smallPercent;
                }

                public void setSmallPercent(String smallPercent) {
                    this.smallPercent = smallPercent;
                }

                public int getVsCount() {
                    return vsCount;
                }

                public void setVsCount(int vsCount) {
                    this.vsCount = vsCount;
                }

                public int getBig() {
                    return big;
                }

                public void setBig(int big) {
                    this.big = big;
                }

                public String getDrawPercent() {
                    return drawPercent;
                }

                public void setDrawPercent(String drawPercent) {
                    this.drawPercent = drawPercent;
                }

                public int getSmall() {
                    return small;
                }

                public void setSmall(int small) {
                    this.small = small;
                }
            }

            public static class TrendListBeanX {
                /**
                 * tot : 2
                 * homeGround : true
                 */

                private int tot;
                private boolean homeGround;

                public int getTot() {
                    return tot;
                }

                public void setTot(int tot) {
                    this.tot = tot;
                }

                public boolean isHomeGround() {
                    return homeGround;
                }

                public void setHomeGround(boolean homeGround) {
                    this.homeGround = homeGround;
                }
            }
        }
    }

    public static class ScoreRankBean {
        /**
         * home : {"rank":"10","team":"弗鲁米嫩塞","vsCount":"20","win":"6","draw":"9","lose":"5","integral":"27","goalDiff":"2","goal":"29","miss":"27"}
         * guest : {"rank":"13","team":"米内罗竞技","vsCount":"20","win":"7","draw":"5","lose":"8","integral":"26","goalDiff":"-2","goal":"21","miss":"23"}
         */

        private HomeBean home;
        private GuestBean guest;

        public HomeBean getHome() {
            return home;
        }

        public void setHome(HomeBean home) {
            this.home = home;
        }

        public GuestBean getGuest() {
            return guest;
        }

        public void setGuest(GuestBean guest) {
            this.guest = guest;
        }

        public static class HomeBean {
            /**
             * rank : 10
             * team : 弗鲁米嫩塞
             * vsCount : 20
             * win : 6
             * draw : 9
             * lose : 5
             * integral : 27
             * goalDiff : 2
             * goal : 29
             * miss : 27
             */

            private String rank;
            private String team;
            private String vsCount;
            private String win;
            private String draw;
            private String lose;
            private String integral;
            private String goalDiff;
            private String goal;
            private String miss;

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getTeam() {
                return team;
            }

            public void setTeam(String team) {
                this.team = team;
            }

            public String getVsCount() {
                return vsCount;
            }

            public void setVsCount(String vsCount) {
                this.vsCount = vsCount;
            }

            public String getWin() {
                return win;
            }

            public void setWin(String win) {
                this.win = win;
            }

            public String getDraw() {
                return draw;
            }

            public void setDraw(String draw) {
                this.draw = draw;
            }

            public String getLose() {
                return lose;
            }

            public void setLose(String lose) {
                this.lose = lose;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public String getGoalDiff() {
                return goalDiff;
            }

            public void setGoalDiff(String goalDiff) {
                this.goalDiff = goalDiff;
            }

            public String getGoal() {
                return goal;
            }

            public void setGoal(String goal) {
                this.goal = goal;
            }

            public String getMiss() {
                return miss;
            }

            public void setMiss(String miss) {
                this.miss = miss;
            }
        }

        public static class GuestBean {
            /**
             * rank : 13
             * team : 米内罗竞技
             * vsCount : 20
             * win : 7
             * draw : 5
             * lose : 8
             * integral : 26
             * goalDiff : -2
             * goal : 21
             * miss : 23
             */

            private String rank;
            private String team;
            private String vsCount;
            private String win;
            private String draw;
            private String lose;
            private String integral;
            private String goalDiff;
            private String goal;
            private String miss;

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getTeam() {
                return team;
            }

            public void setTeam(String team) {
                this.team = team;
            }

            public String getVsCount() {
                return vsCount;
            }

            public void setVsCount(String vsCount) {
                this.vsCount = vsCount;
            }

            public String getWin() {
                return win;
            }

            public void setWin(String win) {
                this.win = win;
            }

            public String getDraw() {
                return draw;
            }

            public void setDraw(String draw) {
                this.draw = draw;
            }

            public String getLose() {
                return lose;
            }

            public void setLose(String lose) {
                this.lose = lose;
            }

            public String getIntegral() {
                return integral;
            }

            public void setIntegral(String integral) {
                this.integral = integral;
            }

            public String getGoalDiff() {
                return goalDiff;
            }

            public void setGoalDiff(String goalDiff) {
                this.goalDiff = goalDiff;
            }

            public String getGoal() {
                return goal;
            }

            public void setGoal(String goal) {
                this.goal = goal;
            }

            public String getMiss() {
                return miss;
            }

            public void setMiss(String miss) {
                this.miss = miss;
            }
        }
    }

    public static class BothRecordBean {
        /**
         * home : {"historyWin":2,"historyDraw":2,"recentRecord":[0,0,1,0,1,2],"futureMatch":{"team":"利加大学","logoUrl":"http://pic.13322.com/icons/teams/100/1949.png","diffDays":1}}
         * guest : {"historyWin":2,"historyDraw":2,"recentRecord":[1,0,2,2,1,2],"futureMatch":{"team":"庞特普雷塔","logoUrl":"http://pic.13322.com/icons/teams/100/381.png","diffDays":4}}
         */

        private HomeBeanX home;
        private GuestBeanX guest;

        public HomeBeanX getHome() {
            return home;
        }

        public void setHome(HomeBeanX home) {
            this.home = home;
        }

        public GuestBeanX getGuest() {
            return guest;
        }

        public void setGuest(GuestBeanX guest) {
            this.guest = guest;
        }

        public static class HomeBeanX {
            /**
             * historyWin : 2
             * historyDraw : 2
             * recentRecord : [0,0,1,0,1,2]
             * futureMatch : {"team":"利加大学","logoUrl":"http://pic.13322.com/icons/teams/100/1949.png","diffDays":1}
             */

            private int historyWin;
            private int historyDraw;
            private FutureMatchBean futureMatch;
            private List<Integer> recentRecord;

            public int getHistoryWin() {
                return historyWin;
            }

            public void setHistoryWin(int historyWin) {
                this.historyWin = historyWin;
            }

            public int getHistoryDraw() {
                return historyDraw;
            }

            public void setHistoryDraw(int historyDraw) {
                this.historyDraw = historyDraw;
            }

            public FutureMatchBean getFutureMatch() {
                return futureMatch;
            }

            public void setFutureMatch(FutureMatchBean futureMatch) {
                this.futureMatch = futureMatch;
            }

            public List<Integer> getRecentRecord() {
                return recentRecord;
            }

            public void setRecentRecord(List<Integer> recentRecord) {
                this.recentRecord = recentRecord;
            }

            public static class FutureMatchBean {
                /**
                 * team : 利加大学
                 * logoUrl : http://pic.13322.com/icons/teams/100/1949.png
                 * diffDays : 1
                 */

                private String team;
                private String logoUrl;
                private int diffDays;

                public String getTeam() {
                    return team;
                }

                public void setTeam(String team) {
                    this.team = team;
                }

                public String getLogoUrl() {
                    return logoUrl;
                }

                public void setLogoUrl(String logoUrl) {
                    this.logoUrl = logoUrl;
                }

                public int getDiffDays() {
                    return diffDays;
                }

                public void setDiffDays(int diffDays) {
                    this.diffDays = diffDays;
                }
            }
        }

        public static class GuestBeanX {
            /**
             * historyWin : 2
             * historyDraw : 2
             * recentRecord : [1,0,2,2,1,2]
             * futureMatch : {"team":"庞特普雷塔","logoUrl":"http://pic.13322.com/icons/teams/100/381.png","diffDays":4}
             */

            private int historyWin;
            private int historyDraw;
            private FutureMatchBeanX futureMatch;
            private List<Integer> recentRecord;

            public int getHistoryWin() {
                return historyWin;
            }

            public void setHistoryWin(int historyWin) {
                this.historyWin = historyWin;
            }

            public int getHistoryDraw() {
                return historyDraw;
            }

            public void setHistoryDraw(int historyDraw) {
                this.historyDraw = historyDraw;
            }

            public FutureMatchBeanX getFutureMatch() {
                return futureMatch;
            }

            public void setFutureMatch(FutureMatchBeanX futureMatch) {
                this.futureMatch = futureMatch;
            }

            public List<Integer> getRecentRecord() {
                return recentRecord;
            }

            public void setRecentRecord(List<Integer> recentRecord) {
                this.recentRecord = recentRecord;
            }

            public static class FutureMatchBeanX {
                /**
                 * team : 庞特普雷塔
                 * logoUrl : http://pic.13322.com/icons/teams/100/381.png
                 * diffDays : 4
                 */

                private String team;
                private String logoUrl;
                private int diffDays;

                public String getTeam() {
                    return team;
                }

                public void setTeam(String team) {
                    this.team = team;
                }

                public String getLogoUrl() {
                    return logoUrl;
                }

                public void setLogoUrl(String logoUrl) {
                    this.logoUrl = logoUrl;
                }

                public int getDiffDays() {
                    return diffDays;
                }

                public void setDiffDays(int diffDays) {
                    this.diffDays = diffDays;
                }
            }
        }
    }

    public static class AsiaTrendBean {
        /**
         * homeRecent : {"trendList":[{"let":1,"homeGround":false},{"let":1,"homeGround":false},{"let":1,"homeGround":true},{"let":1,"homeGround":false},{"let":1,"homeGround":false},{"let":2,"homeGround":true}],"statistics":{"draw":0,"lose":1,"winPercent":"83%","vsCount":6,"win":5,"losePercent":"17%","drawPercent":"0%"}}
         * battleHistory : {"pointList":[{"point1":"1-2-2","point2":"4-2-1","point3":"1-1-2","point4":"1-2-1","point5":"4-1-2","point6":"0-0-1"},{"point1":"1","point2":"0","point3":"0.5","point4":"0/0.5","point5":"0/0.5","point6":"0/0.5"},{"point1":1,"point2":1,"point3":1,"point4":2,"point5":2,"point6":2}],"statistics":{"draw":0,"lose":3,"winPercent":"50%","vsCount":6,"losePercent":"50%","win":3,"drawPercent":"0%"},"letList":[{"let":1,"homeGround":false},{"let":1,"homeGround":true},{"let":1,"homeGround":false},{"let":2,"homeGround":true},{"let":2,"homeGround":false},{"let":2,"homeGround":true}]}
         * guestRecent : {"trendList":[{"let":1,"homeGround":true},{"let":2,"homeGround":true},{"let":2,"homeGround":false},{"let":2,"homeGround":true},{"let":1,"homeGround":false},{"let":2,"homeGround":false}],"statistics":{"draw":0,"lose":4,"winPercent":"33%","vsCount":6,"win":2,"losePercent":"67%","drawPercent":"0%"}}
         */

        private HomeRecentBean homeRecent;
        private BattleHistoryBean battleHistory;
        private GuestRecentBean guestRecent;

        public HomeRecentBean getHomeRecent() {
            return homeRecent;
        }

        public void setHomeRecent(HomeRecentBean homeRecent) {
            this.homeRecent = homeRecent;
        }

        public BattleHistoryBean getBattleHistory() {
            return battleHistory;
        }

        public void setBattleHistory(BattleHistoryBean battleHistory) {
            this.battleHistory = battleHistory;
        }

        public GuestRecentBean getGuestRecent() {
            return guestRecent;
        }

        public void setGuestRecent(GuestRecentBean guestRecent) {
            this.guestRecent = guestRecent;
        }

        public static class HomeRecentBean {
            /**
             * trendList : [{"let":1,"homeGround":false},{"let":1,"homeGround":false},{"let":1,"homeGround":true},{"let":1,"homeGround":false},{"let":1,"homeGround":false},{"let":2,"homeGround":true}]
             * statistics : {"draw":0,"lose":1,"winPercent":"83%","vsCount":6,"win":5,"losePercent":"17%","drawPercent":"0%"}
             */

            private StatisticsBean statistics;
            private List<TrendListBean> trendList;

            public StatisticsBean getStatistics() {
                return statistics;
            }

            public void setStatistics(StatisticsBean statistics) {
                this.statistics = statistics;
            }

            public List<TrendListBean> getTrendList() {
                return trendList;
            }

            public void setTrendList(List<TrendListBean> trendList) {
                this.trendList = trendList;
            }

            public static class StatisticsBean {
                /**
                 * draw : 0
                 * lose : 1
                 * winPercent : 83%
                 * vsCount : 6
                 * win : 5
                 * losePercent : 17%
                 * drawPercent : 0%
                 */

                private int draw;
                private int lose;
                private String winPercent;
                private int vsCount;
                private int win;
                private String losePercent;
                private String drawPercent;

                public int getDraw() {
                    return draw;
                }

                public void setDraw(int draw) {
                    this.draw = draw;
                }

                public int getLose() {
                    return lose;
                }

                public void setLose(int lose) {
                    this.lose = lose;
                }

                public String getWinPercent() {
                    return winPercent;
                }

                public void setWinPercent(String winPercent) {
                    this.winPercent = winPercent;
                }

                public int getVsCount() {
                    return vsCount;
                }

                public void setVsCount(int vsCount) {
                    this.vsCount = vsCount;
                }

                public int getWin() {
                    return win;
                }

                public void setWin(int win) {
                    this.win = win;
                }

                public String getLosePercent() {
                    return losePercent;
                }

                public void setLosePercent(String losePercent) {
                    this.losePercent = losePercent;
                }

                public String getDrawPercent() {
                    return drawPercent;
                }

                public void setDrawPercent(String drawPercent) {
                    this.drawPercent = drawPercent;
                }
            }

            public static class TrendListBean {
                /**
                 * let : 1
                 * homeGround : false
                 */

                private int let;
                private boolean homeGround;

                public int getLet() {
                    return let;
                }

                public void setLet(int let) {
                    this.let = let;
                }

                public boolean isHomeGround() {
                    return homeGround;
                }

                public void setHomeGround(boolean homeGround) {
                    this.homeGround = homeGround;
                }
            }
        }

        public static class BattleHistoryBean {
            /**
             * pointList : [{"point1":"1-2-2","point2":"4-2-1","point3":"1-1-2","point4":"1-2-1","point5":"4-1-2","point6":"0-0-1"},{"point1":"1","point2":"0","point3":"0.5","point4":"0/0.5","point5":"0/0.5","point6":"0/0.5"},{"point1":1,"point2":1,"point3":1,"point4":2,"point5":2,"point6":2}]
             * statistics : {"draw":0,"lose":3,"winPercent":"50%","vsCount":6,"losePercent":"50%","win":3,"drawPercent":"0%"}
             * letList : [{"let":1,"homeGround":false},{"let":1,"homeGround":true},{"let":1,"homeGround":false},{"let":2,"homeGround":true},{"let":2,"homeGround":false},{"let":2,"homeGround":true}]
             */

            private StatisticsBeanX statistics;
            private List<PointListBean> pointList;
            private List<LetListBean> letList;

            public StatisticsBeanX getStatistics() {
                return statistics;
            }

            public void setStatistics(StatisticsBeanX statistics) {
                this.statistics = statistics;
            }

            public List<PointListBean> getPointList() {
                return pointList;
            }

            public void setPointList(List<PointListBean> pointList) {
                this.pointList = pointList;
            }

            public List<LetListBean> getLetList() {
                return letList;
            }

            public void setLetList(List<LetListBean> letList) {
                this.letList = letList;
            }

            public static class StatisticsBeanX {
                /**
                 * draw : 0
                 * lose : 3
                 * winPercent : 50%
                 * vsCount : 6
                 * losePercent : 50%
                 * win : 3
                 * drawPercent : 0%
                 */

                private int draw;
                private int lose;
                private String winPercent;
                private int vsCount;
                private String losePercent;
                private int win;
                private String drawPercent;

                public int getDraw() {
                    return draw;
                }

                public void setDraw(int draw) {
                    this.draw = draw;
                }

                public int getLose() {
                    return lose;
                }

                public void setLose(int lose) {
                    this.lose = lose;
                }

                public String getWinPercent() {
                    return winPercent;
                }

                public void setWinPercent(String winPercent) {
                    this.winPercent = winPercent;
                }

                public int getVsCount() {
                    return vsCount;
                }

                public void setVsCount(int vsCount) {
                    this.vsCount = vsCount;
                }

                public String getLosePercent() {
                    return losePercent;
                }

                public void setLosePercent(String losePercent) {
                    this.losePercent = losePercent;
                }

                public int getWin() {
                    return win;
                }

                public void setWin(int win) {
                    this.win = win;
                }

                public String getDrawPercent() {
                    return drawPercent;
                }

                public void setDrawPercent(String drawPercent) {
                    this.drawPercent = drawPercent;
                }
            }

            public static class PointListBean {
                /**
                 * point1 : 1-2-2
                 * point2 : 4-2-1
                 * point3 : 1-1-2
                 * point4 : 1-2-1
                 * point5 : 4-1-2
                 * point6 : 0-0-1
                 */

                private String point1;
                private String point2;
                private String point3;
                private String point4;
                private String point5;
                private String point6;

                public String getPoint1() {
                    return point1;
                }

                public void setPoint1(String point1) {
                    this.point1 = point1;
                }

                public String getPoint2() {
                    return point2;
                }

                public void setPoint2(String point2) {
                    this.point2 = point2;
                }

                public String getPoint3() {
                    return point3;
                }

                public void setPoint3(String point3) {
                    this.point3 = point3;
                }

                public String getPoint4() {
                    return point4;
                }

                public void setPoint4(String point4) {
                    this.point4 = point4;
                }

                public String getPoint5() {
                    return point5;
                }

                public void setPoint5(String point5) {
                    this.point5 = point5;
                }

                public String getPoint6() {
                    return point6;
                }

                public void setPoint6(String point6) {
                    this.point6 = point6;
                }
            }

            public static class LetListBean {
                /**
                 * let : 1
                 * homeGround : false
                 */

                private int let;
                private boolean homeGround;

                public int getLet() {
                    return let;
                }

                public void setLet(int let) {
                    this.let = let;
                }

                public boolean isHomeGround() {
                    return homeGround;
                }

                public void setHomeGround(boolean homeGround) {
                    this.homeGround = homeGround;
                }
            }
        }

        public static class GuestRecentBean {
            /**
             * trendList : [{"let":1,"homeGround":true},{"let":2,"homeGround":true},{"let":2,"homeGround":false},{"let":2,"homeGround":true},{"let":1,"homeGround":false},{"let":2,"homeGround":false}]
             * statistics : {"draw":0,"lose":4,"winPercent":"33%","vsCount":6,"win":2,"losePercent":"67%","drawPercent":"0%"}
             */

            private StatisticsBeanXX statistics;
            private List<TrendListBeanX> trendList;

            public StatisticsBeanXX getStatistics() {
                return statistics;
            }

            public void setStatistics(StatisticsBeanXX statistics) {
                this.statistics = statistics;
            }

            public List<TrendListBeanX> getTrendList() {
                return trendList;
            }

            public void setTrendList(List<TrendListBeanX> trendList) {
                this.trendList = trendList;
            }

            public static class StatisticsBeanXX {
                /**
                 * draw : 0
                 * lose : 4
                 * winPercent : 33%
                 * vsCount : 6
                 * win : 2
                 * losePercent : 67%
                 * drawPercent : 0%
                 */

                private int draw;
                private int lose;
                private String winPercent;
                private int vsCount;
                private int win;
                private String losePercent;
                private String drawPercent;

                public int getDraw() {
                    return draw;
                }

                public void setDraw(int draw) {
                    this.draw = draw;
                }

                public int getLose() {
                    return lose;
                }

                public void setLose(int lose) {
                    this.lose = lose;
                }

                public String getWinPercent() {
                    return winPercent;
                }

                public void setWinPercent(String winPercent) {
                    this.winPercent = winPercent;
                }

                public int getVsCount() {
                    return vsCount;
                }

                public void setVsCount(int vsCount) {
                    this.vsCount = vsCount;
                }

                public int getWin() {
                    return win;
                }

                public void setWin(int win) {
                    this.win = win;
                }

                public String getLosePercent() {
                    return losePercent;
                }

                public void setLosePercent(String losePercent) {
                    this.losePercent = losePercent;
                }

                public String getDrawPercent() {
                    return drawPercent;
                }

                public void setDrawPercent(String drawPercent) {
                    this.drawPercent = drawPercent;
                }
            }

            public static class TrendListBeanX {
                /**
                 * let : 1
                 * homeGround : true
                 */

                private int let;
                private boolean homeGround;

                public int getLet() {
                    return let;
                }

                public void setLet(int let) {
                    this.let = let;
                }

                public boolean isHomeGround() {
                    return homeGround;
                }

                public void setHomeGround(boolean homeGround) {
                    this.homeGround = homeGround;
                }
            }
        }
    }
}
