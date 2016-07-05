package com.icip.framework.wx.vo;

/**
 * 弹出微信相册发图器或拍照的照片信息
 *
 */
public class PicInfo {

    /**
     * 图片的MD5值，开发者若需要，可用于验证接收到图片
     */
    private String picMd5Sum;

    public String getPicMd5Sum() {
        return picMd5Sum;
    }

    public void setPicMd5Sum(String picMd5Sum) {
        this.picMd5Sum = picMd5Sum;
    }

    @Override
    public String toString() {
        return "PicInfo{" +
                "picMd5Sum='" + picMd5Sum + '\'' +
                '}';
    }
}
