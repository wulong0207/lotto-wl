package com.hhly.lotto.api.pc.controller.news.v1_0;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.commoncore.remote.operate.service.IOperateArticleService;
import com.hhly.lotto.api.common.controller.news.v1_0.NewsCommonV10Controller;
import com.hhly.lotto.api.data.news.v1_0.OperateNewsData;
import com.hhly.lotto.base.common.HeaderParam;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.ArticleConstants;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.lotto.base.operate.bo.OperateArticleLottBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateArticleTypeLottBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateArticleUserBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateArticleLottVO;

/** 
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/v1.0/news")
public class NewsPcV10Controller extends NewsCommonV10Controller{
	@Autowired
	private OperateNewsData operateNewsData;
	@Autowired
	private ILotteryIssueService  iLotteryIssueService;
	@Autowired
    private RedisUtil objectRedisUtil;
	@Autowired
	private IOperateArticleService operatArticleService;
	/**
	 * 查询资讯主页头部信息信息
	 * @return
	 */
	@RequestMapping(value = "/newstop", method = RequestMethod.GET)
	public Object findNewsHomeTop() throws Exception{	
		return iLotteryIssueService.findNewsHomeTop();
	}
	/**
	 * 查询多个资讯信息
	 * @return
	 */
	@RequestMapping(value = "/newsbytypes", method = RequestMethod.POST )
	public Object findNewsByTypes(@RequestBody List<OperateArticleLottVO> artList,HttpServletRequest request) throws Exception{	
		if(artList==null||artList.isEmpty()){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		for(OperateArticleLottVO artVo : artList){
			if(artVo.getRownum()==null||artVo.getRownum()==0){
				return ResultBO.err(MessageCodeConstants.ROW_IS_NULL_FIELD);
			}
			if(artVo.getRownum()>ArticleConstants.NEWS_MAX_ROW){
				return ResultBO.err(MessageCodeConstants.ROW_MAX_FIELD,ArticleConstants.NEWS_MAX_ROW);
			}			
			HeaderParam headerParam = getHeaderParam(request);
			artVo.setPlatform(headerParam.getPlatformId().shortValue());
		}
		String key = CacheConstants.C_PC_ARTICLE_FIND_BY_TYPE_LIST;
		for(OperateArticleLottVO vo :artList){
			key+=vo.getTypeCode()+vo.getRownum()+vo.getPlatform();
			if(!operateNewsData.checkNewsType(vo.getTypeCode())){
				throw new ResultJsonException(ResultBO.err(MessageCodeConstants.PARAM_INVALID,"typeCode"));
			}
		}
		Map<String,List<OperateArticleLottBO>> ariMap = objectRedisUtil.getObj(key,new HashMap<String, List<OperateArticleLottBO>>());
		if(ariMap==null){
			ariMap = operatArticleService.findNewsByTypeList(artList).getData();		
			objectRedisUtil.addObj(key, ariMap,(long)Constants.NUM_20000);
		}
		return ResultBO.ok(artList);
	}
	
	
	/**
	 * 查询专栏的专家信息
	 * @return
	 */
	@RequestMapping(value = "/expert", method = RequestMethod.GET)
	public Object findExpertByCode(OperateArticleLottVO vo,HttpServletRequest request) throws Exception{	
		if(vo==null||vo.getTypeCode()==null||vo.getRownum()==null){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		if(vo.getRownum()>ArticleConstants.NEWS_MAX_ROW){
			return ResultBO.err(MessageCodeConstants.ROW_MAX_FIELD,ArticleConstants.NEWS_MAX_ROW);
		}
		if(vo.getTypeCode()==null){
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID,"typeCode");
		}
		if(!operateNewsData.checkNewsType(vo.getTypeCode())){
			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.PARAM_INVALID,"typeCode"));
		}
		HeaderParam headerParam = getHeaderParam(request);
		vo.setPlatform(headerParam.getPlatformId().shortValue());
		String key = CacheConstants.C_PC_ARTICLE_FIND_BY_CODE+vo.getTypeCode()+vo.getPlatform()+vo.getRownum();
		List<OperateArticleUserBO> list  = objectRedisUtil.getObj(key,new ArrayList<OperateArticleUserBO>());
		if(list ==null){
			list = operatArticleService.findExpertByCode(vo).getData();
			objectRedisUtil.addObj(key, list, (long)Constants.NUM_20000);
		}
		return ResultBO.ok(list);
	}
	
	
	/**
	 * 更新文章点击量
	 * @return
	 */
	@RequestMapping(value = "/click", method = {RequestMethod.POST })
	public Object updateAriClick(OperateArticleLottVO vo) throws Exception{	
		if(vo==null||StringUtil.isBlank(vo.getArticleId())){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}	
		return operatArticleService.updateAriClick(vo);
	}
	
	/**
	 * 查询所有文章栏目信息
	 * @return
	 */
	@RequestMapping(value = "/type", method = { RequestMethod.GET})
	public Object findNewsTypeList() {
		return operatArticleService.findNewsTypeList();
	}
	
	/**
	 * 根据父栏目查询子栏目信息
	 * @return
	 */
	@RequestMapping(value = "/sontype", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findListByFaType(String typeCode) {
		if(typeCode==null){
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID,"typeCode");
		}
		String key = CacheConstants.C_PC_ARTICLE_FIND_BY_FATYPE+typeCode;
		List<OperateArticleTypeLottBO>  list= objectRedisUtil.getObj(key,new ArrayList<OperateArticleTypeLottBO>());
		if(list==null){
			list = operatArticleService.findListByFaType(typeCode).getData();
			objectRedisUtil.addObj(key, list, (long)Constants.DAY_1);
		}
		return ResultBO.ok(list);
	}
	

	/**
	 * 查询侧边栏资讯头条信息 ，查询规则查当前节点和子节点下创建的信息
	 */
	@RequestMapping(value = "/sidebar", method =  RequestMethod.GET)
	public Object findSidebarNews(OperateArticleLottVO artVo,HttpServletRequest request) {
		if(artVo==null){
			return ResultBO.err("40000");
		}
		if(artVo.getTypeCode()==null){
			return ResultBO.err("20001");
		}	
		HeaderParam headerParam = getHeaderParam(request);
		artVo.setPlatform(headerParam.getPlatformId().shortValue());
		if(!operateNewsData.checkNewsType(artVo.getTypeCode())){
			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.PARAM_INVALID,"typeCode"));
		}
		String key = CacheConstants.C_PC_ARTICLE_FIND_SIDEBAR_NEWS+artVo.getTypeCode()+artVo.getPlatform()+artVo.getRownum();
		List<OperateArticleLottBO> boList = objectRedisUtil.getObj(key,new ArrayList<OperateArticleLottBO>());
		if(boList==null){
			boList = operatArticleService.findSidebarNews(artVo).getData();
			objectRedisUtil.addObj(key, boList, (long)Constants.NUM_20000);
		}
		return ResultBO.ok(boList);
	}	
}