package com.icip.framework.message.dao;

import java.util.Map;

import com.icip.framework.message.bean.MeSmsCloseOpend;
import com.icip.framework.message.bean.MeSmsCloseOpendKey;

public interface MeSmsCloseOpendMapper {

    MeSmsCloseOpend selectByKey(MeSmsCloseOpendKey key);

    int update(MeSmsCloseOpend record);
    
    Map<String,String> selectByCompCidInfo(Map<String,String> map);
}