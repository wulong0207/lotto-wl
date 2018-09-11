package com.hhly.lotto.api.pc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import com.hhly.lotto.base.common.Constants;
import com.hhly.lotto.base.util.SecurityUtil;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.PropertyUtil;

/**
 * 
 * 会员查询url http://192.168.69.34:8080/lotto/agent/user/seach
 * 代理充值url http://192.168.69.34:8080/lotto/agent/pay/recharge
 * @author YiJian
 * @date 2017年7月25日 上午10:03:13
 * @company 深圳益彩网络科技有限公司
 * @version v1.0
 */
public class AgentTestDemo {
	
	public static void main(String[] args) {
		//http://192.168.74.169/api/lotto/pc/home/article
		String url = "http://127.0.0.1:8080/lotto/agent/pay/recharge";
		Map< String, String> c = new HashMap<>();
		c.put("accountName", "roseking");
		c.put("amount", "100");
		c.put("agentCode", "755");
		c.put("agentTradeNo", "CZ0011");
		String json = JsonUtil.object2Json(c);
		String agentSign = SecurityUtil.encryptRSAPublic(json, PropertyUtil.getPropertyValue(Constants.SYS_PROPERTIES_PATH,"rsa_publickey"));
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("charset", "UTF-8"));
		params.add(new BasicNameValuePair("agentPaySign", agentSign));
		//params.add(new BasicNameValuePair("agentUserSign", agentSign));
		String result = postPramaList(params,url);
		System.out.println(result);
	}	
	
	
	public static String postPramaList(List<NameValuePair> list, String url) {
		HttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		BufferedReader br = null;
		try {
			UrlEncodedFormEntity formEntiry = new UrlEncodedFormEntity(list, HTTP.UTF_8);
			// 设置请求参数
			post.setEntity(formEntiry);
			// 发起交易
			HttpResponse resp = httpClient.execute(post);
			System.out.println("请求[" + url + "] " + resp.getStatusLine());
			int ret = resp.getStatusLine().getStatusCode();
			if (ret == HttpStatus.SC_OK) {
				// 响应分析
				HttpEntity entity = resp.getEntity();
				br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				StringBuffer responseString = new StringBuffer();
				String result = br.readLine();
				while (result != null) {
					responseString.append(result);
					result = br.readLine();
				}
				return responseString.toString();
			} else {
				System.out.println("retcode:" + ret);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
	}
}
