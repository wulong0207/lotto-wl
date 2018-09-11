package com.hhly.lotto.api.android.controller.user.member.v1_0;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.UserConstants;
import com.hhly.skeleton.base.qiniu.QiniuUpload;
import com.hhly.skeleton.base.qiniu.QiniuUploadResultVO;
import com.hhly.skeleton.base.qiniu.QiniuUploadVO;
import com.hhly.skeleton.user.vo.UserInfoVO;
import com.hhly.usercore.remote.member.service.IMemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/android")
public class AndroidUploadPortrait extends BaseController {

    @Autowired
    private IMemberInfoService memberInfoService;


    /**
     *
     * @param file
     * @param token
     * @param request
     * @return
     */
    @RequestMapping(value = "/userinfo/uploadHeadPortrait", method = RequestMethod.POST)
    public Object uploadHeadPortrait2(@RequestPart MultipartFile file, String token, HttpServletRequest request) {
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
}
