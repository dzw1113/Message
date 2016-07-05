package com.icip.framework.message.send;

import java.util.List;
import java.util.Map;

/**
 * 功能: 推送服务
 * 
 */
public interface PushSender {

	public void sendPushMessage(String alert) throws Exception;

	public void sendPushMessage(List<String> to, String title, String msgConent,
			Map<String, String> extras) throws Exception;

	public void sendPushMessage(String type, List<String> to, String title,
			String msgConent, Map<String, String> extras) throws Exception;

	public void sendPushMessage(String type, List<String> to, String title,
			String msgConent, Map<String, String> extras, String sound)
			throws Exception;

	public void sendPushMessage(String title, String msgConent,
			Map<String, String> extras, String sound) throws Exception;

}
