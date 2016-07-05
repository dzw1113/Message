package com.icip.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icip.controller.SendMsg;
import com.icip.framework.common.cache.util.CacheUtil;
import com.icip.framework.common.model.SysPara;
import com.icip.framework.message.bean.MeEmployeeInfo;
import com.icip.framework.message.bean.MeSmsCanuse;
import com.icip.framework.message.bean.MeSmsCountInfo;
import com.icip.framework.message.bean.SysSendLog;
import com.icip.framework.message.dao.MeEmployeeInfoMapper;
import com.icip.framework.message.dao.MeSmsCanuseMapper;
import com.icip.framework.message.dao.MeSmsCloseOpendMapper;
import com.icip.framework.message.dao.MeSmsCountInfoMapper;
import com.icip.framework.message.dao.SysSendLogMapper;
import com.icip.framework.message.dao.UserRelativeMapper;
import com.icip.framework.message.impl.MessageServiceImpl;

/**
 * 
 * @Description: 描述 ME系统接口调用
 * @author 肖伟
 * @date 2016年6月12日 下午1:41:34
 * @update 2016年6月12日 下午1:41:34
 */
@Service
public class MessageService {

	private static final Logger logger = Logger.getLogger(MessageService.class);

	@Autowired
	SysSendLogMapper sysSendLogMapper;
	@Autowired
	MeSmsCloseOpendMapper smsCloseOpendMapper;
	@Autowired
	MeSmsCanuseMapper meSmsCanuseMapper;
	@Autowired
	UserRelativeMapper userRelativeMapper;
	@Autowired
	MeSmsCountInfoMapper meSmsCountInfoMapper;
	@Autowired
	MeEmployeeInfoMapper employeeInfoMapper;
	@Autowired
	MessageServiceImpl messageService;

	/**
	 * @Description: 查找模板
	 * @param templetId
	 *            ：模板ID，number：发送总条数 mobile：手机号码 pcid：公司编号 cid
	 * @return 1：没有找到模板 2：短信不足，已发送管理员 3：免审材料直接发送
	 * @throws
	 * @author 肖伟
	 */
	public int checkOffFlag(String templetId, int number, String mobile,
			String pcid, String cid) throws Exception {
		if (cid == null) {
			cid = "-1";
		}
		int flag = 1;
		// 从根据模板id获取免审模板
		Object obj = CacheUtil.getSysCahce(templetId);
		if (obj == null) {// 没有找到免审模板
			Map<String, String> map = new HashMap<String, String>();
			map.put("mobile", mobile);
			map.put("bmtCode", templetId);
			map.put("cid", cid);
			Map<String, String> resultMap = smsCloseOpendMapper
					.selectByCompCidInfo(map);// 根据手机、模板、小区 查找短信条数等信息。
			if (resultMap == null || resultMap.size() == 0) {
				logger.info("找不到相关信息");
				flag = 1;
				return flag;
			}
			String status = resultMap.get("STATUS");// 1：已开通 2：未开通
			
			// 开通
			if ("1".equals(status)) {
				int canuseCount = Integer.parseInt(resultMap
						.get("CANUSE_COUNT"));// 剩余条数
				if(canuseCount==0 ||canuseCount - number<=0){//剩余条数必须大于零    剩余的条数减此次要发送的条数必须大于0
					flag = 1;
				}else{
					sendByEid(pcid, canuseCount - number);// 发送
					flag = 2;
				}
			} else {
				logger.info("该公司暂未开通短信");
			}
		} else {
			logger.info("材料免审：" + templetId);
			flag = 3;
		}
		return flag;
	}

