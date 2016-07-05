package com.icip.framework.message;

import java.util.List;
import java.util.Map;

import cn.jpush.api.common.DeviceType;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.PushPayload.Builder;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 推送服务，推送方式：别名、标签、注册ID、分群、广播等 notification:通知 message：消息
 * 
 * @author lenovo
 * 
 *         YW
 */
public class PushService {

	private static final String DEFAULT = "default";
	private static boolean pushApnsProduction = false;
	private static final ConfigService cs = new ConfigService();

	static {
		setPushApnsProduction(Boolean.parseBoolean(cs.getConfig(BasePropertyID.PUSH_APNS_PRODUCTION)));
	}

	/**
	 * 推送所有通知
	 * 
	 * @return
	 */
	public static PushPayload buildPushObject_all_all_alert(String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.all()).setNotification(Notification.alert(alert)).build();
	}

	/**
	 * 针对所有：别名所有通知
	 * 
	 * @return
	 */
	public static PushPayload buildPushObject_all_alias_alert(String alias, String alert) {
		return PushPayload.newBuilder().setPlatform(Platform.all()).setOptions(Options.newBuilder().setApnsProduction(pushApnsProduction).build()).setAudience(Audience.alias(alias))
				.setNotification(Notification.alert(alert)).build();
	}

	/**
	 * 针对安卓：tag通知带tag和title
	 * 
	 * @return
	 */
	public static PushPayload buildPushObject_android_tag_alertWithTitle(String tag, String alert, String title) {
		return PushPayload.newBuilder().setPlatform(Platform.android()).setOptions(Options.newBuilder().setApnsProduction(pushApnsProduction).build()).setAudience(Audience.tag(tag))
				.setNotification(Notification.android(alert, title, null)).build();
	}

	/**
	 * 带扩展(Extra)的的通知
	 * 
	 * @param tag
	 * @param title
	 * @param alertConent
	 * @param map
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_android_and_ios(String tag, String title, String alertConent, Map<String, String> extras) {
		Builder builder = PushPayload.newBuilder();
		return builder
				.setPlatform(Platform.android_ios())
				.setOptions(Options.newBuilder().setApnsProduction(pushApnsProduction).build())
				.setAudience(Audience.tag(tag))
				.setNotification(
						Notification.newBuilder().setAlert(alertConent).addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).build())
								.addPlatformNotification(IosNotification.newBuilder().incrBadge(1).addExtras(extras).build()).build()).build();
	}

	/**
	 * IOS:推送，带声音、扩展
	 * 
	 * @param tagValue
	 * @param alert
	 * @param happy
	 * @param extras
	 * @param msgContent
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage(String[] tagValue, String alert, String happy, Map<String, String> extras, String msgContent) {
		return PushPayload
				.newBuilder()
				.setPlatform(Platform.ios())
				.setOptions(Options.newBuilder().setApnsProduction(pushApnsProduction).build())
				.setAudience(Audience.registrationId(tagValue))
				.setNotification(
						Notification.newBuilder().addPlatformNotification(IosNotification.newBuilder().setAlert(alert).setBadge(1).setSound(happy).addExtras(extras).build()).build())
				.setMessage(Message.content(msgContent)).setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
	}

	/**
	 * IOS推送
	 * 
	 * @param tagValue
	 * @param alias
	 * @param extras
	 * @param msgContent
	 * @return PushPayload
	 */
	public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String[] tagValue, String[] alias, Map<String, String> extras, String msgContent) {
		return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setOptions(Options.newBuilder().setApnsProduction(pushApnsProduction).build())
				.setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.tag(tagValue)).addAudienceTarget(AudienceTarget.alias(alias)).build())
				.setMessage(Message.newBuilder().setMsgContent(msgContent).addExtras(extras).build()).build();
	}

	/**
	 * 通知：包含全部,IOS-安卓都收的到
	 * 
	 * @param tag
	 * @param title
	 * @param alertConent
	 * @param map
	 * @return PushPayload
	 */
	public static PushPayload buildPushNothObject(String type, List<String> to, String title, String alertConent, Map<String, String> extras, String sound) {
		Builder builder = PushPayload.newBuilder().setPlatform(Platform.android_ios()).setOptions(Options.newBuilder().setApnsProduction(pushApnsProduction).build());
		if (to != null && to.size() > 0) {
			builder.setAudience(Audience.registrationId(to));
		} else {
			builder.setAudience(Audience.all());
		}
		AndroidNotification anf = AndroidNotification.newBuilder().setTitle(title).addExtras(extras).build();

		if (sound == null || sound.trim().length() > 0) {
			sound = DEFAULT;
		}
		IosNotification inf = IosNotification.newBuilder().setBadge(1).addExtras(extras).setSound(sound).build();

		PushPayload ppl = builder.setNotification(Notification.newBuilder().setAlert(alertConent).addPlatformNotification(anf).addPlatformNotification(inf).build()).build();
		return ppl;
	}

	/**
	 * 通知：包含全部,IOS-安卓都收的到
	 * 
	 * @param tag
	 * @param title
	 * @param alertConent
	 * @param map
	 * @return PushPayload
	 */
	public static PushPayload buildPushNothObject(List<String> to, String title, String msgConent, Map<String, String> extras, String sound) {
		return buildPushNothObject(null, to, title, msgConent, extras, sound);
	}

	/**
	 * 通知：包含全部,IOS-安卓都收的到
	 * 
	 * @param title
	 * @param alertConent
	 * @param map
	 * @return PushPayload
	 */
	public static PushPayload buildPushNothObject(String title, String alertConent, Map<String, String> extras, String sound) {
		return buildPushNothObject(null, null, title, alertConent, extras, sound);
	}

	/**
	 * 通知：包含全部,IOS-安卓都收的到
	 * 
	 * @param title
	 * @param alertConent
	 * @param map
	 * @return PushPayload
	 */
	public static PushPayload buildPushNothObject(String title, String msgConent, Map<String, String> extras) {
		return buildPushNothObject(null, null, title, msgConent, extras, null);
	}

	/**
	 * 消息和通知--混合体IOS收不到
	 * 
	 * @param type
	 * @param tag
	 * @param title
	 * @param msgContent
	 * @param extras
	 * @param sound
	 * @return
	 */
	public static PushPayload buildPush_Noth_MsgObject(String type, List<String> to, String title, String msgContent, Map<String, String> extras, String sound) {
		Builder builder = PushPayload.newBuilder().setPlatform(getPlatType(type)).setOptions(Options.newBuilder().setApnsProduction(pushApnsProduction).build());
		if (to != null && to.size() > 0) {
			builder.setAudience(Audience.registrationId(to));
		} else {
			builder.setAudience(Audience.all());
		}
		// 设置消息
		Message message = Message.newBuilder().addExtras(extras).setMsgContent(msgContent).setTitle(title).addExtras(extras).build();
		// 设置安卓通知
		AndroidNotification anf = AndroidNotification.newBuilder().setTitle(title).addExtras(extras).build();

		if (sound == null || sound.trim().length() > 0) {
			sound = DEFAULT;
		}
		// 设置IOS通知
		IosNotification inf = IosNotification.newBuilder().incrBadge(1).addExtras(extras).setSound(sound).build();
		PushPayload ppl = builder.setNotification(Notification.newBuilder().setAlert(msgContent).addPlatformNotification(anf).addPlatformNotification(inf).build()).setMessage(message)
				.build();

		return ppl;
	}

	/**
	 * 消息和通知--混合体IOS收不到
	 * 
	 * @param tag
	 * @param title
	 * @param msgConent
	 * @param extras
	 * @param sound
	 * @return PushPayload
	 */
	public static PushPayload buildPush_Noth_MsgObject(List<String> to, String title, String msgContent, Map<String, String> extras, String sound) {
		return buildPush_Noth_MsgObject(null, to, title, msgContent, extras, sound);
	}

	/**
	 * 消息和通知--混合体IOS收不到
	 * 
	 * @param title
	 * @param msgConent
	 * @param extras
	 * @param sound
	 * @return PushPayload
	 */
	public static PushPayload buildPush_Noth_MsgObject(String title, String msgContent, Map<String, String> extras, String sound) {
		return buildPush_Noth_MsgObject(null, null, title, msgContent, extras, sound);
	}

	/**
	 * 消息和通知--混合体IOS收不到
	 * 
	 * @param title
	 * @param msgConent
	 * @param extras
	 * @return PushPayload
	 */
	public static PushPayload buildPush_Noth_MsgObject(String title, String msgContent, Map<String, String> extras) {
		return buildPush_Noth_MsgObject(null, null, title, msgContent, extras, null);
	}

	/**
	 * 消息--IOS没有通知一说
	 * 
	 * @param type
	 * @param tag
	 * @param title
	 * @param msgContent
	 * @param extras
	 * @return
	 */
	public static PushPayload buildPush_MsgObject(String type, List<String> to, String title, String msgContent, Map<String, String> extras) {
		Builder builder = PushPayload.newBuilder().setPlatform(getPlatType(type)).setOptions(Options.newBuilder().setApnsProduction(pushApnsProduction).build());
		if (to != null && to.size() > 0) {
			builder.setAudience(Audience.registrationId(to));
		} else {
			builder.setAudience(Audience.all());
		}
		// 设置消息
		Message message = Message.newBuilder().addExtras(extras).setMsgContent(msgContent).setTitle(title).addExtras(extras).build();
		PushPayload ppl = builder.setMessage(message).build();

		return ppl;
	}

	/**
	 * 消息--IOS没有通知一说
	 * 
	 * @param tag
	 * @param title
	 * @param msgContent
	 * @param extras
	 * @return
	 */
	public static PushPayload buildPush_MsgObject(List<String> to, String title, String msgContent, Map<String, String> extras) {
		return buildPush_MsgObject(null, to, title, msgContent, extras);
	}

	/**
	 * 消息--IOS没有通知一说
	 * 
	 * @param title
	 * @param msgContent
	 * @param extras
	 * @return
	 */
	public static PushPayload buildPush_MsgObject(String title, String msgContent, Map<String, String> extras) {
		return buildPush_MsgObject(null, null, title, msgContent, extras);
	}

	private static Platform getPlatType(String type) {
		if (type == null || type.trim().length() == 0) {
			Platform.all();
		} else {
			if (type.equals(DeviceType.Android.toString())) {
				return Platform.android();
			}
			if (type.equals(DeviceType.IOS.toString())) {
				return Platform.ios();
			}
			if (type.equals(DeviceType.WinPhone.toString())) {
				return Platform.winphone();
			}
		}
		return Platform.all();
	}

	public static boolean isPushApnsProduction() {
		return pushApnsProduction;
	}

	public static void setPushApnsProduction(boolean pushApnsProduction) {
		PushService.pushApnsProduction = pushApnsProduction;
	}
}
