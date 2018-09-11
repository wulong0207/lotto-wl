package com.hhly.lotto.interceptor;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
/**
 * 拦截单式上传文件大小、文件类型...
 * @author longguoyou
 * @date 2017年6月23日
 * @compay 益彩网络科技有限公司
 */
public class FileUploadInterceptor extends HandlerInterceptorAdapter {
	
    private long maxSize;
    private String fileType;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否文件上传
        if(request!=null && ServletFileUpload.isMultipartContent(request)) {
            ServletRequestContext ctx = new ServletRequestContext(request);
            //获取上传文件尺寸大小
            long requestSize = ctx.getContentLength();
            if (requestSize > maxSize) {
                //当上传文件大小超过指定大小限制后，模拟抛出MaxUploadSizeExceededException异常
                throw new ResultJsonException(ResultBO.err(MessageCodeConstants.UPLOAD_FILE_SIZE_LIMIT_SERVICE));
            }
        }
        //判断上传文件类型
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> files = multipartRequest.getFileMap();
			Iterator<String> iterator = files.keySet().iterator();
			//对多部件请求资源进行遍历
			while (iterator.hasNext()) {
				String formKey = (String) iterator.next();
				MultipartFile multipartFile = multipartRequest.getFile(formKey);
				String filename = multipartFile.getOriginalFilename();
				//判断是否为限制文件类型
				if (! checkFile(filename)) {
				   throw new ResultJsonException(ResultBO.err(MessageCodeConstants.UPLOAD_FILE_TYPE_LIMIT_SERVICE));
				} 
	        }
        }
        return true;
    }
        
    /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    private boolean checkFile(String fileName) {
        //设置允许上传文件类型
        String suffixList = fileType;
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }
    
    public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }
}