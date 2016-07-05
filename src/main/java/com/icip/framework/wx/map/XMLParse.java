package com.icip.framework.wx.map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * 提供提取消息格式中的密文及生成回复消息格式的接口.
 *
 */
class XMLParse {

    /**
     * 提取出xml数据包中的加密消息
     *
     * @param xmltext 待提取的xml字符串
     * @return 提取出的加密消息字符串
     * @throws com.icip.framework.wx.map.AesException
     */
    public static Object[] extract(String xmltext) throws AesException {
        try {
            SAXParserFactory sax = SAXParserFactory.newInstance();
            SAXParser parser = sax.newSAXParser();
            final Map<String,Object[]> map = new HashMap<String,Object[]>();
            DefaultHandler handler = new DefaultHandler(){
                private Object[] result =  new Object[3];
                private String temp;

                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    super.startElement(uri, localName, qName, attributes);
                }

                @Override
                public void endElement(String uri, String localName, String qName) throws SAXException {
                    if (qName.equalsIgnoreCase("Encrypt")){
                        result[1] = temp;
                        return ;
                    }

                    if (qName.equalsIgnoreCase("ToUserName")){
                        result[2] = temp;
                        return ;
                    }

                    if (qName.equalsIgnoreCase("xml")) {
                        result[0] = 0;
                        map.put("result", result);
                    }
                }

                @Override
                public void characters(char[] ch, int start, int length) throws SAXException {
                    temp = new String(ch, start, length);
                }
            };

            InputStream is = new ByteArrayInputStream(xmltext.getBytes());
            parser.parse(is, handler);
            return map.get("result");
        } catch (Exception e) {
            throw new AesException(AesException.ParseXmlError);
        }

    }
}
