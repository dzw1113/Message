package com.icip.framework.message.dao;

import com.icip.framework.message.bean.SysSendControl;
import com.icip.framework.message.bean.SysSendControlKey;
import java.util.List;
import java.util.Map;

public interface SysSendControlMapper {
    List<SysSendControl> selectAll(Map map);

    int delete(SysSendControlKey key);

    int insert(SysSendControl record);

    SysSendControl selectByKey(SysSendControlKey key);

    int update(SysSendControl record);
    
    Map<String,Object> selectRule(Map<String,String> map);
}