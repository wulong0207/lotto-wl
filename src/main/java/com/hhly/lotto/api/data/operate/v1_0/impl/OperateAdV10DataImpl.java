package com.hhly.lotto.api.data.operate.v1_0.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.commoncore.remote.operate.service.IOperateAdService;
import com.hhly.lotto.api.data.cache.v1_0.UserInfoCacheData;
import com.hhly.lotto.api.data.operate.v1_0.OperateAdV10Data;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.lotto.base.operate.bo.OperateAdLottoBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateAdVO;
import com.hhly.skeleton.user.bo.UserInfoBO;

/**
 * @desc 广告管理服务实现类
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class OperateAdV10DataImpl  implements OperateAdV10Data{
	@Autowired
    private IOperateAdService operateAdService;	
	@Autowired
    private RedisUtil objectRedisUtil;
	@Autowired 
	private UserInfoCacheData userInfoCacheData;

	/*****************查询广告信息*************************/	
	@Override	
	public ResultBO<List<OperateAdLottoBO>> findHomeOperAd(OperateAdVO vo) {		
		if(!StringUtil.isBlank(vo.getToken())){
			UserInfoBO userInfo = (UserInfoBO) userInfoCacheData.checkToken(vo.getToken()).getData();
			 if (ObjectUtil.isBlank(userInfo)) {
		            throw new ResultJsonException(ResultBO.err(MessageCodeConstants.TOKEN_LOSE_SERVICE));
		     }
			 vo.setUserId(userInfo.getId().longValue());
		}
		if(StringUtil.isBlank(vo.getChannelId())){
			//为了兼容版本，如果没有查到渠道，默认选择全部
			vo.setChannelId(""+Constants.NUM_0);
		}
		String key = CacheConstants.C_COMM_AD_FIND_HOME_OPER_AD+vo.getMenu()+vo.getPlatform()+vo.getChannelId()+vo.getToken()+vo.getTypeCode();		
		List<OperateAdLottoBO> lottoList = objectRedisUtil.getObj(key,new ArrayList<OperateAdLottoBO>());
		if(lottoList==null){
			lottoList = operateAdService.findHomeOperAd(vo).getData();
			objectRedisUtil.addObj(key, lottoList, (long)Constants.NUM_600);
		}	
		return ResultBO.ok(lottoList);
	}
}