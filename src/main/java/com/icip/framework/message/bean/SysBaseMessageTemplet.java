package com.icip.framework.message.bean;
/**
* @Description: 系统模板类（消息模板）
* @author  肖伟
* @date 2016年6月12日 下午3:24:37
* @update 2016年6月12日 下午3:24:37
 */

public class SysBaseMessageTemplet {
    private String bmtCode;//模板代码

    private String bmtType;//模板类型	1：邮箱	 2：短信	3：极光推送 微信  5：语音

    private String bmtTitle;//模板标题

    private String bmtContent;//模板内容

    private String updateTime;//修改时间

    private String state;//状态0停用 1启用

    public String getBmtCode() {
        return bmtCode;
    }

    public void setBmtCode(String bmtCode) {
        this.bmtCode = bmtCode == null ? null : bmtCode.trim();
    }

    public String getBmtType() {
        return bmtType;
    }

    public void setBmtType(String bmtType) {
        this.bmtType = bmtType == null ? null : bmtType.trim();
    }

    public String getBmtTitle() {
        return bmtTitle;
    }

    public void setBmtTitle(String bmtTitle) {
        this.bmtTitle = bmtTitle == null ? null : bmtTitle.trim();
    }

    public String getBmtContent() {
        return bmtContent;
    }

    public void setBmtContent(String bmtContent) {
        this.bmtContent = bmtContent == null ? null : bmtContent.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
}