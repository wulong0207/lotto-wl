package com.hhly.lotto.api.common.controller.news.v1_0;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.commoncore.remote.operate.service.IOperateArticleService;
import com.hhly.lotto.api.data.news.v1_0.OperateNewsData;
import com.hhly.lotto.base.common.HeaderParam;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.ArticleConstants;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.lotto.base.operate.bo.OperateArticleLottBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateArticleTypeLottBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateArticleLottVO;

/** 
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class NewsCommonV10Controller extends BaseController{
	
	@Autowired
	private IOperateArticleService operatArticleService;
	@Autowired
	private OperateNewsData operateNewsData;
	@Autowired
    private RedisUtil objectRedisUtil;
	
	/**
	 * 查询单个类型资讯信息
	 * @return
	 */
	@RequestMapping(value = "/articletop", method = { RequestMethod.GET})
	public Object findArticleTop(OperateArticleLottVO artVo,HttpServletRequest request) throws Exception{	
		if(artVo==null){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		if(artVo.getTypeCode()==null){
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID,"typeCode");
		}	
		//查询行数与起始结束行数不能同时为空
		if(artVo.getEndRow()==null||artVo.getStartRow()==null){
			return ResultBO.err(MessageCodeConstants.ROW_IS_NULL_FIELD);
		}
		if(artVo.getEndRow()<0||artVo.getEndRow()<artVo.getStartRow()||(artVo.getEndRow()-artVo.getStartRow())>ArticleConstants.NEWS_MAX_ROW){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		if(!operateNewsData.checkNewsType(artVo.getTypeCode())){
			throw new ResultJsonException(ResultBO.err(MessageCodeConstants.PARAM_INVALID,"typeCode"));
		}
		HeaderParam headerParam = getHeaderParam(request);
		artVo.setPlatform(headerParam.getPlatformId().shortValue());
		String key = CacheConstants.C_COMM_ARTICLE_FIND_BY_TOP+artVo.getTypeCode()+artVo.getStartRow()+artVo.getEndRow()+artVo.getPlatform();		
		List<OperateArticleLottBO> list = objectRedisUtil.getObj(key,new ArrayList<OperateArticleLottBO>());
		if(list==null){
			list = operatArticleService.findArticleByTop(artVo).getData();			
			objectRedisUtil.addObj(key, list, (long)Constants.NUM_20000);
		}		
		return ResultBO.ok(list);
	}
	
	
	/**
	 * 更新文章点击量
	 * @return
	 */
	@RequestMapping(value = "/click", method = {RequestMethod.GET,RequestMethod.POST})
	public Object updateAriClick( OperateArticleLottVO vo) throws Exception{	
		if(vo==null||StringUtil.isBlank(vo.getArticleId())){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}	
		return operatArticleService.updateAriClick(vo);
	}
	
	/**
	 * 查询资讯相似信息
	 * @return
	 */
	@RequestMapping(value = "/label", method = RequestMethod.GET )
	public Object findMobailArticleLabel(OperateArticleLottVO artVo,HttpServletRequest request) {
		if(artVo==null){
			return ResultBO.err("40000");
		}
		if(artVo.getStartRow()==null||artVo.getEndRow()==null){
			return ResultBO.err("20001");
		}
		if(artVo.getTypeCode()==null){
			return ResultBO.err("20001");
		}
		if(artVo.getId()==null){
			return ResultBO.err("20001");
		}	
		HeaderParam headerParam = getHeaderParam(request);
		artVo.setPlatform(headerParam.getPlatformId().shortValue());
		String key = CacheConstants.C_COMM_ARTICLE_FIND_BY_LABLE+artVo.getTypeCode()+artVo.getStartRow()+artVo.getEndRow()+artVo.getArticleLabel()+artVo.getPlatform()+artVo.getId();
		List<OperateArticleLottBO> lottBOList  = objectRedisUtil.getObj(key,new ArrayList<OperateArticleLottBO>());
		if(lottBOList == null){
			lottBOList =  operatArticleService.findMobailArticleLabel(artVo).getData();
			objectRedisUtil.addObj(key, lottBOList, (long)Constants.NUM_20000);
		}
		return ResultBO.ok(lottBOList);
	}
	
	/**
	 * 查询所有资讯栏目关系信息
	 * @return
	 */
	@RequestMapping(value = "/newtype", method = { RequestMethod.GET})
	public Object findNewTypeRelatList(OperateArticleLottVO artVo ,HttpServletRequest request) {
		HeaderParam headerParam = getHeaderParam(request);
		artVo.setPlatform(headerParam.getPlatformId().shortValue());
		String key = CacheConstants.C_COMM_ARTICLE_FIND_RELA_LIST+artVo.getPlatform();
		List<OperateArticleTypeLottBO> typelist = objectRedisUtil.getObj(key,new ArrayList<OperateArticleTypeLottBO>());
		if(typelist==null){
			typelist = operatArticleService.findNewTypeRelatList(artVo).getData();
			objectRedisUtil.addObj(key, typelist, (long)Constants.DAY_1);
		}
		return ResultBO.ok(typelist);
	}
}