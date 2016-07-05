package com.icip.framework.wx.vo;

/**
 * 微信分组信息
 *
 */
public class Group {

    /**
     * 分组唯一ID
     */
    private int id;

    /**
     * 分组名称
     */
    private String name;

    /**
     * 用户统计
     */
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
