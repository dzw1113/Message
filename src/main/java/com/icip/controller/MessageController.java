package com.icip.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.icip.framework.common.cache.CacheManagerFactory;
import com.icip.framework.common.exception.AppException;
import com.icip.framework.message.BasePropertyID;
import com.icip.framework.message.VelocityParserUtil;
import com.icip.framework.message.bean.SysBaseMessageTemplet;
import com.icip.framework.message.bean.UserRelative;
//import com.icip.framework.message.dao.MeSmsCanuseMapper;
import com.icip.framework.message.dao.SysBaseMessageTempletMapper;
//import com.icip.framework.message.dao.SysCompanyInfoMapper;
//import com.icip.framework.message.dao.SysSendControlMapper;
//import com.icip.framework.message.dao.SysSendLogMapper;
import com.icip.framework.message.dao.UserRelativeMapper;
import com.icip.framework.message.impl.MessageServiceImpl;
import com.icip.service.MessageService;

/**
* ME信息发送入口
* @Description: 描述
* @author  肖伟
* @date 2016年6月16日 上午9:38:58
* @update 2016年6月16日 上午9:38:58
 */
@Controller
public class MessageController {

	private static final String SIGN = "sign";
	private static final String PARAMS = "params";
	private static final String BMT_CODE = "bmtCode";
	private static final String SYSTEM_ID = "systemID";
	private static final String COMPANY_ID = "companyID";
	private static final String CIDS = "cids";
	

	/* private static final String MOBILE_NOS = "mobileNos"; */
	private static final Logger logger = Logger
			.getLogger(MessageController.class);
	
	@Autowired
	MessageServiceImpl messageService;

	@Autowired
	MessageService service;

	@Autowired
	UserRelativeMapper userRelativeMapper;

//	@Autowired
//	SysSendControlMapper sysSendControlMapper;

//	@Autowired
//	SysSendLogMapper sysSendLogMapper;

//	@Autowired
//	SysCompanyInfoMapper sysCompanyInfoMapper;

	@Autowired
	private SysBaseMessageTempletMapper sysBaseMessageTempletMapper;

//	@Autowired
//	MeSmsCanuseMapper meSmsCanuseMapper;

