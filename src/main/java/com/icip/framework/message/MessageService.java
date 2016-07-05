package com.icip.framework.message;

import java.util.Map;

import com.icip.framework.message.bean.SysBaseMessageTemplet;

/**
 * 功能: 系统消息发送服务
 * 
 * @author lenovo
 * 
 *         YW
 */
public interface MessageService {

	/**
	 * 根据消息模板表中的消息编号取得消息模板，填充，发送
	 * 
	 * @param bmtCode
	 *            消息模板表中的消息编号
	 * @param params
	 *            填充模板内容的参数
	 * @param to
	 *            消息的接收人
	 * @throws Exception
	 *             模板不存在，或是发送消息出现异常
	 */
	@SuppressWarnings("rawtypes")
	public void sendMessage(SysBaseMessageTemplet templet, Map params,
			String[] to) throws Exception;

	public void sendMessage(String bmtCode, Map params, String[] to)
			throws Exception;

	public void sendMessage(SysBaseMessageTemplet templet, Map params,
			String[] to, String sign) throws Exception;

}
