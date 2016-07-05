package com.icip.framework.message;

import com.icip.utils.string.StringUtil;

/**
 * 发送消息参数
 * 
 * @author lenovo
 * 
 */
public class BasePropertyID {

	// public static final String TYPE_MAIL = "mail";
	// public static final String TYPE_SMS = "sms";
	// public static final String TYPE_PUSH = "push";
	// public static final String TYPE_WX = "wx";

	public static final String SYSTEM_BCC = "BCC";
	public static final String SYSTEM_ME = "ME";
	public static final String SYSTEM_MS = "MS";
	public static final String SYSTEM_PDS = "PDS";
	public static final String SYSTEM_US = "US";
	public static final String SYSTEM_BS = "BS";
	public static final String SYSTEM_PS = "PS";
	public static final String SYSTEM_BMS = "BMS";
	/** 邮箱 */
	public static final String TYPE_MAIL = "1";
	/** 短信 */
	public static final String TYPE_SMS = "2";
	/** 极光推送 */
	public static final String TYPE_PUSH = "3";
	/** 微信 */
	public static final String TYPE_WX = "4";
	/** 语音验证码 */
	public static final String TYPE_VOICE = "5";
	
	public static final String MAIL_SMTP_USER = "MAIL_SMTP_USER";
	public static final String MAIL_SMTP_HOST = "MAIL_SMTP_HOST";
	public static final String MAIL_SMTP_PORT = "MAIL_SMTP_PORT";
	public static final String MAIL_SMTP_CONNECTIONTIMEOUT = "MAIL_SMTP_CONNECTIONTIMEOUT";
	public static final String MAIL_SMTP_TIMEOUT = "MAIL_SMTP_TIMEOUT";
	public static final String MAIL_SMTP_FROM = "MAIL_SMTP_FROM";
	public static final String MAIL_SMTP_AUTH = "MAIL_SMTP_AUTH";
	public static final String MAIL_SMTP_PASSWORD = "MAIL_SMTP_PASSWORD";
	public static final String MAIL_SMTP_FROMNAME = "MAIL_SMTP_FROMNAME";

	public static final String SMS_URL = "SMS_URL";
	public static final String SMS_NAME_ID = "SMS_NAME_ID";
	public static final String SMS_NAME_VALUE = "SMS_NAME_VALUE";
	public static final String SMS_PWD_ID = "SMS_PWD_ID";
	public static final String SMS_PWD_VALUE = "SMS_PWD_VALUE";
	public static final String SMS_MOBILE_ID = "SMS_MOBILE_ID";
	public static final String SMS_CONTENT_ID = "SMS_CONTENT_ID";
	public static final String SMS_SIGN_ID = "SMS_SIGN_ID";
	public static final String SMS_SIGN_VALUE = "SMS_SIGN_VALUE";
	public static final String SMS_TYPE_ID = "SMS_TYPE_ID";
	public static final String SMS_TYPE_VALUE = "SMS_TYPE_VALUE";
	public static final String SMS_EXTNO_ID = "SMS_EXTNO_ID";
	public static final String SMS_EXTNO_VALUE = "SMS_EXTNO_VALUE";
	public static final String SMS_MSG_MAXLENGTH = "SMS_MSG_MAXLENGTH";

	//阿里云 语音验证码
	public static final String ALYURL="ALYURL";
	public static final String ALYAPPKEY="ALYAPPKEY";
	public static final String ALYAPPSECRET="ALYAPPSECRET";
	public static final String ALYVOICEPHONE="ALYVOICEPHONE";
	
	
	// 生产 — 缴费
	public static final String PUSH_APPKEY_UAT_C = "PUSH_APPKEY_UAT_C";
	public static final String PUSH_MASTERSECRET_UAT_C = "PUSH_MASTERSECRET_UAT_C";
	// 生产 — 收费
	public static final String PUSH_APPKEY_UAT_P = "PUSH_APPKEY_UAT_P";
	public static final String PUSH_MASTERSECRET_UAT_P = "PUSH_MASTERSECRET_UAT_P";
	// 测试 缴费
	public static final String PUSH_APPKEY_TEST_C = "PUSH_APPKEY_TEST_C";
	public static final String PUSH_MASTERSECRET_TEST_C = "PUSH_MASTERSECRET_TEST_C";
	// 测试 收费
	public static final String PUSH_APPKEY_TEST_P = "PUSH_APPKEY_TEST_P";
	public static final String PUSH_MASTERSECRET_TEST_P = "PUSH_MASTERSECRET_TEST_P";

	// 业主贝贝企业 — 缴费（企业版）
	public static final String PUSH_APPKEY_IOS_S_C = "PUSH_APPKEY_IOS_S_C";
	public static final String PUSH_MASTERSECRET_IOS_S_C = "PUSH_MASTERSECRET_IOS_S_C";
	// 物业贝贝企业 — 收费（企业版）
	public static final String PUSH_APPKEY_IOS_S_P = "PUSH_APPKEY_IOS_S_P";
	public static final String PUSH_MASTERSECRET_IOS_S_P = "PUSH_MASTERSECRET_IOS_S_P";

	public static final String PUSH_HOSTNAME = "PUSH_HOSTNAME";
	public static final String PUSH_MAXRETRY_TIMES = "PUSH_MAXRETRY_TIMES";
	public static final String PUSH_USERNAME = "PUSH_USERNAME";
	public static final String PUSH_PASSWORD = "PUSH_PASSWORD";
	public static final String PUSH_APNS_PRODUCTION = "PUSH_APNS_PRODUCTION";

	public static final String WX_MPID = "WX_MPID";
	public static final String WX_OPENID = "WX_OPENID";
	public static final String WX_APPID = "WX_APPID";
	public static final String WX_APPSECRET = "WX_APPSECRET";
	public static final String WX_TOKEN = "WX_TOKEN";
	public static final String WX_AESKEY = "WX_AESKEY";
	public static final String WX_ACCESSTOKEN = "WX_ACCESSTOKEN";
	public static final String WX_MEDIAID = "WX_MEDIAID";
	public static final String WX_MSGSING = "WX_MSGSING";
	public static final String WX_TIMESTAMP = "WX_TIMESTAMP";
	public static final String WX_ECHOSTR = "WX_ECHOSTR";
	public static final String WX_NONCE = "WX_NONCE";
	public static final String WX_TEMPLATEID = "WX_TEMPLATEID";

	public static final String APP_OS_TYPE = "app.os.type";
	public static final int OS_TYPE_SIT = 1;
	public static final int OS_TYPE_UAT = 2;
	public static final String GROUP_MOBILES = "group.mobiles.json";

	/**
	 * 检查系统
	 * 
	 * @param systemID
	 * @return
	 */
	public static boolean checkSystemID(String systemID) {
		if (StringUtil.isEmpty(systemID)) {
			return false;
		}
		if (systemID.equals(BasePropertyID.SYSTEM_BCC)) {
			return true;
		}
		if (systemID.equals(BasePropertyID.SYSTEM_BMS)) {
			return true;
		}
		if (systemID.equals(BasePropertyID.SYSTEM_BS)) {
			return true;
		}
		if (systemID.equals(BasePropertyID.SYSTEM_MS)) {
			return true;
		}
		if (systemID.equals(BasePropertyID.SYSTEM_PDS)) {
			return true;
		}
		if (systemID.equals(BasePropertyID.SYSTEM_PS)) {
			return true;
		}
		if (systemID.equals(BasePropertyID.SYSTEM_US)) {
			return true;
		} else {
			return true;
		}
	}
}
