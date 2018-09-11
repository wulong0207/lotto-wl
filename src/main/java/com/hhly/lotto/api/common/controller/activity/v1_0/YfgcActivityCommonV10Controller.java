package com.hhly.lotto.api.common.controller.activity.v1_0;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottoactivity.remote.service.IActivityService;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.lottocore.remote.lotto.service.ILotteryService;
import com.hhly.lottocore.remote.order.service.IOrderAddService;
import com.hhly.lottocore.remote.order.service.IOrderService;
import com.hhly.skeleton.activity.bo.ActivityDynamicBO;
import com.hhly.skeleton.activity.bo.YfgcActivityBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.ChaseEnum.ChaseStopType;
import com.hhly.skeleton.base.common.ChaseEnum.ClientType;
import com.hhly.skeleton.base.common.LotteryChildEnum;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.common.LotteryEnum.ConIssue;
import com.hhly.skeleton.base.common.OrderEnum;
import com.hhly.skeleton.base.common.OrderEnum.BetContentType;
import com.hhly.skeleton.base.common.OrderEnum.PlatformType;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.lotto.base.issue.bo.IssueBO;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryBO;
import com.hhly.skeleton.lotto.base.lottery.bo.LotteryIssueBaseBO;
import com.hhly.skeleton.lotto.base.lottery.vo.LotteryVO;
import com.hhly.skeleton.lotto.base.order.bo.OrderAddBO;
import com.hhly.skeleton.lotto.base.order.bo.WinBO;
import com.hhly.skeleton.lotto.base.order.vo.OrderAddIssueVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderAddQueryVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderAddVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderDetailVO;
import com.hhly.skeleton.lotto.base.order.vo.OrderInfoQueryVO;
import com.hhly.skeleton.lotto.base.order.vo.WinVO;
import com.hhly.skeleton.user.bo.UserInfoBO;
import com.hhly.usercore.remote.activity.service.IMemberActivityService;

