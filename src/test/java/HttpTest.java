import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.icip.framework.wx.util.StrResponseHandler;
import com.icip.utils.encrypt.EncryptUtil;

public class HttpTest {

	private static final StrResponseHandler respHandler = new StrResponseHandler();

	public static void main(String[] args) throws Exception {
		System.err.println(EncryptUtil.encrypt("dzw1113"));
	}
	@Test
	public void sendTest() throws Exception {
		//String url = "http://localhost:8081/ICIPMessages/sendMsg.json";
		String url = "http://localhost:8081/ICIPMessages/sendMsgByCids.json";

		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> param = new HashMap<String, Object>();
		List<String> cids = new ArrayList<String>();
		cids.add("C2200000000217");
		param.put("productId", "1234");
		param.put("cids", cids);
		param.put("templateId", "me_model_sysMessage");

		// String[] to = {
		// "oKOV2wG5jxA1jKOouTJYq3ew4iNo","oKOV2wMJoC9MBmO5woOHAC_GLewY","oKOV2wKd0_rsAZ9fVuKYA5LeLSvY"
		// };
		String[] to = { "oKOV2wG5jxsA1jKOouTJYq3ew4iNo" };

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		list.add(new BasicNameValuePair("bmtCode", "20150825000013"));
		list.add(new BasicNameValuePair("params", JSONObject
				.toJSONString(param)));
		list.add(new BasicNameValuePair("to", JSONObject.toJSONString(to)));

		Request.Post(url).bodyForm(list, Consts.UTF_8).execute()
				.handleResponse(respHandler);
	}

	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	private List<BasicNameValuePair> getParamsList(String key, Object value) {
		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		if (value instanceof Map) {
			Map map = (Map) value;
			List list = new ArrayList<Object>(map.keySet());
			// Ensure consistent ordering in query string
			if (list.size() > 0 && list.get(0) instanceof Comparable) {
				Collections.sort(list);
			}
			for (Object nestedKey : list) {
				if (nestedKey instanceof String) {
					Object nestedValue = map.get(nestedKey);
					if (nestedValue != null) {
						params.addAll(getParamsList(
								key == null ? (String) nestedKey : String
										.format(Locale.US, "%s[%s]", key,
												nestedKey), nestedValue));
					}
				}
			}
		} else if (value instanceof List) {
			List list = (List) value;
			int listSize = list.size();
			for (int nestedValueIndex = 0; nestedValueIndex < listSize; nestedValueIndex++) {
				params.addAll(getParamsList(String.format(Locale.US, "%s[%d]",
						key, nestedValueIndex), list.get(nestedValueIndex)));
			}
		} else if (value instanceof Object[]) {
			Object[] array = (Object[]) value;
			int arrayLength = array.length;
			for (int nestedValueIndex = 0; nestedValueIndex < arrayLength; nestedValueIndex++) {
				params.addAll(getParamsList(String.format(Locale.US, "%s[%d]",
						key, nestedValueIndex), array[nestedValueIndex]));
			}
		} else if (value instanceof Set) {
			Set set = (Set) value;
			for (Object nestedValue : set) {
				params.addAll(getParamsList(key, nestedValue));
			}
		} else {
			params.add(new BasicNameValuePair(key, value.toString()));
		}
		return params;
	}
}
