package com.icip.framework.message.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import com.icip.framework.common.exception.AppException;
import com.icip.framework.message.BasePropertyID;
import com.icip.framework.message.ConfigService;
import com.icip.framework.message.PushService;
import com.icip.framework.message.send.PushSender;
import com.icip.utils.encrypt.EncryptUtil;

/**
 * IOS没有消息机制，如果安卓要调用请参考PushService
 * 
 * @author lenovo
 * 
 *         YW
 */
public class PushSenderImpl implements PushSender, InitializingBean {

	/**
	 * Logger for this class
	 */
	private static final Logger LOG = Logger.getLogger(PushSenderImpl.class);

	private ConfigService configService;

	/** UAT */
	private String pushAppkey_UAT_C;
	private String pushMastersecret_UAT_C;
	private String pushAppkey_UAT_P;
	private String pushMastersecret_UAT_P;
	/** SIT */
	private String pushAppkey_TEST_C;
	private String pushMastersecret_TEST_C;
	private String pushAppkey_TEST_P;
	private String pushMastersecret_TEST_P;
	/** IOS 企业版(special) */
	private String pushAppkey_S_IOS_C;
	private String pushMastersecret_S_IOS_C;
	private String pushAppkey_S_IOS_P;
	private String pushMastersecret_S_IOS_P;

	private String pushUsername;
	private String pushPassword;
	private String pushHostname;
	private Integer pushMaxretryTimes;
	
	private ClientConfig config;

	private int osType = -1;

	private List<JPushClient> pushClients;

	/**
	 * 普通广播
	 */
	@Override
	public void sendPushMessage(String alert) throws Exception {
		PushPayload payload = PushService.buildPushObject_all_all_alert(alert);
		_sendPishMessage(payload);
	}

	/**
	 * 通知
	 */
	@Override
	public void sendPushMessage(List<String> to, String title, String msgConent, Map<String, String> extras) throws Exception {
		sendPushMessage(null, to, title, msgConent, extras, null);
	}

	@Override
	public void sendPushMessage(String title, String msgConent, Map<String, String> extras, String sound) throws Exception {
		sendPushMessage(null, null, title, msgConent, extras, sound);
	}

	/**
	 * 通知带类型
	 */
	@Override
	public void sendPushMessage(String type, List<String> to, String title, String alertConent, Map<String, String> extras, String sound) throws Exception {
		PushPayload payload = PushService.buildPushNothObject(type, to, title, alertConent, extras, sound);
		_sendPishMessage(payload);
	}

