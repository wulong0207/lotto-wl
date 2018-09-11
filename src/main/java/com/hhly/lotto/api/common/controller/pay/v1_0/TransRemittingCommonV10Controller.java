package com.hhly.lotto.api.common.controller.pay.v1_0;

import com.hhly.lotto.api.data.pay.v1_0.TransMgrService;
import com.hhly.lotto.base.util.UserUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.transmgr.bo.TransRemittingBO;
import com.hhly.skeleton.user.bo.UserInfoBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @desc    汇款controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class TransRemittingCommonV10Controller {

    @Autowired
    private TransMgrService transMgrService;
    @Autowired
    private UserUtil userUtil;
    /**
     * 提交汇款记录
     * @return
     */
    @RequestMapping(value = "" ,method = RequestMethod.POST)
    public ResultBO insert(TransRemittingBO vo) {
        Assert.notNull(vo.getToken(),"token必传");
        UserInfoBO user = userUtil.getUserByToken(vo.getToken());
        Assert.notNull(user,"请选登录");
        Assert.notNull(user.getId(),"请选登录");
        Assert.notNull(vo.getImg(),"请上传汇款截图");
        Assert.notNull(vo.getRemittingBank(),"请选择汇款银行");
        Assert.notNull(vo.getRemittingAmount(),"请填写汇款金额");
        vo.setUserId(user.getId());
        Date now = new Date();
        vo.setCommitTime(now);
        vo.setCreateTime(now);
        vo.setCreateBy(user.getRealName());
        return transMgrService.insertRemitting(vo);
    }
}