package com.hhly.yibifen.entity;

import java.util.List;

/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2017/8/21.
 * @company 益彩网络科技有限公司
 */
public class FutureMatchBO {

    /**
     * 主队未来赛事
     */
    private List<FutureMatchTeamBO> home;

    /**
     * 客队未来赛事
     */
    private List<FutureMatchTeamBO> guest;

    public List<FutureMatchTeamBO> getHome() {
        return home;
    }

    public void setHome(List<FutureMatchTeamBO> home) {
        this.home = home;
    }

    public List<FutureMatchTeamBO> getGuest() {
        return guest;
    }

    public void setGuest(List<FutureMatchTeamBO> guest) {
        this.guest = guest;
    }
}
