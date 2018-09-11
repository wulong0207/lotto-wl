package com.hhly.lotto.api.pc.controller.pay.v1_0;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;
import com.hhly.lotto.api.data.pay.v1_0.PayCommon;
import com.hhly.lotto.api.data.pay.v1_0.PayComponent;
import com.hhly.lotto.api.data.pay.v1_0.RechargeComponent;
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.PayConstants;
import com.hhly.skeleton.base.constants.PayConstants.ChannelTypeEnum;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.pay.vo.PayNotifyResultVO;
import com.hhly.skeleton.pay.vo.RechargeInputParamVO;
import com.hhly.skeleton.pay.vo.RechargeParamVO;

/**
 * @desc 充值中心
 * @author xiongJinGang
 * @date 2017年4月8日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RestController
@RequestMapping("/pc/v1.0/rechargeCenter")
public class RechargePCV10Controller extends BaseController {
	private static final Logger logger = Logger.getLogger(RechargePCV10Controller.class);

	@Resource
	private RechargeComponent rechargeComponent;
	@Resource
	private PayComponent payComponent;
	@Value("${now_pay_url}")
	private String now_pay_url;
	@Value("${lian_web_pay_url}")
	private String lianWebPayUrl;
	@Value("${lian_wap_pay_url}")
	private String lianWapPayUrl;

	/**  
	* 方法说明: 跳转到充值页面（获取红包列表，支付渠道等信息）
	* @auth: xiongJinGang
	* @param token 登录token 
	* @param orderCode 订单号
	* @throws Exception
	* @time: 2017年3月21日 下午4:39:59
	* @return: Object 
	*/
	@RequestMapping(value = "/toRecharge", method = RequestMethod.GET)
	public Object toRecharge(HttpServletRequest request) throws Exception {
		ResultBO<?> resultBO = rechargeComponent.toRecharge(request, PayConstants.TakenPlatformEnum.WEB);
		if (resultBO.isError()) {
			logger.info("充值请求失败：" + resultBO.getMessage());
		}
		return resultBO;
	}

	/**  
	* 方法说明: 获取充值红包
	* @auth: xiongJinGang
	* @param rechargeInputParam
	* @throws Exception
	* @time: 2017年5月18日 上午10:34:04
	* @return: Object 
	*/
	@RequestMapping(value = "/findRechargeRed", method = RequestMethod.POST)
	public Object findRechargeRed(@RequestBody RechargeInputParamVO rechargeInputParam) throws Exception {
		rechargeInputParam.setPlatformEnum(PayConstants.TakenPlatformEnum.WEB);
		ResultBO<?> resultBO = rechargeComponent.findRechargeRed(rechargeInputParam);
		if (resultBO.isError()) {
			logger.info("获取充值红包失败：" + resultBO.getMessage());
		}
		return resultBO;
	}

	/**  
	* 方法说明:  统一充值接口
	* @auth: xiongJinGang
	* @throws Exception
	* @time: 2017年4月8日 下午1:16:17
	* @return: Object 
	*/
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public Object recharge(@RequestBody RechargeInputParamVO rechargeInputParam, HttpServletRequest request) throws Exception {
		ResultBO<?> resultBO = rechargeComponent.recharge(rechargeInputParam, getIp(request), PayConstants.TakenPlatformEnum.WEB);
		if (resultBO.isError()) {
			logger.info("调用统一充值接口失败：" + resultBO.getMessage());
		}
		return resultBO;
	}

	/**  
	* 方法说明: 充值结果查询
	* @auth: xiongJinGang
	* @param rechargeParam 只需要token和transCode
	* @throws Exception
	* @time: 2017年5月23日 下午8:41:02
	* @return: Object 
	*/
	@RequestMapping(value = "/rechargeResult", method = RequestMethod.GET)
	public Object rechargeResult(RechargeParamVO rechargeParam) throws Exception {
		return rechargeComponent.rechargeResult(rechargeParam);
	}

	/**  
	* 方法说明: 支付同步返回（与前端沟通，充值同步直接回调到前端）
	* @throws Exception
	* @time: 2017年3月13日 下午3:53:10
	* @return: void 
	*/
	@RequestMapping(value = "/rechargeReturn", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView rechargeReturn(HttpServletRequest request) throws Exception {
		try {
			request.setCharacterEncoding("GBK");
			// 易宝PC
			Map<String, String> notifyMap = PayCommon.getReqParams(request);
			if (notifyMap.containsKey("r8_MP")) {
				String attach = notifyMap.get("r8_MP");// 充值的这个参数，存了recharge,同步返回地址
				if (ObjectUtil.isBlank(attach)) {
					return new ModelAndView(new RedirectView("http://cp.2ncai.com"));
				} else {
					String[] result = attach.split(",");
					return new ModelAndView(new RedirectView(result[1].replace("_", "=")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("http://cp.2ncai.com");
	}

	/**  
	* 方法说明: 支付异步回调（将返回结果组装成map）
	* @throws Exception
	* @time: 2017年3月6日 下午5:50:30
	* @return: void 
	*/
	@RequestMapping(value = "/{payWayCode}", method = { RequestMethod.GET, RequestMethod.POST })
	public void rechargeNotify(@PathVariable("payWayCode") String payWayCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> notifyMap = PayCommon.getNotify(payWayCode, request);

		if (!ObjectUtil.isBlank(notifyMap)) {
			PrintWriter out = response.getWriter();
			notifyMap.put(Constants.PAY_CHANNEL_TYPE_NAME, payWayCode);// 支付渠道类型，后台区分具体支付渠道
			logger.info(payWayCode + "充值，第三方支付异步返回【" + notifyMap.toString() + "】");
			ResultBO<?> resultBO = rechargeComponent.rechargeNotify(notifyMap);
			logger.info(payWayCode + "充值，hession接口处理异步回调返回【" + JSON.toJSONString(resultBO) + "】");
			if (resultBO.isOK()) {
				PayNotifyResultVO payNotifyResult = (PayNotifyResultVO) resultBO.getData();
				if (!ObjectUtil.isBlank(payNotifyResult)) {
					String message = payNotifyResult.getResponse();
					if (ObjectUtil.isBlank(message)) {
						message = PayCommon.returnByPayWay(payWayCode);
					}
					out.print(message);
					out.flush();
					out.close();
				}
			}
		} else {
			logger.info(payWayCode + "异步返回空");
		}

	}

	@RequestMapping(value = "/yeePay", method = { RequestMethod.GET, RequestMethod.POST })
	public void yeePay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> notifyMap = new HashMap<String, String>();
		PrintWriter out = response.getWriter();
		String reqStr = request.getQueryString();
		try {
			String decodeReqStr = URLDecoder.decode(reqStr, "GBK");
			String[] reqArry = decodeReqStr.split("&");
			for (String reqItem : reqArry) {
				String[] reqItemArray = reqItem.split("=");
				if (reqItemArray.length != 1) {
					if (reqItemArray.length == 2) {
						notifyMap.put(reqItemArray[0], reqItemArray[1]);
					}
				}
			}

			if (!ObjectUtil.isBlank(notifyMap)) {
				notifyMap.put(Constants.PAY_CHANNEL_TYPE_NAME, ChannelTypeEnum.YEEPAY_WEB.getChannel());// 支付渠道类型，后台区分具体支付渠道
				logger.info("易宝支付异步请求【" + notifyMap.toString() + "】");
				String attach = notifyMap.get("r8_MP");// 充值的这个参数，存了recharge,同步返回地址
				ResultBO<?> resultBO = null;
				if (!ObjectUtil.isBlank(attach) && attach.contains("recharge")) {
					// 调用充值的服务层
					resultBO = rechargeComponent.rechargeNotify(notifyMap);
				} else {
					// 调用支付的服务层
					resultBO = payComponent.payNotify(notifyMap);
				}
				logger.info("处理易宝异步回调返回【" + JSON.toJSONString(resultBO) + "】");
				if (resultBO.isOK()) {
					PayNotifyResultVO payNotifyResult = (PayNotifyResultVO) resultBO.getData();
					if (!ObjectUtil.isBlank(payNotifyResult)) {
						String message = payNotifyResult.getResponse();
						if (ObjectUtil.isBlank(message)) {
							message = "FAIL";
						}
						out.print(message);
						out.flush();
						out.close();
					}
				}
			}
		} catch (Exception e) {
			logger.error("处理异步返回异常", e);
		}
	}

	/**  
	* 方法说明: 支付同步返回（与前端沟通，支付同步直接回调到前端）
	* @throws Exception
	* @time: 2017年3月13日 下午3:53:10
	* @return: void 
	*/
	@RequestMapping(value = "/{noOrder}/{platform}/payNowReturn", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView payNowReturn(@PathVariable("noOrder") String noOrder, @PathVariable("platform") String platform) throws Exception {
		if (ObjectUtil.isBlank(noOrder) || ObjectUtil.isBlank(platform)) {
			logger.error("noOrder=" + noOrder + ",platform=" + platform);
			throw new Exception("参数错误");
		}
		// platform 1：本站WEB；2：本站WAP；3：Android客户端；4：IOS客户端；5：未知；
		String viewUrl = null;
		switch (platform) {
		case "2":
			viewUrl = now_pay_url + "sc.html#/recharge-result?tc=" + noOrder;
			break;
		case "3":
		case "4":
			viewUrl = now_pay_url + "czjg.html?tc=" + noOrder;
			break;
		default:
			break;
		}
		return new ModelAndView(new RedirectView(viewUrl));
	}

	/**  
	* 方法说明: 充值同步返回（与前端沟通，支付同步直接回调到前端）
	* @throws Exception
	* @time: 2017年3月13日 下午3:53:10
	* @return: void 
	*/
	@RequestMapping(value = "/{noOrder}/{platform}/{isActivity}/lianReturn", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView lianReturn(@PathVariable("noOrder") String noOrder, @PathVariable("platform") String platform, @PathVariable("isActivity") String isActivity) throws Exception {
		if (ObjectUtil.isBlank(noOrder) || ObjectUtil.isBlank(platform) || ObjectUtil.isBlank(isActivity)) {
			logger.error("noOrder=" + noOrder + ",platform=" + platform);
			throw new Exception("参数错误");
		}
		String viewUrl = null;
		// platform 1：本站WEB；2：本站WAP；3：Android客户端；4：IOS客户端；5：未知；
		if ("0".equals(isActivity)) {// 非活动
			switch (platform) {
			case "1":
				viewUrl = lianWebPayUrl + "page.html#/pay-success?tc=" + noOrder;
				break;
			case "2":
			case "3":
			case "4":
				viewUrl = lianWapPayUrl + "sc.html#/recharge-result?tc=" + noOrder;
				break;
			default:
				break;
			}
		} else {// 活动
			switch (platform) {
			case "1":
				viewUrl = lianWebPayUrl + "hd-recharge.html?pay-success=1&tc=" + noOrder;
				break;
			case "2":
			case "3":
			case "4":
				viewUrl = lianWapPayUrl + "sc.html#/recharge-result?tc=" + noOrder;
				break;
			default:
				break;
			}
		}
		return new ModelAndView(new RedirectView(viewUrl));
	}
}