	private void _sendPishMessage(PushPayload payload) throws Exception {
		if (pushClients.isEmpty())
			throw new AppException("", new Exception("组装推送client失败"));

		boolean flag = false;
		for (JPushClient client : pushClients) {
			try {
				PushResult result_P = client.sendPush(payload);
				LOG.info("Got result - " + result_P);
				flag = true;
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}

		if (flag == false) {
			throw new Exception("服务器错误响应", new Exception("服务器错误响应"));
		}
	}

	/**
	 * 通知不带声音
	 */
	@Override
	public void sendPushMessage(String type, List<String> to, String title, String alertConent, Map<String, String> extras) throws Exception {
		sendPushMessage(type, to, title, alertConent, extras, null);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initPublicParams();
		initSITParams();
		initUATParams();
		
		switch (osType) {
		case BasePropertyID.OS_TYPE_SIT:
			initSITParams();
			pushClients.add(new JPushClient(pushMastersecret_TEST_C, pushAppkey_TEST_C, pushMaxretryTimes, null, config));
			pushClients.add(new JPushClient(pushMastersecret_TEST_P, pushAppkey_TEST_P, pushMaxretryTimes, null, config));
			break;
		case BasePropertyID.OS_TYPE_UAT: // 生产环境推送包含企业版
			initUATParams();
			pushClients.add(new JPushClient(pushMastersecret_UAT_C, pushAppkey_UAT_C, pushMaxretryTimes, null, config));
			pushClients.add(new JPushClient(pushMastersecret_UAT_P, pushAppkey_UAT_P, pushMaxretryTimes, null, config));
			pushClients.add(new JPushClient(pushMastersecret_S_IOS_C, pushAppkey_S_IOS_C, pushMaxretryTimes, null, config));
			pushClients.add(new JPushClient(pushMastersecret_S_IOS_P, pushAppkey_S_IOS_P, pushMaxretryTimes, null, config));
			break;
		default:
			throw new AppException("", new Exception("未设置环境版本 SIT-1， UAT-2"));
		}
	}

	private void initUATParams() {
		pushAppkey_UAT_C = getConfigService().getConfig(BasePropertyID.PUSH_APPKEY_UAT_C);
		pushMastersecret_UAT_C = getConfigService().getConfig(BasePropertyID.PUSH_MASTERSECRET_UAT_C);
		pushAppkey_UAT_P = getConfigService().getConfig(BasePropertyID.PUSH_APPKEY_UAT_P);
		pushMastersecret_UAT_P = getConfigService().getConfig(BasePropertyID.PUSH_MASTERSECRET_UAT_P);
		//企业版IOS
		pushAppkey_S_IOS_C = getConfigService().getConfig(BasePropertyID.PUSH_APPKEY_IOS_S_C);
		pushMastersecret_S_IOS_C = getConfigService().getConfig(BasePropertyID.PUSH_MASTERSECRET_IOS_S_C);
		pushAppkey_S_IOS_P = getConfigService().getConfig(BasePropertyID.PUSH_APPKEY_IOS_S_P);
		pushMastersecret_S_IOS_P = getConfigService().getConfig(BasePropertyID.PUSH_MASTERSECRET_IOS_S_P);
	}

	private void initSITParams() {
		pushAppkey_TEST_C = getConfigService().getConfig(BasePropertyID.PUSH_APPKEY_TEST_C);
		pushMastersecret_TEST_C = getConfigService().getConfig(BasePropertyID.PUSH_MASTERSECRET_TEST_C);
		pushAppkey_TEST_P = getConfigService().getConfig(BasePropertyID.PUSH_APPKEY_TEST_P);
		pushMastersecret_TEST_P = getConfigService().getConfig(BasePropertyID.PUSH_MASTERSECRET_TEST_P);
	}

	private void initPublicParams() {
		this.osType = Integer.valueOf(getConfigService().getConfig(BasePropertyID.APP_OS_TYPE));
		this.pushHostname = getConfigService().getConfig(BasePropertyID.PUSH_HOSTNAME);
		this.pushMaxretryTimes = getConfigService().getConfigByInteger(BasePropertyID.PUSH_MAXRETRY_TIMES);
		this.pushUsername = getConfigService().getConfig(BasePropertyID.PUSH_USERNAME);
		this.pushPassword = getConfigService().getConfig(BasePropertyID.PUSH_PASSWORD);
		this.config = ClientConfig.getInstance();
		this.config.setPushHostName(pushHostname);
		if (pushClients == null)
			this.pushClients = new ArrayList<JPushClient>();
		else
			this.pushClients.clear();
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	public String getPushUsername() {
		return pushUsername;
	}

	public void setPushUsername(String pushUsername) {
		this.pushUsername = pushUsername;
	}

	public String getPushPassword() {
		return pushPassword;
	}

	public void setPushPassword(String pushPassword) {
		this.pushPassword = pushPassword;
	}

	public static void main(String[] args) throws Exception {
		// String str =
		// EncryptUtil.decrypt("0a21fa96d7c391f6c00f559f7b28a56d494bd15870e2f1ad70b06e46ae2c9dcb");
		// System.err.println(str);
		// 业主贝贝 UAT AppKey
		
		 String str = EncryptUtil.encrypt("91e94cb300336eb054375705");
		 System.err.println(str); // Master Secret str =
		 System.err.println(EncryptUtil.encrypt("f35ac47720dd4ddf5337235a"));
//		 * 
//		 * str = EncryptUtil.encrypt("1cf1765d451edd28bd7cadfa");
//		 * System.err.println(str); // Master Secret str =
//		 * EncryptUtil.encrypt("0a4e5f26d8961fdb9e2f1fac"); System.err.println(str);
//		 * 
//		 * str = EncryptUtil.encrypt("a577bf0702a13ae59e1e19f2");
//		 * System.err.println(str); // Master Secret str =
//		 * EncryptUtil.encrypt("07c3fbc49e12eceed4e42cdd"); System.err.println(str);
//		 * 
//		 * str = EncryptUtil.encrypt("8ed890644192a32d5becdcaf");
//		 * System.err.println(str); // Master Secret str =
//		 * EncryptUtil.encrypt("a3c4064deffeffbb44434096"); System.err.println(str);
		 
	}
}
