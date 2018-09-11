
package com.hhly.lotto.config;

import com.hhly.lottocore.remote.rcmd.service.IRcmdService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.hhly.commoncore.remote.dicdata.service.IDicDataDetailService;
import com.hhly.commoncore.remote.lotto.service.IJcDataCommService;
import com.hhly.commoncore.remote.lotto.service.ILotteryBuyService;
import com.hhly.commoncore.remote.operate.service.ILotteryIssueCacheService;
import com.hhly.commoncore.remote.operate.service.IOperateAdService;
import com.hhly.commoncore.remote.operate.service.IOperateArticleService;
import com.hhly.commoncore.remote.operate.service.IOperateFastService;
import com.hhly.commoncore.remote.operate.service.IOperateHelpService;
import com.hhly.commoncore.remote.operate.service.IOperateLotteryService;
import com.hhly.commoncore.remote.operate.service.IOperateMsgService;
import com.hhly.commoncore.remote.operate.service.IOperateService;
import com.hhly.lottoactivity.remote.service.IAcitivtyCdkeyService;
import com.hhly.lottoactivity.remote.service.IActivityAddedService;
import com.hhly.lottoactivity.remote.service.IActivityAwardService;
import com.hhly.lottoactivity.remote.service.IActivityMutilBetService;
import com.hhly.lottoactivity.remote.service.IActivityNewUserService;
import com.hhly.lottoactivity.remote.service.IActivityService;
import com.hhly.lottocore.remote.lotto.service.IFootBallAnalysisService;
import com.hhly.lottocore.remote.lotto.service.IJcDataService;
import com.hhly.lottocore.remote.lotto.service.ILotteryIssueService;
import com.hhly.lottocore.remote.lotto.service.ILotteryService;
import com.hhly.lottocore.remote.lotto.service.ISportDataService;
import com.hhly.lottocore.remote.order.service.IOrderAddService;
import com.hhly.lottocore.remote.order.service.IOrderSearchService;
import com.hhly.lottocore.remote.order.service.IOrderService;
import com.hhly.lottocore.remote.order.service.ISingleOrderService;
import com.hhly.lottocore.remote.order.service.ITicketDetailService;
import com.hhly.lottocore.remote.ordercopy.service.IOrderCopyService;
import com.hhly.lottocore.remote.ordercopy.service.v1_1.IOrderCopyServiceV11;
import com.hhly.lottocore.remote.ordergroup.service.IOrderGroupService;
import com.hhly.lottocore.remote.trend.service.IBbwpTrendService;
import com.hhly.lottocore.remote.trend.service.IC11x5TrendService;
import com.hhly.lottocore.remote.trend.service.IDltTrendService;
import com.hhly.lottocore.remote.trend.service.IF3dTrendService;
import com.hhly.lottocore.remote.trend.service.IHighTrendService;
import com.hhly.lottocore.remote.trend.service.IK3TrendService;
import com.hhly.lottocore.remote.trend.service.IKl10TrendService;
import com.hhly.lottocore.remote.trend.service.IKl12TrendService;
import com.hhly.lottocore.remote.trend.service.IKl8TrendService;
import com.hhly.lottocore.remote.trend.service.IKzcTrendService;
import com.hhly.lottocore.remote.trend.service.INumTrendService;
import com.hhly.lottocore.remote.trend.service.IPl3TrendService;
import com.hhly.lottocore.remote.trend.service.IPl5TrendService;
import com.hhly.lottocore.remote.trend.service.IPokeTrendService;
import com.hhly.lottocore.remote.trend.service.IQlcTrendService;
import com.hhly.lottocore.remote.trend.service.IQxcTrendService;
import com.hhly.lottocore.remote.trend.service.IQyhTrendService;
import com.hhly.lottocore.remote.trend.service.ISscTrendService;
import com.hhly.lottocore.remote.trend.service.ISslTrendService;
import com.hhly.lottocore.remote.trend.service.ISsqTrendService;
import com.hhly.lottocore.remote.trend.service.IXyscTrendService;
import com.hhly.lottocore.remote.trend.service.IYydjTrendService;
import com.hhly.paycore.remote.service.IOperateCouponService;
import com.hhly.paycore.remote.service.IPayChannelService;
import com.hhly.paycore.remote.service.IPayService;
import com.hhly.paycore.remote.service.IRechargeService;
import com.hhly.paycore.remote.service.ITransRechargeService;
import com.hhly.paycore.remote.service.ITransRedService;
import com.hhly.paycore.remote.service.ITransRemittingService;
import com.hhly.paycore.remote.service.ITransTakenConfirmService;
import com.hhly.paycore.remote.service.ITransTakenService;
import com.hhly.paycore.remote.service.ITransUserLogService;
import com.hhly.paycore.remote.service.ITransUserService;
import com.hhly.paycore.remote.service.IUserWalletService;
import com.hhly.usercore.remote.activity.service.IMemberActivityService;
import com.hhly.usercore.remote.member.service.IMemberBankcardService;
import com.hhly.usercore.remote.member.service.IMemberCouponService;
import com.hhly.usercore.remote.member.service.IMemberInfoService;
import com.hhly.usercore.remote.member.service.IMemberMessageService;
import com.hhly.usercore.remote.member.service.IMemberSecurityService;
import com.hhly.usercore.remote.member.service.IMemberWinningService;
import com.hhly.usercore.remote.member.service.IVerifyCodeService;
import com.hhly.usercore.remote.passport.service.IMemberLoginService;
import com.hhly.usercore.remote.passport.service.IMemberRegisterService;
import com.hhly.usercore.remote.passport.service.IMemberRetrieveService;
import com.hhly.usercore.remote.passport.service.IThirdPartyLoginService;

