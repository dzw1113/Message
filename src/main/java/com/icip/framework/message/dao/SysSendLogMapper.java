package com.icip.framework.message.dao;

import com.icip.framework.message.bean.SysSendLog;
import com.icip.framework.message.bean.SysSendLogKey;
import java.util.List;
import java.util.Map;

public interface SysSendLogMapper {
    List<SysSendLog> selectAll(Map map);

    int delete(SysSendLogKey key);

    int insert(SysSendLog record);

    SysSendLog selectByKey(SysSendLogKey key);

    int update(SysSendLog record);
}