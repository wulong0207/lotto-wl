package com.hhly.lotto.api.data.operate.v1_0.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.commoncore.remote.operate.service.IOperateFastService;
import com.hhly.lotto.api.data.lotto.v1_0.JcDataCommV10Data;
import com.hhly.lotto.api.data.operate.v1_0.OperateFastV10Data;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.DicEnum;
import com.hhly.skeleton.base.common.DicEnum.FastFootBollEnum;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.common.SportEnum.SportSaleStatusEnum;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.lotto.base.operate.bo.OperateFastBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateFastLottBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateFastVO;
import com.hhly.skeleton.lotto.base.sport.bo.JcMathSPBO;
import com.hhly.skeleton.lotto.base.sport.bo.JcMathWinSPBO;

/**
 * @desc 彩种管理服务实现类
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class OperateFastV10DataImpl  implements OperateFastV10Data{
	@Autowired
    private IOperateFastService operateFastService;	
	@Autowired
    private JcDataCommV10Data jcDataCommData;
	@Autowired
    private RedisUtil objectRedisUtil;
	@Override
	public ResultBO<List<OperateFastLottBO>> findOperFastIssueDetail(OperateFastVO operateFastVO) {
		String key = CacheConstants.C_COMM_FAST_FIND_FAST_INFO+operateFastVO.getPlatform()+operateFastVO.getPosition();
		if(operateFastVO.getLotteryCode() != null) {
			key += operateFastVO.getLotteryCode();
		}
		OperateFastBO  fastBO= (OperateFastBO)objectRedisUtil.getObj(key);
		if(fastBO!=null&&resetFastBO(fastBO)){
			fastBO=null;
		}
		if(fastBO==null){
			fastBO =operateFastService.findOperFastIssueDetail(operateFastVO).getData();		
			objectRedisUtil.addObj(key, fastBO, (long)Constants.DAY_1);
		}		
		List<OperateFastLottBO>  fastList = fastBO.getFastInfoList();
		OperateFastLottBO delLotoBO = null;
		for(OperateFastLottBO fastLotoBO : fastList){//设置快投各种玩法	
			if(fastLotoBO.getTypeId()==Lottery.FB.getName()){
				getFastFBInfo(fastLotoBO,operateFastVO);
				if(fastLotoBO.getSportSp()==null)delLotoBO=fastLotoBO;			
			}
		}
		if(delLotoBO!=null)fastList.remove(delLotoBO);
		return ResultBO.ok(fastBO.getFastInfoList());
	}
	
	/**
	 * 情况快投信息
	 * @param fastBO
	 */
	public boolean resetFastBO(OperateFastBO fastBO){
		//如果缓存中的快投时间超时则重新查询数据
		String nowstr = DateUtil.dayForWeek()+DateUtil.getNow("HH:mm:ss");
		if(!GetOnlineWeekCheck(fastBO.getOnlineWeek(),fastBO.getOnlineTime(),fastBO.getOfflineWeek(),fastBO.getOfflineTime(),nowstr)){
			return true;			
		}else{
			//如果有彩种截止销售则设置快投为空重新查询
			for(OperateFastLottBO bo :fastBO.getFastInfoList()){
				if(bo.getSaleEndTime()==null||bo.getSaleEndTime().getTime()<new Date().getTime()){
					fastBO = null;
					return true;
				}
			}
			//如果配置了竞彩足球，但是竞彩足球赛事信息为空,将数据重置
			List<OperateFastLottBO>  fastList = fastBO.getFastInfoList();
			for(OperateFastLottBO fastLotoBO : fastList){
				if(fastLotoBO.getTypeId()==Lottery.FB.getName()
						&&Objects.equals(FastFootBollEnum.SINGLE_BET.getValue(), fastLotoBO.getCategoryId())
						&&fastLotoBO.getSportSp()==null){	
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 判断当前时间是否在时间区间之内
	 * @param onlineWeek：上线周期
	 * @param onlineTime：上线时间
	 * @param offlineWeek：下线周期
	 * @param offlineTime：下线时间
	 * @param nowstr
	 * @return
	 */
	boolean GetOnlineWeekCheck(Short onlineWeek,String onlineTime,Short offlineWeek,String offlineTime,String nowstr){
		String upstr =onlineWeek+onlineTime;
		String downstr=offlineWeek+offlineTime;
		if(onlineWeek<=offlineWeek){
			if(nowstr.compareTo(upstr)>0 && nowstr.compareTo(downstr)<=0){
				return true;				
			}								
		}else{
			//下线时间小于上线时间
			String endstr = 7+"23:59:59";//星期天结束时间				
			String startstr = 1+"00:00:00";//星期1开始时间
			if((nowstr.compareTo(upstr)>0 && nowstr.compareTo(endstr)<=0)||
					(nowstr.compareTo(startstr)>0 && nowstr.compareTo(downstr)<=0)){
				return true;
			}
		}
		return false;
	}
	/**
	 * 快投竞彩足球信息处理
	 * @param fastBO
	 */
	void getFastFBInfo(OperateFastLottBO fastBO,OperateFastVO operateFastVO){
		//查询竞彩推荐信息
		Integer saleStatus =null;
		if(Objects.equals(FastFootBollEnum.SINGLE_BET.getValue(), fastBO.getCategoryId())){
			saleStatus=SportSaleStatusEnum.NORMAL_SALE.getValue();
		}
		//单关查询状态为单关或者胜平负的赛事信息
		List<JcMathSPBO> jcMathList=jcDataCommData.findSportMatchFBSPInfo(fastBO.getTypeId(),fastBO.getIssueCode(),operateFastVO.getQueryDate(),saleStatus) ;
		if(jcMathList==null||jcMathList.size()==0){
			return;
		}
		if(operateFastVO.getPlatform()==DicEnum.PlatFormEnum.WEB.getValue()){
			//随机取一条数据给pc前台,pc端要求随机显示一条数据
			Random random = new Random();		
			JcMathSPBO jcmatchBO= jcMathList.get(random.nextInt(jcMathList.size()));				
			doFastFBCategoryInfo(jcmatchBO,fastBO);
			fastBO.setSportSp(jcmatchBO);
		}else{
			//其他端要求显示全部信息
			for(JcMathSPBO jcmatchBO : jcMathList){
				doFastFBCategoryInfo(jcmatchBO,fastBO);
			}
			fastBO.setSportSp(jcMathList);
		}
		
	}
	
	/**
	 * 快投竞彩足球子玩法信息处理
	 * @param fastBO
	 */
	void doFastFBCategoryInfo(JcMathSPBO jcmatchBO,OperateFastLottBO fastBO){			
		//单场致胜数据
		if(Objects.equals(FastFootBollEnum.SINGLE_WIN.getValue(), fastBO.getCategoryId())){
			JcMathWinSPBO winSpBo = jcDataCommData.findSportFBMatchDCZSInfo(jcmatchBO.getId(),fastBO.getTypeId(), fastBO.getIssueCode(), jcmatchBO.getSaleEndTime(),jcmatchBO.getStartTime(), jcmatchBO.getSystemCode());
			jcmatchBO.setJcDCZSBO(winSpBo);
		}		
	}
}