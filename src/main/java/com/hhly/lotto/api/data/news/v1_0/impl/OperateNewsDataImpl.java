package com.hhly.lotto.api.data.news.v1_0.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.commoncore.remote.operate.service.IOperateArticleService;
import com.hhly.lotto.api.data.cache.v1_0.UserInfoCacheData;
import com.hhly.lotto.api.data.news.v1_0.OperateNewsData;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.lotto.base.operate.bo.OperateArticleBaseBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateArticleLottBO;

/**
 * @desc 运营管理服务实现类
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class OperateNewsDataImpl  implements OperateNewsData{
	@Autowired
	private IOperateArticleService iOperateService;
	@Autowired
	private UserInfoCacheData userInfoCacheData;
	@Autowired
    private RedisUtil objectRedisUtil;
	
	@SuppressWarnings("unchecked")
	@Override
	public ResultBO<List<OperateArticleLottBO>> findMobailHomeArticle(short platFrom) {
		//查询头条信息
		String key = CacheConstants.C_COMM_ARTICLE_FIND_HOME+platFrom;
		List<OperateArticleLottBO> topList = (List<OperateArticleLottBO>)objectRedisUtil.getObj(key);
		if(topList==null){
			topList=iOperateService.findMobailHomeArticle(platFrom);
			objectRedisUtil.addObj(key, topList, (long)Constants.NUM_300);
		}
		return ResultBO.ok(topList);
	}

	/**
	 * 查询资讯所有栏目信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ResultBO<List<OperateArticleBaseBO>> findNewsTypeList(){
		String key = CacheConstants.C_COMM_ARTICLE_FIND_LIST ;
		List<OperateArticleBaseBO> boList = (List<OperateArticleBaseBO>)objectRedisUtil.getObj(key);
		if(boList==null){
			boList = iOperateService.findNewsTypeList().getData();
			objectRedisUtil.addObj(key, boList, (long)Constants.DAY_1);
		}
		return new ResultBO<List<OperateArticleBaseBO>>(boList);		
	}
	
	/**
	 * 判断栏目是否存在
	 */
	@Override
	public boolean checkNewsType(String typeCode){
		List<OperateArticleBaseBO>  list = findNewsTypeList().getData();
		for(OperateArticleBaseBO operBo : list){
			if(operBo.getTypeCode().equals(typeCode)){
				return true;
			}			
		}
		return false;
	}
}