package com.hhly.lotto.api.common.controller.home.v1_0;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.hhly.skeleton.user.vo.UserWinInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.commoncore.remote.dicdata.service.IDicDataDetailService;
import com.hhly.commoncore.remote.operate.service.IOperateService;
import com.hhly.lotto.api.data.operate.v1_0.OperateLotteryV10Data;
import com.hhly.lotto.base.common.HeaderParam;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.lottocore.remote.lotto.service.ILotteryService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.util.CopyUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryTypeBO;
import com.hhly.skeleton.lotto.base.dic.bo.DicDataDetailBO;
import com.hhly.skeleton.lotto.base.dic.vo.DicDataDetailVO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateLottSoftwareVersionBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateLotteryDetailBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateLotteryOperinBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateLottSoftwareVersionVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateLotteryLottVO;
import com.hhly.usercore.remote.member.service.IMemberWinningService;

/**
 * @desc 主页
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class HomeCommonV10Controller extends BaseController {
	@Autowired
	private IOperateService iOperateService;
	@Autowired
	private ILotteryService iLotteryService;
	@Autowired
	private IDicDataDetailService IDicDataDetailService;
	@Autowired
	private OperateLotteryV10Data operateLotteryData ;
	@Autowired
    private RedisUtil objectRedisUtil;
	@Autowired
	private ILotteryIssueService iLotteryIssueService;
	@Autowired
	private IMemberWinningService memberWinningService;

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

	/**
	 * 客户电话
	 * @return
	 */
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
	@RequestMapping(value = "/channelVersion" , method = { RequestMethod.GET })
	public Object findVersionByChannelId(OperateLottSoftwareVersionVO vo,HttpServletRequest request) {
		HeaderParam headerParam = getHeaderParam(request);
		if(ObjectUtil.isBlank(vo.getChannelId())) {
			if ( ObjectUtil.isBlank(headerParam.getChannelId())) {
				vo.setChannelId(Constants.BASE_CHINAL);
			}else{
				vo.setChannelId(Integer.valueOf(headerParam.getChannelId()));
			}
		}
		return iOperateService.findVersionByChannelId(vo);
	}
	
	/**
	 * 查询版本信息
	 * @return
	 */
	@RequestMapping(value = "/version", method = { RequestMethod.GET })
	public Object findNewVersion(OperateLottSoftwareVersionVO vo,HttpServletRequest request){
		HeaderParam headerParam = getHeaderParam(request);
		if(ObjectUtil.isBlank(vo.getChannelId())) {
			if ( ObjectUtil.isBlank(headerParam.getChannelId())) {
				vo.setChannelId(Constants.BASE_CHINAL);
			}else{
				vo.setChannelId(Integer.valueOf(headerParam.getChannelId()));
			}
		}
		if(ObjectUtil.isBlank(vo.getType())){
			vo.setType(getMobilePlatForm());
		}		
		String key = CacheConstants.C_COMM_VERSION_FIND_NEW_VERSION+vo.getChannelId()+vo.getType();
		OperateLottSoftwareVersionBO versionBO =  (OperateLottSoftwareVersionBO)objectRedisUtil.getObj(key);
		if(versionBO==null){
			versionBO = iOperateService.findNewVersion(vo).getData();
			objectRedisUtil.addObj(key, versionBO, (long)Constants.NUM_600);
		}				
		return ResultBO.ok(versionBO);
	}
	
	/**
	 * 查询快速入口信息
	 * @return
	 */
	@RequestMapping(value = "/operin", method = { RequestMethod.GET, RequestMethod.POST })
	public Object findOperin(HttpServletRequest request) {		
		OperateLotteryLottVO lottvo = new OperateLotteryLottVO();
		HeaderParam headerParam = getHeaderParam(request);
		lottvo.setPlatform(headerParam.getPlatformId().shortValue());
		List<OperateLotteryDetailBO> detailList = operateLotteryData.findHomeOperLottery(lottvo).getData();
		List<OperateLotteryOperinBO> operinList  = CopyUtil.copyPropertiesList(OperateLotteryOperinBO.class, detailList);
		return ResultBO.ok(operinList);
	}

	/**
	 * 查询中奖轮播信息
	 * @param lotteryCodes
	 * @return
	 */
	@RequestMapping(value = "/roll/winInfo", method = RequestMethod.GET)
	public ResultBO<?> queryUserWinInfo(String lotteryCodes) {
		try {
			return memberWinningService.queryUserWinInfo(lotteryCodes);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultBO.err();
		}
	}

	@RequestMapping(value = "/winInfo/list", method = RequestMethod.GET)
	public ResultBO<?> queryWinInfoList(UserWinInfoVO vo) {
		try {
			return memberWinningService.queryWinInfoList(vo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResultBO.err();
		}
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
	 * @desc   返回平台代码，对应表OPERATE_SOFTWARE_VERSION的type字段
	 * @author Tony Wang
	 * @create 2017年12月26日
	 * @return 
	 */
	protected abstract Short getMobilePlatForm();
}