/**
 * @desc    广东11选5一分购彩活动
 * @author  Tony Wang
 * @date    2017年8月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public abstract class YfgcActivityCommonV10Controller extends BaseController {

    @Autowired
    private IActivityService activityService;
    @Autowired
    private ILotteryService lotteryService;
    @Autowired
    private ILotteryIssueService lotteryIssueService;
    @Autowired
    private IOrderService orderService;
    /**
	 * 远程服务:追号服务(包含追号)
	 */
	@Autowired
	private IOrderAddService orderAddService;
    @Autowired
    private IMemberActivityService memberActivityService;
    
	public static final List<Integer> ISSUE_RANGE = Collections.unmodifiableList(Arrays.asList(1,2,3));
	
	public abstract int getLottery();
	
    /**
     * @desc   活动信息
     * @author Tony Wang
     * @create 2017年8月12日
     * @return 
     */
    @RequestMapping(value = "/info" ,method = RequestMethod.GET)
    public ResultBO<YfgcActivityBO> info(){
        return activityService.findYfgcActivityInfo(getLottery());
    }
    
    /**
     * @desc   彩种信息
     * @author Tony Wang
     * @create 2017年8月12日
     * @return 
     */
    @RequestMapping(value = "/lottoinfo" ,method = RequestMethod.GET)
    public ResultBO<LotteryIssueBaseBO> lottoInfo(){
    	LotteryIssueBaseBO lottery = new LotteryIssueBaseBO();
    	LotteryBO lot = lotteryService.findSingleFront(new LotteryVO(getLottery()));
    	lot.setDrawTime(null);
    	lot.setVacations(null);
    	lot.setStartSailTime(null);
    	lot.setEndSaleTime(null);
    	lot.setEndCheckTime(null);
    	lot.setBuyEndTime(null);
    	lottery.setCurLottery(lot);
    	
    	LotteryVO issueVO = new LotteryVO();
    	issueVO.setLotteryCode(getLottery());
    	issueVO.setCurrentIssue(ConIssue.CURRENT.getValue());
    	IssueBO curIssue = lotteryIssueService.findSingleFront(issueVO);
    	curIssue.setDrawDetail(null);
    	curIssue.setLotteryCode(null);
    	curIssue.setOfficialEndTime(null);
    	curIssue.setOfficialStartTime(null);
    	lottery.setCurIssue(curIssue);
    	return ResultBO.ok(lottery);
    }
    
    /**
     * @desc   查询最近中奖
     * @author Tony Wang
     * @create 2017年8月12日
     * @param token
     * @return 
     */
    @RequestMapping(value = "/win" ,method = RequestMethod.GET)
    public ResultBO<List<WinBO>> winInfo(){
    	WinVO win = new WinVO();
    	win.setLotteryCode(getLottery());
    	// 1：未开奖；2：未中奖；3：已中奖；4：已派奖
    	win.setWinningStatuses(Arrays.asList(3,4));
    	// 6：已出票
    	win.setOrderStatus(6);
    	win.setOrderField("end_ticket_time");
    	win.setOrderValue("desc");
    	// 正常情况下展示该彩种最近20条中奖记录，前端页面展示5条且轮播展示
    	// 
    	win.setLimit(20);
    	return ResultBO.ok(orderService.findWinInfo(win));
    }
    
    /**
     * @desc   下单前验证
     * @author Tony Wang
     * @create 2017年8月18日
     * @param chase
     * @return
     * @throws Exception 
     */
    @RequestMapping(value = "/validate" ,method = RequestMethod.POST)
    public ResultBO<?> validate(@RequestBody OrderAddVO chase) throws Exception{
    	// 各种校验
    	validateActivityInfoBasic();
    	UserInfoBO userInfo = validateUserInfo(chase);
    	validateOrderInfo(chase);
    	chase.setActivityId(activityService.findYfgcActivityInfo(getLottery()).getData().getActivityCode());
    	validateActivityInfo(chase, userInfo);
		return ResultBO.ok();
    }
    
	/**
     * @desc   一分购彩下单，实际是追号单
     * @author Tony Wang
     * @create 2017年8月12日
     * @return 
     * @throws Exception 
     */
    @RequestMapping(value = "/order" ,method = RequestMethod.POST)
    public ResultBO<?> orderYfgc(@RequestBody OrderAddVO chase) throws Exception{
    	// 各种校验
    	validateActivityInfoBasic();
    	UserInfoBO userInfo = validateUserInfo(chase);
    	validateOrderInfo(chase);
    	// 查询活动Id并设置
    	chase.setActivityId(activityService.findYfgcActivityInfo(getLottery()).getData().getActivityCode());
    	validateActivityInfo(chase, userInfo);
    	// 若走正常追号流程，则会根据此字段来决定是否生成追号列表
    	chase.setSource(getClientType());
		return orderAddService.addChase(chase);
    }
    
    /**
     * @desc   验证活动是否有效
     * @author Tony Wang
     * @create 2017年9月14日 
     */
    private void validateActivityInfoBasic() {
    	ResultBO<YfgcActivityBO> ret = activityService.findYfgcActivityInfo(getLottery());
    	// 活动信息不存在
		Assert.notNull(ret, MessageCodeConstants.FOOTBALL_FIRST_ACTIVITY_NOT_EXIST);
		YfgcActivityBO activity = ret.getData();
		// 0:停用 1:启用
		Assert.notNull(activity, MessageCodeConstants.FOOTBALL_FIRST_ACTIVITY_NOT_EXIST);
		Assert.isTrue(Objects.equals(activity.getActivityStatus(), 1), MessageCodeConstants.FOOTBALL_FIRST_DISCOUNT_OVERDUE);
		Date now = new Date();
		Assert.isFalse(now.before(activity.getActivityStartTime()), MessageCodeConstants.FOOTBALL_FIRST_DISCOUNT_NO_START);
		Assert.isFalse(now.after(activity.getActivityEndTime()), MessageCodeConstants.FIRST_REC_ACTIVITY_END);
	}
    
    /**
	 * @desc   验证用户信息
	 * @author Tony Wang
	 * @create 2017年8月15日
	 * @param chase 
     * @throws Exception 
	 */
	private UserInfoBO validateUserInfo(OrderAddVO chase) throws Exception {
		// 验证是否登录及用户资料是否完善
    	ResultBO<?> ret;
    	ret = memberActivityService.verifyPerfectInfo(chase.getToken());
    	if(ret.isError())
    		throw new ResultJsonException(ret);
    	return (UserInfoBO)ret.getData();
	}
    
	/**
	 * @desc   验证活动信息
	 * @author Tony Wang
	 * @create 2017年8月15日
	 * @param chase 
	 */
	private void validateActivityInfo(OrderAddVO chase, UserInfoBO userInfo) {
		// 获取用户信息,以用户ID、身份证、手机号查询是否参与过活动及购买过广东十一选五
    	OrderAddQueryVO orderAddcriteria = new OrderAddQueryVO();
    	orderAddcriteria.setUserId(userInfo.getId());
    	orderAddcriteria.setActivityId(chase.getActivityId());
    	/* 
    	 * 因为活动是下追号单，所以查询追号表
    	 * (待支付//支付中）只允许一个订单存在并且下单时引导支付， （ 未支付过期/支付失败/用户删除未支付的订单）可以再次下单，其他状态订单视为已参加过活动。
    	 * 视为参与过活动的：2支付成功  6退款；
    	 * 可以再次下单的：  3未支付过期 4支付失败 5用户取消；
    	 * 已经参活动，提示支付的：1等待支付 7支付中(只能有一条记录)
    	 */
    	List<Short> payStatuses = Arrays.asList(
			OrderEnum.PayStatus.WAITING_PAY.getValue(),
			OrderEnum.PayStatus.PAYING.getValue(),
			OrderEnum.PayStatus.OVERDUE_PAY.getValue(),
			OrderEnum.PayStatus.FAILING_PAY.getValue(),
			OrderEnum.PayStatus.USER_CANCLE_PAY.getValue()
		);
    	orderAddcriteria.setNegativePayStatuses(payStatuses);
    	// 如果参与过活动且支付成功或退过款
    	Assert.isTrue(orderAddService.countOrderAdd(orderAddcriteria)==0, MessageCodeConstants.YFGC_USER_PARTICIPATED ,
    			ActivityDynamicBO.newInstanceWithAmount(chase.getAddAmount()), 
    			new Object [] {chase.getAddAmount()});
    	
    	// 查询是否有(待支付//支付中）的活动记录
    	orderAddcriteria =  new OrderAddQueryVO();
    	orderAddcriteria.setUserId(userInfo.getId());
    	orderAddcriteria.setActivityId(chase.getActivityId());
    	orderAddcriteria.setPayStatuses(Arrays.asList(OrderEnum.PayStatus.WAITING_PAY.getValue(),OrderEnum.PayStatus.PAYING.getValue()));
    	// 如果有未支付订单，要返回订单号(此处是追号单)
    	List<OrderAddBO> orderAddes = orderAddService.findOrderAdd(orderAddcriteria);
    	if(!CollectionUtils.isEmpty(orderAddes)) {
    		throw new ResultJsonException(ResultBO.err(MessageCodeConstants.YFGC_USER_NOTPAY, 
    				ActivityDynamicBO.newInstanceWithOrderAddCode(orderAddes.get(0).getOrderAddCode()),
    				new Object [] {""}));
    	}
    	// 验证是否有同一身份证、手机号的账号正在参与活动
    	// 查询是否有(待支付//支付中）的活动记录
    	orderAddcriteria =  new OrderAddQueryVO();
    	orderAddcriteria.setActivityId(chase.getActivityId());
    	orderAddcriteria.setJoinUser(true);
    	orderAddcriteria.setIdCard(userInfo.getIdCard());
    	orderAddcriteria.setMobile(userInfo.getMobile());
    	orderAddcriteria.setPayStatuses(Arrays.asList(OrderEnum.PayStatus.WAITING_PAY.getValue(),OrderEnum.PayStatus.PAYING.getValue()));
    	// 如果有未支付订单，要返回订单号(此处是追号单)
    	orderAddes = orderAddService.findOrderAdd(orderAddcriteria);
    	if(!CollectionUtils.isEmpty(orderAddes)) {
    		throw new ResultJsonException(ResultBO.err(MessageCodeConstants.YFGC_ACCOUNT_NOTPAY, 
    				ActivityDynamicBO.newInstanceWithOrderAddCode(orderAddes.get(0).getOrderAddCode()),
    				new Object [] {""}));
    	}

    	// 身份证及手机号码排重，是否参与过活动
    	orderAddcriteria =  new OrderAddQueryVO();
    	orderAddcriteria.setActivityId(chase.getActivityId());
    	orderAddcriteria.setJoinUser(true);
    	orderAddcriteria.setIdCard(userInfo.getIdCard());
    	orderAddcriteria.setMobile(userInfo.getMobile());
    	orderAddcriteria.setNegativePayStatuses(payStatuses);
    	// 如果参与过活动且支付成功或退过款
    	Assert.isTrue(orderAddService.countOrderAdd(orderAddcriteria)==0, MessageCodeConstants.YFGC_ACCOUNT_PARTICIPATED ,
    			ActivityDynamicBO.newInstanceWithAmount(chase.getAddAmount()), 
    			new Object [] {chase.getAddAmount()});
    	
    	// 用户是否购买过11选5
    	OrderInfoQueryVO orderInfocriteria = new OrderInfoQueryVO();
    	orderInfocriteria.setUserId(userInfo.getId());
    	orderInfocriteria.setLotteryCode(getLottery());
    	// 查询支付成功或退款的订单
    	orderInfocriteria.setPayStatuses(Arrays.asList(OrderEnum.PayStatus.SUCCESS_PAY.getValue(),OrderEnum.PayStatus.REFUNDMENT.getValue()));
    	Assert.isTrue(orderService.countOrderInfo(orderInfocriteria)==0, MessageCodeConstants.YFGC_11X5_USER_PARTICIPATED ,
    			ActivityDynamicBO.newInstanceWithAmount(chase.getAddAmount()), 
    			new Object [] {chase.getAddAmount()});
    	
    	// 身份证及手机号码排重，是否购买过11选5
    	orderInfocriteria.setUserId(null);
    	orderInfocriteria.setJoinUser(true);
    	orderInfocriteria.setIdCard(userInfo.getIdCard());
    	orderInfocriteria.setMobile(userInfo.getMobile());
    	Assert.isTrue(orderService.countOrderInfo(orderInfocriteria)==0, MessageCodeConstants.YFGC_11X5_ACCOUNT_PARTICIPATED,
    			ActivityDynamicBO.newInstanceWithAmount(chase.getAddAmount()), 
    			new Object [] {chase.getAddAmount()});

    	// 参数活动的用户，真实姓名是否超过10个,不包含自己
    	// 排除 3未支付过期 4支付失败 5用户取消
    	orderAddcriteria = new OrderAddQueryVO();
    	orderAddcriteria.setActivityId(chase.getActivityId());
    	orderAddcriteria.setNegativeUserId(userInfo.getId());
    	orderAddcriteria.setJoinUser(true);
    	orderAddcriteria.setActualName(userInfo.getRealName());
    	orderAddcriteria.setNegativePayStatuses(
    			Arrays.asList(OrderEnum.PayStatus.OVERDUE_PAY.getValue(),
    			OrderEnum.PayStatus.FAILING_PAY.getValue(),
    			OrderEnum.PayStatus.USER_CANCLE_PAY.getValue())
    			);
    	Assert.isTrue(orderAddService.countOrderAdd(orderAddcriteria)<10, MessageCodeConstants.YFGC_10_PARTICIPATED,
    			ActivityDynamicBO.newInstanceWithName(userInfo.getRealName()), 
    			new Object [] {userInfo.getRealName()});
	}
	
	/**
	 * @desc   验证下单信息
	 * @author Tony Wang
	 * @create 2017年8月15日
	 * @param chase 
	 */
	private void validateOrderInfo(OrderAddVO chase) {
		// 活动只支持广东十一选五，暂时配一个江西11选5
    	Assert.isTrue(Objects.equals(chase.getLotteryCode(), LotteryEnum.Lottery.D11X5.getName()) || 
    				 Objects.equals(chase.getLotteryCode(), LotteryEnum.Lottery.JX11X5.getName()),MessageCodeConstants.YFGC_LOTTERY);
    	// 活动只支持PC和H5端
    	Assert.isTrue(
    			Objects.equals(chase.getPlatform(), PlatformType.WEB.getValue()) || 
    			Objects.equals(chase.getPlatform(), PlatformType.WAP.getValue()),
    			MessageCodeConstants.YFGC_PLATFORM);
    	// 只能买1，2，3期
    	Assert.isTrue(ISSUE_RANGE.contains(chase.getAddIssues()),MessageCodeConstants.YFGC_ISSUE_RANGE);
    	// 活动要把选号方式传过来，所以要生成只有一行投注内容的ContentList
    	Assert.isFalse(CollectionUtils.isEmpty(chase.getOrderAddContentList()), MessageCodeConstants.BET_CONTENT_IS_NULL_FIELD);
    	// 只能有一行投注内容
    	Assert.isTrue(chase.getOrderAddContentList().size()==1, MessageCodeConstants.YFGC_ONLY_ONE_BET);
    	// 活动单只能投1注1倍
    	Assert.isTrue(Objects.equals(chase.getAddCount(), 1), MessageCodeConstants.YFGC_ONLY_ONE_BET);
    	// 移动端追号倍数
    	Assert.isTrue(Objects.equals(chase.getAddMultiples(), 1), MessageCodeConstants.YFGC_ONLY_ONE_BET);
    	// 验证总金额
    	Assert.isTrue(Objects.equals(chase.getAddAmount(), chase.getAddIssues() * 2d), MessageCodeConstants.BET_AMOUNT_CAL_SERVICE);
    	// 追号计划总倍数,该倍数无实际操作意义，仅仅是各追号子彩期倍数的和
    	Assert.isTrue(Objects.equals(chase.getAddIssues(), chase.getMultipleNum()), MessageCodeConstants.YFGC_ONLY_ONE_BET);
    	// 活动投注追号类型只支持永追
		Assert.isTrue(Objects.equals(ChaseStopType.ALWAYS.getValue(), chase.getStopType()), MessageCodeConstants.YFGC_STOP_TYPE);

    	// 活动只支持任八玩法
    	OrderDetailVO firstContent = chase.getOrderAddContentList().get(0);
    	Assert.isTrue(Objects.equals(firstContent.getLotteryChildCode(), LotteryChildEnum.LotteryChild.D11X5_R8.getValue()) || Objects.equals(firstContent.getLotteryChildCode(), LotteryChildEnum.LotteryChild.JX11X5_R8.getValue()), MessageCodeConstants.YFGC_LOTTERY_CHILD);
    	// 1注1倍2元
    	Assert.isTrue(Objects.equals(firstContent.getBuyNumber(), 1), MessageCodeConstants.YFGC_ONLY_ONE_BET);
    	Assert.isTrue(Objects.equals(firstContent.getMultiple(), 1), MessageCodeConstants.YFGC_ONLY_ONE_BET);
    	Assert.isTrue(Objects.equals(firstContent.getAmount(), 2d), MessageCodeConstants.YFGC_ONLY_ONE_BET);
    	// 单式
    	Assert.isTrue(Objects.equals(firstContent.getContentType(), BetContentType.SINGLE.getValue()), MessageCodeConstants.YFGC_LOTTERY_CHILD);
		
    	// 如果是PC端，则要追号列表，且大小与追号期数一致
    	if(Objects.equals(ClientType.PC.getValue(), getClientType())) {
			Assert.notEmpty(chase.getOrderAddIssueList(), MessageCodeConstants.YFGC_ISSUE_LIST);
			Assert.isTrue(Objects.equals(chase.getOrderAddIssueList().size(), chase.getAddIssues()), MessageCodeConstants.YFGC_ISSUE_LIST);
			// 验证所追的每期都是1注1倍2元
			for(OrderAddIssueVO addIssue : chase.getOrderAddIssueList()) {
				Assert.isTrue(StringUtils.hasText(addIssue.getIssueCode()), MessageCodeConstants.LOTTERY_ISSUE_IS_NULL_FIELD);
		    	Assert.isTrue(Objects.equals(addIssue.getMultiple(), 1), MessageCodeConstants.YFGC_ONLY_ONE_BET);
		    	Assert.isTrue(Objects.equals(addIssue.getChaseBetNum(), 1), MessageCodeConstants.YFGC_ONLY_ONE_BET);
		    	Assert.isTrue(Objects.equals(addIssue.getBuyAmount(), 2d), MessageCodeConstants.YFGC_ONLY_ONE_BET);
			}
				
    	}

    	// 若走正常追号流程，则会根据此字段来决定是否生成追号列表
    	//chase.setSource(getClientType());
    	// PC端追号倍数 方案倍数(该倍数无实际操作意义，仅仅是各追号子彩期倍数的和)
    	//chase.setMultipleNum(chase.getAddIssues());
    	//chase.setAddType(ChaseType.FIXED_NUMBER.getValue());
    	//chase.setIsDltAdd(DltAdd.NO.getValue());
    	// 设置为永追
    	//chase.setStopType(ChaseStopType.ALWAYS.getValue());
    	// 设置投注列表的其他信息
    	// 生成投注内容
    	// 设置追号列表,如果是PC端，则在此处生成追号列表,否则订单验证不通过
    	//if(Objects.equals(ClientType.PC.getValue(), chase.getSource())) {
    	//	chase.setOrderAddIssueList(getIssueList(chase));
    	//}
	}

	/**
	 * @desc   获取客户端类型
	 * @author Tony Wang
	 * @create 2017年8月14日
	 * @return 
	 */
	protected abstract Short getClientType();
	
