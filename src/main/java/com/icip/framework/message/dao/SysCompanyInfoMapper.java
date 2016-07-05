package com.icip.framework.message.dao;

import com.icip.framework.message.bean.SysCompanyInfo;
import java.util.List;
import java.util.Map;

public interface SysCompanyInfoMapper {
    List<SysCompanyInfo> selectAll(Map map);

    int delete(String companyId);

    int insert(SysCompanyInfo record);

    SysCompanyInfo selectByKey(String companyId);

    int update(SysCompanyInfo record);
}