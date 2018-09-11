package com.hhly.lotto.api.pc.order.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hhly.lotto.base.controller.BaseController;
import com.hhly.lottocore.remote.order.service.ISingleOrderService;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.bo.UploadFileBO;
import com.hhly.skeleton.base.common.SingleUploadEnum.UploadType;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.MessageCodeConstants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.qiniu.QiniuUpload;
import com.hhly.skeleton.base.qiniu.QiniuUploadVO;
import com.hhly.skeleton.base.util.FileUtil;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.lotto.base.singleupload.vo.SingleUploadJCVO;
import com.hhly.skeleton.user.bo.UserInfoBO;
import com.hhly.usercore.remote.member.service.IMemberInfoService;
/**
 * 单式上传
 * @author longguoyou
 * @date 2017年6月9日
 * @compay 益彩网络科技有限公司
 */
@Controller
@RequestMapping("/singleupload")
public class SingleUploadController extends BaseController{
	
	public static final Logger logger = Logger.getLogger(BaseController.class);
	
	private static final String DEFAULT_PATH = "C:\\upload";
	
	private static final String UNIX_PATH = "/_upload_file/single_upload";
	
	@Autowired
	private IMemberInfoService memberInfoService;
	
	@Autowired
    private ISingleOrderService iSingleOrderService;
	
	/**
	 * 文件大小
	 */
	private static final long MAXSIZE = 10485760;
	/**
	 * 支持文件类型
	 */
	private static final String FILE_TYPE = "txt";
	 
	@Value("${single_upload_dir}")
	protected String singleUploadDir;//根目录
	
//	@Value("${single_file_url}")
//	protected String singleFileUrl;//文件服务器地址
	
	/** 7牛accessKey  **/
	@Value("${accessKey}")
	private String accessKey;
	/** 7牛secretKey **/
	@Value("${secretKey}")
	private String secretKey;
	/** bucketName **/
	@Value("${bucketName}")
	private String bucketName;
	/** 允许批量上传文件数量  **/
	@Value("${uploadLimit}")
	private Integer uploadLimit;
	/** 允许上传文件类型 **/
	@Value("${fileType}")
	private String fileType;
	/** 文件访问路径  **/
	@Value("${uploadURL}")
	private String uploadURL;
	/**域名和文件名中间的路径*/
	@Value("${savePath}")
	private String savePath;
	/**允许批量上传文件大小*/
	@Value("${limitSize}")
	private String limitSize;
	
