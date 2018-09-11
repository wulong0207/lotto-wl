package com.hhly.usercore.remote.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.pay.vo.PayBankcardVO;
import com.hhly.usercore.remote.member.service.IMemberBankcardV11Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhouyang
 * @version 1.0
 * @desc
 * @date 2018/6/23
 * @company 益彩网络科技公司
 */
@Service("iMemberBankcardV11Service")
public class IMemberBankcardV11ServiceImpl implements IMemberBankcardV11Service {

    @Value("${user_core_url}")
    private String userCoreUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResultBO<?> getBankName(PayBankcardVO vo) {
        String url = userCoreUrl+"/member/bankcard/detail";
        return restTemplate.postForEntity(url, vo, ResultBO.class).getBody();
    }

    @Override
    public ResultBO<?> getValidateCode(PayBankcardVO vo) throws Exception {
        String url = userCoreUrl+"/member/bankcard/get/code";
        return restTemplate.postForEntity(url, vo, ResultBO.class).getBody();
    }

    @Override
    public ResultBO<?> addBankCard(PayBankcardVO vo) throws Exception {
        String url = userCoreUrl+"/member/bankcard/add/bankcard";
        return restTemplate.postForEntity(url, vo, ResultBO.class).getBody();
    }
}
