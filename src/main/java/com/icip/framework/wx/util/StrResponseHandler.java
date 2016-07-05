package com.icip.framework.wx.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * HTTP请求响应处理
 *
 */
public class StrResponseHandler implements ResponseHandler<String> {

    @Override
    public String handleResponse(HttpResponse resp)
            throws ClientProtocolException, IOException {
        int status = resp.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = resp.getEntity();
            String body = (null!= entity) ? EntityUtils.toString(entity,"UTF-8") : "";
            return body;
        } else {
            throw new ClientProtocolException("请求失败,服务器响应代码: " + status);
        }
    }
}
