package com.icip.framework.wx.web;

import com.icip.framework.wx.core.WxBase;
import com.icip.framework.wx.core.WxHandler;
import com.icip.framework.wx.vo.MPAct;

/**
 * 微信WEB环境接口设计
 *
 */
public interface WxWebSupport {

    /**
     * 设置微信公众号信息
     *
     * @param mpAct 公众号信息
     */
    void setMpAct(MPAct mpAct);

    /**
     * 设置微信消息处理器
     *
     * @param wxHandler 微信消息处理器
     */
    void setWxHandler(WxHandler wxHandler);

    /**
     * 获取微信基础功能
     *
     * @return 基础功能
     */
    WxBase getWxBase();
}
