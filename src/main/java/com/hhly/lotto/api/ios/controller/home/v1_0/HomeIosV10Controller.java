package com.hhly.lotto.api.ios.controller.home.v1_0;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.commoncore.remote.operate.service.IOperateArticleService;
import com.hhly.commoncore.remote.operate.service.IOperateService;
import com.hhly.lotto.api.common.controller.home.v1_0.HomeCommonV10Controller;
import com.hhly.lotto.api.data.cache.v1_0.UserInfoCacheData;
import com.hhly.lotto.api.data.news.v1_0.OperateNewsData;
import com.hhly.lotto.api.data.operate.v1_0.OperateAdV10Data;
import com.hhly.lotto.api.data.operate.v1_0.OperateFastV10Data;
import com.hhly.lotto.api.data.operate.v1_0.OperateLotteryV10Data;
import com.hhly.lotto.api.data.operate.v1_0.OperateV10Data;
import com.hhly.lotto.api.h5.home.controller.HomeH5Controller;
import com.hhly.lotto.base.common.HeaderParam;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.DicEnum.AdMenuEnum;
import com.hhly.skeleton.base.common.DicEnum.FastMenuEnum;
import com.hhly.skeleton.base.common.DicEnum.MobailPlatFormEnum;
import com.hhly.skeleton.base.common.DicEnum.PlatFormEnum;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.lotto.base.operate.bo.OperateAdLottoBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateArticleLottBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateFastLottBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateLottBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateLotteryDetailBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateAdVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateFastVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateVO;
import com.hhly.skeleton.lotto.mobile.home.bo.MobailHomeBO;
import com.hhly.skeleton.user.bo.UserInfoBO;

import common.Logger;

/** 
 * @desc ios主页
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/ios/v1.0/home")
public class HomeIosV10Controller extends HomeCommonV10Controller{
	private static final Logger LOGGER = Logger.getLogger(HomeH5Controller.class);
	@Autowired
	private IOperateService iOperateService ;
	@Autowired
	private OperateLotteryV10Data operateLotteryData ;
	@Autowired
	private OperateAdV10Data operateAdData ;
	@Autowired
	private OperateFastV10Data operateFastData ;
	@Autowired
	private UserInfoCacheData userInfoCacheService;
	@Autowired
	private OperateV10Data operateV10Data ;
	@Autowired
    private OperateNewsData OperateNewsData;
	/**
	 * 查询主页信息
	 * @return
	 */
	@RequestMapping(value = "/operall", method = { RequestMethod.GET})
	public Object findMobialHome(OperateVO vo,HttpServletRequest request) {
		Long userId =null;
		if(!StringUtil.isBlank(vo.getToken())){
			UserInfoBO userInfo = (UserInfoBO) userInfoCacheService.checkToken(vo.getToken()).getData();
			 if (ObjectUtil.isBlank(userInfo)) {
		            throw new ResultJsonException(ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE));
		     }
			 userId =userInfo.getId().longValue(); 
		}
		Short platFrom =PlatFormEnum.IOS.getValue();
		OperateAdVO advo =new OperateAdVO();
		advo.setPlatform(platFrom);
		advo.setMenu(AdMenuEnum.MOBAILHOME.getValue());	
		advo.setUserId(userId);	
		HeaderParam headerParam = getHeaderParam(request);
		advo.setChannelId(headerParam.getChannelId());
		OperateFastVO fastVo = new OperateFastVO();
		fastVo.setPlatform(platFrom);
		fastVo.setPosition(FastMenuEnum.HOME.getValue());		
		List<OperateLotteryDetailBO>  operLottList = null;
		try {
			operLottList =operateLotteryData.findOperLottery(platFrom);
		} catch (Exception e) {
			LOGGER.error("彩种运营异常"+e.getMessage());
		}		
		List<OperateAdLottoBO>  operAdList = null;
		try {
			operAdList=	operateAdData.findHomeOperAd(advo).getData();		
		} catch (Exception e) {
			LOGGER.error("广告异常"+e.getMessage());
		}
		List<OperateFastLottBO>  operFastList = null;
		try {
			operFastList=operateFastData.findOperFastIssueDetail(fastVo).getData();
		} catch (Exception e) {
			LOGGER.error("快投异常"+e.getMessage());
		}
		List<OperateArticleLottBO>  operArtList = null;
		try {
			operArtList=OperateNewsData.findMobailHomeArticle(platFrom).getData();
		} catch (Exception e) {
			LOGGER.error("文章信息异常"+e.getMessage());
		}
		List<OperateLottBO> lotteryList =  null;
		try {
			lotteryList = operateLotteryData.getMobailLottList();
		} catch (Exception e) {
			LOGGER.error("彩种信息异常"+e.getMessage());
		}

		List<String> winInfoList = null;
		try {
			winInfoList = operateV10Data.findUserWinInfo().getData();
		} catch (Exception e) {
			LOGGER.error("用户中奖轮播信息异常"+e.getMessage());
		}
		Integer status = Constants.NUM_1;
		return  ResultBO.ok(new MobailHomeBO(operLottList,operAdList,operArtList,operFastList, winInfoList,lotteryList,status));
	}
	@Override
	protected Short getMobilePlatForm() {
		return MobailPlatFormEnum.IOS.getValue();
	}
}