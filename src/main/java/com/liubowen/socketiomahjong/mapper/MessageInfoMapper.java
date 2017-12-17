package com.liubowen.socketiomahjong.mapper;

import com.liubowen.socketiomahjong.common.MyMapper;
import com.liubowen.socketiomahjong.entity.MessageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author liubowen
 * @date 2017/11/9 21:30
 * @description
 */
@Mapper
public interface MessageInfoMapper extends MyMapper<MessageInfo> {

    @Select("SELECT * FROM t_message WHERE `type`=#{type} AND `version`=#{version}")
    MessageInfo findMessageInfoByTypeAndVersion(@Param("type") String type,
                                                @Param("version") String version);

}
