package com.icip.framework.message.send;

import java.util.Map;


/**
 * 发送语音
 * 
 */
public interface VoiceSender {

	/**
	 * @param title
	 *            消息的标题
	 * @param content
	 *            消息的内容
	 * @param to
	 *            消息的接收人
	 * @throws CheckException
	 */
	public void senderVoice(String bmtCode, String[] to, Map<String, String> params) throws Exception;
	

}
