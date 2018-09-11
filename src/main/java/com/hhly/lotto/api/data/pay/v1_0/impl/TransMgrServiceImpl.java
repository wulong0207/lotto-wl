package com.hhly.lotto.api.data.pay.v1_0.impl;

import com.hhly.lotto.api.common.controller.user.member.v1_0.MemberInfoV10Controller;
import com.hhly.lotto.api.data.pay.v1_0.TransMgrService;
import com.hhly.paycore.remote.service.IPayService;
import com.hhly.paycore.remote.service.ITransRemittingService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.UserConstants;
import com.hhly.skeleton.base.qiniu.QiniuUpload;
import com.hhly.skeleton.base.qiniu.QiniuUploadResultVO;
import com.hhly.skeleton.base.qiniu.QiniuUploadVO;
import com.hhly.skeleton.cms.transmgr.bo.TransRemittingBO;
import com.hhly.skeleton.user.vo.UserInfoVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * @author TonyOne
 * @version 1.0
 * @desc
 * @date 2018/7/4 17:17
 * @company StayReal LTD
 */
@Service
public class TransMgrServiceImpl implements TransMgrService {

    private static final Logger logger = Logger.getLogger(TransMgrServiceImpl.class);
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
    @Value("${trans_remitting_path}")
    protected String transRemittingPath;
    /**允许批量上传文件大小*/
    @Value("${limitSize}")
    protected String limitSize;

    @Resource
    private ITransRemittingService transRemittingService;

    @Override
    public ResultBO<?> insertRemitting(TransRemittingBO vo) {
        // 上传图片
        try {
            //上传文件到七牛   牛云
            MultipartFile file = vo.getImg();
            QiniuUploadVO qiniuUploadVO = new QiniuUploadVO(accessKey, secretKey, bucketName, uploadLimit, fileType, transRemittingPath, Long.parseLong(limitSize));
            qiniuUploadVO.setUploadURL(uploadURL);
            qiniuUploadVO.setFileName(file.getOriginalFilename());

            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(file.getBytes());
            QiniuUpload qiniuUpload = new QiniuUpload(qiniuUploadVO);
            ResultBO<?> resultBO = qiniuUpload.uploadFileRename(byteInputStream);
            if (resultBO.isError()) {
                return resultBO;
            }
            List<QiniuUploadResultVO> list =  (List<QiniuUploadResultVO>)resultBO.getData();
            vo.setScreenshot(list.get(0).getFileName());
            vo.setImg(null);
            return transRemittingService.insert(vo);
        } catch (Exception e) {
            logger.error(e);
            return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS,"transMgrServiceImpl.insertRemitting");
        }
    }
}
