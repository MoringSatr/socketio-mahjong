package com.liubowen.socketiomahjong.mapper;

import com.liubowen.socketiomahjong.common.MyMapper;
import com.liubowen.socketiomahjong.entity.RoomConfigInfo;
import com.liubowen.socketiomahjong.entity.RoomPlayerInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author liubowen
 * @date 2017/11/9 21:30
 * @description
 */
@Mapper
public interface RoomPlayerInfoMapper extends MyMapper<RoomPlayerInfo> {

    @Select("SELECT * FROM room_player WHERE room_id=#{roomId}")
    List<RoomPlayerInfo> findRoomPlayerInfosByRoomId(@Param("roomId") String roomId);

}
