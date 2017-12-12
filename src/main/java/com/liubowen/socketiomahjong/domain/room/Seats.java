package com.liubowen.socketiomahjong.domain.room;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liubowen.socketiomahjong.entity.RoomInfo;

import java.util.List;
import java.util.Map;

/**
 * @author liubowen
 * @date 2017/12/12 10:10
 * @description 位子管理
 */
public class Seats {

    /** 位子信息集合 */
    private Map<Integer, Seat> seats;

    /** 玩家id和位子索引集合 */
    private Map<Long, Integer> userIdWithSeatIndexMap;

    public Seats(RoomInfo roomInfo) {
        this.seats = Maps.newHashMap();
        this.userIdWithSeatIndexMap = Maps.newHashMap();
        initSeat();
        initRoomPlayers(roomInfo);
    }

    private void initSeat() {
        for (int i = 0; i < 4; i++) {
            Seat seat = new Seat();
            seat.setSeatIndex(i);
            this.seats.put(seat.getSeatIndex(), seat);
        }
    }

    private void initRoomPlayers(RoomInfo roomInfo) {
        List<RoomPlayer> roomPlayers = roomInfo.getRoomPlayers();
        roomPlayers.forEach(roomPlayer -> {
            putRoomPLayerOnSeat(roomPlayer);
        });
    }

    public List<Seat> allSeat() {
        return Lists.newArrayList(this.seats.values());
    }

    public List<RoomPlayer> allRoomPlayer() {
        List<RoomPlayer> roomPlayers = Lists.newArrayList();
        this.allSeat().forEach(seat -> {
            if (seat.hasRoomPlayer()) {
                roomPlayers.add(seat.getRoomPlayer());
            }
        });
        return roomPlayers;
    }

    public void putRoomPLayerOnSeat(RoomPlayer roomPlayer) {
        int seatIndex = roomPlayer.getSeatIndex();
        Seat seat = this.seats.get(seatIndex);
        seat.setRoomPlayer(roomPlayer);
        this.userIdWithSeatIndexMap.put(roomPlayer.getUserId(), seatIndex);
    }

    public Seat getSeatByUserId(long userId) {
        if (!this.userIdWithSeatIndexMap.containsKey(userId)) {
            return null;
        }
        int seatIndex = this.userIdWithSeatIndexMap.get(userId);
        return this.seats.get(seatIndex);
    }

    public void exitRoom(long userId) {
        Seat seat = this.getSeatByUserId(userId);
        seat.exitRoom();
        this.userIdWithSeatIndexMap.remove(userId);
    }
}
