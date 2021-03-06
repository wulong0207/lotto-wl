package com.hhly.lotto.api.common.controller.pay.v1_0;

import java.io.PrintWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hhly.skeleton.base.constants.MessageCodeConstants;
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
import com.hhly.lotto.base.controller.BaseController;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.pay.vo.PayNotifyResultVO;
import com.hhly.skeleton.pay.vo.PayParamVO;
import com.hhly.skeleton.pay.vo.PayQueryParamVO;

@RestController
@RequestMapping("/v1.0/payCenter")
public class PayCommonV10Controller extends BaseController {
	private static final Logger logger = Logger.getLogger(PayCommonV10Controller.class);

	@Resource
	private PayComponent payComponent;
	@Value("${now_pay_url}")
	private String now_pay_url;
	@Value("${lian_wap_pay_url}")
	private String lianWapPayUrl;
	@Value("${lian_web_pay_url}")
	private String lianWebPayUrl;

	/**  
	* 方法说明: 支付结果查询，供客户端来获取支付结果
	* @auth: xiongJinGang
	* @param payParam 只需要token和transCode
	* @throws Exception
	* @time: 2017年5月23日 下午8:41:02
	* @return: Object 
	*/
	@RequestMapping(value = "/payResult", method = RequestMethod.GET)
	public Object payResult(PayParamVO payParam) throws Exception {
		return payComponent.payResult(payParam);
	}

	/**  
	* 方法说明: 查询第三方支付结果【供测试用】
	* @auth: xiongJinGang
	* @param payQueryParamVO
	* @param request
	* @throws Exception
	* @time: 2017年11月4日 上午10:33:47
	* @return: Object 
	*/
	@RequestMapping(value = "/payQuery", method = RequestMethod.POST)
	public Object payQuery(@RequestBody PayQueryParamVO payQueryParamVO, HttpServletRequest request) throws Exception {
		return payComponent.payQuery(payQueryParamVO, request);
	}

	/**  
	* 方法说明: 退款查询接口【供测试用】
	* @auth: xiongJinGang
	* @param payQueryParamVO
	* @param request
	* @throws Exception
	* @time: 2017年11月4日 上午10:57:22
	* @return: Object 
	*/
	@RequestMapping(value = "/refundQuery", method = RequestMethod.POST)
	public Object refundQuery(@RequestBody PayQueryParamVO payQueryParamVO, HttpServletRequest request) throws Exception {
		return payComponent.refundQuery(payQueryParamVO, request);
	}

