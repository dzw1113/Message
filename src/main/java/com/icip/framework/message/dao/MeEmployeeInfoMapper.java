package com.icip.framework.message.dao;

import java.util.List;
import java.util.Map;

import com.icip.framework.message.bean.MeEmployeeInfo;

public interface MeEmployeeInfoMapper {
    List<Map<String,String>> selectAll(@SuppressWarnings("rawtypes") Map map);

    int update(MeEmployeeInfo record);
}