//	/**
//	 * @desc   获取客户端类型
//	 * @author Tony Wang
//	 * @create 2017年8月14日
//	 * @return 
//	 */
//	protected abstract Short getPlatformType();

//	private List<OrderAddIssueVO> getIssueList(OrderAddVO chase) {
//	String currIssueCode = lotteryIssueService.findIssueAndPreIssueByCode(chase.getLotteryCode()).getData().getIssueCode();
//	List<NewIssueBO> issueList = lotteryIssueService.listLotteryIssue(chase.getLotteryCode(), chase.getAddIssues(), currIssueCode);
//	Assert.notEmpty(issueList);
//	List<String> issues = new ArrayList<String>();
//	// 由于列表中不包含当前期，所有要特殊处理下(加入当前期，删除最后一期)
//	issues.add(currIssueCode);
//	for (int i = 0; i < issueList.size()-1; i++) {
//		issues.add(issueList.get(i).getIssueCode());
//	}
//	// 1.初始化追号彩期列表,赋值每期追号的彩期、注数、倍数、金额,一分购彩活动都是1倍1注
//	List<OrderAddIssueVO> orderAddIssueList = new ArrayList<>();
//	for (String issue : issues) {
//		orderAddIssueList.add(new OrderAddIssueVO(issue, BET_NUM, chase.getAddMultiples(), SINGLE_AMOUNT * chase.getAddMultiples()));
//	}
//	return orderAddIssueList;
//}
	
