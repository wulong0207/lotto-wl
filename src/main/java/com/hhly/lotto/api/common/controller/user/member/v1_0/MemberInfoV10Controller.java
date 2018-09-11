package com.hhly.lotto.api.common.controller.user.member.v1_0;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.UserConstants;
import com.hhly.skeleton.base.qiniu.QiniuUpload;
import com.hhly.skeleton.base.qiniu.QiniuUploadResultVO;
import com.hhly.skeleton.base.qiniu.QiniuUploadVO;
import com.hhly.skeleton.lotto.base.dic.bo.DicDataDetailBO;
import com.hhly.skeleton.lotto.base.dic.vo.DicDataDetailVO;
import com.hhly.skeleton.user.vo.PassportVO;
import com.hhly.skeleton.user.vo.UserInfoVO;
import com.hhly.usercore.remote.member.service.IMemberInfoService;
import com.hhly.usercore.remote.member.service.IVerifyCodeService;

/**
 * 用户信息管理控制层
 * @desc
 * @author zhouyang
 * @date 2017年12月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class MemberInfoV10Controller extends BaseController {
	
	private static final Logger logger = Logger.getLogger(MemberInfoV10Controller.class);
	
	@Autowired
	private IMemberInfoService memberInfoService;
	
	@Autowired
	private IVerifyCodeService verifyCodeService;

	@Autowired
	private com.hhly.commoncore.remote.dicdata.service.IDicDataDetailService IDicDataDetailService;

	/**
	 * 验证手机号码段
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value = "/validate/number", method = RequestMethod.GET)
	public Object verifyMobile(String mobile) {
		try {
			return memberInfoService.verifyMobile(mobile);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.verifyMobile"), e);
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.verifyMobile");
		}
	}
	
	/**
	 * 用户基本信息及钱包信息展示
	 * @param userInfoVO
	 * @return
	 */
	@RequestMapping(value="/index", method=RequestMethod.POST)
	public Object userIndex(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request){
		try {
			userInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			userInfoVO.setChannelId(getHeaderParam(request).getChannelId());
			userInfoVO.setIp(getIp(request));
			return memberInfoService.showUserIndex(userInfoVO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.showUserIndex"), e);
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.showUserIndex");
		}
	}
	
	/**
	 * 用户基本信息及银行卡信息展示
	 * @param userInfoVO
	 * @return
	 */
	@RequestMapping(value="/info", method=RequestMethod.POST)
	public Object myInfo(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request){
		try {
			userInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			userInfoVO.setChannelId(getHeaderParam(request).getChannelId());
			userInfoVO.setIp(getIp(request));
			return memberInfoService.showUserPersonalData(userInfoVO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.showUserPersonalData"), e);
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.showUserPersonalData");
		}
	}

	/**
	 * 获取旧手机验证码
	 * @param passportVO
	 * @return
	 */
	@RequestMapping(value="/get/mobile/code", method=RequestMethod.POST)
	public Object sendMobileVerifyCode(@RequestBody PassportVO passportVO, HttpServletRequest request) {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return verifyCodeService.sendMobileVerifyCode(passportVO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "verifyCodeService.sendMobileVerifyCode"), e);
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"verifyCodeService.sendMobileVerifyCode");
		}
	}

	/**
	 * 获取旧邮箱验证码
	 * @param passportVO
	 * @return
	 */
	@RequestMapping(value="/get/email/code", method=RequestMethod.POST)
	public Object sendEmailVerifyCode(@RequestBody PassportVO passportVO, HttpServletRequest request) {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return verifyCodeService.sendEmailVerifyCode(passportVO);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "verifyCodeService.sendEmailVerifyCode"), e);
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"verifyCodeService.sendEmailVerifyCode");
		}
	}

	/**
	 * 验证旧手机号码
	 * @param passportVO
	 * @return
	 */
	@RequestMapping(value="/validate/mobile", method=RequestMethod.POST)
	public Object checkOldMobile(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberInfoService.checkMobile(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.checkMobile"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.checkMobile");
		}
	}


	/**
	 * 验证旧邮箱地址
	 * @param passportVO
	 * @return
	 */
	@RequestMapping(value="/validate/email", method=RequestMethod.POST)
	public Object checkOldEmail(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberInfoService.checkEmail(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.checkEmail"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.checkEmail");
		}
	}
	
	/**
	 * 获取新手机验证码
	 * @param passportVO
	 * @return
	 */
	@RequestMapping(value="/get/new/mobile/code", method=RequestMethod.POST)
	public Object sendNewMobileCode(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return verifyCodeService.sendNewMobileVerifyCode(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "verifyCodeService.sendNewMobileVerifyCode"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"verifyCodeService.sendNewMobileVerifyCode");
		}
	}
	
	/**
	 * 获取新邮箱验证码
	 * @param passportVO 邮箱地址
	 * @return
	 */
	@RequestMapping(value="/get/new/email/code", method=RequestMethod.POST)
	public Object sendNewEmailCode(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return verifyCodeService.sendNewEmailVerifyCode(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "verifyCodeService.sendNewEmailVerifyCode"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"verifyCodeService.sendNewEmailVerifyCode");
		}
	}
	
	/**
	 * 验证银行卡
	 * @param passportVO
	 * @return
	 */
	@RequestMapping(value="/validate/bankcard", method=RequestMethod.POST)
	public Object checkCreditCardNum(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberInfoService.checkCreditCardNum(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.checkCreditCardNum"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.checkCreditCardNum");
		}
	}

	/**
	 * 验证身份证号码
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/validate/idcard", method=RequestMethod.POST)
	public Object checkIDCard(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberInfoService.checkIDCard(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.checkIDCard"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.checkIDCard");
		}
	}

	/**
	 * 验证密码 - h5专用
	 * @param passportVO
	 * @return
	 */
	@RequestMapping(value="/validate/password", method=RequestMethod.POST)
	public Object checkPassword(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberInfoService.checkPassword(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.checkPassword"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.checkPassword");
		}
	}

	/**
	 * 设置密码
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/set/password", method=RequestMethod.POST)
	public Object setPassword(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberInfoService.setPassword(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.setPassword"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.setPassword");
		}
	}
	
	/**
	 * 修改/登记手机号码
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/modify/mobile", method=RequestMethod.POST)
	public Object updateMobile(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberInfoService.updateMobile(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.updateMobile"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.updateMobile");
		}
	}
	
	/**
	 * 修改/登记邮箱地址
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/modify/email", method=RequestMethod.POST)
	public Object updateEmail(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberInfoService.updateEmail(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.updateEmail"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.updateEmail");
		}
	}
	
	/**
	 * 修改密码
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/modify/password", method=RequestMethod.POST)
	public Object updatePassword(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberInfoService.updatePassword(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.updatePassword"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.updatePassword");
		}
	}

	/**
	 * 验证昵称
	 * @param passportVO
	 * @return
	 */
	@RequestMapping(value = "/validate/nickname", method = RequestMethod.POST)
	public Object validateNickname(@RequestBody PassportVO passportVO, HttpServletRequest request) {
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberInfoService.validateNickname(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.validateNickname"), e);
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.validateNickname");
		}
	}

	/**
	 * 修改昵称
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/modify/nickname", method=RequestMethod.POST)
	public Object updateNickname(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberInfoService.updateNickname(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.updateAccount"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.updateAccount");
		}
	}

	/**
	 * 修改帐户名
	 * @param passportVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/modify/account", method=RequestMethod.POST)
	public Object updateAccount(@RequestBody PassportVO passportVO, HttpServletRequest request){
		try {
			passportVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			passportVO.setChannelId(getHeaderParam(request).getChannelId());
			passportVO.setIp(getIp(request));
			return memberInfoService.updateAccount(passportVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.updateAccount"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.updateAccount");
		}
	}

	/**
	 * 开启手机登录
	 * @param userInfoVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/open/mobile", method=RequestMethod.POST)
	public Object openMobileLogin(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request){
		try {
			userInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			userInfoVO.setChannelId(getHeaderParam(request).getChannelId());
			userInfoVO.setIp(getIp(request));
			return memberInfoService.openMobileLogin(userInfoVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.openMobileLogin"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.openMobileLogin");
		}
	}
	
	/**
	 * 关闭手机登录
	 * @param userInfoVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/close/mobile", method=RequestMethod.POST)
	public Object closeMobileLogin(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request){
		try {
			userInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			userInfoVO.setChannelId(getHeaderParam(request).getChannelId());
			userInfoVO.setIp(getIp(request));
			return memberInfoService.closeMobileLogin(userInfoVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.closeMobileLogin"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.closeMobileLogin");
		}
	}
	
	/**
	 * 开启邮箱登录
	 * @param userInfoVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/open/email", method=RequestMethod.POST)
	public Object openEmailLogin(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request){
		try {
			userInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			userInfoVO.setChannelId(getHeaderParam(request).getChannelId());
			userInfoVO.setIp(getIp(request));
			return memberInfoService.openEmailLogin(userInfoVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.openEmailLogin"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.openEmailLogin");
		}
	}
	
	/**
	 * 关闭邮箱登录
	 * @param userInfoVO
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/close/email", method=RequestMethod.POST)
	public Object closeEmailLogin(@RequestBody UserInfoVO userInfoVO, HttpServletRequest request){
		try {
			userInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
			userInfoVO.setChannelId(getHeaderParam(request).getChannelId());
			userInfoVO.setIp(getIp(request));
			return memberInfoService.closeEmailLogin(userInfoVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.closeEmailLogin"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.closeEmailLogin");
		}
	}

	/**
	 * 上传头像
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/upload/headPortrait", method = RequestMethod.POST)
	public Object uploadHeadPortrait(@RequestPart MultipartFile file, String token, HttpServletRequest request) {
		UserInfoVO userInfoVO = new UserInfoVO();
		userInfoVO.setToken(token);
		userInfoVO.setPlatform(getHeaderParam(request).getPlatformId().shortValue());
		userInfoVO.setChannelId(getHeaderParam(request).getChannelId());
		try {
			if (file.getSize() > UserConstants.PHOTO_SIZE) {
				return ResultBO.err(MessageCodeConstants.HEADPORTRAIT_OUT_OF_MEMORY);
			}
			//上传文件到七牛   牛云
			QiniuUploadVO qiniuUploadVO = new QiniuUploadVO(accessKey, secretKey, bucketName, uploadLimit, fileType, usrImgSavePath, Long.parseLong(limitSize));
			qiniuUploadVO.setUploadURL(uploadURL);
			qiniuUploadVO.setFileName(file.getOriginalFilename());

			ByteArrayInputStream byteInputStream = new ByteArrayInputStream(file.getBytes());
			QiniuUpload qiniuUpload = new QiniuUpload(qiniuUploadVO);
			ResultBO<?> resultBO = qiniuUpload.uploadFileRename(byteInputStream);
			if (resultBO.isError()) {
				return resultBO;
			}
			List<QiniuUploadResultVO> list =  (List<QiniuUploadResultVO>)resultBO.getData();

			userInfoVO.setHeadUrl(list.get(0).getFileName());
			return memberInfoService.uploadHeadPortrait(userInfoVO);
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.uploadHeadPortrait"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.uploadHeadPortrait");
		}
	}

	/**
	 * 客服电话
	 * @return
	 */
	@RequestMapping(value = "/customertel", method = RequestMethod.GET)
	public Object findCustomerTel() {
		DicDataDetailVO dicDataDetailVO = new DicDataDetailVO();
		dicDataDetailVO.setDicCode(Constants.DIC_CUS_TEL);
		DicDataDetailBO dicDataDetailBO = IDicDataDetailService.findSingle(dicDataDetailVO);
		return ResultBO.ok(dicDataDetailBO.getDicDataName());
	}

	/**
	 * 验证账号登录状态
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/token/status", method = RequestMethod.POST)
	public Object getTokenStatus(@RequestBody PassportVO vo) {
		try {
			return memberInfoService.getTokenStatus(vo.getToken());
		} catch (Exception e) {
			logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "memberInfoService.setPassword"));
			e.printStackTrace();
			return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"memberInfoService.setPassword");
		}
	}	
}
