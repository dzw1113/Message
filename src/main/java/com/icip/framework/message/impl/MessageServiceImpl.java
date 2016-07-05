package com.icip.framework.message.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.icip.framework.common.exception.AppException;
import com.icip.framework.message.MessageService;
import com.icip.framework.message.VelocityParserUtil;
import com.icip.framework.message.bean.SysBaseMessageTemplet;
import com.icip.framework.message.dao.SysBaseMessageTempletMapper;
import com.icip.framework.message.dao.SysSendLogMapper;
import com.icip.framework.message.send.Sender;
import com.icip.framework.message.send.SenderFactory;


/**
 * 
 * @Description: 发送消息接口
 * @author 肖伟
 * @date 2016年6月12日 下午5:20:37
 * @update 2016年6月12日 下午5:20:37
 */
public class MessageServiceImpl implements MessageService {

	@Autowired
	SysSendLogMapper sysSendLogMapper;
	
	private SysBaseMessageTempletMapper sysBaseMessageTempletMapper;
	private SenderFactory senderFactory;
	
	/**
	 * 普通文本信息发送
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void sendMessage(SysBaseMessageTemplet template, Map params,
			String[] to) throws Exception {
		sendMessage(template, params, to, null);
	}

	
	/**
	  * @Description: 根据模板ID类型发送：普通文本信息
	  * @param bmtCode：模板ID，params：参数宏的替换，to：发给谁
	  * @return 
	  * @throws 
	  * @author 肖伟
	  */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public void sendMessage(String bmtCode, Map params, String[] to)
			throws Exception {
		// 检查参数
		if (StringUtils.isEmpty(bmtCode)) {
			throw new AppException("EICIPME00001");
		}

		SysBaseMessageTemplet template = null;
		try {
			template = getSysBaseMessageTempletMapper().selectByKey(bmtCode);
		} catch (Exception e) {
			// 查询失败
			throw new AppException("EICIPME00002");
		}
		// 检查模板是否存在
		if (template == null) {
			throw new AppException("EICIPME00002");
		}

		// 检查消息类型
		String msgType = template.getBmtType();
		if (StringUtils.isEmpty(msgType)) {
			throw new AppException("EICIPME00003");
		}

		// 解析标题
		String title = null;
		if ((!StringUtils.isEmpty(template.getBmtTitle())) && params != null) {
			try {
				title = VelocityParserUtil.getInstance().parseVelocityTemplate(
						template.getBmtTitle(), params);
			} catch (Exception e) {
				throw new AppException("EICIPME00004");
			}
		}
		// 解析内容
		String content = null;
		if ((!StringUtils.isEmpty(template.getBmtContent())) && params != null) {
			try {
				content = VelocityParserUtil
						.getInstance()
						.parseVelocityTemplate(template.getBmtContent(), params);
			} catch (Exception e) {
				throw new AppException("EICIPME00005");
			}
		}

		// 发送
		Sender sender = getSenderFactory().getSender(template.getBmtType());
		if (sender == null) {
			// 找不到对应类型的发送器
			throw new AppException("EICIPME00000");
		}
		if(template.getBmtType().equals("5")){//语音
			sender.sender(template.getBmtCode(),to,params);
		}else{
			sender.sender(title, content, to, params);
		}
	}
	
	
	@SuppressWarnings({ "rawtypes"})
	@Override
	/**
	  * @Description: 不是短信类型直接发送方法
	  * @param 参数
	  * @return 返回值
	  * @throws 异常
	  * @author 肖伟
	 */
	public void sendMessage(SysBaseMessageTemplet template,  Map params,
			String[] to, String sign) throws Exception {
		// 发送
		Sender sender = getSenderFactory().getSender(template.getBmtType());
		if (sender == null) {
			// 找不到对应类型的发送器
			throw new AppException("EICIPME00000");
		}
		if(template.getBmtType().equals("5")){//走语音方法
			sender.sender(template.getBmtCode(),to,params);
		}else{
			sender.sender(template.getBmtTitle(), template.getBmtContent(), to,
					params);
		}
	}
 
	
	public SenderFactory getSenderFactory() {
		return senderFactory;
	}

	public void setSenderFactory(SenderFactory senderFactory) {
		this.senderFactory = senderFactory;
	}

	public SysBaseMessageTempletMapper getSysBaseMessageTempletMapper() {
		return sysBaseMessageTempletMapper;
	}

	public void setSysBaseMessageTempletMapper(
			SysBaseMessageTempletMapper sysBaseMessageTempletMapper) {
		this.sysBaseMessageTempletMapper = sysBaseMessageTempletMapper;
	}


	public SysSendLogMapper getSysSendLogMapper() {
		return sysSendLogMapper;
	}


	public void setSysSendLogMapper(SysSendLogMapper sysSendLogMapper) {
		this.sysSendLogMapper = sysSendLogMapper;
	}

}
