package com.liubowen.socketiomahjong.mapper;

import com.liubowen.socketiomahjong.common.MyMapper;
import com.liubowen.socketiomahjong.entity.Test3;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liubowen
 * @date 2017/11/29 11:06
 * @description
 */
@Mapper
public interface Test3Mapper extends MyMapper<Test3> {

    @Select("select * from test_3 where test_1_id = #{test_1_id}")
    List<Test3> selectByTest1Id(@Param("test_1_id") int test1Id);
}
