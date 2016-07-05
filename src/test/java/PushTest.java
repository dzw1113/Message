import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.PushPayload;

import com.icip.framework.message.BasePropertyID;
import com.icip.framework.message.ConfigService;
import com.icip.framework.message.PushService;

public class PushTest {

	private static final Logger LOG = Logger.getLogger(PushTest.class);

	public static void main(String[] args) {

		/*ConfigService configService = new ConfigService();
		String pushAppkey = configService.getConfig(BasePropertyID.PUSH_APPKEY);
		String pushMastersecret = configService
				.getConfig(BasePropertyID.PUSH_MASTERSECRET);
		String pushHostname = configService
				.getConfig(BasePropertyID.PUSH_HOSTNAME);
		int pushMaxretryTimes = configService
				.getConfigByInteger(BasePropertyID.PUSH_MAXRETRY_TIMES);
		ClientConfig config = ClientConfig.getInstance();
		config.setPushHostName(pushHostname);
		JPushClient jpushClient = new JPushClient(pushMastersecret, pushAppkey,
				pushMaxretryTimes, null, config);
		// String alert = "广播来了!";
		// PushPayload payload =
		// PushService.buildPushObject_all_all_alert(alert);
		// String alert = "广播来了!";
		Map<String, String> map = new HashMap<String, String>();
		map.put("type", "1");
		// PushPayload payload = PushService.buildPushNothObject(null, alert,
		// "这是个自定义参数的通知,带扩展字段的！", map,null);
//		String msgContent = "这是个消息,收到请吱个声";
//		PushPayload payload = PushService.buildPushNothObject("标题党",
//				msgContent, map);
		String msgContent = "这是个消息,收到请吱个声";
		List<String> to = new ArrayList<String>();
		//我自己
//		to.add("010f60d48ef");
		
//		to.add("06084a9284b");
//		to.add("0a1ae17be17");
//		to.add("071d40fea89");
		PushPayload payload = PushService.buildPushNothObject(to,"标题党",
				msgContent, map,null);
		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);
		} catch (APIConnectionException e) {
			LOG.error("Connection error. Should retry later. ", e);
		} catch (APIRequestException e) {
			LOG.error(
					"Error response from JPush server. Should review and fix it. ",
					e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}*/
	}
}
