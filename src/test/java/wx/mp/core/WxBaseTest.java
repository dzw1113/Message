package wx.mp.core;

import static org.hamcrest.core.Is.is;

import static org.junit.Assert.*;

import org.junit.Test;

import wx.test.TestSupport;

import com.icip.framework.wx.core.WxBase;
import com.icip.framework.wx.core.WxDefaultHandler;
import com.icip.framework.wx.core.WxHandler;
import com.icip.framework.wx.util.StreamTool;
import com.icip.framework.wx.util.XmlMsgBuilder;


/**
 * 微信公众平台普通消息互动测试
 *
 */
public class WxBaseTest extends TestSupport {

    private WxBase wxBase = new WxBase();
    private WxHandler handler = new WxDefaultHandler();

    @Override
    public void init() throws Exception {
        super.init();
        this.wxBase.setMpAct(mpAct);
        this.wxBase.setWxHandler(handler);
        this.wxBase.setMsgSignature(msgSing);
        this.wxBase.setTimeStamp(timestamp);
        this.wxBase.setNonce(nonce);
        this.wxBase.setMpAct(mpAct);
        this.wxBase.setAesEncrypt(true);
    }


    private String textXml = "<xml><ToUserName><![CDATA[" + this.mpId + "]]></ToUserName>\n" +
            "<FromUserName><![CDATA[" + this.openId + "]]></FromUserName>\n" +
            "<CreateTime>1418182341</CreateTime>\n" +
            "<MsgType><![CDATA[text]]></MsgType>\n" +
            "<Content><![CDATA[你好!]]></Content>\n" +
            "<MsgId>6091046778677430</MsgId>\n" +
            "</xml>";

    private String enrcpXml = "<xml>\n" +
            "    <ToUserName><![CDATA[" + this.mpId + "]]></ToUserName>\n" +
            "    <Encrypt><![CDATA[8K+mgBCAiyKgFlAr7yfUeKUrYd0jJyy4pzj3QpGCW3+j/7u0H2d9Rkw0xH0FMXWYBE86eOHWkElQZwqnCsmPx/7mMe5Truivwp7CO/Dd4iOFoZ2rElBYDAZYBjwajKX3Gp0g2wZ8RSR1jUo4P77wpS8SprGxMFTOUUsSlUvbehMShfLFSAxmkqIKxiIM5y/R1htyk6aWXp0mA3F//jDq6XFYA7+DyBU2xLa4Lwv4zcGZ0rzjepm5Iy3szjRq3KhesjgKpeFZKHZameaAEUOKmrmEXraL4V6SDs9tGMacfP/1gYbwc7CtXLQ6qD6dmPiewk8nAw3zfhY+58ihnUfADanmEXT/eHcKGaSRr0lS1xnzUp62xJPdFutoUDqrP70bbv+fkMMo5S6UKdLKnnVcUk1jlBQf5q0hUQbfyPcUFAwEFUtTEP5EW+uEqOexBghH7CQRrw3490yVOd4Hs0FLmg==]]></Encrypt>\n" +
            "</xml>";

    private String pushXml = "<xml>\n" +
            "<AppId>" + this.mpId + "</AppId>\n" +
            "<CreateTime>1413192605</CreateTime>\n" +
            "<InfoType>component_verify_ticket</InfoType>\n" +
            "<ComponentVerifyTicket></ComponentVerifyTicket>\n" +
            "</xml>";

    /**
     * 测试微信验证URL方法
     */
    @Test
    public void testCheck() throws Exception {
        String echostr = this.wxBase.check();
        assertThat(echostr, is(this.wxBase.getEchostr()));
    }

    /**
     * 测试微信普通互动与事件消息
     */
    @Test
    public void testHandler() throws Exception {
        if (this.wxBase.isAesEncrypt()) {
            // 加密消息
            this.wxBase.setWxInMsg(StreamTool.toStream(this.enrcpXml));
        } else {
            this.wxBase.setWxInMsg(StreamTool.toStream(this.textXml));
        }
        String reply_msg = this.wxBase.handler();
        String xml_msg = XmlMsgBuilder.create().
                text(this.wxBase.getOutPutMsg()).build();
        assertThat(reply_msg, is(xml_msg));
    }

    /**
     * 测试微信开放平台推送事件消息
     */
    @Test
    public void testHandlerPush() throws Exception {
        this.wxBase.setWxInMsg(StreamTool.toStream(this.pushXml));
        String reply_msg = this.wxBase.handlerPush();
        assertThat("success", is(reply_msg));
    }
}