package com.hhly.lotto.api.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;

/**
 * @author lgs on
 * @version 1.0
 * @desc HTTP异常处理类
 * @date 2017/5/11.
 * @company 益彩网络科技有限公司
 */
@RestController
@RequestMapping(value = "/handle")
public class HttpErrorController extends BaseController {

    /**
     * 404错误 没有找到页面
     *
     * @return
     */
    @RequestMapping(value = "/404")
    public Object notFoundView(HttpServletRequest request) {
        return ResultBO.err(MessageCodeConstants.NOT_FOUND_VIEW_HTTP_ERROR_CODE);
    }
}
