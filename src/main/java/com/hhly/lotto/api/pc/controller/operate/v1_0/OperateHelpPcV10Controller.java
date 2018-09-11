package com.hhly.lotto.api.pc.controller.operate.v1_0;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.api.common.controller.operate.v1_0.OperateHelpCommonV10Controller;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.operate.bo.OperateHelpAbleBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateHelpBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateHelpLotteryBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateHelpTypeBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateHelpAbleVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateHelpVO;

@RestController
@RequestMapping("/pc/v1.0/help")
public class OperateHelpPcV10Controller extends OperateHelpCommonV10Controller {

	/**
	 * 相关词
	 * 
	 * @param helpVO
	 * @return
	 */
	@RequestMapping(value = "relatedwords", method = RequestMethod.GET)
	public ResultBO<?> relatedwords(OperateHelpVO helpVO, HttpServletRequest request) {
		Assert.paramNotNull(helpVO);
		helpVO.setPlatform(getHeaderParam(request).getPlatformId());
		// typeCode和keyword不能同时有值，也不能同时为空
		Assert.paramLegal(!ObjectUtil.isBlank(helpVO.getTypeCode()) || !ObjectUtil.isBlank(helpVO.getKeyword()), "typeCode和keyword");
		Assert.paramLegal(ObjectUtil.isBlank(helpVO.getTypeCode()) || ObjectUtil.isBlank(helpVO.getKeyword()), "typeCode和keyword");
		String key = new StringBuilder(CacheConstants.C_COMM_HELP_RELATEDWORD).append(SymbolConstants.UNDERLINE)
				.append(helpVO.getPlatform()).append(SymbolConstants.UNDERLINE).append(helpVO.getTypeCode())
				.append(SymbolConstants.UNDERLINE).append(helpVO.getKeyword()).toString();
		ResultBO<?> result = (ResultBO<?>) redisUtil.getObj(key);
		if (result != null && result.isOK()) {
			return result;
		}
		List<String> typeHotwords = operateHelpService.findRelatedwords(helpVO);
		result = ResultBO.ok(typeHotwords);
		redisUtil.addObj(key, result, CacheConstants.ONE_DAY);
		return result;
	}

	/**
	 * 彩种列表
	 * 
	 * @param helpVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/lottery/list", method = RequestMethod.GET)
	public ResultBO<?> lotteryList() {
		ResultBO<?> result = (ResultBO<?>) redisUtil.getObj(CacheConstants.C_COMM_HELP_LOTTERY_LIST);
		if (result != null && result.isOK()) {
			return result;
		}
		List<OperateHelpLotteryBO> lotteryList = operateHelpService.findLotteryList();
		result = ResultBO.ok(lotteryList);
		redisUtil.addObj(CacheConstants.C_COMM_HELP_LOTTERY_LIST, result, CacheConstants.ONE_DAY);
		return result;
	}

	/**
	 * 查询栏目和子栏目
	 * 
	 * @param helpVO
	 * @return
	 */
	@RequestMapping(value = "/type/child", method = RequestMethod.GET)
	public ResultBO<?> childTypes(OperateHelpVO helpVO, HttpServletRequest request) {
		Assert.paramNotNull(helpVO);
		Assert.paramNotNull(helpVO.getTypeCode(), "typeCode");
		String key = new StringBuilder(CacheConstants.C_COMM_HELP_TYPE_CHILD).append(SymbolConstants.UNDERLINE).append(helpVO.getTypeCode())
				.toString();
		ResultBO<?> result = (ResultBO<?>) redisUtil.getObj(key);
		if (result != null && result.isOK()) {
			return result;
		}
		OperateHelpTypeBO helpType = operateHelpService.findChildType(helpVO.getTypeCode());
		result = ResultBO.ok(helpType);
		redisUtil.addObj(key, result, CacheConstants.ONE_DAY);
		return result;
	}

	/**
	 * 查询栏目下的第一篇详情
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "detail/bytype", method = RequestMethod.GET)
	public ResultBO<?> detail(String typeCode, HttpServletRequest request) {
		Assert.paramNotNull(typeCode, "typeCode");
		OperateHelpVO helpVO = new OperateHelpVO();
		helpVO.setTypeCode(typeCode);
		helpVO.setPlatform(getHeaderParam(request).getPlatformId());
		Assert.paramNotNull(helpVO);
		Assert.paramNotNull(helpVO.getPlatform(), "platform");
		// c_comm_help_detail_bytype_{platform}_{typeCode}
		String key = new StringBuilder(CacheConstants.C_COMM_HELP_DETAIL).append(SymbolConstants.UNDERLINE).append("bytype")
				.append(SymbolConstants.UNDERLINE).append(helpVO.getPlatform()).append(SymbolConstants.UNDERLINE)
				.append(helpVO.getTypeCode()).toString();
		ResultBO<?> result = (ResultBO<?>) redisUtil.getObj(key);
		if (result != null && result.isOK()) {
			return result;
		}
		OperateHelpBO helpBO = operateHelpService.findHelpByTypeCode(helpVO);
		result = ResultBO.ok(helpBO);
		redisUtil.addObj(key, result, CacheConstants.ONE_DAY);
		return result;
	}

	/**
	 * 用户文章的是否满意查询
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "able", method = RequestMethod.POST)
	public ResultBO<?> able(@RequestBody OperateHelpAbleVO vo, HttpServletRequest request) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getHelpId(), "helpId");
		vo.setIp(getIp(request));
		OperateHelpAbleBO ableBO = operateHelpService.findHelpAble(vo);
		if (ableBO == null) {
			return ResultBO.err(MessageCodeConstants.DATA_NOT_EXIST);
		}
		return ResultBO.ok(ableBO);
	}

	/**
	 * 添加用户对文章的满意
	 * 
	 * @param vo
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "able/update", method = RequestMethod.POST)
	public ResultBO<?> updateAble(@RequestBody OperateHelpAbleVO vo, HttpServletRequest request) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getHelpId(), "helpId");
		Assert.paramNotNull(vo.getIsAble(), "isAble");
		Assert.paramLegal(vo.getIsAble().shortValue() == Constants.YES || vo.getIsAble().shortValue() == Constants.NO, "isAble");
		vo.setIp(getIp(request));
		operateHelpService.updateHelpAble(vo);
		return ResultBO.ok();
	}

}