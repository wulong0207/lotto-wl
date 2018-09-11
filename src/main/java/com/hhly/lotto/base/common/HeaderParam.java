/**
 * @see     headerParam参数
 * @author  scott
 * @date    2017-12-07
 * @company 益彩网络科技有限公司
 * @version v1.0
 */
package com.hhly.lotto.base.common;


public class HeaderParam {
    
	/** 渠道ID **/
	public String channelId ;
	
	/** 平台ID  1--PC 2--H5 3--Android 4--IOS **/
	public Integer platformId ;
	
	/** 应用版本ID **/
	public String versionId ;
	
	/** 手机设备ID PC,h5没有此Id **/
	public String deviceId ;
	
	
	public HeaderParam(String channelId, Integer platformId, String versionId, String deviceId) {
		super();
		this.channelId = channelId;
		this.platformId = platformId;
		this.versionId = versionId;
		this.deviceId = deviceId;
	}


	@Override
	public String toString() {
		return "HeaderParam [channelId=" + channelId + ", platformId=" + platformId + ", versionId=" + versionId
				+ ", deviceId=" + deviceId + "]";
	}


	public String getChannelId() {
		return channelId;
	}


	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	public Integer getPlatformId() {
		return platformId;
	}


	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}


	public String getVersionId() {
		return versionId;
	}


	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}


	public String getDeviceId() {
		return deviceId;
	}


	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	
	
	
	


}