//	/**
//	 * @desc   完善下单信息
//	 * @author Tony Wang
//	 * @create 2017年8月15日
//	 * @param chase 
//	 */
//	private void completeOrderInfo(OrderAddVO chase) {
//    	chase.setSource(getClientType());
//    	chase.setPlatform(getPlatformType());
//    	chase.setLotteryCode(LotteryEnum.Lottery.D11X5.getName());
//    	chase.setAddAmount(chase.getAddIssues() * SINGLE_AMOUNT );
//    	chase.setAddCount(BET_NUM);
//    	// PC端追号倍数 方案倍数(该倍数无实际操作意义，仅仅是各追号子彩期倍数的和)
//    	chase.setMultipleNum(chase.getAddIssues());
//    	// 移动端追号倍数
//    	chase.setAddMultiples(BET_MULTI);
//    	chase.setAddType(ChaseType.FIXED_NUMBER.getValue());
//    	chase.setIsDltAdd(DltAdd.NO.getValue());
//    	// 设置为永追
//    	chase.setStopType(ChaseStopType.ALWAYS.getValue());
//    	// 设置投注列表的其他信息
//    	// 生成投注内容
//    	OrderDetailVO firstContent = chase.getOrderAddContentList().get(0);
//    	// 活动只支持任八玩法
//    	firstContent.setLotteryChildCode(LotteryChildEnum.LotteryChild.D11X5_R8.getValue());
//    	// 1注1倍2元
//    	firstContent.setBuyNumber(BET_NUM);
//    	firstContent.setMultiple(BET_MULTI);
//    	firstContent.setAmount(SINGLE_AMOUNT);
//    	// 单式
//    	firstContent.setContentType(BetContentType.SINGLE.getValue());
//    	// 设置追号列表,如果是PC端，则在此处生成追号列表,否则订单验证不通过
//    	if(Objects.equals(ClientType.PC.getValue(), chase.getSource())) {
//    		chase.setOrderAddIssueList(getIssueList(chase));
//    	}
//    	// 查询活动Id并设置
//    	chase.setActivityId(activityService.findYfgcActivityInfo().getData().getActivityCode());
//	}
}
