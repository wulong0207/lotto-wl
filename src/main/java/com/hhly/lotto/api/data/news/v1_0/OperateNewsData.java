package com.hhly.lotto.api.data.news.v1_0;
import java.util.List;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateArticleBaseBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateArticleLottBO;
/**
 * @desc 资讯文章管理服务
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface OperateNewsData {	

	
	/**
	 * 查询手机首页新闻资讯信息
	 * @return
	 */
	ResultBO<List<OperateArticleLottBO>> findMobailHomeArticle(short platFrom);
	
	/**
	 * 查询栏目信息
	 * @return
	 */
	public ResultBO<List<OperateArticleBaseBO>> findNewsTypeList();
	/**
	 * 判断栏目是否存在
	 * @param typeCode
	 * @return
	 */
	boolean checkNewsType(String typeCode);
}
