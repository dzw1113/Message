package wx.mp.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import wx.test.TestSupport;

import com.alibaba.fastjson.JSON;
import com.icip.framework.wx.commons.WxApiUrl;
import com.icip.framework.wx.util.SimpleHttpReq;

/**
 * HTTP请求测试
 *
 */
public class SimpleHttpReqTest extends TestSupport {

    private String mediaType = "image";

    @Before
    public void init() throws Exception {
        super.init();
    }

    @Test
    public void testGet() throws Exception {
        String url = String.format(WxApiUrl.ACCESS_TOKEN_API, appId, appSecret);
        System.out.println(url);
        String content = SimpleHttpReq.get(url);
        System.out.println(content);
    }

    @Test
    public void testPost() throws Exception {
        Map<String,Object> msg =  new HashMap<String, Object>();
        msg.put("touser", openId);
        msg.put("msgtype", mediaType);
        Map<String,String> image = new HashMap<String, String>();
        image.put("media_id", mediaId);
        msg.put("image", image);
        String url = String.format(WxApiUrl.CUSTOM_MESSAGE_API, accessToken);
        System.out.println(url);
        System.out.println(JSON.toJSONString(msg));
        String content = SimpleHttpReq.post(url, SimpleHttpReq.APPLICATION_JSON, JSON.toJSONString(msg));
        System.out.println(content);
    }

    @Test
    public void testUpload() throws Exception {
        File f = new File("D:/upload.jpg");
        String url = String.format(WxApiUrl.MEDIA_UP_API, mediaType, accessToken);
        System.out.println(url);
        String content = SimpleHttpReq.upload(url, f);
        System.out.println(content);
    }

    @Test
    public void testDownload() throws Exception {
        File f = new File("D:/download.jpg");
        String url = String.format(WxApiUrl.MEDIA_DL_API, accessToken, mediaId);
        System.out.println(url);
        SimpleHttpReq.download(url, f);
    }
}