	/**
	 * 0发送信息入口
	 * 根据模板中的类型发送信息
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping("/sendMsg")
	public void sendMsg(HttpServletRequest request) throws Exception {
		String systemID = request.getParameter(SYSTEM_ID);//从哪个系统过来  如：ME
		String companyID = request.getParameter(COMPANY_ID);//公司ID
		String bmtCode = request.getParameter(BMT_CODE);//模板ID
		String par = request.getParameter(PARAMS);//参数
		String sign = request.getParameter(SIGN);//签名(暂不用管)
		String[] cids = request.getParameterValues(CIDS);//小区编号
		Map params = JSONObject.parseObject(par, HashMap.class);//String 符合MAP格式转换Map
		List<String> toObj = JSONObject.parseArray(request.getParameter("to"),
				String.class);
		if (toObj == null || toObj.isEmpty()) {
			throw new AppException("", "无发送人！");
		}

		String[] o = new String[toObj.size()];

		// 检查系统
		if (!BasePropertyID.checkSystemID(systemID)) {
			throw new AppException("", "非法系统！");
		}
		// 检查参数
		if (StringUtils.isEmpty(bmtCode)) {
			throw new AppException("", "模板编号不能为空，或消息的接收人不能为空");
		}

		SysBaseMessageTemplet template = null;
		try {
			template = sysBaseMessageTempletMapper.selectByKey(bmtCode);
		} catch (Exception e) {
			// 查询失败
			logger.error("查询模板:" + bmtCode + " 时发生异常", e);
			throw new AppException("", "查询模板:" + bmtCode + " 时发生异常");
		}
		// 检查模板是否存在
		if (template == null || template.getState() == null
				|| template.getState().equals("0")) {
			logger.info("编号为: " + bmtCode + " 的消息模板不存在或已停用.");
			throw new AppException("", "编号为: " + bmtCode + " 的消息模板不存在.");
		}

		// 检查消息类型
		String msgType = template.getBmtType();
		if (StringUtils.isEmpty(msgType)) {
			logger.info("编号为: " + bmtCode + " 的消息模板消息类型未知.");
			throw new AppException("", "编号为: " + bmtCode + " 的消息模板消息类型未知，无法发送.");
		}

		// 解析标题
		String title = null;
		if (!StringUtils.isEmpty(template.getBmtTitle()) && params != null) {
			try {
				title = VelocityParserUtil.getInstance().parseVelocityTemplate(
						template.getBmtTitle(), params);
				template.setBmtTitle(title);
			} catch (Exception e) {
				logger.error("编号为: " + bmtCode + " 的消息模板解析 <标题 > 时失败");
				throw new AppException("", "编号为: " + bmtCode
						+ " 的消息模板解析 <标题 > 时失败");
			}
		}
		// 解析内容
		String content = null;
		if (!StringUtils.isEmpty(template.getBmtContent()) && params != null) {
			try {
				content = VelocityParserUtil
						.getInstance()
						.parseVelocityTemplate(template.getBmtContent(), params);
				template.setBmtContent(content);
			} catch (Exception e) {
				logger.error("编号为: " + bmtCode + " 的消息模板解析 <内容> 时失败");
				throw new AppException("", "编号为: " + bmtCode
						+ " 的消息模板解析 <内容> 时失败");
			}
		}
		if (StringUtils.isEmpty(companyID) || "1".equals(companyID)) {
			companyID = "ICIP000000";
		}
		int number = toObj.size();
		String pcid = companyID;
		try {
			// 不是短信类型直接发送
			if (!template.getBmtType().equals(BasePropertyID.TYPE_SMS)) {
				messageService.sendMessage(template, params, toObj.toArray(o),
						sign);
			} else {
				
				String cid = cids!=null&&cids.length>0?cids[0]:"-1";
				//1 检查
				int flag = service.checkOffFlag(template.getBmtCode(), number,
						toObj.get(0), pcid,cid);
				if (flag != 1) {
					//2 发送
					messageService.sendMessage(template, params,
							toObj.toArray(o), sign);
					try {
						SendMsg sendMsg = new SendMsg();
						sendMsg.setCids(Arrays.asList(cids));
						List<String> pcids = new ArrayList<String>();
						pcids.add(pcid);
						sendMsg.setPcids(pcids);
						sendMsg.setTempletId(template.getBmtCode());
						//3 修改扣除、提醒
						service.update(sendMsg, number, flag,template.getBmtCode());
					} catch (Exception e) {
						logger.error("更新可用条数出错", e);
						flag = 1;
					}
					//4 记日志
					service.addLog(bmtCode, companyID, msgType, "1", systemID,
							1, flag);
				}
			}
		} catch (Exception e) {
			if (e instanceof AppException) {
				logger.error("发送信息出错！");
			} else {
				logger.error("统计日志报错！");
			}
		}
	}

	/**
	 * 1 通过手机号码发送短信
	 * 
	 * @param msg
	 *            发送内容
	 * @param mobileNos
	 *            发送手机号
	 * @param templetId
	 *            模板编号
	 * @return 发送成功，返回true
	 * @throws Exception
	 */
	@RequestMapping("/sendMsgThroughSMSByMobileNos")
	public void sendMsgThroughSMSByMobileNos(@RequestBody SendMsg sendMsg)
			throws Exception {
		Map<String, String> params = sendMsg.getParams();
		List<String> mobileNos = sendMsg.getMobileNos();
		if(null==mobileNos||mobileNos.size()==0){
			logger.error("手机号没有！");
			throw new AppException("手机号没有！");
		}
		String templetId = sendMsg.getTempletId();
		String[] to = new String[mobileNos.size()];//发给谁
		
		//取pcid
		String pcid = null;
		if(sendMsg.getPcids()!=null&&sendMsg.getPcids().size()>0){
			pcid = sendMsg.getPcids().get(0);
		}else{
			pcid = "ICIP000000";
		}
		
		int number = to.length;
		String cid = sendMsg.getCids()!=null&&sendMsg.getCids().size()>0?sendMsg.getCids().get(0):"-1";
		logger.info(mobileNos.toArray(to));
		
		int flag = service.checkOffFlag(sendMsg.getTempletId(), number,
				mobileNos.toArray(to)[0], pcid,cid);
		try {
			if (flag != 1) {
				messageService.sendMessage(templetId, params,
						mobileNos.toArray(to));
				try {
					service.update(sendMsg, number, flag,templetId);
				} catch (Exception e) {
					logger.error("更新可用条数出错", e);
					flag = 1;
				}
				service.addLog(templetId, pcid, "1", "2", "1", to.length, flag);
			}
		} catch (Exception e) {
			if (e instanceof AppException) {
				logger.error("发送信息出错！");
			} else {
				logger.error("统计日志报错！");
			}
		}
	}