	/**
	 * 前置控制器上传文件
	 * @author longguoyou
	 * @date 2017年6月16日
	 * @return
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public @ResponseBody String upload(@RequestPart MultipartFile file, HttpServletRequest request, ModelMap model){
		ResultBO<?> result = null;
		try {
			result = exe(request,file.getBytes());
			if(result.isError()){
				return JsonUtil.objectToJcakJson(result.isError()?ResultBO.err(result.getErrorCode()):ResultBO.ok(result.getData()));
			}
			String token = request.getParameter("token");
			Integer lotteryCode = Integer.valueOf(request.getParameter("lotteryCode"));//彩种编号
			Integer lotteryChildCode = Integer.valueOf(request.getParameter("lotteryChildCode"));//子玩法编号
			String selectedMatchs = request.getParameter("selectedMatchs");//选择场次信息
			String shiftContent = request.getParameter("shiftContent");//用户转换字符
			Short uploadType = ObjectUtil.isBlank(request.getParameter("uploadType"))?null:Short.valueOf(request.getParameter("uploadType"));
			Boolean flag = Boolean.valueOf(request.getParameter("flag"));//是否第一次上传
		    result = memberInfoService.findUserInfoByToken(token);
		    if(result.isError()){return JsonUtil.objectToJcakJson(ResultBO.err(result.getErrorCode()));}
		    UserInfoBO userInfoBO = (UserInfoBO)result.getData();
			SingleUploadJCVO singleUploadJCVO = new SingleUploadJCVO();
			singleUploadJCVO.setLotteryCode(lotteryCode);
			singleUploadJCVO.setLotteryChildCode(lotteryChildCode);
			singleUploadJCVO.setSelectedMatchs(selectedMatchs);
			singleUploadJCVO.setShiftContent(shiftContent);
			singleUploadJCVO.setUploadType(uploadType);
			singleUploadJCVO.setUserId(userInfoBO.getId());
			singleUploadJCVO.setToken(token);
			singleUploadJCVO.setFlag(flag);
			Map<String, Object> map = new HashMap<String, Object>();
//            List<String> list = FileUtil.getListStringFromFile(file.getInputStream(), EncodingType.GBK.getShortName());
            
            /**七牛云 , 上传文件*/
            File[] myPath = FileUtil.getFilePath(singleUploadJCVO, savePath, file.getOriginalFilename());
            logger.debug("file path:"+myPath[1].getPath());
            QiniuUploadVO qiniuUploadVO = new QiniuUploadVO(accessKey, secretKey, bucketName, uploadLimit, fileType, savePath, Long.parseLong(limitSize));
            qiniuUploadVO.setFileRelativePath(myPath[1].getPath());
            qiniuUploadVO.setUploadURL(uploadURL);
            QiniuUpload qiniuUpload = new QiniuUpload(qiniuUploadVO);
    		// 以字节流数组流的方式上传
//    		byte[] uploadBytes = file.getBytes();
//    		ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
    		ResultBO<?> resultBO = qiniuUpload.uploadFile(file);
//    		ResultBO<?> resultBO = qiniuUpload.uploadFileNotRename(byteInputStream);
            if(resultBO.isError()){
            	return JsonUtil.objectToJcakJson(result.isError()?ResultBO.err(result.getErrorCode()):ResultBO.ok(result.getData()));
            }
    		
//			map.put(Constants.TXT_CONTENT, list);
			singleUploadJCVO.setFilePath(myPath[1].getPath());
			result = iSingleOrderService.validateOrder(file.getOriginalFilename(), singleUploadJCVO, map);
		} catch (Exception e) {
			logger.debug(e);
			return JsonUtil.objectToJcakJson(ResultBO.err(MessageCodeConstants.SYS_ERROR_SYS));
		}
		return JsonUtil.objectToJcakJson(result.isError()?ResultBO.err(result.getErrorCode()):ResultBO.ok(result.getData()));
	}
    
	public ResultBO<?> exe(HttpServletRequest request, byte[] uploadBytes){
		 //判断是否文件上传
        if(request!=null && ServletFileUpload.isMultipartContent(request)) {
        	if(uploadBytes.length > MAXSIZE){
        		return ResultBO.err(MessageCodeConstants.UPLOAD_FILE_SIZE_LIMIT_SERVICE);
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
				   return ResultBO.err(MessageCodeConstants.UPLOAD_FILE_TYPE_LIMIT_SERVICE);
				} 
	        }
        }
        return ResultBO.ok();
	}
	
	 /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    private boolean checkFile(String fileName) {
        //设置允许上传文件类型
        String suffixList = FILE_TYPE;
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }
	
	/**
	 * 单式上传文件
	 * @author longguoyou
	 * @date 2017年6月16日
	 * @param file
	 * @param request
	 * @param model
	 * @return
	 */
	public UploadFileBO singleUpload(@RequestPart MultipartFile file, SingleUploadJCVO singleUploadJCVO) {
		logger.debug("单式上传文件开始.....");
		String basePath = singleUploadDir + UNIX_PATH;//保存根目录
		String os = System.getProperty("os.name");  
	    if(os.toLowerCase().startsWith("win")){  
	    	basePath = DEFAULT_PATH;
	    }
        File[] targetFile = getFilePath(singleUploadJCVO, basePath, file.getOriginalFilename()); 
        if(!targetFile[0].exists()){  
            targetFile[0].mkdirs();  
        }  
        //保存  
        try {  
            file.transferTo(targetFile[1]);  
        } catch (Exception e) {  
            logger.error(e);
        } 
        UploadFileBO vo = new UploadFileBO();
        vo.setName(singleUploadJCVO.getSecondFileName());
        vo.setUrl(targetFile[1].getPath());
        logger.debug("单式上传文件结束.....");
        return vo;  
	}
	
	/**
	 * 
	 * @author longguoyou
	 * @date 2017年6月16日
	 * @param basePath 文件上传根目录
	 * @param lotteryCode 彩种
	 * @param userId  用户id
	 * @param originalFileName 上传文件原始文件名
	 * @param flag 是否首次
	 * 
	 * @return 按规则需存盘最终的文件
	 */
	private File[] getFilePath(SingleUploadJCVO singleUploadJCVO, String basePath,  String originalFileName){
        long curTime = System.currentTimeMillis();
		String strTime = String.valueOf(curTime);
	    String userFile = null;//按规律定义的文件名
	    userFile = singleUploadJCVO.getUserId() + SymbolConstants.UNDERLINE + Constants.NUM_1 + SymbolConstants.UNDERLINE + singleUploadJCVO.getLotteryCode() + SymbolConstants.UNDERLINE + strTime.substring(strTime.length()-6, strTime.length());
	    singleUploadJCVO.setSecondFileName(userFile);

	    String path = FileUtil.getFilePath(basePath, FileUtil.getExtensionName(originalFileName), UploadType.getUploadType(singleUploadJCVO.getUploadType()).getShortName(), singleUploadJCVO.getLotteryCode());
	    
	    File[] file = new File[2];
	    file[0]= new File(path);
	    file[1]= new File(path + singleUploadJCVO.getSecondFileName() + SymbolConstants.DOT + FileUtil.getExtensionName(originalFileName));
		return file;
	}
	
	public static void main(String[] args) {
		File file = new File("C:\\upload\\txt\\jczq\\bhcc\\201708\\241_1_300_124139.txt");
		System.out.println(file.getAbsolutePath());
		System.out.println(file.getPath());
	}
}
