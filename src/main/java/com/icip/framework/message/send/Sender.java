package com.icip.framework.message.send;

import java.util.Map;



/**
 * 功能: 消息发送器接口
 * 
 */
public interface Sender {

	/**
	 * @param title
	 *            消息的标题
	 * @param content
	 *            消息的内容
	 * @param to
	 *            消息的接收人
	 * @throws CheckException
	 */

	public void sender(String title, String content, String[] to,Map<String,String> params)
			throws Exception;
	
	/**
	  * @Description: 语音发送（VoiceSenderAdapter实现即可）
	  * @param 参数
	  * @return 返回值
	  * @throws 异常
	  * @author 肖伟
	 */
	public void sender(String bmtCode, String[] to, Map<String, String> params)
			throws Exception;
	

}
