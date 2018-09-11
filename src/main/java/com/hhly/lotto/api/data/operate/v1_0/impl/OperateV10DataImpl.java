package com.hhly.lotto.api.data.operate.v1_0.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hhly.commoncore.remote.operate.service.IOperateService;
import com.hhly.lotto.api.data.operate.v1_0.OperateV10Data;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.Constants;

/**
 * @desc 广告管理服务实现类
 * @author lidecheng
 * @date 2017年3月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class OperateV10DataImpl  implements OperateV10Data{
	@Autowired
    private IOperateService operateService;	
	@Autowired
    private RedisUtil objectRedisUtil;

	/*****************查询中奖信息*************************/	
	@Override	
	public ResultBO<List<String>> findUserWinInfo() {		
		String key = CacheConstants.C_COMM_USER_WIN;		
		List<String> list = objectRedisUtil.getObj(key,new ArrayList<String>());
		if(list==null){
			list = operateService.findUserWinInfo().getData();
			objectRedisUtil.addObj(key, list, (long)Constants.NUM_300);
		}	
		return ResultBO.ok(list);
	}
}