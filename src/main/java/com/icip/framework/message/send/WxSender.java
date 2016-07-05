package com.icip.framework.message.send;


/**
 * 微信推送
 * 
 */
public interface WxSender {

	/**
	 * 微信群发
	 * 
	 * @param from
	 *            发件人
	 * @param to
	 *            多个收件人
	 * @param subject
	 *            微信主题
	 * @param body
	 *            内容
	 * @throws Exception
	 *             抛出此异常
	 */
	void sendWxMessage(String from, String[] to, String subject, String msgBody)
			throws Exception;

}
