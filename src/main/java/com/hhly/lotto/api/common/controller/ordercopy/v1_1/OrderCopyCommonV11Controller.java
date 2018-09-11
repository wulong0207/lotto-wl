package com.hhly.lotto.api.common.controller.ordercopy.v1_1;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.ordercopy.service.IOrderCopyService;
import com.hhly.lottocore.remote.ordercopy.service.v1_1.IOrderCopyServiceV11;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.lotto.base.ordercopy.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2017/9/28.
 * @company 益彩网络科技有限公司
 */
public class OrderCopyCommonV11Controller extends BaseController {
	
	@Value("${before_file_url}")
	protected String beforeFileUrl;
    
    @Autowired
    private IOrderCopyServiceV11 iOrderCopyService;

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
    @RequestMapping(value = "/isFocus",method = RequestMethod.POST)
    public ResultBO<?> findIfFocus(@RequestBody MUserIssueLinkVO mUserIssueLinkVO) throws Exception{
    	return iOrderCopyService.findIfFocus(mUserIssueLinkVO.getUserIssueId(), mUserIssueLinkVO.getToken());
    }
    
    /**
     * 新增关注/取消关注
     * @author longguoyou
     * @date 2017年9月30日
     * @param mUserIssueLinkVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateFocus",method = RequestMethod.POST)
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
    @RequestMapping(value = "/listFocusOfIssueUser",method = RequestMethod.POST)
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
    @RequestMapping(value = "/listOrderIssues",method = RequestMethod.POST)
    public ResultBO<?> queryIssueInfo(@RequestBody QueryVO queryVO)throws Exception{
    	return iOrderCopyService.queryIssueInfo(queryVO);
    }
    
    /**
     * 实单查询总记录数
     * @author longguoyou
     * @date 2017年9月30日
     * @param queryVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listOrderIssuesCount",method = RequestMethod.POST)
    public ResultBO<?> queryIssueInfoCount(@RequestBody QueryVO queryVO)throws Exception{
    	return iOrderCopyService.queryIssueInfoCount(queryVO);
    }

    /**
     * 专家推荐-专家列表/动态列表-我的关注
     * @author longguoyou
     * @date 2017年10月10日
     * @param queryVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listUserIssueInfo",method = RequestMethod.POST)
    public ResultBO<?> queryUserIssueInfo(@RequestBody QueryVO queryVO)throws Exception{
    	return iOrderCopyService.queryUserIssueInfo(queryVO);
    }
    
    /**
     * 返佣情况
     * @author longguoyou
     * @date 2017年10月12日
     * @param queryVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listCommissions",method = RequestMethod.POST)
    public ResultBO<?> listCommissions(@RequestBody QueryVO queryVO)throws Exception{
    	return iOrderCopyService.queryCommissions(queryVO);
    }
 
    /**
     * 返佣明细
     * @author longguoyou
     * @date 2017年10月13日
     * @param queryVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listCommissionDetails",method = RequestMethod.POST)
    public ResultBO<?> listCommissionDetails(@RequestBody QueryVO queryVO)throws Exception{
    	return iOrderCopyService.queryCommissionsDetails(queryVO);
    }
    
    /**
     * 明细总提成金额查询
     * @author longguoyou
     * @date 2017年11月20日
     * @param queryVO
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getTotalCommissionDetails",method = RequestMethod.POST)
    public ResultBO<?> getTotalCommissionDetails(@RequestBody QueryVO queryVO)throws Exception{
    	return iOrderCopyService.getCommissionDetailsSumCommission(queryVO);
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
    @RequestMapping(value = "/listFollowedDetails", method = RequestMethod.POST)
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

    /**
     * 获取篮球盘口变化数据
     * @param issueId
     * @param token
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getHandicapChange", method = RequestMethod.GET)
    public ResultBO<?> getHandicapChange(@RequestParam(value = "issueId", required = false) Long issueId,
                                          @RequestParam(value = "token", required = false) String token) throws Exception{
        return iOrderCopyService.getHandicapChange(issueId, token);
    }

}
