package com.hhly.lotto.api.pc.news.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.commoncore.remote.operate.service.IOperateArticleService;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.DicEnum.PlatFormEnum;
import com.hhly.skeleton.base.constants.ArticleConstants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.lotto.base.operate.vo.OperateArticleLottVO;

/** 
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/news")
public class NewsController extends BaseController{
	@Autowired
	private ILotteryIssueService  iLotteryIssueService;
	@Autowired
	private IOperateArticleService iOperateService;
	
	
	/**
	 * 查询资讯主页头部信息信息
	 * @return
	 */
	@RequestMapping(value = "/newstop", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findNewsHomeTop() throws Exception {
		return iLotteryIssueService.findNewsHomeTop();
	}
	/**
	 * 查询多个资讯信息
	 * @return
	 */
	@RequestMapping(value = "/newsbytypes", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findNewsByTypes(@RequestBody List<OperateArticleLottVO> artList ) throws Exception{	
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
			if(!PlatFormEnum.contain(artVo.getPlatform())){
				return ResultBO.err(MessageCodeConstants.PARAM_INVALID,"platform");
			}
		}
		return iOperateService.findNewsByTypeList(artList);
	}
	
	/**
	 * 查询单个类型资讯信息
	 * @return
	 */
	@RequestMapping(value = "/articletop", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findArticleTop(OperateArticleLottVO artVo) throws Exception{	
		if(artVo==null){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		if(artVo.getTypeCode()==null){
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID,"typeCode");
		}
		if(!PlatFormEnum.contain(artVo.getPlatform())){
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID,"platform");
		}
		//查询行数与起始结束行数不能同时为空
		if(artVo.getEndRow()==null||artVo.getStartRow()==null){
			return ResultBO.err(MessageCodeConstants.ROW_IS_NULL_FIELD);
		}
		if(artVo.getEndRow()<0||artVo.getEndRow()<artVo.getStartRow()||(artVo.getEndRow()-artVo.getStartRow())>ArticleConstants.NEWS_MAX_ROW){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		return iOperateService.findArticleByTop(artVo);
	}
	/**
	 * 查询单个资讯详细信息
	 * @return
	 */
	@RequestMapping(value = "/article",method = { RequestMethod.GET, RequestMethod.POST })
	public Object findArticleById(OperateArticleLottVO vo) throws Exception{	
		if(vo==null||vo.getId()==null){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}		
		return iOperateService.findArticleFromLott(vo);
	}
	
	/**
	 * 查询专栏的专家信息
	 * @return
	 */
	@RequestMapping(value = "/expert", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findExpertByCode(OperateArticleLottVO vo) throws Exception{	
		if(vo==null||vo.getTypeCode()==null||vo.getRownum()==null){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		if(!PlatFormEnum.contain(vo.getPlatform())){
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID,"platform");
		}
		if(vo.getRownum()>ArticleConstants.NEWS_MAX_ROW){
			return ResultBO.err(MessageCodeConstants.ROW_MAX_FIELD,ArticleConstants.NEWS_MAX_ROW);
		}
		if(vo.getTypeCode()==null){
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID,"typeCode");
		}
		return iOperateService.findExpertByCode(vo);
	}
	
	
	/**
	 * 更新文章点击量
	 * @return
	 */
	@RequestMapping(value = "/click", method = {RequestMethod.GET,RequestMethod.POST })
	public Object updateAriClick(OperateArticleLottVO vo) throws Exception{	
		if(vo==null||StringUtil.isBlank(vo.getArticleId())){
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}	
		return iOperateService.updateAriClick(vo);
	}
	
	/**
	 * 查询资讯相似信息
	 * @return
	 */
	@RequestMapping(value = "/label", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findMobailArticleLabel(OperateArticleLottVO artVo) {
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
		if(!PlatFormEnum.contain(artVo.getPlatform())){
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID,"platform");
		}
		return iOperateService.findMobailArticleLabel(artVo);
	}
	
	/**
	 * 查询所有文章栏目信息
	 * @return
	 */
	@RequestMapping(value = "/type", method = { RequestMethod.GET})
	public Object findNewsTypeList() {
		return iOperateService.findNewsTypeList();
	}
	/**
	 * 查询所有资讯栏目关系信息
	 * @return
	 */
	@RequestMapping(value = "/newtype", method = { RequestMethod.GET})
	public Object findNewTypeRelatList(OperateArticleLottVO artVo ) {
		if(artVo==null){
			return ResultBO.err("40000");
		}
		if(!PlatFormEnum.contain(artVo.getPlatform())){
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID,"platform");
		}
		return iOperateService.findNewTypeRelatList(artVo);
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
		return iOperateService.findListByFaType(typeCode);
	}
	

	/**
	 * 查询侧边栏资讯头条信息 ，查询规则查当前节点和子节点下创建的信息
	 */
	@RequestMapping(value = "/sidebar", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findSidebarNews(OperateArticleLottVO artVo) {
		if(artVo==null){
			return ResultBO.err("40000");
		}
		if(artVo.getTypeCode()==null){
			return ResultBO.err("20001");
		}	
		if(!PlatFormEnum.contain(artVo.getPlatform())){
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID,"platform");
		}
		return iOperateService.findSidebarNews(artVo);
	}	
}