package com.hhly.lotto.api.data.operate.v1_0.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.commoncore.remote.operate.service.IOperateLotteryService;
import com.hhly.lotto.api.data.operate.v1_0.OperateLotteryV10Data;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.lottocore.remote.lotto.service.IJcDataService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.DicEnum.PlatFormEnum;
import com.hhly.skeleton.base.common.LotteryEnum.Lottery;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.lotto.base.operate.bo.OperateLottBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateLotteryBO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateLotteryDetailBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateLotteryLottVO;
import com.hhly.skeleton.lotto.base.sport.bo.JclqDataBO;
import com.hhly.skeleton.lotto.base.sport.bo.JczqDaoBO;
import com.hhly.skeleton.lotto.base.sport.vo.JcParamVO;

/**
 * @desc 彩种管理服务实现类
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class OperateLotteryV10DataImpl  implements OperateLotteryV10Data{
	@Autowired
    private IOperateLotteryService operateLotteryService;	
	@Autowired
    private IJcDataService iJcDataService;
	@Autowired
    private RedisUtil objectRedisUtil;
	@Override
	public ResultBO<List<OperateLotteryDetailBO>> findHomeOperLottery(OperateLotteryLottVO vo) {
		String key = CacheConstants.C_COMM_LOTTERY_FIND_HOME_OPER+vo.getPlatform()+vo.getCategoryId();
		OperateLotteryBO  operLotteryBO= (OperateLotteryBO)objectRedisUtil.getObj(key);	 
		if(operLotteryBO!=null){
			//如果缓存中的时间超时则重新查询数据
			String nowstr = DateUtil.dayForWeek()+DateUtil.getNow("HH:mm:ss");
			if(!GetOnlineWeekCheck(operLotteryBO.getOnlineWeek(),operLotteryBO.getOnlineTime(),operLotteryBO.getOfflineWeek(),operLotteryBO.getOfflineTime(),nowstr)){
				operLotteryBO = null;
			}else{
				//如果有彩种截止销售则设置快投为空重新查询
				for(OperateLotteryDetailBO bo :operLotteryBO.getLotteryInfoList()){
					if(bo.getSaleEndTime()==null||bo.getSaleEndTime().getTime()<new Date().getTime()){
						operLotteryBO = null;
					}
				}
			}
		}
		if(operLotteryBO==null){
			operLotteryBO=operateLotteryService.findHomeOperLottery(vo).getData();
			operLotteryBO=operLotteryBO==null?new OperateLotteryBO():operLotteryBO;
			assembleData(vo.getPlatform(),operLotteryBO.getLotteryInfoList());
			objectRedisUtil.addObj(key, operLotteryBO, (long)Constants.DAY_1);
		}		
		return ResultBO.ok(operLotteryBO.getLotteryInfoList());
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
	 * 信息组装：
	 * pc端组装：胜负过关放在北京单场下面，冠亚军放在竞彩足球下面
	 * @param platform
	 * @param list
	 */
	public void assembleData(short platform,List<OperateLotteryDetailBO>  list){
		if(platform==PlatFormEnum.WEB.getValue()&&list!=null){
			for(OperateLotteryDetailBO lottBo  :  list){
				if(lottBo.getTypeId()==Lottery.SFGG.getName()){
					lottBo.setLotteryChildCode(""+lottBo.getTypeId());
					lottBo.setTypeId(306);					
				}
				if(lottBo.getTypeId()==Lottery.FNL.getName()||lottBo.getTypeId()==Lottery.CHP.getName()){
					lottBo.setLotteryChildCode(""+lottBo.getTypeId());
					lottBo.setTypeId(300);
				}
			}
		}
	}
	
	
	
	
	/**
	 * 获取彩种运营管理信息
	 * @return
	 */
	public List<OperateLotteryDetailBO> findOperLottery(short platform) {
		OperateLotteryLottVO lottvo = new OperateLotteryLottVO();
		lottvo.setPlatform(platform);
		List<OperateLotteryDetailBO>  list =findHomeOperLottery(lottvo).getData();	
		/*if(platform != PlatFormEnum.WEB.getValue()&&list!=null){
			JcParamVO jcvo = new JcParamVO();
			for(OperateLotteryDetailBO lottBo  :  list){
				//获取场次信息
				if(lottBo.getTypeId()==Lottery.FB.getName()){
					List<JczqDaoBO> bolist =iJcDataService.findJczqData(jcvo).getData().getDaoBOs();
					if(bolist!=null)  lottBo.setMatchNum(bolist.size());					
				}
				if(lottBo.getTypeId()==Lottery.BB.getName()){
					List<JclqDataBO> bolist =iJcDataService.findJclqData(jcvo).getData().getData();
					if(bolist!=null)  lottBo.setMatchNum(bolist.size());
				}
			}
		}*/
		return list;
	}
	
	
	/**
	 * 
	*	周一20:00至周二20:00显示：大乐透、七乐彩、3D、排列三
	*	周二20:00至周三20:00显示：双色球、七星彩、3D、排列三
	*	周三20:00至周四20:00显示：大乐透、七乐彩、3D、排列三
	*	周四20:00至周五20:00显示：双色球、15先5、3D、排列三
	*	周五20:00至周六20:00显示：七乐彩、七星彩、3D、排列三
	*	周六20:00至周日20:00显示：大乐透、东方6+1、3D、排列三
	*  周日20:00至周一20:00显示：双色球、七星彩、3D、排列三
	 * 
	 */
	public List<OperateLottBO> getMobailLottList(){
		String week = DateUtil.dayForWeek(new Date())+"";
		String  hour = StringUtil.padZeroLeft(DateUtil.getHourOfNow()+"",2);
		String min = StringUtil.padZeroLeft(DateUtil.getMinOfNow()+"",2);	
		List<OperateLottBO> list = new ArrayList<OperateLottBO>(); 
		OperateLottBO ssq = new OperateLottBO(Lottery.SSQ);
		OperateLottBO dlt = new OperateLottBO(Lottery.DLT);
		OperateLottBO f3d = new OperateLottBO(Lottery.F3D);
		OperateLottBO pl3 = new OperateLottBO(Lottery.PL3);
		OperateLottBO qlc = new OperateLottBO(Lottery.QLC);
		OperateLottBO qxc = new OperateLottBO(Lottery.QXC);
		//OperateLottBO df6 = new OperateLottBO(Lottery.D);东方6+1
		//OperateLottBO 15x5 = new OperateLottBO(Lottery.D);15先5
		int time = Integer.parseInt(week+hour+min);
		if(time>12020&&time<=22020){		 
			list.add(dlt);
			list.add(qlc);			
		}
		if(time>22020&&time<=32020){		 
			list.add(ssq);
			list.add(qxc);
		}
		if(time>32020&&time<=42020){		 
			list.add(dlt);
			list.add(qlc);
		}
		if(time>42020&&time<=52020){		 
			list.add(ssq);
		}
		if(time>52020&&time<=62020){		 
			list.add(qlc);
			list.add(qxc);
		}
		if(time>62020&&time<=72020){		 
			list.add(dlt);
		}
		if((time>72020&&time<=72359)||(time>=10000&&time<=12020)){		 
			list.add(ssq);
			list.add(qxc);
		}
		list.add(f3d);
		list.add(pl3);
		return list;
	}
}