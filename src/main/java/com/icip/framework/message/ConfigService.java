package com.icip.framework.message;

import org.apache.log4j.Logger;

import com.icip.controller.MessageController;
import com.icip.utils.encrypt.EncryptUtil;
import com.icip.utils.json.PropertiesUtil;

/**
 * 读取配置信息
 * 
 * @author lenovo
 * 
 */
public class ConfigService {
	private static final Logger logger = Logger
			.getLogger(MessageController.class);
	/**
	 * 针对手机名、手机和邮箱密码密钥加密
	 * 
	 * @param proKey
	 * @return
	 */
	public String getConfig(String proKey) {
		String value = PropertiesUtil.getInstance("/message.properties")
				.getValue(proKey);
		if (proKey.equals("SMS_NAME_VALUE") || proKey.equals("SMS_PWD_VALUE")
				|| proKey.equals("MAIL_SMTP_PASSWORD")
				|| proKey.equals("PUSH_USERNAME")
				|| proKey.equals("PUSH_PASSWORD")
				|| proKey.indexOf("PUSH_APPKEY")!=-1
				|| proKey.indexOf("PUSH_MASTERSECRET")!=-1) {
			try {
				value = EncryptUtil.decrypt(value);
			} catch (Exception e) {
				logger.error("-------->",e);
				//e.printStackTrace();
			}
		}
		return value;
	}

	public int getConfigByInteger(String smsMsgMaxlengthId) {
		return Integer.parseInt(PropertiesUtil.getInstance(
				"/message.properties").getValue(smsMsgMaxlengthId));
	}
	
}
