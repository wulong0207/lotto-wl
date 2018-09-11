package com.hhly.lotto.base.controller;

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.MultipartFile;

import com.hhly.lotto.base.common.HeaderParam;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.bo.UploadFileBO;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.exception.ResultJsonException;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.FileUtil;

/**
 * @desc 基础控制器
 * @author huangb
 * @date 2017年3月6日
 * @company 益彩网络
 * @version v1.0
 */
@Controller
public class BaseController {

	public static final Logger logger = LoggerFactory.getLogger(BaseController.class);
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

	// 递增数
	private static final AtomicInteger ASCEND = new AtomicInteger(0);

	/**  
	* 方法说明: 字符串类型的时间转换成Date类型的格式
	* @param binder
	* @time: 2017年3月7日 下午5:51:55
	* @return: void
	*/
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new MyDateEditor());
	}

	private class MyDateEditor extends PropertyEditorSupport {
		@Override
		public void setAsText(String text) throws IllegalArgumentException {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = null;
			try {
				date = format.parse(text);
			} catch (ParseException e) {
				format = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date = format.parse(text);
					Calendar cal = Calendar.getInstance();
				    cal.setTime(date);
				    int year = cal.get(Calendar.YEAR);
				    // 验证年月日的合法性，如2017-13-13为非法
				    // Calendar.MONTH的1月到12月对应数字0~11
				    int month = cal.get(Calendar.MONTH)+1;
				    int day = cal.get(Calendar.DAY_OF_MONTH);
				    String [] dateArray = StringUtils.tokenizeToStringArray(text, "-");
				    int orignalYear = Integer.parseInt(dateArray[0]);
				    int orignalMonth = Integer.parseInt(dateArray[1]);
				    int orignalDay = Integer.parseInt(dateArray[2]);
				    if(year != orignalYear || month != orignalMonth || day!= orignalDay)
				    	throw new Exception();
				} catch (Exception e1) {
					// 测试要求验证时期格式
					throw new ResultJsonException(ResultBO.err(MessageCodeConstants.QUERY_DATE));
				}
			}
			setValue(date);
		}
	}
	
	/**
	 * 得到IP
	 * @param request
	 * @return
	 */
	public String getIp(HttpServletRequest request) {
		// 有cnd 加速时也能取到用户ip地址
		if (request == null)
			return "";
		String ip = request.getHeader("Cdn-Src-Ip");
		if (ip == null) {
			ip = request.getHeader("X-Forwarded-For");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("X-Real-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		}
		if (ip != null && ip.contains(",")) {
			return ip.split(",")[0].trim();
		}
		if (ip.contains(":")) {
			int index = ip.lastIndexOf(":");
			ip = ip.substring(index+1, ip.length());
		}
		return ip;
	}
	
	
	/**
	 * 上传文件到服务器
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 下午3:43:19
	 * @param file 文件
	 * @param catalogue 文件保存相对路径
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	protected UploadFileBO uploadFile(MultipartFile file,String catalogue) throws IllegalStateException, IOException {
		// 拆分文件名和后缀
		String[] tokens = file.getOriginalFilename().split("\\.(?=[^\\.]+$)");
		// 新的文件名
		String newFileName = DateUtil.getNow("yyMMddkkmmss") + getAscend()+"_help" + SymbolConstants.DOT + tokens[1];
		String savePath = beforeFileDir+catalogue ;
		FileUtil.createDir(savePath);
		String imagePath = savePath +SymbolConstants.OBLIQUE_LINE+ newFileName;
		file.transferTo(new File(imagePath));
		UploadFileBO vo = new UploadFileBO();
		vo.setUrl(catalogue+SymbolConstants.OBLIQUE_LINE+ newFileName);
		return vo;
	}
	/**
	 * 获取递增数
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月10日 下午3:54:09
	 * @return
	 */
	private int getAscend() {
		int num = ASCEND.incrementAndGet();
		if (num == 99999) {
			ASCEND.set(1);
		}
		return num;
	}
	
	
	/**
	 * 获取请求头部参数
	 * @param request
	 * @return HeaderParam
	 * @author scott
	 * @date   2017-12-7
	 */
	public HeaderParam  getHeaderParam(HttpServletRequest request){
		// 渠道ID 
		String channelId = request.getHeader("cId");
		// 平台ID  1--PC 2--H5 3--Android 4--IOS
		Integer platformId = Integer.parseInt(request.getHeader("pId"));
		// 应用版本ID 
		String versionId = request.getHeader("vId");
		// 设备ID 
		String deviceId = request.getHeader("eId");
		HeaderParam headerParam = new HeaderParam(channelId,platformId,versionId,deviceId);
		return headerParam ;
	}
    
    /**
     * 输出错误日志并返回
     * @param e 错误信息
     * @param desc 类.方法名称
     * @return
     */
    protected ResultBO<?> printException(Exception e,String desc){
        logger.error(ResultBO.getMsg(MessageCodeConstants.HESSIAN_ERROR_SYS, desc), e);
        e.printStackTrace();
        return ResultBO.err(MessageCodeConstants.HESSIAN_ERROR_SYS, desc);
    }
	
}
