package com.icip.framework.message.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.icip.framework.common.cache.util.CacheUtil;
import com.icip.framework.common.exception.AppException;
import com.icip.framework.common.model.SysPara;
import com.icip.framework.message.BasePropertyID;
import com.icip.framework.message.ConfigService;
import com.icip.framework.message.send.VoiceSender;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcTtsNumSinglecallRequest;
import com.taobao.api.response.AlibabaAliqinFcTtsNumSinglecallResponse;

/**
 * 功能: 阿里云 语音发送服务
 * 
 */
public class VoiceSenderImpl implements VoiceSender, InitializingBean { 
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VoiceSenderImpl.class);

	private ConfigService configService;

	private String alyUrl;//语音发送URL
	private String alyAppKey;//应用APP证书key
	private String alyAppSecret;//应用APP证书Secret
	private String alyVoicePhone;//呼叫显示号码



	/**
	 * @param content
	 * @param phoneNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public void senderVoice(String bmtCode, String[] to, Map<String, String> params) throws Exception {
		try {
			String paramsStr=setParams(params);//阿里云需要jsion格式的替换参数
			//非空判断
			if(!StringUtils.isEmpty(paramsStr) && !StringUtils.isEmpty(alyUrl)
				&& !StringUtils.isEmpty(alyAppKey)	&& !StringUtils.isEmpty(alyAppSecret)	
				&& !StringUtils.isEmpty(alyVoicePhone) ){
				TaobaoClient client=null;//组装所需参数
				AlibabaAliqinFcTtsNumSinglecallRequest req=null;//发送
				for(String phone:to){
					client = new DefaultTaobaoClient(alyUrl, alyAppKey, alyAppSecret);
					req = new AlibabaAliqinFcTtsNumSinglecallRequest();
					req.setTtsParam(paramsStr);//参数
					req.setCalledNum(phone);//手机号码
					req.setCalledShowNum(alyVoicePhone);//显示号码
					req.setTtsCode(getAlitemplateId(bmtCode));//模板ID
					AlibabaAliqinFcTtsNumSinglecallResponse rsp = client.execute(req);//发送
					if(!rsp.isSuccess()){//如果发送失败打印失败消息
						logger.error("手机号码为："+phone+"发送出错!,错误原因:"+rsp.getBody());
					}
				}
			}else{
				throw new AppException("", "缺少必要参数");
			}
		} catch (Exception e) {
			logger.error("发送语音验证码出错", e);
		}
	}

	public void afterPropertiesSet() throws Exception {
		// 初始化
		init();
	}

	private void init() throws Exception {
		alyUrl=configService.getConfig(BasePropertyID.ALYURL);
		alyAppKey=configService.getConfig(BasePropertyID.ALYAPPKEY);
		alyAppSecret=configService.getConfig(BasePropertyID.ALYAPPSECRET);
		alyVoicePhone=configService.getConfig(BasePropertyID.ALYVOICEPHONE);
		//maxLength = getConfigService().getConfigByInteger(BasePropertyID.SMS_MSG_MAXLENGTH);
	}

	//转换参数，需要jsion格式参数{\"code\":\"13245\"}
	public String setParams(Map<String,String> map){
		if(map!=null && map.size()>0){
			Set keys = map.keySet();
			if(keys != null) {
				Iterator iterator = keys.iterator();
				 String remp="";
				 while(iterator.hasNext( )) {
					 String key = (String)iterator.next();
					 remp+="\""+key+"\":\""+map.get(key)+"\",";
				 }
				 return "{"+remp.substring(0,remp.length()-1)+"}";
				}
		}
			return null;
	}
	
	
	public ConfigService getConfigService() {
		return configService;
	}

	public void setConfigService(ConfigService configService) {
		this.configService = configService;
	}
	
	private boolean isReady() {
		return !(alyUrl == null || alyAppKey == null || alyAppSecret == null || alyVoicePhone == null);
	}
	
	/**
	 * 
	  * @Description: 从系统参数缓存中获取阿里云的模板id
	  * @param 参数
	  * @return 返回值
	  * @throws 异常
	  * @author 肖伟
	 */
	private String getAlitemplateId(String localTemplateId){
		Object temp = CacheUtil.getSysCahce("yy_"+localTemplateId);
		SysPara para = (SysPara)temp;
		String paramValue = para.getParamValue();
		return paramValue ; 
	}

		
}
