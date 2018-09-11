package com.hhly.lotto.api.common.operate.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.commoncore.remote.operate.service.IOperateHelpService;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OperateHelpCorrectEnum;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.qiniu.QiniuUpload;
import com.hhly.skeleton.base.qiniu.QiniuUploadVO;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.operate.vo.OperateHelpCorrectVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateHelpTypeVO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateHelpVO;
import com.hhly.usercore.remote.member.service.IMemberInfoService;

@RestController
@RequestMapping("/common/help")
public class HelpController extends BaseController {

	@Autowired
	private IOperateHelpService operateHelpService;
	@Autowired
	private IMemberInfoService memberInfoService;

	/**
	 * 热搜词
	 * 
	 * @param num
	 * @return
	 */
	@RequestMapping(value = "hotword", method = RequestMethod.GET)
	public ResultBO<?> hotword(Integer num) {
		return ResultBO.ok(operateHelpService.findHotwords(num));
	}

	/**
	 * 关键字(搜索引导)
	 * 
	 * @param keyword
	 * @return
	 */
	@RequestMapping(value = "keyword", method = RequestMethod.GET)
	public ResultBO<?> keyword(OperateHelpVO helpVO) {
		Assert.paramNotNull(helpVO);
		Assert.paramNotNull(helpVO.getKeyword(), "keyword");
		Assert.paramNotNull(helpVO.getPlatform(), "platform");
		return ResultBO.ok(operateHelpService.findKeywords(helpVO));
	}

	/**
	 * 搜索
	 * 
	 * @return
	 */
	@RequestMapping(value = "search", method = RequestMethod.GET)
	public ResultBO<?> search(OperateHelpVO helpVO) {
		Assert.paramNotNull(helpVO);
		Assert.paramNotNull(helpVO.getKeyword(), "keyword");
		Assert.paramNotNull(helpVO.getPlatform(), "platform");
		return ResultBO.ok(operateHelpService.search(helpVO));
	}

	/**
	 * 栏目列表(树)
	 * 
	 * @return
	 */
	@RequestMapping(value = "type", method = RequestMethod.GET)
	public ResultBO<?> type() {
		return ResultBO.ok(operateHelpService.findTypeTree());
	}

	/**
	 * 栏目列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "typelist", method = RequestMethod.GET)
	public ResultBO<?> typelist(OperateHelpTypeVO vo) {
		vo.setMenu((short) Constants.NUM_3);
		return ResultBO.ok(operateHelpService.findTypeList(vo));
	}

	/**
	 * 查询帮助信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "listbytype", method = RequestMethod.POST)
	public ResultBO<?> listByType(@RequestBody List<OperateHelpVO> helpVOList) {
		Assert.paramNotNull(helpVOList);
		return ResultBO.ok(operateHelpService.findHelpByTypeList(helpVOList));
	}

	/**
	 * 查询帮助列表
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "article", method = RequestMethod.GET)
	public ResultBO<?> article(OperateHelpVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getPlatform(), "platform");
		return ResultBO.ok(operateHelpService.findHelpPage(vo));
	}

	/**
	 * 查询帮助详细内容
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "article/{id}", method = RequestMethod.GET)
	public ResultBO<?> detail(@PathVariable Integer id, OperateHelpVO vo) {
		if (vo == null) {
			vo = new OperateHelpVO();
		}
		vo.setId(id);
		Assert.paramNotNull(vo.getPlatform(), "platform");
		return ResultBO.ok(operateHelpService.findHelpById(vo));
	}

	/**
	 * 意见反馈
	 * 
	 * @return
	 */
	@RequestMapping(value = "feedback", method = RequestMethod.POST)
	public ResultBO<?> feedback(@RequestBody OperateHelpCorrectVO vo) {
		Assert.paramNotNull(vo);
		Assert.paramNotNull(vo.getContent(), "content");
		Assert.paramNotNull(vo.getSources(), "sources");
		Assert.paramNotNull(vo.getType(), "type");
		Assert.paramLegal(OperateHelpCorrectEnum.Sources.contains(vo.getSources()), "sources");
		Assert.paramLegal(OperateHelpCorrectEnum.Type.contains(vo.getType()), "type");
		// 验证手机号
		if (!ObjectUtil.isBlank(vo.getMobile())) {
			ResultBO<?> resultBO = memberInfoService.verifyMobile(vo.getMobile());
			if (resultBO.isError()) {
				return resultBO;
			}
		}
		operateHelpService.addHelpCorrect(vo);
		return ResultBO.ok();
	}

	public ResultBO<?> upload(@RequestPart MultipartFile file) throws IOException {
		if (file == null) {
			return ResultBO.err(MessageCodeConstants.UPLOAD_NULL_FAIL);
		}
		if (file.getSize() > Constants.MAX_UPLOAD_SIZE) {
			return ResultBO.err(MessageCodeConstants.UPLOAD_MAX_SIZE_FAIL, Constants.NUM_5 + "M");
		}
		QiniuUploadVO qiniuUploadVO = new QiniuUploadVO(accessKey, secretKey, bucketName, uploadLimit, fileType, usrImgSavePath,
				Long.parseLong(limitSize));
		qiniuUploadVO.setUploadURL(uploadURL);
		qiniuUploadVO.setFileName(file.getOriginalFilename());
		ByteArrayInputStream byteInputStream = null;
		try {
			new ByteArrayInputStream(file.getBytes());
			QiniuUpload qiniuUpload = new QiniuUpload(qiniuUploadVO);
			ResultBO<?> resultBO = qiniuUpload.uploadFileRename(byteInputStream);
			if (resultBO.isError()) {
				return resultBO;
			}
			// TODO 图片上传暂时不做
		} finally {
			try {
				IOUtils.closeQuietly(byteInputStream);
			} catch (Exception e) {
			}
		}
		return null;
	}

}
