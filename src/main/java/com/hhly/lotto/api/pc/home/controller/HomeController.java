package com.hhly.lotto.api.pc.home.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.commoncore.remote.dicdata.service.IDicDataDetailService;
import com.hhly.commoncore.remote.operate.service.IOperateArticleService;
import com.hhly.commoncore.remote.operate.service.IOperateMsgService;
import com.hhly.commoncore.remote.operate.service.IOperateService;
import com.hhly.lotto.api.data.cache.v1_0.UserInfoCacheData;
import com.hhly.lotto.api.data.operate.v1_0.OperateFastV10Data;
import com.hhly.lotto.api.data.operate.v1_0.OperateLotteryV10Data;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.lottocore.remote.lotto.service.IJcDataService;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.lottocore.remote.lotto.service.ILotteryService;
import com.hhly.lottocore.remote.order.service.IOrderSearchService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.DicEnum.FastMenuEnum;
import com.hhly.skeleton.base.common.DicEnum.PlatFormEnum;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.common.LotteryEnum.LotteryCategory;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryTypeBO;
import com.hhly.skeleton.lotto.base.dic.bo.DicDataDetailBO;
import com.hhly.skeleton.lotto.base.dic.vo.DicDataDetailVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateFastVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateLottSoftwareVersionVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateLotteryLottVO;
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
@RequestMapping("/pc/home")
public class HomeController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private IOperateService iOperateService;
	@Autowired
	private ILotteryIssueService iLotteryIssueService;
	@Autowired
	private IJcDataService jcDataService;
	@Autowired
	private IOrderSearchService iorderSearchService;
	@Autowired
	private IMemberWinningService memberWinningService;
	@Autowired
	private ILotteryService iLotteryService;
	@Autowired
	private IDicDataDetailService IDicDataDetailService;
	@Autowired
    private UserInfoCacheData userInfoCacheData;
	@Autowired
	private IOperateMsgService iLotteryMsgService;
	@Autowired
	private OperateFastV10Data lotteryFastData;
	@Autowired
	private OperateLotteryV10Data operateLotteryData;
	@Autowired
	private IOperateArticleService iOperateArticleService;
	@Autowired
    private RedisUtil objectRedisUtil;
	/**
	 * 查询主页彩种运营信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/operlottery", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findOperLottery(OperateLotteryLottVO vo) {
		if (vo.getCategoryId() != null && !LotteryCategory.contain(vo.getCategoryId())) {
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID, "categoryId");
		}
		if (vo.getPlatform() != null && !PlatFormEnum.contain(vo.getPlatform())) {
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID, "platform");
		}
		if (vo.getPlatform() == null) {
			vo.setPlatform(PlatFormEnum.WEB.getValue());
		}

		return operateLotteryData.findHomeOperLottery(vo);
	}

	/**
	 * 查询开奖公告
	 * 
	 * @return
	 */
	@RequestMapping(value = "/drawlott", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findDrawlott() {
		return iLotteryIssueService.findHomeDrawLott();
	}

	/**
	 * 查询快投信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/fast", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findFast(OperateFastVO vo) {
		if (vo.getPosition() != null && !FastMenuEnum.contain(vo.getPosition())) {
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID, "position");
		}
		if (vo.getPlatform() != null && !PlatFormEnum.contain(vo.getPlatform())) {
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID, "platform");
		}
		if (vo.getPlatform() == null) {
			vo.setPlatform(PlatFormEnum.WEB.getValue());
		}
		if (vo.getPosition() == null) {
			vo.setPosition(FastMenuEnum.HOME.getValue());
		}
		return lotteryFastData.findOperFastIssueDetail(vo);
	}

	/**
	 * 查询主页新闻信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/article", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findArticle() {
		return iOperateArticleService.findPCHomeArticle();
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
		return iLotteryMsgService.findOperMsgList(userInfo.getId());
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
	public ResultBO<?> queryHomeOrderInfoList(OrderQueryVo orderQueryVo) {
		logger.debug("查询首页订单");
		if (ObjectUtil.isBlank(orderQueryVo) || ObjectUtil.isBlank(orderQueryVo.getToken())
				|| ObjectUtil.isNull(orderQueryVo.getStatus())) {
			return ResultBO.err(MessageCodeConstants.PARAM_IS_FIELD);
		}
		if (orderQueryVo.getStatus() != Constants.NUM_1 && orderQueryVo.getStatus() != Constants.NUM_2) {
			return ResultBO.err(MessageCodeConstants.PARAM_INVALID, "status");
		}
		ResultBO<?> resultBO = null;
		try {
			resultBO = iorderSearchService.queryHomeOrderList(orderQueryVo);
		}catch (Exception e){
			logger.error("查询首页订单异常！",e);
			return ResultBO.err();
		}
        return resultBO;
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
		objectRedisUtil.addObj(CacheConstants.C_COMM_USER_WINNING, result, CacheConstants.FIVE_MINUTES);
		return result;
	}

	/**
	 * 查询当前期和上一期信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/prelott", method = RequestMethod.GET)
	public Object findIssueAndPreIssueByCode(Integer lotteryCode) {
		return iLotteryIssueService.findIssueAndPreIssueByCode(lotteryCode);
	}

	/**
	 * 查询所有彩种基本信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/lotterytype", method = RequestMethod.GET)
	public Object findAllLotteryType() {
		String key = CacheConstants.C_COMM_LOTTERY_FIND_ALL_TYPE;
    	List<LotteryTypeBO> list  = objectRedisUtil.getObj(key,new ArrayList<LotteryTypeBO>());
    	if(list==null){
    		list = iLotteryService.findAllLotteryType().getData();
    		objectRedisUtil.addObj(key, list, (long)Constants.DAY_1);
    	}
		return ResultBO.ok(list);
	}

	/**
	 * 查询服务器时间
	 * 
	 * @return
	 */
	@RequestMapping(value = "/servicetime", method = RequestMethod.GET)
	public Object findServiceTime() {
		return ResultBO.ok(new Date().getTime());
	}

	@RequestMapping(value = "/customertel", method = RequestMethod.GET)
	public Object findCustomerTel() {
		DicDataDetailVO dicDataDetailVO = new DicDataDetailVO();
		dicDataDetailVO.setDicCode(Constants.DIC_CUS_TEL);
		DicDataDetailBO dicDataDetailBO = IDicDataDetailService.findSingle(dicDataDetailVO);
		return ResultBO.ok(dicDataDetailBO.getDicDataName());
	}
	/**
	 * 查询渠道版本信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/channelVersion" , method = { RequestMethod.GET, RequestMethod.POST })
	public Object findVersionByChannelId(OperateLottSoftwareVersionVO vo) {
		if ( ObjectUtil.isBlank(vo.getChannelId())) {
			vo.setChannelId(Constants.BASE_CHINAL);
		}
		return iOperateService.findVersionByChannelId(vo);
	}
}