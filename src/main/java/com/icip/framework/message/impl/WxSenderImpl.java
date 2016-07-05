package com.icip.framework.message.impl;

import org.springframework.beans.factory.InitializingBean;

import com.icip.framework.common.exception.AppException;
import com.icip.framework.message.BasePropertyID;
import com.icip.framework.message.ConfigService;
import com.icip.framework.message.send.WxSender;
import com.icip.framework.wx.core.WxApi;
import com.icip.framework.wx.core.WxApiImpl;
import com.icip.framework.wx.vo.MPAct;
import com.icip.framework.wx.vo.OutPutMsg;

public class WxSenderImpl implements WxSender, InitializingBean {

	private ConfigService configService;

	protected String mpId;
	protected String appId;
	protected String appSecret;
	protected String token;
	protected String openId;
	protected String aesKey;

	protected String templateId;
	protected String mediaId;
	protected String accessToken;

	protected String msgSing;
	protected String timestamp;
	protected String echostr;
	protected String nonce;

	protected MPAct mpAct;

	@Override
	public void sendWxMessage(String from, String[] to, String subject,
			String msgBody) throws Exception {
		WxApi wxApi = new WxApiImpl(mpAct);
		OutPutMsg msg = new OutPutMsg();
		msg.setContent(msgBody);
		msg.setTitle(subject);
		msg.setMsgType(WxApi.TEXT);
		try {
			if (to.length > 0) {
				for (int i = 0; i < to.length; i++) {
					msg.setToUserName(to[i]);
					wxApi.sendCustomerMsg(msg);
				}
			} else {
				wxApi.sendAll(msg);
			}
		} catch (Exception e) {
			throw new AppException("", "发送失败！");
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		mpId = getConfigService().getConfig(BasePropertyID.WX_MPID);
		appId = getConfigService().getConfig(BasePropertyID.WX_APPID);
		appSecret = getConfigService().getConfig(BasePropertyID.WX_APPSECRET);
		token = getConfigService().getConfig(BasePropertyID.WX_TOKEN);
		openId = getConfigService().getConfig(BasePropertyID.WX_OPENID);
		aesKey = getConfigService().getConfig(BasePropertyID.WX_AESKEY);

		templateId = getConfigService().getConfig(BasePropertyID.WX_TEMPLATEID);
		mediaId = getConfigService().getConfig(BasePropertyID.WX_MEDIAID);
		accessToken = getConfigService().getConfig(
				BasePropertyID.WX_ACCESSTOKEN);

		mpAct = new MPAct();
		mpAct.setMpId(mpId);
		mpAct.setAppId(appId);
		mpAct.setAppSecret(appSecret);
		mpAct.setToken(token);
		mpAct.setAESKey(aesKey);
		if (!accessToken.equals("NOT") || !accessToken.isEmpty()) {
			mpAct.setAccessToken(accessToken);
			mpAct.setExpiresIn(7000 * 1000 + System.currentTimeMillis());
		}
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

}
