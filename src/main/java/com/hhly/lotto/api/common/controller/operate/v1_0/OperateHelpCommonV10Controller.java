package com.hhly.lotto.api.common.controller.operate.v1_0;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.commoncore.remote.operate.service.IOperateHelpService;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OperateHelpCorrectEnum;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.Md5Util;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.operate.bo.OperateHelpBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateHelpTypeBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateHelpCorrectVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateHelpTypeVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateHelpVO;
import com.hhly.usercore.remote.member.service.IMemberInfoService;

/**
 * 帮助中心接口
 *
 * @author huangchengfang1219
 * @date 2017年12月6日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class OperateHelpCommonV10Controller extends BaseController {

	@Autowired
	protected IOperateHelpService operateHelpService;
	@Autowired
	private IMemberInfoService memberInfoService;
	@Autowired
	protected RedisUtil redisUtil;

	/**
	 * 热搜词
	 * 
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "hotwords", method = RequestMethod.GET)
	public ResultBO<?> hotwords(Integer num) {
		StringBuilder keyBuilder = new StringBuilder(CacheConstants.C_COMM_HELP_HOTWORD);
		if (num != null && num >= 0) {
			keyBuilder.append(SymbolConstants.UNDERLINE).append(num);
		}
		String key = keyBuilder.toString();
		ResultBO<?> result = (ResultBO<?>) redisUtil.getObj(key);
		if (result != null && result.isOK()) {
			return result;
		}
		List<String> datas = operateHelpService.findHotwords(num);
		result = ResultBO.ok(datas);
		redisUtil.addObj(key, result, CacheConstants.ONE_MONTH);
		return result;
	}

	/**
	 * 关键字(搜索引导)
	 * 
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value = "keywords", method = RequestMethod.GET)
	public ResultBO<?> keywords(String keyword, HttpServletRequest request) {
		Assert.paramNotNull(keyword, "keyword");
		OperateHelpVO helpVO = new OperateHelpVO();
		helpVO.setKeyword(keyword);
		helpVO.setPlatform(getHeaderParam(request).getPlatformId());
		Assert.paramNotNull(helpVO.getPlatform(), "platform");
		String key = new StringBuilder(CacheConstants.C_COMM_HELP_KEYWORD).append(SymbolConstants.UNDERLINE).append(helpVO.getPlatform())
				.append(SymbolConstants.UNDERLINE).append(helpVO.getKeyword()).toString();
		ResultBO<?> result = (ResultBO<?>) redisUtil.getObj(key);
		if (result != null && result.isOK()) {
			return result;
		}
		List<String> keywords = operateHelpService.findKeywords(helpVO);
		result = ResultBO.ok(keywords);
		redisUtil.addObj(key, result, CacheConstants.TEN_MINUTES);
		return result;
	}

	/**
	 * 搜索
	 * 
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public ResultBO<?> search(OperateHelpVO helpVO, HttpServletRequest request) {
		helpVO.setPlatform(getHeaderParam(request).getPlatformId());
		Assert.paramNotNull(helpVO.getKeyword(), "keyword");
		Assert.paramNotNull(helpVO.getPlatform(), "platform");
		PagingBO<OperateHelpBO> pageData = operateHelpService.search(helpVO);
		return ResultBO.ok(pageData);
	}

	/**
	 * 栏目树
	 * 
	 * @return
	 */
	@RequestMapping(value = "/type/tree", method = RequestMethod.GET)
	public ResultBO<?> typeTree() {
		ResultBO<?> result = (ResultBO<?>) redisUtil.getObj(CacheConstants.C_COMM_HELP_TYPE_TREE);
		if (result != null) {
			return result;
		}
		List<OperateHelpTypeBO> typeTree = operateHelpService.findTypeTree();
		result = ResultBO.ok(typeTree);
		redisUtil.addObj(CacheConstants.C_COMM_HELP_TYPE_TREE, result, CacheConstants.ONE_DAY);
		return result;
	}

	/**
	 * 栏目列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/types", method = RequestMethod.GET)
	public ResultBO<?> types() {
		ResultBO<?> result = (ResultBO<?>) redisUtil.getObj(CacheConstants.C_COMM_HELP_TYPE_LIST);
		if (result != null && result.isOK()) {
			return result;
		}
		OperateHelpTypeVO vo = new OperateHelpTypeVO();
		vo.setMenu((short) Constants.NUM_3);
		List<OperateHelpTypeBO> typeList = operateHelpService.findTypeList(vo);
		result = ResultBO.ok(typeList);
		redisUtil.addObj(CacheConstants.C_COMM_HELP_TYPE_LIST, result, CacheConstants.ONE_DAY);
		return result;
	}

	@RequestMapping(value = "/bytypes", method = RequestMethod.POST)
	public ResultBO<?> byTypes(@RequestBody List<OperateHelpVO> helpVOList, HttpServletRequest request) {
		Assert.paramNotNull(helpVOList);
		for (OperateHelpVO helpVO : helpVOList) {
			helpVO.setPlatform(getHeaderParam(request).getPlatformId());
		}
		String json = JsonUtil.objectToJson(helpVOList);
		String key = new StringBuilder(CacheConstants.C_COMM_HELP_TYPE_ARTICLE).append(SymbolConstants.UNDERLINE)
				.append(Md5Util.md5_32(json)).toString();
		ResultBO<?> result = (ResultBO<?>) redisUtil.getObj(key);
		if (result != null && result.isOK()) {
			return result;
		}
		result = ResultBO.ok(operateHelpService.findHelpByTypeList(helpVOList));
		redisUtil.addObj(key, result, CacheConstants.ONE_DAY);
		return result;
	}

	/**
	 * 帮助详情
	 * 
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public ResultBO<?> list(OperateHelpVO helpVO, HttpServletRequest request) {
		helpVO.setPlatform(getHeaderParam(request).getPlatformId());
		Assert.paramNotNull(helpVO);
		Assert.paramNotNull(helpVO.getPlatform(), "platform");
		return ResultBO.ok(operateHelpService.findHelpPage(helpVO));
	}

	/**
	 * 查询帮助详细内容
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "detail", method = RequestMethod.GET)
	public ResultBO<?> detail(Integer helpId, HttpServletRequest request) {
		Assert.paramNotNull(helpId, "id");
		OperateHelpVO helpVO = new OperateHelpVO();
		helpVO.setId(helpId);
		helpVO.setPlatform(getHeaderParam(request).getPlatformId());
		Assert.paramNotNull(helpVO);
		Assert.paramNotNull(helpVO.getPlatform(), "platform");
		String key = new StringBuilder(CacheConstants.C_COMM_HELP_DETAIL).append(SymbolConstants.UNDERLINE).append(helpVO.getPlatform())
				.append(SymbolConstants.UNDERLINE).append(helpVO.getId()).toString();
		ResultBO<?> result = (ResultBO<?>) redisUtil.getObj(key);
		if (result != null && result.isOK()) {
			return result;
		}
		OperateHelpBO helpBO = operateHelpService.findHelpById(helpVO);
		result = ResultBO.ok(helpBO);
		redisUtil.addObj(key, result, CacheConstants.ONE_DAY);
		return result;
	}

	/**
	 * 意见反馈
	 * 
	 * @return
	 */
	@RequestMapping(value = "feedback", method = RequestMethod.POST)
	public ResultBO<?> feedback(@RequestBody OperateHelpCorrectVO vo, HttpServletRequest request) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getContent(), "content");
		Assert.paramNotNull(vo.getSources(), "sources");
		Assert.paramNotNull(vo.getType(), "type");
		Assert.paramLegal(OperateHelpCorrectEnum.Sources.contains(vo.getSources()), "sources");
		Assert.paramLegal(OperateHelpCorrectEnum.Type.contains(vo.getType()), "type");
		vo.setIp(getIp(request));
		// 验证手机号
		if (!ObjectUtil.isBlank(vo.getMobile())) {
			ResultBO<?> resultBO = memberInfoService.verifyMobile(vo.getMobile());
			if (resultBO.isError()) {
				return resultBO;
			}
		}
		operateHelpService.addHelpCorrect(vo);
		return ResultBO.ok();
	}

	@RequestMapping(value = "cache", method = RequestMethod.DELETE)
	public ResultBO<?> cache() {
		redisUtil.delKeys(CacheConstants.C_COMM_HELP_CLEAR);
		return ResultBO.ok();
	}

}
