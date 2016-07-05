package com.icip.framework.message.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.icip.framework.common.exception.AppException;
import com.icip.framework.message.BasePropertyID;
import com.icip.framework.message.ConfigService;
import com.icip.framework.message.send.SmsSender;
import com.icip.framework.wx.util.SimpleHttpReq;
import com.icip.utils.string.StringUtil;

/**
 * 功能: 短信发送服务
 * 
 */
public class SmsSenderImpl implements SmsSender, InitializingBean {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SmsSenderImpl.class);

	private ConfigService configService;

	private String smsUrl;
	private String smsNameId;
	private String smsNameValue;
	private String smsPwdId;
	private String smsPwdValue;
	private String smsSignId;
	private String smsSignValue;
	private String smsTypeId;
	private String smsTypeValue;
	private String smsExtnoId;
	private String smsExtnoValue;
	private String smsContentId;
	private String smsMobileId;
	private int maxLength = 300; // 默认值

	@Override
	public void sendSms(String mobilePhone, String message, String sign) throws Exception {
		send(message, mobilePhone, sign);

	}

	@Override
	public void sendSms(String[] mobilePhones, String message, String sign) throws Exception {
		mobilePhones = mobileFilter(mobilePhones);
		if (ArrayUtils.isEmpty(mobilePhones)) {
			throw new Exception("手机号码不能为空");
		}
		for (String phone : mobilePhones) {
			sendSms(phone, message, sign);
		}
	}

	/**
	 * 如果超过短信的长度，则分成几条发
	 * @param content
	 * @param phoneNo
	 * @return
	 * @throws Exception
	 */
	private String send(String content, String phoneNo, String sign) throws Exception {
		if (StringUtils.isEmpty(content)) {
			throw new AppException("", "短信内容为空");
		}
		if (StringUtils.isEmpty(phoneNo)) {
			throw new AppException("", "手机号为空");
		}
		// 如果服务未准备好，先初始化
		if (!isReady()) {
			try {
				init();
				// 初始化后，服务仍未准备好
				if (!isReady()) {
					throw new AppException("", "短信服务初始化异常");
				}
			} catch (Exception e) {
				logger.error("send(String, String)", e);
				throw new AppException("", "短信服务初始化异常");
			}
		}

		// 如果超过最大长度，则分成几条发送
		int count = content.length() / maxLength;
		int reminder = content.length() % maxLength;
		StringBuffer result = new StringBuffer();
		if (content.length() < maxLength) {
			doSend(content, phoneNo, sign);
		} else {
			if (reminder != 0) {
				count += 1;
			}
			int i = 0;
			while (count > i) {
				int x = ((i + 1) * maxLength);
				if (x > content.length()) {
					result.append(doSend(content.substring(i * maxLength, content.length()), phoneNo, sign));
					break;
				} else {
					result.append(doSend(content.substring(i * maxLength, x), phoneNo, sign));
				}
				i++;
			}
		}
		return result.toString();
	}

	/*
	 * public static void main(String[] args) { String content =
	 * "各位同事：自4 月1 日起，所有员工考勤将采用ERP 注册发。使用方法：在登录ERP 后，在首页点“考勤卡”，然后点“上班注册”或“下班注册”按钮即可，需要说明的是，考勤时间（即ERP 首页上显示的时间）为公司ERP 服务器的时间，而非个人的本机时间。特此通知"
	 * ; int maxLength = 100; int count = content.length() / maxLength; int
	 * reminder = content.length() % maxLength; if (reminder != 0) { count += 1; }
	 * int i = 0; while (count > i) { int x = ((i + 1) * maxLength); if (x >
	 * content.length()) { System.err.println(content.substring(i * maxLength,
	 * content.length())); break; } else { System.err.println(content.substring(i
	 * * maxLength, x)); } i++; } }
	 */

	private boolean isReady() {
		return !(smsUrl == null || smsNameId == null || smsNameValue == null || smsPwdId == null || smsSignId == null || smsSignValue == null || smsTypeId == null
				|| smsTypeValue == null || smsExtnoId == null || smsContentId == null || smsMobileId == null || maxLength <= 0);
	}

	/**
	 * @param content
	 * @param phoneNo
	 * @return
	 * @throws Exception
	 */
	private String doSend(String content, String phoneNo, String sign) throws Exception {
		// 使用httpclient模拟http请求
		HttpClient client = new HttpClient();
		// 设置参数编码
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");

		PostMethod method = new PostMethod(smsUrl);

		if (!StringUtil.isEmpty(sign)) {
			smsSignValue = sign;
		}
		method.addParameter(smsNameId, smsNameValue);
		method.addParameter(smsPwdId, smsPwdValue);
		method.addParameter(smsSignId, smsSignValue);
		method.addParameter(smsTypeId, smsTypeValue);
		method.addParameter(smsExtnoId, smsExtnoValue);
		method.addParameter(smsContentId, content);
		method.addParameter(smsMobileId, phoneNo);

		BufferedReader br = null;
		String reponse = null;
		try {
			int returnCode = client.executeMethod(method);

			if (returnCode != org.apache.commons.httpclient.HttpStatus.SC_OK) {
				// 请求出错
				throw new AppException("", "短信接口异常");
			}
			br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
			reponse = br.readLine();
			String responseCode = reponse.substring(0, reponse.indexOf(','));
			if (!"0".equals(responseCode)) {
				throw new AppException("", getResponseMsg(responseCode));
			}
		} catch (Exception e) {
			logger.error("doSend(String, String)", e);
			throw new AppException("", "未知异常");
		} finally {
			method.releaseConnection();
			if (br != null)
				try {
					br.close();
				} catch (Exception e1) {
					logger.error("doSend(String, String)", e1);
					throw new AppException("", "未知异常");
				}
		}

		return reponse;
	}

	public void afterPropertiesSet() throws Exception {
		// 初始化
		init();
	}

	private void init() throws Exception {
		smsUrl = getConfigService().getConfig(BasePropertyID.SMS_URL);
		smsNameId = getConfigService().getConfig(BasePropertyID.SMS_NAME_ID);
		smsNameValue = getConfigService().getConfig(BasePropertyID.SMS_NAME_VALUE);
		smsPwdId = getConfigService().getConfig(BasePropertyID.SMS_PWD_ID);
		smsPwdValue = getConfigService().getConfig(BasePropertyID.SMS_PWD_VALUE);
		smsSignId = getConfigService().getConfig(BasePropertyID.SMS_SIGN_ID);
		smsSignValue = getConfigService().getConfig(BasePropertyID.SMS_SIGN_VALUE);
		smsTypeId = getConfigService().getConfig(BasePropertyID.SMS_TYPE_ID);
		smsTypeValue = getConfigService().getConfig(BasePropertyID.SMS_TYPE_VALUE);
		smsExtnoId = getConfigService().getConfig(BasePropertyID.SMS_EXTNO_ID);
		smsExtnoValue = getConfigService().getConfig(BasePropertyID.SMS_EXTNO_VALUE);
		smsMobileId = getConfigService().getConfig(BasePropertyID.SMS_MOBILE_ID);
		smsContentId = getConfigService().getConfig(BasePropertyID.SMS_CONTENT_ID);

		maxLength = getConfigService().getConfigByInteger(BasePropertyID.SMS_MSG_MAXLENGTH);

	}

	private String getResponseMsg(String code) {
		String msg = "未知返回值:" + code;

		if ("0".equals(code)) {
			msg = "提交成功";
		} else if ("1".equals(code)) {
			msg = "含有敏感词汇";
		} else if ("2".equals(code)) {
			msg = "余额不足";
		} else if ("3".equals(code)) {
			msg = "没有号码";
		} else if ("4".equals(code)) {
			msg = "包含sql语句";
		} else if ("10".equals(code)) {
			msg = "账号不存在/用户名或密码错误";
		} else if ("11".equals(code)) {
			msg = "账号注销";
		} else if ("12".equals(code)) {
			msg = "账号停用";
		} else if ("13".equals(code)) {
			msg = "IP鉴权失败";
		} else if ("14".equals(code)) {
			msg = "格式错误";
		} else if ("-1".equals(code)) {
			msg = "系统异常";
		}
		return msg;
	}

	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}

	private String[] mobileFilter(String[] mobilePhones) {
		int osType = new Integer(configService.getConfig(BasePropertyID.APP_OS_TYPE));
		return 2 == osType ? mobilePhones : getGroupMobiles(mobilePhones);
	}

	@SuppressWarnings("unchecked")
	private String[] getGroupMobiles(String[] mobilePhones) {
		String jsonStr = null;
		try {
			jsonStr = SimpleHttpReq.get(configService.getConfig(BasePropertyID.GROUP_MOBILES));
		} catch (IOException e) {
			logger.error("-------->", e);
			//e.printStackTrace();
		}

		if (StringUtil.isEmpty(jsonStr))
			return null;
		
		JSONObject json = JSONObject.fromObject(jsonStr);
		
		Map<String, String> mobiles = (Map<String, String>)json.get("groupMobile");
		if(mobiles == null)
			return null;
		
		Set<String> ks = mobiles.keySet();
		int randomKey = new Random().nextInt(mobiles.size());
		
		for(int i=0;i<mobilePhones.length;i++) {
			if(!ks.contains(mobilePhones[i])) {
				Object o = mobiles.keySet().toArray()[randomKey];
				logger.debug("======================== 随机手机号：" + o.toString() +  "===========================");
				mobilePhones[i] = o.toString();
			}
		}
		
		return mobilePhones;
	}

}
