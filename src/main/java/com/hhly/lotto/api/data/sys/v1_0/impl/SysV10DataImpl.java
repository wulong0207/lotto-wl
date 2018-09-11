
package com.hhly.lotto.api.data.sys.v1_0.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.commoncore.remote.dicdata.service.IDicDataDetailService;
import com.hhly.lotto.api.data.sys.v1_0.SysV10Data;
import com.hhly.lotto.base.util.RedisUtil;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.CacheConstants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.model.DicDataEnum;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.dic.bo.DicDataDetailBO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2018年1月12日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class SysV10DataImpl implements SysV10Data {
	
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired
	IDicDataDetailService dicDataDetailService;

	@Override
	public Map<String, String> findWxAccList() {
		Map<String, String> map = redisUtil.getObj(CacheConstants.C_H5_WX_ACCOUNT, new HashMap<String, String>());
		if(ObjectUtil.isBlank(map)){
			List<DicDataDetailBO> list = dicDataDetailService.findSimple(DicDataEnum.H5_WX_ACC_PRIVILEG.getDicCode());
			if(!ObjectUtil.isBlank(list)){
				map = new HashMap<>();
				for (DicDataDetailBO dicDataDetailBO : list) {
					String[] acc = dicDataDetailBO.getDicDataValue().split(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR);
					map.put(acc[0], acc[1]);
				}
				redisUtil.addObj(CacheConstants.C_H5_WX_ACCOUNT, map, CacheConstants.FOUR_HOURS);
			}			
		}
		return map;
	}

	@Override
	public ResultBO<?> getWxAcc() {
		List<DicDataDetailBO> list = dicDataDetailService.findSimple(DicDataEnum.H5_WX_ACC_PRIVILEG.getDicCode());
		if(ObjectUtil.isBlank(list))
			return ResultBO.err();
		DicDataDetailBO bo = list.get(0);
		String[] acc = bo.getDicDataValue().split(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR);
		Map<String, String> map = new HashMap<>();
		map.put("appId", acc[0]);
		return ResultBO.ok(map);
	}
}
