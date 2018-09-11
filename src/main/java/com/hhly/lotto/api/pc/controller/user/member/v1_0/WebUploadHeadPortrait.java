package com.hhly.lotto.api.pc.controller.user.member.v1_0;

import java.io.ByteArrayInputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.lotto.api.common.controller.user.member.v1_0.MemberInfoV10Controller;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.UserConstants;
import com.hhly.skeleton.base.qiniu.QiniuUpload;
import com.hhly.skeleton.base.qiniu.QiniuUploadResultVO;
import com.hhly.skeleton.base.qiniu.QiniuUploadVO;
import com.hhly.skeleton.user.vo.UserInfoVO;
import com.hhly.usercore.remote.member.service.IMemberInfoService;

/**
 * Created by zhouy478 on 2017/12/22.
 */
@RestController
@RequestMapping("/pc/v1.0/upload")
public class WebUploadHeadPortrait {

    @Value("${before_file_dir}")
    protected String beforeFileDir;

    /** 7牛accessKey  **/
    @Value("${accessKey}")
    protected String accessKey;
    /** 7牛secretKey **/
    @Value("${secretKey}")
    protected String secretKey;
    /** bucketName **/
    @Value("${bucketName}")
    protected String bucketName;
    /** 允许批量上传文件数量  **/
    @Value("${uploadLimit}")
    protected Integer uploadLimit;
    /** 允许上传文件类型 **/
    @Value("${fileType}")
    protected String fileType;
    /** 文件访问路径  **/
    @Value("${uploadURL}")
    protected String uploadURL;
    /**域名和文件名中间的路径*/
    @Value("${usrImgSavePath}")
    protected String usrImgSavePath;
    /**允许批量上传文件大小*/
    @Value("${limitSize}")
    protected String limitSize;

    private static final Logger logger = Logger.getLogger(MemberInfoV10Controller.class);

    @Autowired
    private IMemberInfoService memberInfoService;

    @RequestMapping(value = "/headPortrait", method = RequestMethod.POST)
    public ResponseEntity<Object> uploadHeadPortrait(@RequestPart MultipartFile file, String token, HttpServletResponse response) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        response.setContentType(MediaType.TEXT_HTML_VALUE);
        try {
            if (file.getSize() > UserConstants.PHOTO_SIZE) {
                return new ResponseEntity<Object>(ResultBO.err(MessageCodeConstants.HEADPORTRAIT_OUT_OF_MEMORY), HttpStatus.OK);
            }
            //上传文件到七牛   牛云
            QiniuUploadVO qiniuUploadVO = new QiniuUploadVO(accessKey, secretKey, bucketName, uploadLimit, fileType, usrImgSavePath, Long.parseLong(limitSize));
            qiniuUploadVO.setUploadURL(uploadURL);
            qiniuUploadVO.setFileName(file.getOriginalFilename());

            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(file.getBytes());
            QiniuUpload qiniuUpload = new QiniuUpload(qiniuUploadVO);
            ResultBO<?> resultBO = qiniuUpload.uploadFileRename(byteInputStream);
            if (resultBO.isError()) {
                return new ResponseEntity<Object>(resultBO, headers, HttpStatus.OK);
            }
            List<QiniuUploadResultVO> list =  (List<QiniuUploadResultVO>)resultBO.getData();
            UserInfoVO userInfoVO = new UserInfoVO();
            userInfoVO.setHeadUrl(list.get(0).getFileName());
            userInfoVO.setToken(token);
            return new ResponseEntity<Object>(memberInfoService.uploadHeadPortrait(userInfoVO), headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, "userInfoService.uploadHeadPortrait"));
            e.printStackTrace();
            return new ResponseEntity<Object>(ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"userInfoService.uploadHeadPortrait"), headers, HttpStatus.OK);
        }
    }
}
