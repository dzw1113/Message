package com.icip.framework.message.dao;

import java.util.List;
import java.util.Map;

import com.icip.framework.message.bean.UserRelative;
import com.icip.framework.message.bean.UserRelativeKey;

public interface UserRelativeMapper {
    List<UserRelative> selectAll(@SuppressWarnings("rawtypes") Map map);

    int delete(UserRelativeKey key);

    int insert(UserRelative record);

    UserRelative selectByKey(UserRelativeKey key);

    int update(UserRelative record);
    
    List<UserRelative> selectByCustNos(Map<String,Object> map);
    
    int selectByInfo(Map<String,Object> map);
}