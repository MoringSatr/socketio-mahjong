package com.liubowen.socketiomahjong.mapper;

import com.liubowen.socketiomahjong.common.MyMapper;
import com.liubowen.socketiomahjong.entity.RoomInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author liubowen
 * @date 2017/11/9 21:30
 * @description
 */
@Mapper
public interface RoomInfoMapper extends MyMapper<RoomInfo> {

    @Select("SELECT count(id) > 0 FROM t_rooms WHERE id = #{roomId}")
    boolean isRoomExist(@Param("roomId") String roomId);

}
