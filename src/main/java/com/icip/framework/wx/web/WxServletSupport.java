package com.icip.framework.wx.web;

import com.icip.framework.wx.core.WxBase;
import com.icip.framework.wx.core.WxHandler;
import com.icip.framework.wx.map.AesException;
import com.icip.framework.wx.vo.MPAct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet环境接入
 *
 */
public abstract class WxServletSupport extends HttpServlet implements WxWebSupport {

	private static final long serialVersionUID = 3333257260210703946L;
	private static final Logger log = LoggerFactory.getLogger(WxServletSupport.class);
    // 微信基础功能
    private WxBase wxBase = new WxBase();

    @Override
    public void init() throws ServletException {
        super.init();
        // 重写此方法
        // 1.设置微信公众号的信息
        // 2.setMpAct();
        // 3.setWxHandler();
    }

    /**
     * 处理微信接收URL验证
     *
     * @param req  请求
     * @param resp 响应
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {
        // 响应设置
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        this.wxBase.init(req);
        try {
            String echo = this.wxBase.check();
            PrintWriter out = resp.getWriter();
            if (!echo.isEmpty()) {
                out.print(echo);
            } else {
                out.print("error");
                log.error("微信接入验证URL时失败!!!");
                log.error("微信服务器echoStr值: {}", this.wxBase.getEchostr());
                log.error("SHA1签名echoStr值: {}", echo);
            }
        } catch (AesException e) {
            log.error("微信接入验证URL时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 处理微信普通消息
     *
     * @param req  请求
     * @param resp 响应
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        this.wxBase.init(req);
        String result = "error";
        try {
            result = this.wxBase.handler();
        } catch (Exception e) {
            log.error("解析微信消息时出现异常!!!");
            log.error(e.getLocalizedMessage(), e);
        }
        // 响应设置
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        resp.getWriter().print(result);
    }

    @Override
    public void setMpAct(MPAct mpAct) {
        this.wxBase.setMpAct(mpAct);
    }

    @Override
    public void setWxHandler(WxHandler wxHandler) {
        this.wxBase.setWxHandler(wxHandler);
    }

    @Override
    public WxBase getWxBase() {
        return this.wxBase;
    }
}
