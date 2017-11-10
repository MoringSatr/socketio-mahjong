package com.liubowen.socketiomahjong.domain.room;

import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.domain.user.UserLocation;
import com.liubowen.socketiomahjong.entity.RoomInfo;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;

/**
 * @author liubowen
 * @date 2017/11/10 6:02
 * @description
 */
@Component
public class RoomContext {

    private ConcurrentMap<Integer, Room> roomMap = Maps.newConcurrentMap();

    private ConcurrentMap<Integer, Room> creatingRoomMap = Maps.newConcurrentMap();

    private ConcurrentMap<Long, UserLocation> userLocationMap = Maps.newConcurrentMap();


    private static int generateRoomId() {
        String roomId = "";
        for(int i = 0; i < 6; i++){
            roomId += Math.round(Math.random() * 9);
        }
        return Integer.valueOf(roomId);
    }

    public Room constructRoomFromDb(RoomInfo roomInfo) {
        Room room = new Room(roomInfo);
        room.getSeats().forEach((index, seat) -> {
            if (seat.getUserId() > 0) {
                UserLocation userLocation = new UserLocation();
                userLocation.setRoomId(roomInfo.getId());
                userLocation.setUserId(seat.getUserId());
                userLocation.setSeatIndex(index);
            }
        });
        return room;
    }

    public Room createRoom() {
        return null;
    }
}
