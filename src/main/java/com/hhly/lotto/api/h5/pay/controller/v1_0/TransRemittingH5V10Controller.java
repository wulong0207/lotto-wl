package com.hhly.lotto.api.h5.pay.controller.v1_0;

import com.hhly.lotto.api.common.controller.pay.v1_0.TransRemittingCommonV10Controller;
import com.hhly.lotto.api.data.pay.v1_0.TransMgrService;
import com.hhly.lotto.base.util.UserUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.transmgr.bo.TransRemittingBO;
import com.hhly.skeleton.user.bo.UserInfoBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @desc    汇款controller
 * @author  Tony Wang
 * @date    2017年3月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/h5/v1.0/trans/remitting")
public class TransRemittingH5V10Controller extends TransRemittingCommonV10Controller{
}