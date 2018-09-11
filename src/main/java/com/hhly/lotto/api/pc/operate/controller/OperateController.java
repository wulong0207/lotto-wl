package com.hhly.lotto.api.pc.operate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.commoncore.remote.operate.service.IOperateService;
import com.hhly.lotto.api.data.cache.v1_0.UserInfoCacheData;
import com.hhly.lotto.api.data.operate.v1_0.OperateAdV10Data;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.DicEnum.AdMenuEnum;
import com.hhly.skeleton.base.common.DicEnum.PlatFormEnum;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.lotto.base.operate.bo.OperateLottSoftwareVersionBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateAdVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateSoftwareVersionVO;
import com.hhly.skeleton.user.bo.UserInfoBO;

import javax.servlet.http.HttpServletRequest;

/** 
 * @desc 运营信息
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/operate")
public class OperateController extends BaseController{
	@Autowired
	private IOperateService iOperateService ;
	@Autowired 
	private UserInfoCacheData userInfoCacheData;
	@Autowired 
	private OperateAdV10Data operateAdData;
	/**
	 * 查询主页广告信息
	 * @return
	 */
	@RequestMapping(value = "/ad", method = RequestMethod.GET)
	public Object findOperAd(OperateAdVO vo) {
		if(vo==null){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		if(vo.getPlatform()==null||!PlatFormEnum.contain(vo.getPlatform())){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		if(vo.getMenu()==null||!AdMenuEnum.contain(vo.getMenu())){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		if(!StringUtil.isBlank(vo.getToken())){
			UserInfoBO userInfo = (UserInfoBO) userInfoCacheData.checkToken(vo.getToken()).getData();
			 if (ObjectUtil.isBlank(userInfo)) {
		            throw new ResultJsonException(ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE));
		     }
			 vo.setUserId(userInfo.getId().longValue());
		}
		return operateAdData.findHomeOperAd(vo);
	}	
	
	/**
	 * @desc   查询APP审核内容管理配置
	 * @author Tony Wang
	 * @create 2017年9月12日
	 * @return 
	 */
	@RequestMapping(value = "/software/hidden", method = RequestMethod.GET)
	public Object findSoftwareHideInfo(OperateSoftwareVersionVO vo) {
		Assert.notNull(vo.getChannelId());
		Assert.notNull(vo.getCode());
//		vo.setIp(getIp(request));
		OperateLottSoftwareVersionBO soft = iOperateService.findSoftwareVersionSingle(vo);
		if(null == soft || !StringUtils.hasText(soft.getHide())) {
			return ResultBO.ok(new Integer[]{});
		} else {
			String[] strArr = soft.getHide().split(",");
			int result [] = new int[strArr.length];
		    for (int i = 0; i < strArr.length; i++) {
		        result[i] = Integer.valueOf(strArr[i]);
		    }
			return ResultBO.ok(result);
		}
	}	
}