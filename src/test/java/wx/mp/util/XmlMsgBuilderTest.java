package wx.mp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import wx.test.TestSupport;

import com.icip.framework.wx.core.WxApi;
import com.icip.framework.wx.util.XmlMsgBuilder;
import com.icip.framework.wx.vo.Article;
import com.icip.framework.wx.vo.OutPutMsg;

public class XmlMsgBuilderTest extends TestSupport {

    private OutPutMsg msg;

    @Before
    public void init() {
        msg = new OutPutMsg(openId, mpId, WxApi.TEXT);
        List<Article> articles = new ArrayList<Article>();
        for (int i=1; i<11; i++) {
            Article a = new Article();
            a.setTitle(getRandomStr(new Random().nextInt(100)+20));
            a.setDescription(getRandomStr(new Random().nextInt(200) + 120));
            a.setPicUrl(getRandomStr(new Random().nextInt(60) + 20));
            a.setUrl(getRandomStr(new Random().nextInt(80) + 20));
            articles.add(a);
        }
        msg.setArticles(articles);
    }

    private String getRandomStr(int length) {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    @Test
    public void testText() throws Exception {
        msg.setContent(getRandomStr(20));
        long t1 = System.currentTimeMillis();
        String xml = XmlMsgBuilder.create().text(msg).build();
        long t2 = System.currentTimeMillis();
        System.out.println(xml);
        System.out.println((t2-t1));
    }

    @Test
    public void testImage() throws Exception {

    }

    @Test
    public void testNews() throws  Exception {
        long t = 0l;
        String xml = "";
        for(int i=0; i<101; i++) {
            long t1 = System.currentTimeMillis();
            xml = XmlMsgBuilder.create().news(msg).build();
            long t2 = System.currentTimeMillis();
            t+= (t2 - t1);
        }
        System.out.println(((double)t / 101.0));
        t = 0l;
        for(int i=0; i<101; i++) {
            long t1 = System.currentTimeMillis();
            XmlMsgBuilder xb = new XmlMsgBuilder();
            xb.news(msg);
            xml = xb.build();
            long t2 = System.currentTimeMillis();
            t+= (t2 - t1);
        }
        System.out.println(((double)t / 101.0));
        System.out.println(xml);
    }
}