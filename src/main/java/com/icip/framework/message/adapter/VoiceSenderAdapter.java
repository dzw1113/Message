package com.icip.framework.message.adapter;

import java.util.Map;

import org.apache.log4j.Logger;
import com.icip.framework.message.send.Sender;
import com.icip.framework.message.send.VoiceSender;
import com.icip.utils.AppUtil;

/**
* @Description: 语音适配器
* @author  肖伟
* @date 2016年6月23日 上午10:27:30
* @update 2016年6月23日 上午10:27:30
 */
public class VoiceSenderAdapter implements Sender {
	
	private static final Logger logger = Logger.getLogger(VoiceSenderAdapter.class);
	
	@Override
	public void sender(String title, String content, String[] to,Map<String,String> params)
			throws Exception {
		
	}

	@Override
	public void sender(String bmtCode, String[] to, Map<String, String> params)
			throws Exception {
		VoiceSender voiceSender = (VoiceSender) AppUtil.getBean(VoiceSender.class);
		voiceSender.senderVoice(bmtCode,to,params);
		
	}
}
