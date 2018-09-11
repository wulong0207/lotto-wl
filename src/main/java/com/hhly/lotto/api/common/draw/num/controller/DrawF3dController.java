package com.hhly.lotto.api.common.draw.num.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hhly.skeleton.base.common.LotteryEnum;

@RestController
@RequestMapping("/common/draw/f3d")
public class DrawF3dController extends DrawNumController {

	@Override
	public Integer getLotteryCode() {
		return LotteryEnum.Lottery.F3D.getName();
	}

}