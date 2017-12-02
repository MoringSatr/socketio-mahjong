package com.liubowen.socketiomahjong.mapper;

import com.liubowen.socketiomahjong.common.MyMapper;
import com.liubowen.socketiomahjong.entity.AccountInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author liubowen
 * @date 2017/11/29 11:06
 * @description
 */
@Mapper
public interface AccountInfoMapper extends MyMapper<AccountInfo> {

    @Select("SELECT count(account) > 0 FROM t_accounts WHERE account = #{account}")
    boolean isUserExist(@Param("account") String account);

    @Select("SELECT * FROM t_accounts WHERE account = #{account} and password = #{password}")
    AccountInfo findAccountInfoByAccountAndPassword(@Param("account") String account, @Param("password") String password);
}
