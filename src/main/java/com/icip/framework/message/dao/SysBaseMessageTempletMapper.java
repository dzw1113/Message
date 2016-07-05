package com.icip.framework.message.dao;

import java.util.List;
import java.util.Map;

import com.icip.framework.message.bean.SysBaseMessageTemplet;

public interface SysBaseMessageTempletMapper {
    @SuppressWarnings("rawtypes")
	List<SysBaseMessageTemplet> selectAll(Map map);

    int delete(String bmtCode);

    int insert(SysBaseMessageTemplet record);

    SysBaseMessageTemplet selectByKey(String bmtCode);

    int update(SysBaseMessageTemplet record);
}