package com.hhly.lotto.api.h5.ordercopy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.ordercopy.service.IOrderCopyService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.IssueUtil;
import com.hhly.skeleton.base.util.MathUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.UserInfoBOUtil;
import com.hhly.skeleton.lotto.base.ordercopy.bo.CommissionBO;
import com.hhly.skeleton.lotto.base.ordercopy.bo.QueryBO;
import com.hhly.skeleton.lotto.base.ordercopy.bo.QueryUserIssueInfoBO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.MUserIssueInfoVO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.MUserIssueLinkVO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.OrderFollowedInfoVO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.OrderIssueInfoVO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.QueryVO;


/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2017/9/28.
 * @company 益彩网络科技有限公司
 */
@RequestMapping("/order-copy")
@RestController
public class OrderCopyController extends BaseController {
	
	@Value("${before_file_url}")
	protected String beforeFileUrl;
    
    @Autowired
    private IOrderCopyService iOrderCopyService;

    @RequestMapping(method = RequestMethod.POST)
    public Object insertOrderCopy(@RequestBody OrderIssueInfoVO vo) throws Exception {
        return iOrderCopyService.insertOrderCopy(vo);
    }
    
    /**
     * 是否已关注
     * @author longguoyou
     * @date 2017年9月30日
     * @param mUserIssueLinkVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "h5/isFocus",method = RequestMethod.POST)
    public ResultBO<?> findIfFocus(@RequestBody MUserIssueLinkVO mUserIssueLinkVO) throws Exception{
    	return ResultBO.ok(iOrderCopyService.findIfFocus(mUserIssueLinkVO.getUserIssueId(), mUserIssueLinkVO.getToken()));
    }
    
    /**
     * 新增关注/取消关注
     * @author longguoyou
     * @date 2017年9月30日
     * @param mUserIssueLinkVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "h5/updateFocus",method = RequestMethod.POST)
    public ResultBO<?> updateFocus(@RequestBody MUserIssueLinkVO mUserIssueLinkVO)throws Exception{
    	return iOrderCopyService.updateFocus(mUserIssueLinkVO);
    }
    
    /**
     * 查询关注列表
     * @author longguoyou
     * @date 2017年9月30日
     * @param mUserIssueLinkVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "h5/listFocusOfIssueUser",method = RequestMethod.POST)
    public ResultBO<?> queryFocusByMUserIssueLinkVO(@RequestBody MUserIssueLinkVO mUserIssueLinkVO)throws Exception{
    	return iOrderCopyService.queryFocusByMUserIssueLinkVO(mUserIssueLinkVO);
    }
    
    /**
     * 实单查询列表
     * @author longguoyou
     * @date 2017年9月30日
     * @param queryVO
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "h5/listOrderIssues",method = RequestMethod.POST)
    public ResultBO<?> queryIssueInfo(@RequestBody QueryVO queryVO)throws Exception{
    	List<QueryBO> listQueryBO = (List<QueryBO>) iOrderCopyService.queryIssueInfo(queryVO).getData();
		/** 处理 抄单人、近期战绩、发单时间、连红 */
		if (!ObjectUtil.isBlank(listQueryBO)) {
			for (QueryBO bean : listQueryBO) {
				bean.setPassway(IssueUtil.getPasswayFromBetContent(bean.getBetContent()));
				if (!ObjectUtil.isBlank(bean.getMaxRoiDb())) {
					bean.setMaxRoi(IssueUtil.getOnlyMultiple(bean.getMaxRoiDb()));
				}
				if (!ObjectUtil.isBlank(bean.getHitRateDb())) {
					bean.setHitRate(IssueUtil.getOnlyPercent(bean.getHitRateDb()));
				}
				bean.setContinueHit(IssueUtil.getContinueHitStr(bean.getContinueHitDb()));
				bean.setFollowNumStr(IssueUtil.replaceMantissa(bean.getFollowNum()));
				bean.setEndTime(IssueUtil.getEndTimeStr(bean.getEndTimeDb()));
				bean.setCreateTimeStr(IssueUtil.getShowDateStr(bean.getCreateTime()));
				bean.setRecentRecord(IssueUtil.getRecentRecordStr(bean.getRecentRecord()));
				bean.setHeadUrl(UserInfoBOUtil.getHeadUrl(bean.getHeadUrl(), beforeFileUrl));
			}
		}
    	return ResultBO.ok(listQueryBO);
    }
    
    /**
     * 实单查询总记录数
     * @author longguoyou
     * @date 2017年9月30日
     * @param queryVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "h5/listOrderIssuesCount",method = RequestMethod.POST)
    public ResultBO<?> queryIssueInfoCount(@RequestBody QueryVO queryVO)throws Exception{
    	return ResultBO.ok(iOrderCopyService.queryIssueInfoCount(queryVO));
    }

    /**
     * 专家推荐-专家列表/动态列表-我的关注
     * @author longguoyou
     * @date 2017年10月10日
     * @param queryVO
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "h5/listUserIssueInfo",method = RequestMethod.POST)
    public ResultBO<?> queryUserIssueInfo(@RequestBody QueryVO queryVO)throws Exception{
    	List<QueryUserIssueInfoBO> list = (List<QueryUserIssueInfoBO>)iOrderCopyService.queryUserIssueInfo(queryVO).getData();
		if(!ObjectUtil.isBlank(list)){
			for(QueryUserIssueInfoBO bean : list){
				/** 处理显示中文、数字格式*/
				bean.setFocusNumStr(IssueUtil.replaceMantissa(bean.getFocusNum()));
				bean.setRecentRecord(IssueUtil.getRecentRecordStr(bean.getRecentRecord()));
				if(!ObjectUtil.isBlank(bean.getBonusRateDb())){
					bean.setBonusRate(IssueUtil.getOnlyPercent(Double.valueOf(bean.getBonusRateDb()+SymbolConstants.ENPTY_STRING)));
				}
				bean.setHitRate(IssueUtil.getOnlyPercent(bean.getHitRateDb()));
				bean.setContinueHit(IssueUtil.getContinueHitStr(bean.getContinueHitDb()));
                bean.setHeadUrl(UserInfoBOUtil.getHeadUrl(bean.getHeadUrl(), beforeFileUrl));
			}
		}
    	return ResultBO.ok(list);
    }
    
    /**
     * 返佣情况
     * @author longguoyou
     * @date 2017年10月12日
     * @param queryVO
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "h5/listCommissions",method = RequestMethod.POST)
    public ResultBO<?> listCommissions(@RequestBody QueryVO queryVO)throws Exception{
    	List<CommissionBO> list = (List<CommissionBO>) iOrderCopyService.queryCommissions(queryVO).getData();
    	if(!ObjectUtil.isBlank(list)){
        	for(CommissionBO bean : list){
                if(!ObjectUtil.isBlank(bean.getCommissionAmount())){
                    bean.setCommissionAmount(MathUtil.round(bean.getCommissionAmount(), 2));
                }
        		bean.setCreateTimeStr(DateUtil.convertDateToStr(bean.getCreateTime(), DateUtil.FORMAT_MM_DD));
        		bean.setCreateTime(null);
        	}
		}
    	return ResultBO.ok(list);
    }
 
    /**
     * 返佣明细
     * @author longguoyou
     * @date 2017年10月13日
     * @param queryVO
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "h5/listCommissionDetails",method = RequestMethod.POST)
    public ResultBO<?> listCommissionDetails(@RequestBody QueryVO queryVO)throws Exception{
    	List<CommissionBO> list = (List<CommissionBO>) iOrderCopyService.queryCommissionsDetails(queryVO).getData();
    	if(!ObjectUtil.isBlank(list)){
        	for(CommissionBO bean : list){
                if(!ObjectUtil.isBlank(bean.getCommissionAmount())){
                    bean.setCommissionAmount(MathUtil.round(bean.getCommissionAmount(), 2));
                }
        	}
		}
    	return ResultBO.ok(list);
    }
    
    /**
     * 明细总提成金额查询
     * @author longguoyou
     * @date 2017年11月20日
     * @param queryVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "h5/getTotalCommissionDetails",method = RequestMethod.POST)
    public ResultBO<?> getTotalCommissionDetails(@RequestBody QueryVO queryVO)throws Exception{
    	return ResultBO.ok(iOrderCopyService.getCommissionDetailsSumCommission(queryVO));
    }

    /**
     * 专家统计信息
     *
     * @param id          专家id
     * @param lotteryCode 彩种编号
     * @return
     * @throws Exception
     * @author liguisheng
     * @date 2017年9月30日
     */
    @RequestMapping(value = "/user-prize-count")
    public Object findUserIssuePrizeCount(@RequestParam("id") Integer id,
                                          @RequestParam("lotteryCode") String lotteryCode,
                                          @RequestParam(value = "token", required = false) String token) {
        MUserIssueInfoVO vo = new MUserIssueInfoVO();
        vo.setId(id);
        vo.setLotteryCode(lotteryCode);
        vo.setToken(token);
        return iOrderCopyService.findUserIssuePrizeCount(vo);
    }


    /**
     * 专家统计信息
     *
     * @param id    专家id
     * @param token 用户token
     * @return
     * @throws Exception
     * @author liguisheng
     * @date 2017年9月30日
     */
    @RequestMapping(value = "/user-issue-info")
    public Object findUserIssueInfoBoById(@RequestParam(value = "id", required = false) Long id,
                                          @RequestParam(value = "token", required = false) String token) {
        return iOrderCopyService.findUserIssueInfoBoById(id, token);
    }


    /**
     * 用户跟单接口
     *
     * @param vo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/followed", method = RequestMethod.POST)
    public Object orderFollowed(@RequestBody OrderFollowedInfoVO vo) throws Exception {
        return iOrderCopyService.orderFollowed(vo);
    }
    
    /**
     * 查找抄单明细
     * @author longguoyou
     * @date 2017年10月14日
     * @param queryVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "h5/listFollowedDetails", method = RequestMethod.POST)
    public Object listFollowedDetails(@RequestBody QueryVO queryVO) throws Exception {
        return iOrderCopyService.queryFollowedDetails(queryVO);
    }

    /**
     * 查看订单明细
     *
     * @param id
     * @param token
     * @return
     * @throws Exception
     * @author liguisheng
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Object orderCopyInfo(Long id, String token) {
        return iOrderCopyService.findOrderCopyIssueInfoBOById(id, token);
    }

    /**
     * 查看订单是否能够发单
     *
     * @param orderCode
     * @param token
     * @return
     * @throws Exception
     * @author liguisheng
     */
    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public Object validateOrderCopy(String orderCode, String token) throws Exception {
        return iOrderCopyService.validateOrderCopy(orderCode, token);
    }
}
