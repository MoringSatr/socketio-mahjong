package com.liubowen.socketiomahjong.mapper;

import com.liubowen.socketiomahjong.common.MyMapper;
import com.liubowen.socketiomahjong.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author liubowen
 * @date 2017/11/9 21:30
 * @description
 */
@Mapper
public interface UserInfoMapper extends MyMapper<UserInfo> {

    @Select("SELECT count(userid) > 0 FROM t_users WHERE account = #{account}")
    boolean isUserExistByAccount(@Param("account") String account);

    @Select("SELECT * FROM t_users WHERE account = #{account}")
    UserInfo findUserInfoByAccount(@Param("account") String account);

}