	/**
	 * 2 通过短信发送消息
	 * 通过客户号发送短信，传入模板code
	 * @param params
	 *            发送内容
	 * @param custNos
	 *            发送客户号，需根据客户号查询对应的手机号码后发送
	 * @param templetId
	 *            模板编号
	 * @return 发送成功，返回true
	 * @throws Exception
	 */
	@RequestMapping("/sendMsgThroughSMSByCustNos")
	public void sendMsgThroughSMSByCustNos(@RequestBody SendMsg sendMsg)
			throws Exception {
		Map<String, String> params = sendMsg.getParams();
		List<String> custNos = sendMsg.getCustNos();
		String templetId = sendMsg.getTempletId();
		String pcid = sendMsg.getPcids().size() > 0 ? sendMsg.getPcids().get(0)
				: "ICIP000000";
		boolean pushAll = false;
		if (custNos == null || custNos.isEmpty()) {
			pushAll = true;
			logger.debug("================= 发送所有用户 ==================");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("custNos", custNos);
		List<UserRelative> list = userRelativeMapper.selectByCustNos(map);
		List<String> smsList = new ArrayList<String>();
		for (UserRelative re : list) {
			if (!StringUtils.isEmpty(re.getMobile())) {
				smsList.add(re.getMobile());
			}
		}
		if (pushAll || !smsList.isEmpty()) {
			int number = smsList.size();
			String cid = sendMsg.getCids()!=null&&sendMsg.getCids().size()>0?sendMsg.getCids().get(0):"-1";
			int flag = service.checkOffFlag(sendMsg.getTempletId(), number,
					smsList.get(0), pcid,cid);
			try {
				if (flag != 1) {
					String[] to = new String[smsList.size()];
					messageService.sendMessage(templetId, params,
							smsList.toArray(to));
					try {
						service.update(sendMsg, number, flag,templetId);
					} catch (Exception e) {
						logger.error("更新可用条数出错", e);
						flag = 1;
					}
					service.addLog(templetId, pcid, "1", "2", "1", number, flag);
				}
			} catch (Exception e) {
				if (e instanceof AppException) {
					logger.error("发送信息出错！");
				} else {
					logger.error("统计日志报错！");
				}
			}
		}

	}

	/**
	 * 3 通过短信发送消息
	 * 根据小区发送：手机短信
	 * @param msg
	 * @param cids
	 *            发送的小区，根据此编号查询这些小区下的客户手机号，然后批量发送短信
	 * @param templetId
	 *            模板编号
	 * @return 发送成功，返回true
	 * @throws Exception
	 */
	@RequestMapping("/sendMsgThroughSMS")
	public void sendMsgThroughSMS(@RequestBody SendMsg sendMsg)
			throws Exception {
		Map<String, String> params = sendMsg.getParams();
		List<String> cids = sendMsg.getCids();
		String templetId = sendMsg.getTempletId();
		String pcid = sendMsg.getPcids().size() > 0 ? sendMsg.getPcids().get(0)
				: "ICIP000000";
		boolean pushAll = false;
		if (cids == null || cids.isEmpty()) {
			pushAll = true;
			logger.debug("================= 发送所有用户 ==================");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cids", cids);
		map.put("groupType", BasePropertyID.TYPE_SMS);
		List<UserRelative> list = userRelativeMapper.selectByCustNos(map);
		List<String> smsList = new ArrayList<String>();
		for (UserRelative re : list) {
			if (!StringUtils.isEmpty(re.getMobile())) {
				smsList.add(re.getMobile());
			}
		}
		if (!smsList.isEmpty() || pushAll) {
			int number = smsList.size();
			String cid = sendMsg.getCids()!=null&&sendMsg.getCids().size()>0?sendMsg.getCids().get(0):"-1";
			int flag = service.checkOffFlag(sendMsg.getTempletId(), number,
					smsList.get(0), pcid,cid);
			try {
				if (flag != 1) {
					String[] to = new String[smsList.size()];
					messageService.sendMessage(templetId, params,
							smsList.toArray(to));
					try {
						service.update(sendMsg, number, flag,templetId);
					} catch (Exception e) {
						logger.error("更新可用条数出错", e);
						flag = 1;
					}
					service.addLog(templetId, pcid, "1", "2", "1", number, flag);
				}
			} catch (Exception e) {
				if (e instanceof AppException) {
					logger.error("发送信息出错！");
				} else {
					logger.error("统计日志报错！");
				}
			}
		}
	}

	/**
	 * 4 根据客户号发送系统推送（极光）类型为3 ，针对极光推送   params  key 必须     （msgId未知）：问客户端
	 * @param params
	 *            发送内容,其中msgType和msgId为固定字段,其中msgType必须上送,如果要跳转消息详情必须上送msgId
	 *            msgType:01--资讯消息 02--账单 03--营销消息
	 * @param custNos
	 *            发送客户号，需根据客户号查询对应的推送ID后发送，有可能一个客户对应多个推送id
	 * @param templetId
	 *            模板编号
	 * @param productId
	 *            软件标识，发送给指定的软件，如果为空，则发送给这些客户下的所有推送Id
	 * @return 发送成功，返回true
	 * @throws Exception
	 */
	@RequestMapping("/sendMsgThroughNotificationByCustNos")
	public void sendMsgThroughNotificationByCustNos(@RequestBody SendMsg sendMsg)
			throws Exception {
		Map<String, String> params = sendMsg.getParams();
		List<String> custNos = sendMsg.getCustNos();
		String templetId = sendMsg.getTempletId();
		String productId = sendMsg.getProductId();
		boolean pushAll = false;
		if (custNos == null || custNos.isEmpty()) {
			pushAll = true;
			logger.debug("================= 发送所有用户 ==================");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("custNos", custNos);
		map.put("productId", productId);
		map.put("groupType", BasePropertyID.TYPE_PUSH);
		List<UserRelative> list = userRelativeMapper.selectByCustNos(map);
		List<String> smsList = new ArrayList<String>();
		for (UserRelative re : list) {
			if (!StringUtils.isEmpty(re.getAuroraNo())) {
				smsList.add(re.getAuroraNo());
			}
		}
		if (!smsList.isEmpty() || pushAll) {
			String[] to = new String[smsList.size()];
			messageService.sendMessage(templetId, params, smsList.toArray(to));
		}
	}

	/**
	 * 5 通过系统通知发送消息
	 * 根据小区发送推送（只能发送推送）
	 * @param msg
	 *            发送内容,其中msgType和msgId为固定字段,其中msgType必须上送,如果要跳转消息详情必须上送msgId
	 *            msgType:01--资讯消息 02--账单 03--营销消息
	 * @param cids
	 *            发送的小区，根据此编号查询这些小区下的客户的推送Id，然后批量发送系统通知
	 * @param pcids
	 *            发送的公司，根据此编号查询这些公司下的客户的推送Id，然后批量发送系统通知
	 * @param templetId
	 *            模板编号
	 * @param productId
	 *            软件标识，发送给指定的软件，如果为空，则发送给这些客户下的所有推送Id
	 * @return 发送成功，返回true
	 * @throws Exception
	 */
	@RequestMapping("/sendMsgThroughNotification")
	public void sendMsgThroughNotification(@RequestBody SendMsg sendMsg)
			throws Exception {
		Map<String, String> params = sendMsg.getParams();
		List<String> cids = sendMsg.getCids();
		List<String> pcids = sendMsg.getPcids();
		String templetId = sendMsg.getTempletId();
		String productId = sendMsg.getProductId();
		boolean pushAll = false;
		//如果小区等于空，并且 公司不等于空，并且公司等于ICIP000000（全部）
		if((cids == null || cids.isEmpty()) && (pcids!=null || pcids.size()>0 || pcids.get(0).equals("ICIP000000"))) 
		{
			pcids = null;
			pushAll = true;
			logger.debug("================= 发送所有用户 ==================");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("cids", cids);
//		if(pcids !=null && pcids.size()>0 && pcids.get(0).equals("ICIP000000")){
//			pcids = null;
//		}
		map.put("pcids", pcids);
		map.put("groupType", BasePropertyID.TYPE_PUSH);
		List<UserRelative> list = userRelativeMapper.selectByCustNos(map);
		List<String> smsList = new ArrayList<String>();
		for (UserRelative re : list) {
			if (!StringUtils.isEmpty(re.getAuroraNo())) {
				smsList.add(re.getAuroraNo());
			}
		}
		if (!smsList.isEmpty() || pushAll) {
			String[] to = new String[smsList.size()];
			messageService.sendMessage(templetId, params, smsList.toArray(to));
		}
	}

	/**
	 * 6 通过系统通知发送消息
	 * 根据小区和软件标识发送极光推送或短息
	 * @param msg
	 *            发送内容,如果channelTypes为02时
	 *            其中msgType和msgId为固定字段,其中msgType必须上送,如果要跳转消息详情必须上送msgId
	 *            msgType:01--资讯消息 02--账单 03--营销消息
	 * @param cids
	 *            发送的小区,根据此字段以及渠道类型查询手机号或推送ID
	 * @param templetId
	 *            模板编号
	 * @param productId 
	 *            软件标识，发送给指定的软件，如果为空，则发送给这些客户下的所有推送Id,只有渠道类型是系统通知的情况下上送
	 * @return 发送成功，返回true
	 * @throws Exception
	 */
	@RequestMapping("/sendMsgByCids")
	public void sendMsgByCids(@RequestBody SendMsg sendMsg) throws Exception {
		Map<String, String> params = sendMsg.getParams();
		List<String> cids = sendMsg.getCids();
		List<String> templetIds = sendMsg.getTempletIds();
		String productId = sendMsg.getProductId();
		boolean pushAll = false;
		if (cids == null || cids.isEmpty()) {
			pushAll = true;
			logger.debug("================= 发送所有用户 ==================");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("cids", cids);

		String pcid = sendMsg.getPcids().size() > 0 ? sendMsg.getPcids().get(0)
				: "ICIP000000";
		for (String templetId : templetIds) {
			List<String> smsList = new ArrayList<String>();
			SysBaseMessageTemplet template = null;
			try {
				template = sysBaseMessageTempletMapper.selectByKey(templetId);
				map.put("groupType", template.getBmtType());// 设置去重标识
				List<UserRelative> list = userRelativeMapper
						.selectByCustNos(map);
				if (template.getBmtType().equals(BasePropertyID.TYPE_PUSH)) {
					for (UserRelative re : list) {
						if (!StringUtils.isEmpty(re.getAuroraNo())) {
							smsList.add(re.getAuroraNo());
						}
					}
				} else if (template.getBmtType()
						.equals(BasePropertyID.TYPE_SMS)) {
					for (UserRelative re : list) {
						if (!StringUtils.isEmpty(re.getMobile())) {
							smsList.add(re.getMobile());
						}
					}
				} else if (template.getBmtType().equals(
						BasePropertyID.TYPE_MAIL)) {
					for (UserRelative re : list) {
						if (!StringUtils.isEmpty(re.getEmail())) {
							smsList.add(re.getEmail());
						}
					}
				} else if (template.getBmtType().equals(BasePropertyID.TYPE_WX)) {
					for (UserRelative re : list) {
						if (!StringUtils.isEmpty(re.getWechatNo())) {
							smsList.add(re.getWechatNo());
						}
					}
				}
				//if (!smsList.isEmpty() || pushAll) {
				if (!smsList.isEmpty() && !pushAll) {//如果等于发送全部，则不发送短信
					if (template.getBmtType().equals(BasePropertyID.TYPE_SMS)) {
						int number = smsList.size();
						String cid = sendMsg.getCids()!=null&&sendMsg.getCids().size()>0?sendMsg.getCids().get(0):"-1";
						int flag = service.checkOffFlag(sendMsg.getTempletId(),
								number, smsList.get(0), pcid,cid);
						try {
							if (flag != 1) {
								String[] to = new String[smsList.size()];
								messageService.sendMessage(templetId, params,
										smsList.toArray(to));
								try {
									service.update(sendMsg, number, flag,templetId);
								} catch (Exception e) {
									logger.error("更新可用条数出错", e);
									flag = 1;
								}
								service.addLog(templetId, pcid, "1", "2", "1",
										number, flag);
							}
						} catch (Exception e) {
							if (e instanceof AppException) {
								logger.error("发送信息出错！");
							} else {
								logger.error("统计日志报错！");
							}
						}
					} else {
						String[] to = new String[smsList.size()];
						messageService.sendMessage(templetId, params,
								smsList.toArray(to));
					}
				}else{
					throw new AppException("", "不能给所有用户发送短信");
				}
			} catch (Exception e) {
				// 查询失败
				throw new Exception("没有找到用户，报错啦！！");
			}
		}
	}

	/**
	 * 
	 * 7 通过系统通知发送消息
	 * 根据客户号和软件标识发送短信或者极光推送
	 * @param msg
	 *            发送内容,如果channelTypes为02时,其中msgType和msgId为固定字段,其中msgType必须上送,
	 *            如果要跳转消息详情必须上送msgId msgType:01--资讯消息 02--账单 03--营销消息
	 * @param custNos
	 *            发送的小区,根据此字段以及渠道类型查询手机号或推送ID
	 * @param templetId
	 *            模板编号
	 * @param productId
	 *            软件标识，发送给指定的软件，如果为空，则发送给这些客户下的所有推送Id,只有渠道类型是系统通知的情况下上送
	 * @return 发送成功，返回true
	 * @throws Exception
	 */
	@RequestMapping("/sendMsgByCustNos")
	public void sendMsgByCustNos(@RequestBody SendMsg sendMsg) throws Exception {
		Map<String, String> params = sendMsg.getParams();
		List<String> custNos = sendMsg.getCustNos();
		List<String> templetIds = sendMsg.getTempletIds();
		String productId = sendMsg.getProductId();
		boolean pushAll = false;
		if (custNos == null || custNos.isEmpty()) {
			pushAll = true;
			logger.debug("================= 发送所有用户 ==================");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("custNos", custNos);

		String pcid = sendMsg.getPcids().size() > 0 ? sendMsg.getPcids().get(0)
				: "ICIP000000";

		for (String templetId : templetIds) {
			List<String> smsList = new ArrayList<String>();
			SysBaseMessageTemplet template = null;
			try {
				template = sysBaseMessageTempletMapper.selectByKey(templetId);
				map.put("groupType", template.getBmtType());// 设置去重标识
				List<UserRelative> list = userRelativeMapper
						.selectByCustNos(map);// 查询相应客户数据
				if (template.getBmtType().equals(BasePropertyID.TYPE_PUSH)) {
					for (UserRelative re : list) {
						if (!StringUtils.isEmpty(re.getAuroraNo())) {
							smsList.add(re.getAuroraNo());
						}
					}
				} else if (template.getBmtType()
						.equals(BasePropertyID.TYPE_SMS)) {
					for (UserRelative re : list) {
						if (!StringUtils.isEmpty(re.getMobile())) {
							smsList.add(re.getMobile());
						}
					}
				} else if (template.getBmtType().equals(
						BasePropertyID.TYPE_MAIL)) {
					for (UserRelative re : list) {
						if (!StringUtils.isEmpty(re.getEmail())) {
							smsList.add(re.getEmail());
						}
					}
				} else if (template.getBmtType().equals(BasePropertyID.TYPE_WX)) {
					for (UserRelative re : list) {
						if (!StringUtils.isEmpty(re.getWechatNo())) {
							smsList.add(re.getWechatNo());
						}
					}
				}


				if (!smsList.isEmpty() && !pushAll) {//如果等于发送全部，则不发送短信
					if (template.getBmtType().equals(BasePropertyID.TYPE_SMS)) {
						int number = smsList.size();
						String cid = sendMsg.getCids()!=null&&sendMsg.getCids().size()>0?sendMsg.getCids().get(0):"-1";
						int flag = service.checkOffFlag(templetId,
								number, smsList.get(0), pcid,cid);
						try {
							if (flag != 1) {
								String[] to = new String[smsList.size()];
								messageService.sendMessage(templetId, params,
										smsList.toArray(to));
								try {
									service.update(sendMsg, number, flag,templetId);
								} catch (Exception e) {
									logger.error("更新可用条数出错", e);
									flag = 1;
								}
								service.addLog(templetId, pcid, "1", "2", "1",
										number, flag);
							}
						} catch (Exception e) {
							if (e instanceof AppException) {
								logger.error("发送信息出错！");
							} else {
								logger.error("统计日志报错！");
							}
						}
					} else {
						String[] to = new String[smsList.size()];
						messageService.sendMessage(templetId, params,
								smsList.toArray(to));
					}
				}else{
					throw new AppException("", "不能给所有用户发送短信");
				}
			} catch (Exception e) {
				// 查询失败
				throw new Exception("没有找到用户，报错啦！！");
			}
		}
	}

	private static Object reloadLock = new Object();

	/**
	 * 根据缓存分区名称刷新整个分区<br>
	 * 刷新系统缓存
	 * @param name
	 * @throws Exception
	 */
	@RequestMapping("/reload")
	public static void reload() throws Exception {
		long beginTime = System.currentTimeMillis();
		synchronized (reloadLock) {
			CacheManagerFactory.getInstance().clear("systemCache");
			CacheManagerFactory.getInstance().reload("systemCache");
		}

		long endTime = System.currentTimeMillis();
		logger.debug("刷新" + "systemCache" + "分区缓存" + "刷新完成，耗时"
				+ (endTime - beginTime) + "ms");
	}
}
