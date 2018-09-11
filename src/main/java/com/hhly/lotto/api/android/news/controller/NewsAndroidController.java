package com.hhly.lotto.api.android.news.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.commoncore.remote.operate.service.IOperateArticleService;
import com.hhly.lotto.api.data.cache.v1_0.UserInfoCacheData;
import com.hhly.lotto.api.data.operate.v1_0.OperateAdV10Data;
import com.hhly.lotto.api.data.operate.v1_0.OperateFastV10Data;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.ArticleEnum.AriticleEnum;
import com.hhly.skeleton.base.common.DicEnum.AdMenuEnum;
import com.hhly.skeleton.base.common.DicEnum.FastMenuEnum;
import com.hhly.skeleton.base.common.DicEnum.PlatFormEnum;
import com.hhly.skeleton.base.constants.ArticleConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.lotto.base.operate.bo.OperateAdLottoBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateArticleLottBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateFastLottBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateAdVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateArticleLottVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateFastVO;
import com.hhly.skeleton.lotto.mobile.news.bo.MobialNewsHomeBO;
import com.hhly.skeleton.user.bo.UserInfoBO;

/** 
 * @desc 资讯主页
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/android/news")
public class NewsAndroidController extends BaseController{  	
	@Autowired
	private UserInfoCacheData userInfoCacheData ;
	@Autowired
	private IOperateArticleService iOperateArticleService ;
	@Autowired
	private OperateAdV10Data operateAdData ;
	@Autowired
	private OperateFastV10Data operateFastData ;
	
	/**
	 * 查询资讯主页信息
	 * @return
	 */
	@RequestMapping(value = "/home", method = { RequestMethod.GET})
	public Object findMobailHome(@RequestParam(value = "typeCode", required = true)  String typeCode,@RequestParam(value = "token")  String token) {
		if(StringUtil.isBlank(typeCode)){
			typeCode = AriticleEnum.TOP.getValue();
		}
		short platFrom = PlatFormEnum.ANDROID.getValue();
		OperateAdVO advo =new OperateAdVO();
		advo.setPlatform(platFrom);
		advo.setMenu(AdMenuEnum.NEWS.getValue()) ;		
		if(!StringUtil.isBlank(token)){
			UserInfoBO userInfo = (UserInfoBO) userInfoCacheData.checkToken(token).getData();
			 if (ObjectUtil.isBlank(userInfo)) {
		            throw new ResultJsonException(ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE));
		     }
			 advo.setUserId(userInfo.getId().longValue());
		}			
		OperateFastVO fastVo = new OperateFastVO();
		fastVo.setPlatform(platFrom);
		fastVo.setPosition(FastMenuEnum.NEWS.getValue());
			
		OperateArticleLottVO articleVO = new OperateArticleLottVO();
		articleVO.setTypeCode(typeCode);
		articleVO.setCompleCode(AriticleEnum.NEWS.getValue());
		articleVO.setIsComple(Constants.YES);
		articleVO.setStartRow(Constants.NUM_1);
		articleVO.setPageSize(ArticleConstants.NEWS_MOBAIL_HOME_ROW);
		
		List<OperateAdLottoBO>  operAdList =  operateAdData.findHomeOperAd(advo).getData();
		List<OperateFastLottBO>  operFastList =  operateFastData.findOperFastIssueDetail(fastVo).getData();
		List<OperateArticleLottBO> operArtList = iOperateArticleService.findArticleByTop(articleVO).getData();	
		return ResultBO.ok(new MobialNewsHomeBO(operAdList, operArtList, operFastList));
	}
}