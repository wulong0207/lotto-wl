package com.hhly.lotto.api.common.controller.user.member.v1_1;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.user.vo.UserMsgInfoVO;
import com.hhly.usercore.remote.member.service.IMemberMessageService;

/**
 * 消息中心
 *
 * @author huangchengfang1219
 * @date 2018年2月27日
 * @company 益彩网络科技公司
 * @version 1.1
 */
public class MemberMessageV11Controller extends BaseController {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(MemberMessageV11Controller.class);

	@Autowired
	private IMemberMessageService memberMessageService;

	/**
	 * 查询消息中心集合
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public Object queryMsgList(@RequestBody UserMsgInfoVO vo, HttpServletRequest request) {
		vo.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
		vo.setChannelId(getHeaderParam(request).getChannelId());
		return memberMessageService.findList(vo);
	}

}
