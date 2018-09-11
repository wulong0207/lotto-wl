package com.hhly.yibifen.entity;

import java.util.List;

/**
 * @author lgs on
 * @version 1.0
 * @desc 足球对阵 亚盘大小球 胜平负指数
 * @date 2017/8/30.
 * @company 益彩网络科技有限公司
 */
public class FootBallMatchOddBO {

    /**
     * result : 200
     * listOdd : [{"id":"3","name":"SB","details":[{"time":null,"score":null,"homeOdd":1.03,"hand":0,"guestOdd":0.85},{"time":null,"score":null,"homeOdd":1.05,"hand":0,"guestOdd":0.85}]},{"id":"45","name":"VinBet","details":[{"time":null,"score":null,"homeOdd":1.12,"hand":0.25,"guestOdd":0.81},{"time":null,"score":null,"homeOdd":1.04,"hand":0,"guestOdd":0.88}]},{"id":"31","name":"SBO","details":[{"time":null,"score":null,"homeOdd":1.12,"hand":0.25,"guestOdd":0.81},{"time":null,"score":null,"homeOdd":1.09,"hand":0,"guestOdd":0.83}]},{"id":"38","name":"IBC","details":[{"time":null,"score":null,"homeOdd":1.07,"hand":0.25,"guestOdd":0.85},{"time":null,"score":null,"homeOdd":1.06,"hand":0,"guestOdd":0.86}]},{"id":"44","name":"ISN","details":[{"time":null,"score":null,"homeOdd":1.11,"hand":0,"guestOdd":0.82},{"time":null,"score":null,"homeOdd":1.12,"hand":0,"guestOdd":0.81}]}]
     */

    private String result;
    private List<ListOddBean> listOdd;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<ListOddBean> getListOdd() {
        return listOdd;
    }

    public void setListOdd(List<ListOddBean> listOdd) {
        this.listOdd = listOdd;
    }

    public static class ListOddBean {
        /**
         * id : 3
         * name : SB
         * details : [{"time":null,"score":null,"homeOdd":1.03,"hand":0,"guestOdd":0.85},{"time":null,"score":null,"homeOdd":1.05,"hand":0,"guestOdd":0.85}]
         */

        private String id;
        private String name;
        private List<MatchOddDetailsBO> details;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<MatchOddDetailsBO> getDetails() {
            return details;
        }

        public void setDetails(List<MatchOddDetailsBO> details) {
            this.details = details;
        }
    }
}
