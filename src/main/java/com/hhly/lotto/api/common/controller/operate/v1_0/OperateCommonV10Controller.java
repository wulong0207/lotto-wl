package com.hhly.lotto.api.common.controller.operate.v1_0;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.commoncore.remote.operate.service.IOperateService;
import com.hhly.lotto.api.data.operate.v1_0.OperateAdV10Data;
import com.hhly.lotto.api.data.operate.v1_0.OperateFastV10Data;
import com.hhly.lotto.api.data.operate.v1_0.OperateLotteryV10Data;
import com.hhly.lotto.base.common.HeaderParam;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.DicEnum.AdMenuEnum;
import com.hhly.skeleton.base.common.DicEnum.FastMenuEnum;
import com.hhly.skeleton.base.common.LotteryEnum.LotteryCategory;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.lotto.base.operate.bo.OperateLottSoftwareVersionBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateAdVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateFastVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateLotteryLottVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateSoftwareVersionVO;

/** 
 * @desc 广告信息
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class OperateCommonV10Controller extends BaseController{
	@Autowired
	private OperateAdV10Data operateAdData;	
	@Autowired
	private OperateLotteryV10Data operateLotteryData;
	@Autowired
	private OperateFastV10Data operateFastData;
	@Autowired
	private IOperateService iOperateService ;
	/**
	 * 查询主页广告信息
	 * @return
	 */
	@RequestMapping(value = "/ad", method = RequestMethod.GET)
	public Object findOperAd(OperateAdVO vo,HttpServletRequest request) {
		if(vo==null){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		if(vo.getMenu()==null||!AdMenuEnum.contain(vo.getMenu())){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}	
		HeaderParam headerParam = getHeaderParam(request);
		vo.setPlatform(headerParam.getPlatformId().shortValue());
		vo.setChannelId(headerParam.getChannelId());
		return operateAdData.findHomeOperAd(vo);
	}	

	
	/**
	 * 查询快投信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/fast", method = RequestMethod.GET)
	public Object findFast(OperateFastVO vo,HttpServletRequest request) {
		if (vo.getPosition() != null && !FastMenuEnum.contain(vo.getPosition())) {
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID, "position");
		}
		if (vo.getPosition() == null) {
			vo.setPosition(FastMenuEnum.HOME.getValue());
		}
		HeaderParam headerParam = getHeaderParam(request);
		vo.setPlatform(headerParam.getPlatformId().shortValue());
		return operateFastData.findOperFastIssueDetail(vo);
	}

	
	/**
	 * 查询主页彩种运营信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/operlottery", method = RequestMethod.GET)
	public Object findOperLottery(OperateLotteryLottVO vo,HttpServletRequest request) {
		if (vo.getCategoryId() != null && !LotteryCategory.contain(vo.getCategoryId())) {
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID, "categoryId");
		}
		HeaderParam headerParam = getHeaderParam(request);
		vo.setPlatform(headerParam.getPlatformId().shortValue());
		return operateLotteryData.findHomeOperLottery(vo);
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
		//vo.setIp(getIp(request));
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