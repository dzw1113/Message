package com.icip.framework.message.adapter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.icip.framework.message.send.PushSender;
import com.icip.framework.message.send.Sender;
import com.icip.utils.AppUtil;

/**
 * 推送适配器
 * 
 * @author lenovo
 * 
 */
public class PushSenderAdapter implements Sender {
	
	static final LinkedBlockingQueue<Map<String,Object>> queue = new LinkedBlockingQueue<Map<String,Object>>();
	
	@Override
	public void sender(String title, String content, String[] to, Map<String, String> params) throws Exception {
		PushSender pushSender = (PushSender) AppUtil.getBean("pushSender");
//		List<List<String>> list = new ArrayList<List<String>>();
//		List<String> alist = new ArrayList<String>();
//		for (int i = 0; i < to.length; i++) {
//			if(i%1000==0 && i!=0){
//				list.add(alist);
//				alist = new ArrayList<String>();
//			}
//			alist.add(to[i]);
//			if(i==to.length-1 && alist.size()>0){
//				list.add(alist);
//			}
//		}
		
		for (int i = 0; i < to.length; i++) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("title", title);
			map.put("content", content);
			map.put("params", params);
			map.put("to", to[i]);
			queue.offer(map);
			ListenerThread.getInstance(queue, pushSender);
		}
		
	}

	@Override
	public void sender(String bmtCode, String[] to, Map<String, String> params)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}

class ListenerThread extends Thread {
	private static LinkedBlockingQueue<Map<String,Object>> queue;
	private static PushSender pushSender;

	private static volatile ListenerThread instance = null;

	private ListenerThread() {
	}

	public static ListenerThread getInstance(LinkedBlockingQueue<Map<String,Object>> queue,PushSender pushSender) {
		if (instance == null) {
			synchronized (ListenerThread.class) {
				if (instance == null) {
					instance = new ListenerThread(queue, pushSender);
					instance.start();
				}
			}
		}
		return instance;
	}

	private ListenerThread(LinkedBlockingQueue<Map<String,Object>> queue,PushSender pushSender) {
		this.queue = queue;
		this.pushSender = pushSender;
	}

	public void run() {
		while (true) {
			Map<String,Object> ret = queue.poll();
			try {
				String title = ret.get("title")==null?"":ret.get("title").toString();
				String content = ret.get("content").toString();
				Map<String,String> params = (Map<String, String>) ret.get("params");
				String to = ret.get("to").toString();
				if (ret == null) {
				} else {
					try {
						pushSender.sendPushMessage(null, Arrays.asList(to), title, content, params);
					} catch (Exception e) {
					}
				}
			} catch (Exception e) {
			}
		}
	}
};