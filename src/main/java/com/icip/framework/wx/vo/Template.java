package com.icip.framework.wx.vo;

/**
 * 微信模板消息
 *
 */
public class Template {

    /**
     * 默认颜色(蓝色)
     */
    private String DEFAULT_COLOR = "#119EF3";

    /**
     * 模板字段名称
     */
    private String name;

    /**
     * 显示颜色
     */
    private String color;

    /**
     * 显示数据
     */
    private String value;

    public Template() {
    }

    public Template(String name, String color, String value) {
        this.name = name;
        this.color = color;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 组装模板数据
     */
    public String templateData() {
        StringBuffer data = new StringBuffer("\"" + name + "\":{");
        data.append("\"value\":\"").append(value).append("\",");
        data.append("\"color\":\"").append(color).append("\"}");
        return data.toString();
    }

	public String getDEFAULT_COLOR() {
		return DEFAULT_COLOR;
	}

	public void setDEFAULT_COLOR(String dEFAULT_COLOR) {
		DEFAULT_COLOR = dEFAULT_COLOR;
	}

}