/**
 * @desc    
 * @author  cheng chen
 * @date    2018年8月3日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Configuration
public class HessionClientConfig {
	
	@Value("${hessian_connect_time_out}")
	private Integer connectTimeout;
	
	@Value("${hessian_read_time_out}")
	private Integer readTimeout;	
	
	@Value("${hessian_username}")
	private String userName;
	
	@Value("${hessian_password}")
	private String password;
	
	@Value("${lotto_core_url}")
	private String lotto_core_url;
	
	@Value("${user_core_url}")
	private String user_core_url;
	
	@Value("${lotto_pay_url}")
	private String lotto_pay_url;
	
	@Value("${lotto_activity_url}")
	private String lotto_activity_url;
	
	@Value("${lotto_common_core_url}")
	private String lotto_common_core_url;
	
	@Value("${remote_lottery_issue_service}")
	private String remote_lottery_issue_service;

	@Value("${remote_single_order_service}")
	private String remote_single_order_service;
	
	@Value("${remote_order_service}")
	private String remote_order_service;	
	
	@Value("${remote_orderadd_service}")
	private String remote_orderadd_service;
	
	@Value("${remote_order_search_service}")
	private String remote_order_search_service;	

	@Value("${remote_ticket_search_service}")
	private String remote_ticket_search_service;
	
	@Value("${remote_jczq_all_data_service}")
	private String remote_jczq_all_data_service;
	
	@Value("${remote_sport_all_data_service}")
	private String remote_sport_all_data_service;
	
	@Value("${remote_footballanalysisservice_service}")
	private String remote_footballanalysisservice_service;

	@Value("${remote_ordercopy_service}")
	private String remote_ordercopy_service;
	
	@Value("${remote_ordercopy_service_v11}")
	private String remote_ordercopy_service_v11;
	
	@Value("${remote_order_group_service}")
	private String remote_order_group_service;

	@Value("${remote_rcmd_service}")
    private String remote_rcmd_service;
	
	@Value("${remote_num_trend_service}")
	private String remote_num_trend_service;
	
	@Value("${remote_dlt_trend_service}")
	private String remote_dlt_trend_service;
	
	@Value("${remote_ssq_trend_service}")
	private String remote_ssq_trend_service;
	
	@Value("${remote_f3d_trend_service}")
	private String remote_f3d_trend_service;
	
	@Value("${remote_pl3_trend_service}")
	private String remote_pl3_trend_service;	
	
	@Value("${remote_pl5_trend_service}")
	private String remote_pl5_trend_service;
	
	@Value("${remote_qlc_trend_service}")
	private String remote_qlc_trend_service;
	
	@Value("${remote_qxc_trend_service}")
	private String remote_qxc_trend_service;
	
	@Value("${remote_high_trend_service}")
	private String remote_high_trend_service;
	
	@Value("${remote_11x5_trend_service}")
	private String remote_11x5_trend_service;
	
	@Value("${remote_ssc_trend_service}")
	private String remote_ssc_trend_service;	
	
	@Value("${remote_k3_trend_service}")
	private String remote_k3_trend_service;	
	
	@Value("${remote_kl10_trend_service}")
	private String remote_kl10_trend_service;	
	
	@Value("${remote_poke_trend_service}")
	private String remote_poke_trend_service;
	
	@Value("${remote_kl12_trend_service}")
	private String remote_kl12_trend_service;	
	
	@Value("${remote_xysc_trend_service}")
	private String remote_xysc_trend_service;	
	
	@Value("${remote_ssl_trend_service}")
	private String remote_ssl_trend_service;
	
	@Value("${remote_qyh_trend_service}")
	private String remote_qyh_trend_service;	

	@Value("${remote_yydj_trend_service}")
	private String remote_yydj_trend_service;
	
	@Value("${remote_kl8_trend_service}")
	private String remote_kl8_trend_service;	
	
	@Value("${remote_bbwp_trend_service}")
	private String remote_bbwp_trend_service;
	
	@Value("${remote_kzc_trend_service}")
	private String remote_kzc_trend_service;
	
	@Value("${remote_lotteryDataService_service}")
	private String remote_lotteryDataService_service;	
	//lotto-core end 
	
	@Value("${remote_member_register_service}")
	private String remote_member_register_service;
	
	@Value("${remote_member_login_service}")
	private String remote_member_login_service;
	
	@Value("${remote_member_thidParty_service}")
	private String remote_member_thidParty_service;
	
	@Value("${remote_member_retrieve_service}")
	private String remote_member_retrieve_service;
	
	@Value("${remote_member_info_service}")
	private String remote_member_info_service;
	
	@Value("${remote_member_security_service}")
	private String remote_member_security_service;
		
	@Value("${remote_member_bankcard_service}")
	private String remote_member_bankcard_service;
	
	@Value("${remote_member_message_service}")
	private String remote_member_message_service;	
	
	@Value("${remote_verifyCode_service}")
	private String remote_verifyCode_service;
	
	@Value("${remote_member_activity_service}")
	private String remote_member_activity_service;	
	
	@Value("${remote_member_winning_service}")
	private String remote_member_winning_service;
	
	@Value("${remote_member_coupon_service}")
	private String remote_member_coupon_service;
	
	@Value("${remote_user_wallet_service}")
	private String remote_user_wallet_service;	
	//user-core end
	
	@Value("${remote_pay_channel_service}")
	private String remote_pay_channel_service;
	
	@Value("${remote_trans_recharge_service}")
	private String remote_trans_recharge_service;
	
	@Value("${remote_trans_taken_service}")
	private String remote_trans_taken_service;	
	
	@Value("${remote_trans_taken_confirm_service}")
	private String remote_trans_taken_confirm_service;
	
	@Value("${remote_trans_user_service}")
	private String remote_trans_user_service;
	
	@Value("${remote_trans_user_log_service}")
	private String remote_trans_user_log_service;
	
	@Value("${remote_pay_service}")
	private String remote_pay_service;
	
	@Value("${remote_recharge_service}")
	private String remote_recharge_service;
	
	@Value("${remote_coupon_service}")
	private String remote_coupon_service;
	
	@Value("${remote_transred_service}")
	private String remote_transred_service;	
	
	@Value("${remote_transremitting_service}")
	private String remote_transremitting_service;		
	//lotto-pay end
	
	@Value("${remote_activity_service}")
	private String remote_activity_service;
	
	@Value("${remote_activityaward_service}")
	private String remote_activityaward_service;
	
	@Value("${remote_activityadded_service}")
	private String remote_activityadded_service;
	
	@Value("${remote_activity_mutilBet_service}")
	private String remote_activity_mutilBet_service;
	
	@Value("${remote_activity_new_user_service}")
	private String remote_activity_new_user_service;
	
	@Value("${remote_activity_cdkey_service}")
	private String remote_activity_cdkey_service;
	//lotto-acitivty end
	
	@Value("${remote_common_dic_data_detail_service}")
	private String remote_common_dic_data_detail_service;
	
	@Value("${remote_common_operate_service}")
	private String remote_common_operate_service;
	
	@Value("${remote_common_operate_help_service}")
	private String remote_common_operate_help_service;
	
	@Value("${remote_common_lottery_buy_service}")
	private String remote_common_lottery_buy_service;
	
	@Value("${remote_common_operate_article_service}")
	private String remote_common_operate_article_service;
		
	@Value("${remote_common_operate_ad_service}")
	private String remote_common_operate_ad_service;
	
	@Value("${remote_common_operate_lottery_service}")
	private String remote_common_operate_lottery_service;	
	
	@Value("${remote_common_operate_fast_service}")
	private String remote_common_operate_fast_service;	
	
	@Value("${remote_common_issue_cache_service}")
	private String remote_common_issue_cache_service;
	
	@Value("${remote_common_operate_msg_service}")
	private String remote_common_operate_msg_service;
	
	@Value("${remote_common_jc_data_service}")
	private String remote_common_jc_data_service;	
	
	public HessianProxyFactoryBean hessianProxyConfig() {
		HessianProxyFactoryBean factory = new HessianProxyFactoryBean();
        factory.setUsername(userName);
        factory.setPassword(password);
        factory.setOverloadEnabled(true); 
        factory.setConnectTimeout(connectTimeout);
        factory.setReadTimeout(readTimeout);
		return factory;
	}
	
	// lotto-core hession start
    @Bean
    public HessianProxyFactoryBean iLotteryIssueService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_lottery_issue_service);
        factory.setServiceInterface(ILotteryIssueService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iSingleOrderService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_single_order_service);
        factory.setServiceInterface(ISingleOrderService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iOrderService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_order_service);
        factory.setServiceInterface(IOrderService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iOrderAddService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_orderadd_service);
        factory.setServiceInterface(IOrderAddService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iorderSearchService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_order_search_service);
        factory.setServiceInterface(IOrderSearchService.class);
        return factory;
    }  
    
    @Bean
    public HessianProxyFactoryBean iTicketDetailService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_ticket_search_service);
        factory.setServiceInterface(ITicketDetailService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iJcDataService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_jczq_all_data_service);
        factory.setServiceInterface(IJcDataService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iSportDataService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_sport_all_data_service);
        factory.setServiceInterface(ISportDataService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iFootBallAnalysisService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_footballanalysisservice_service);
        factory.setServiceInterface(IFootBallAnalysisService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iOrderCopyService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_ordercopy_service);
        factory.setServiceInterface(IOrderCopyService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iOrderCopyServiceV11() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_ordercopy_service_v11);
        factory.setServiceInterface(IOrderCopyServiceV11.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iOrderGroupService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_order_group_service);
        factory.setServiceInterface(IOrderGroupService.class);
        return factory;
    }

    @Bean
    public HessianProxyFactoryBean iRcmdService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_rcmd_service);
        factory.setServiceInterface(IRcmdService.class);
        return factory;
    }

    @Bean
    public HessianProxyFactoryBean iNumTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_num_trend_service);
        factory.setServiceInterface(INumTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iDltTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_dlt_trend_service);
        factory.setServiceInterface(IDltTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iSsqTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_ssq_trend_service);
        factory.setServiceInterface(ISsqTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iF3dTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_f3d_trend_service);
        factory.setServiceInterface(IF3dTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iPl3TrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_pl3_trend_service);
        factory.setServiceInterface(IPl3TrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iPl5TrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_pl5_trend_service);
        factory.setServiceInterface(IPl5TrendService.class);
        return factory;
    }    

    @Bean
    public HessianProxyFactoryBean iQlcTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_qlc_trend_service);
        factory.setServiceInterface(IQlcTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iQxcTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_qxc_trend_service);
        factory.setServiceInterface(IQxcTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iHighTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_high_trend_service);
        factory.setServiceInterface(IHighTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iC11x5TrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_11x5_trend_service);
        factory.setServiceInterface(IC11x5TrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iSscTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_ssc_trend_service);
        factory.setServiceInterface(ISscTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iK3TrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_k3_trend_service);
        factory.setServiceInterface(IK3TrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iKl10TrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_kl10_trend_service);
        factory.setServiceInterface(IKl10TrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iPokeTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_poke_trend_service);
        factory.setServiceInterface(IPokeTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iKl12TrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_kl12_trend_service);
        factory.setServiceInterface(IKl12TrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iXscTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_xysc_trend_service);
        factory.setServiceInterface(IXyscTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iSslTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_ssl_trend_service);
        factory.setServiceInterface(ISslTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iQyhTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_qyh_trend_service);
        factory.setServiceInterface(IQyhTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iYydjTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_yydj_trend_service);
        factory.setServiceInterface(IYydjTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iKl8TrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_kl8_trend_service);
        factory.setServiceInterface(IKl8TrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iBbwpTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_bbwp_trend_service);
        factory.setServiceInterface(IBbwpTrendService.class);
        return factory;
    }   
    
    @Bean
    public HessianProxyFactoryBean iKzcTrendService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_kzc_trend_service);
        factory.setServiceInterface(IKzcTrendService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iLotteryService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_core_url + remote_lotteryDataService_service);
        factory.setServiceInterface(ILotteryService.class);
        return factory;
    }    
	// lotto-core hession end
    
    // user-core hession start
    @Bean
    public HessianProxyFactoryBean iMemberRegisterService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_member_register_service);
        factory.setServiceInterface(IMemberRegisterService.class);
        return factory;
    } 
    
    @Bean
    public HessianProxyFactoryBean iMemberLoginService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_member_login_service);
        factory.setServiceInterface(IMemberLoginService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iThirdPartyLoginService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_member_thidParty_service);
        factory.setServiceInterface(IThirdPartyLoginService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iMemberRetrieveService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_member_retrieve_service);
        factory.setServiceInterface(IMemberRetrieveService.class);
        return factory;
    } 
    
    @Bean
    public HessianProxyFactoryBean iMemberInfoService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_member_info_service);
        factory.setServiceInterface(IMemberInfoService.class);
        return factory;
    } 
    
    @Bean
    public HessianProxyFactoryBean iMemberSecurityService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_member_security_service);
        factory.setServiceInterface(IMemberSecurityService.class);
        return factory;
    } 
    
    @Bean
    public HessianProxyFactoryBean iMemberBankCardService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_member_bankcard_service);
        factory.setServiceInterface(IMemberBankcardService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iMemberMessageService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_member_message_service);
        factory.setServiceInterface(IMemberMessageService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iVerifyCodeService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_verifyCode_service);
        factory.setServiceInterface(IVerifyCodeService.class);
        return factory;
    }  
    
    @Bean
    public HessianProxyFactoryBean iMemberActivityService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_member_activity_service);
        factory.setServiceInterface(IMemberActivityService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean memberWinningService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_member_winning_service);
        factory.setServiceInterface(IMemberWinningService.class);
        return factory;
    }    
    
    @Bean
    public HessianProxyFactoryBean iMemberCouponService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_member_coupon_service);
        factory.setServiceInterface(IMemberCouponService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iTransTakenService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(user_core_url + remote_trans_taken_service);
        factory.setServiceInterface(ITransTakenService.class);
        return factory;
    }    
    
    // user-core hession end 
    
    // lotto-pay hession start
    @Bean
    public HessianProxyFactoryBean iUserWalletService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_pay_url + remote_user_wallet_service);
        factory.setServiceInterface(IUserWalletService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iPayChannelService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_pay_url + remote_pay_channel_service);
        factory.setServiceInterface(IPayChannelService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iTransRechangeService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_pay_url + remote_trans_recharge_service);
        factory.setServiceInterface(ITransRechargeService.class);
        return factory;
    }
      
    @Bean
    public HessianProxyFactoryBean iTransTakenConfirmService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_pay_url + remote_trans_taken_confirm_service);
        factory.setServiceInterface(ITransTakenConfirmService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iTransUserService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_pay_url + remote_trans_user_service);
        factory.setServiceInterface(ITransUserService.class);
        return factory;
    } 
    
    @Bean
    public HessianProxyFactoryBean iTransUserLogService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_pay_url + remote_trans_user_log_service);
        factory.setServiceInterface(ITransUserLogService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iPayService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_pay_url + remote_pay_service);
        factory.setServiceInterface(IPayService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iRechargeService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_pay_url + remote_recharge_service);
        factory.setServiceInterface(IRechargeService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iOperateCouponService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_pay_url + remote_coupon_service);
        factory.setServiceInterface(IOperateCouponService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iTransRedService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_pay_url + remote_transred_service);
        factory.setServiceInterface(ITransRedService.class);
        return factory;
    } 
    
    @Bean
    public HessianProxyFactoryBean iTransRemittingService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_pay_url + remote_transremitting_service);
        factory.setServiceInterface(ITransRemittingService.class);
        return factory;
    }     
    // lotto-pay hession end
    
    // lotto-activity hession start 
    @Bean
    public HessianProxyFactoryBean iActivityService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_activity_url + remote_activity_service);
        factory.setServiceInterface(IActivityService.class);
        return factory;
    }    

    @Bean
    public HessianProxyFactoryBean iActivityAwardService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_activity_url + remote_activityaward_service);
        factory.setServiceInterface(IActivityAwardService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iActivityAddedService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_activity_url + remote_activityadded_service);
        factory.setServiceInterface(IActivityAddedService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iActivityMutilBetService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_activity_url + remote_activity_mutilBet_service);
        factory.setServiceInterface(IActivityMutilBetService.class);
        return factory;
    } 
    
    @Bean
    public HessianProxyFactoryBean iActivityNewUserService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_activity_url + remote_activity_new_user_service);
        factory.setServiceInterface(IActivityNewUserService.class);
        return factory;
    }  
    
    @Bean
    public HessianProxyFactoryBean iActivityCdkeyService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_activity_url + remote_activity_cdkey_service);
        factory.setServiceInterface(IAcitivtyCdkeyService.class);
        return factory;
    }
    
    // lotto-activity hession end 
    
    // lotto-common-core hession start
    @Bean
    public HessianProxyFactoryBean iDicDataDetailService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_common_core_url + remote_common_dic_data_detail_service);
        factory.setServiceInterface(IDicDataDetailService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iOperateService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_common_core_url + remote_common_operate_service);
        factory.setServiceInterface(IOperateService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean operateHelpService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_common_core_url + remote_common_operate_help_service);
        factory.setServiceInterface(IOperateHelpService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iLotteryBuyService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_common_core_url + remote_common_lottery_buy_service);
        factory.setServiceInterface(ILotteryBuyService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iOperateArticleService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_common_core_url + remote_common_operate_article_service);
        factory.setServiceInterface(IOperateArticleService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iOperateAdService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_common_core_url + remote_common_operate_ad_service);
        factory.setServiceInterface(IOperateAdService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iOperateLotteryService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_common_core_url + remote_common_operate_lottery_service);
        factory.setServiceInterface(IOperateLotteryService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iOperateFastService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_common_core_url + remote_common_operate_fast_service);
        factory.setServiceInterface(IOperateFastService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iLotteryIssueCacheService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_common_core_url + remote_common_issue_cache_service);
        factory.setServiceInterface(ILotteryIssueCacheService.class);
        return factory;
    }
    
    @Bean
    public HessianProxyFactoryBean iOperateMsgService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_common_core_url + remote_common_operate_msg_service);
        factory.setServiceInterface(IOperateMsgService.class);
        return factory;
    }  
    
    @Bean
    public HessianProxyFactoryBean iJcDataCommService() {
        HessianProxyFactoryBean factory = hessianProxyConfig();
        factory.setServiceUrl(lotto_common_core_url + remote_common_jc_data_service);
        factory.setServiceInterface(IJcDataCommService.class);
        return factory;
    }    
    // lotto-common-core hession end    
}