	/**  
	* 方法说明: 支付同步返回（与前端沟通，支付同步直接回调到前端）
	* @throws Exception
	* @time: 2017年3月13日 下午3:53:10
	* @return: void 
	*/
	@RequestMapping(value = "/payReturn", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView payReturn(HttpServletRequest request) throws Exception {
		try {
			request.setCharacterEncoding("GBK");
			// 易宝PC
			Map<String, String> notifyMap = PayCommon.getReqParams(request);
			if (notifyMap.containsKey("r8_MP")) {
				String attach = notifyMap.get("r8_MP");// 充值的这个参数，存了recharge,同步返回地址
				if (ObjectUtil.isBlank(attach)) {
					return new ModelAndView(new RedirectView("http://cp.2ncai.com"));
				} else {
					return new ModelAndView(new RedirectView(attach.replace("_", "=")));
					// return new ModelAndView(new RedirectView(attach));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView(new RedirectView("http://cp.2ncai.com"));
	}

	/**  
	* 方法说明: 支付同步返回（与前端沟通，支付同步直接回调到前端）
	* @throws Exception
	* @time: 2017年3月13日 下午3:53:10
	* @return: void 
	*/
	@RequestMapping(value = "/{noOrder}/payNowReturn", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView payNowReturn(@PathVariable("noOrder") String noOrder) throws Exception {
		if (ObjectUtil.isBlank(noOrder)) {
			logger.error("noOrder=" + noOrder);
			throw new Exception("参数错误");
		}
		// platform 1：本站WEB；2：本站WAP；3：Android客户端；4：IOS客户端；5：未知；
		return new ModelAndView(new RedirectView(now_pay_url + "pay/result.html?tc=" + noOrder));
	}

	/**  
	* 方法说明: 支付异步回调（将返回结果组装成map）
	* @throws Exception
	* @time: 2017年3月6日 下午5:50:30
	* @return: void 
	*/
	@RequestMapping(value = "/{payWayCode}", method = { RequestMethod.GET, RequestMethod.POST })
	public void payNotify(@PathVariable("payWayCode") String payWayCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> notifyMap = PayCommon.getNotify(payWayCode, request);
		if (!ObjectUtil.isBlank(notifyMap)) {
			notifyMap.put(Constants.PAY_CHANNEL_TYPE_NAME, payWayCode);// 支付渠道类型，后台区分具体支付渠道
			logger.info(payWayCode + "支付，第三方支付异步返回【" + notifyMap.toString() + "】");
			ResultBO<?> resultBO = payComponent.payNotify(notifyMap);
			logger.info(payWayCode + "支付，hession接口处理异步回调返回【" + JSON.toJSONString(resultBO) + "】");

			if (resultBO.isOK()) {
				PrintWriter out = response.getWriter();
				PayNotifyResultVO payNotifyResultVO = (PayNotifyResultVO) resultBO.getData();
				if (!ObjectUtil.isBlank(payNotifyResultVO)) {
					String message = payNotifyResultVO.getResponse();
					if (ObjectUtil.isBlank(message)) {
						message = PayCommon.returnByPayWay(payWayCode);
					}
					out.print(message);
					out.flush();
					out.close();
				}
			}else{
				if (resultBO.getErrorCode().equals(MessageCodeConstants.PAY_RECHARGE_STATUS_FINISHED_ERROR_SERVICE)) {
					PrintWriter out = response.getWriter();
					out.print("SUCCESS");
					out.flush();
					out.close();
				}
			}
		} else {
			logger.info(payWayCode + "异步返回空");
		}
	}

	/**  
	* 方法说明: 申请退款【供内部测试用，不对外开放】
	* @auth: xiongJinGang
	* @param request
	* @param response
	* @throws Exception
	* @time: 2017年10月16日 下午5:04:39
	* @return: void 
	*/
	@RequestMapping(value = "/insideRefund", method = RequestMethod.POST)
	public Object insideRefund(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return payComponent.insideRefund(request);
	}

	/**  
	* 方法说明: 退款异步回调返回
	* @param request
	* @param response
	* @throws Exception
	* @time: 2017年3月6日 下午5:50:41
	* @return: void 
	*/
	@RequestMapping(value = "/refund/{payWayCode}", method = { RequestMethod.GET, RequestMethod.POST })
	public void refundNotify(@PathVariable("payWayCode") String payWayCode, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> notifyMap = PayCommon.getNotify(payWayCode, request);
		if (!ObjectUtil.isBlank(notifyMap)) {
			notifyMap.put(Constants.PAY_CHANNEL_TYPE_NAME, payWayCode);// 支付渠道类型，后台区分具体支付渠道
			logger.info(payWayCode + "退款异步返回【" + notifyMap.toString() + "】");
			ResultBO<?> resultBO = payComponent.refundNotify(notifyMap);
			logger.info(payWayCode + "退款hession接口处理异步回调返回【" + JSON.toJSONString(resultBO) + "】");

			if (resultBO.isOK()) {
				PrintWriter out = response.getWriter();
				PayNotifyResultVO payNotifyResultVO = (PayNotifyResultVO) resultBO.getData();
				if (!ObjectUtil.isBlank(payNotifyResultVO)) {
					String message = payNotifyResultVO.getResponse();
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

	/**  
	* 方法说明: 连连支付同步回调地址（因为连连支付是POST）
	* @auth: xiongJinGang
	* @param noOrder
	* @param platform
	* @throws Exception
	* @time: 2017年9月27日 下午4:54:31
	* @return: ModelAndView 
	*/
	@RequestMapping(value = "/{noOrder}/{platform}/lianPayReturn", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView lianPayReturn(@PathVariable("noOrder") String noOrder, @PathVariable("platform") String platform) throws Exception {
		if (ObjectUtil.isBlank(noOrder) || ObjectUtil.isBlank(platform)) {
			logger.error("noOrder=" + noOrder + ",platform=" + platform);
			throw new Exception("参数错误");
		}
		// platform 1：本站WEB；2：本站WAP；3：Android客户端；4：IOS客户端；5：未知；
		String viewUrl = null;
		switch (platform) {
		case "1":
			viewUrl = lianWebPayUrl + "uc/record?tc=" + noOrder;
			break;
		case "2":
		case "3":
		case "4":
			viewUrl = lianWapPayUrl + "pay/result.html?tc=" + noOrder;
			break;
		default:
			break;
		}
		return new ModelAndView(new RedirectView(viewUrl));
	}
}
