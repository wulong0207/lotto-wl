package com.hhly.lotto.api.pc.controller.home.v1_0;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.commoncore.remote.operate.service.IOperateArticleService;
import com.hhly.commoncore.remote.operate.service.IOperateMsgService;
import com.hhly.lotto.api.common.controller.home.v1_0.HomeCommonV10Controller;
import com.hhly.lotto.api.data.cache.v1_0.UserInfoCacheData;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.lottocore.remote.lotto.service.IJcDataService;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.lottocore.remote.order.service.IOrderSearchService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.lotto.base.order.vo.OrderQueryVo;
import com.hhly.skeleton.user.bo.UserInfoBO;
import com.hhly.usercore.remote.member.service.IMemberWinningService;

/**
 * @desc pc主页
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/v1.0/home")
public class HomePcV10Controller extends HomeCommonV10Controller {
	@Autowired
	private IOperateArticleService iOperateArticleService;
	@Autowired
	private IOperateMsgService iOperateMsgService;
	@Autowired
	private ILotteryIssueService iLotteryIssueService;
	@Autowired
	private IJcDataService jcDataService;
	@Autowired
	private IOrderSearchService iorderSearchService;
	@Autowired
	private IMemberWinningService memberWinningService;
	@Autowired
    private RedisUtil objectRedisUtil;
	
	@Autowired
    private UserInfoCacheData userInfoCacheData;

	/**
	 * 查询开奖公告
	 * 
	 * @return
	 */
	@RequestMapping(value = "/drawlott", method = RequestMethod.GET)
	public Object findDrawlott() {
		return iLotteryIssueService.findHomeDrawLott();
	}

	
	/**
	 * 查询主页新闻信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/article", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findArticle() {		
		String cachekey = CacheConstants.C_PC_ARTICLE_FIND_HOME;
		Map<String,Object> retMap = objectRedisUtil.getObj(cachekey,new HashMap<String,Object>());
		if(retMap==null){
			retMap = iOperateArticleService.findPCHomeArticle().getData();		
			objectRedisUtil.addObj(cachekey, retMap,CacheConstants.SIX_HOURS);
		}		
		return ResultBO.ok(retMap);
	}

	/**
	 * 查询主页短信信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/opermsg", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findOperMsgList(@RequestParam("token") String token) {
		if (StringUtil.isBlank(token)) {
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		UserInfoBO userInfo =(UserInfoBO) userInfoCacheData.checkToken(token).getData();
        if (ObjectUtil.isBlank(userInfo)) {
            throw new ResultJsonException(ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE));
        }
		return iOperateMsgService.findOperMsgList(userInfo.getId());
	}

	/**
	 * 查询胜负彩比赛信息
	 * 
	 * @param lotteryCode
	 * @param issueCode
	 * @return
	 */
	@RequestMapping(value = "/sfcmatch", method = RequestMethod.GET)
	public Object getSportMatch(@RequestParam("lotteryCode") Integer lotteryCode) {
		if (lotteryCode == null || !Lottery.contain(lotteryCode)) {
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		return jcDataService.findHomeSportMatchInfo(lotteryCode);
	}

	/**
	 * 查询用户首页订单和首页统计数据
	 * 
	 * @param orderQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHomeOrderInfoList", method = { RequestMethod.GET, RequestMethod.POST })
	public ResultBO<?> queryHomeOrderInfoList(OrderQueryVo orderQueryVo) throws Exception {
		if (ObjectUtil.isBlank(orderQueryVo) || ObjectUtil.isBlank(orderQueryVo.getToken())
				|| ObjectUtil.isNull(orderQueryVo.getStatus())) {
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		if (orderQueryVo.getStatus() != Constants.NUM_1 && orderQueryVo.getStatus() != Constants.NUM_2) {
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID, "status");
		}
		return iorderSearchService.queryHomeOrderList(orderQueryVo);

	}

	/**
	 * 查询钱包信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/wininfo", method = RequestMethod.GET)
	public Object findWininfo() {
		ResultBO<?> result = (ResultBO<?>) objectRedisUtil.getObj(CacheConstants.C_COMM_USER_WINNING);
		if (result != null && result.isOK()) {
			return result;
		}
		result = ResultBO.ok(memberWinningService.findHomeWin());
		objectRedisUtil.addObj(CacheConstants.C_COMM_USER_WINNING, result, CacheConstants.TEN_MINUTES);
		return result;
	}

	@Override
	protected Short getMobilePlatForm() {
		return null;
	}

}