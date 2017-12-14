package com.liubowen.socketiomahjong.mapper;

import com.liubowen.socketiomahjong.common.MyMapper;
import com.liubowen.socketiomahjong.entity.Test1;
import com.liubowen.socketiomahjong.util.mybatis.typeHandler.ListTypeHandler;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author liubowen
 * @date 2017/11/29 11:06
 * @description
 */
@Mapper
public interface Test1Mapper extends MyMapper<Test1> {

    @Select("SELECT * FROM test_1 WHERE id = #{id}")
    @Results({ @Result(column = "aa", property = "aa", jdbcType = JdbcType.VARCHAR, javaType = List.class, typeHandler = ListTypeHandler.class),
            @Result(column = "test_2_id", property = "test2", one = @One(select = "com.liubowen.socketiomahjong.mapper.Test2Mapper.selectByPrimaryKey")),
            @Result(property = "test3s", column = "id", javaType = List.class, many = @Many(select = "com.liubowen.socketiomahjong.mapper.Test3Mapper.selectByTest1Id")) })
    Test1 findTest1ById(@Param("id") int id);

    // @Insert("")
    // void save(@Param("test1") Test1 test1);
    //
    // @Update("")
    // void update(@Param("id") int id);
    //
    // @Delete("")
    // void delete(@Param("id") int id);

}