	/**
	 * 
	 * @Description: 短信条数不足发送管理员短信
	 * @param pcid
	 *            : 公司编号 canuseCount：剩余总条数
	 * @return 返回值
	 * @throws 异常
	 * @author 肖伟
	 */
	public void sendByEid(String pcid, int canuseCount) {
		SysPara param = (SysPara) CacheUtil.getSysCahce("tips_number");
		if (param == null || param.getParamValue() == null) {
			logger.info("从系统获取提示条数出错！");
			return;
		}
		Map<String, String> pmap = new HashMap<String, String>();
		pmap.put("pcid", pcid);
		List<Map<String, String>> list = employeeInfoMapper.selectAll(pmap);// 查出管理员资料
		if (list.size() == 0 || list.get(0).get("STATUS") == null) {
			logger.info("找不到公司或者找不到手机或找不到管路员!");
			return;
		}
		int maxSize = Integer.parseInt(param.getParamValue());// 系统参数
		if (canuseCount > maxSize) {
			logger.info("可用条数大于坎值!");
			return;
		}
		if (list.get(0).get("STATUS").equals("1")) {// 发送过
			logger.info("已提醒该公司管理员!");
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("companyName", list.get(0).get("COMPANY_NAME"));
		params.put("canuseCount", "" + canuseCount);
		String[] to = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			to[i] = list.get(i).get("MOBILE");
		}
		try {
			// 发送短信
			messageService.sendMessage("me_model_tipsManager", params, to);
			// 修改状态
			MeEmployeeInfo record = new MeEmployeeInfo();
			record.setPcid(pcid);
			record.setStatus("1");
			employeeInfoMapper.update(record);
			logger.info("发送成功！");
		} catch (Exception e) {
			logger.error("余额短信不足发送短信失败", e);
		}

	}

	/**
	 * 获取发送数量
	 * 
	 * @param sendMsg
	 * @return
	 * @throws Exception
	 */
	public int getNumber(SendMsg sendMsg) throws Exception {
		int number = sendMsg.getMobileNos().size();
		// 没有手机则是小区或者公司发
		if (number == 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("custNos", sendMsg.getCustNos());
			map.put("productId", sendMsg.getProductId());
			map.put("cids", sendMsg.getCids());
			map.put("pcids", sendMsg.getPcids());
			number = userRelativeMapper.selectByInfo(map);
		}
		return number;
	}

	/**
	 * @Description: 更新可使用总条数
	 * @param number
	 *            :条数 templetId：模板ID
	 * @return 返回值
	 * @throws 异常
	 * @author 肖伟
	 */

	public void update(SendMsg sendMsg, int number, int flag, String templetId)
			throws Exception {
		if (flag != 2) {
			logger.error("当前状态处于免审或者条数不足！");
			return;
		}
		String pcid = sendMsg.getPcids().get(0);
		// 更新公司总可用条数
		MeSmsCanuse msc = new MeSmsCanuse();
		msc.setPcid(sendMsg.getPcids().get(0));
		msc.setCanuseCount("" + -number);
		meSmsCanuseMapper.update(msc);
		// 更新小区使用条数
		if (sendMsg != null && sendMsg.getCids() != null
				&& sendMsg.getCids().size() > 0) {
			for (int i = 0; i < sendMsg.getCids().size(); i++) {
				String cid = sendMsg.getCids().get(i);
				MeSmsCountInfo record = new MeSmsCountInfo();
				record.setCid(cid);
				record.setPcid(pcid);
				record.setBmtCode(templetId);
				record.setUseCount("" + number);
				meSmsCountInfoMapper.update(record);
			}
		}
	}

	/**
	 * 记录日志
	 * 
	 * @param sysSendLogMapper
	 * @param bmtCode
	 * @param companyID
	 * @param msgType
	 * @param status
	 * @param systemId
	 */
	public void addLog(String bmtCode, String companyID, String msgType,
			String status, String systemId, Integer msgCount, int flag) {
		SysSendLog record = new SysSendLog();
		record.setBmtCode(bmtCode);
		record.setCompanyId(companyID);
		record.setMsgType(msgType);
		record.setStatus(status);
		record.setSystemId(systemId);
		record.setMsgCount(msgCount);
		sysSendLogMapper.insert(record);
	}
}
