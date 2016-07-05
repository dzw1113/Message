package wx.test;

import java.util.Properties;

import org.junit.Before;

import com.icip.framework.wx.vo.MPAct;

/**
 * 测试父类
 * 
 */
public class TestSupport {
    
    protected String mpId;
    protected String appId;
    protected String appSecret;
    protected String token;
    protected String openId;
    protected String aesKey;
    
    protected String templateId;
    protected String mediaId;
    protected String accessToken;

    protected String msgSing;
    protected String timestamp;
    protected String echostr;
    protected String nonce;

    protected MPAct mpAct;
    
    @Before
    public void init() throws Exception {
        Properties p = new Properties();
        p.load(getClass().getResourceAsStream("/message.properties"));
        
        mpId = p.getProperty("WX_MPID");
        appId = p.getProperty("WX_APPID");
        appSecret = p.getProperty("WX_APPSECRET");
        token = p.getProperty("WX_TOKEN");
        openId = p.getProperty("WX_OPENID");
        aesKey = p.getProperty("WX_AESKEY");
        
        templateId = p.getProperty("WX_TEMPLATEID");
        mediaId = p.getProperty("WX_MEDIAID");
        accessToken = p.getProperty("WX_ACCESSTOKEN", "NOT");

        msgSing = p.getProperty("msgSing");
        timestamp = p.getProperty("timestamp");
        echostr = p.getProperty("echostr");
        nonce = p.getProperty("nonce");

        mpAct = new MPAct();
        mpAct.setMpId(mpId);
        mpAct.setAppId(appId);
        mpAct.setAppSecret(appSecret);
        mpAct.setToken(token);
        mpAct.setAESKey(aesKey);
        if (!accessToken.equals("NOT")||!accessToken.isEmpty()) {
            mpAct.setAccessToken(accessToken);
            mpAct.setExpiresIn(7000 * 1000 + System.currentTimeMillis());
        }
    }
}
