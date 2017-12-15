package com.liubowen.socketiomahjong.mapper;

import com.liubowen.socketiomahjong.common.MyMapper;
import com.liubowen.socketiomahjong.entity.GameInfo;
import com.liubowen.socketiomahjong.entity.RoomConfigInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liubowen
 * @date 2017/11/9 21:30
 * @description
 */
@Mapper
public interface RoomConfigInfoMapper extends MyMapper<RoomConfigInfo> {
}
