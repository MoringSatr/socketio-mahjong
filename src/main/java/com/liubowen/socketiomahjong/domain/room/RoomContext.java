package com.liubowen.socketiomahjong.domain.room;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.liubowen.socketiomahjong.common.Initializeable;
import com.liubowen.socketiomahjong.common.Storageable;
import com.liubowen.socketiomahjong.domain.user.UserLocation;
import com.liubowen.socketiomahjong.entity.RoomInfo;
import lombok.experimental.var;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * @author liubowen
 * @date 2017/11/10 6:02
 * @description
 */
@Component
public class RoomContext implements Storageable, Initializeable {

    private static int[] DI_FEN = new int[]{1, 2, 5};
    private static int[] MAX_FAN = new int[]{3, 4, 5};
    private static int[] JU_SHU = new int[]{4, 8};
    private static int[] JU_SHU_COST = new int[]{2, 3};

    private ConcurrentMap<String, Room> roomMap = Maps.newConcurrentMap();

    private Set<String> creatingRoomIds = Sets.newConcurrentHashSet();

    private ConcurrentMap<Long, UserLocation> userLocationMap = Maps.newConcurrentMap();

    @Override
    public void initialize() {
        initCreatingRoomIds();
    }

    @Override
    public void storage() {
        this.roomMap.forEach((roomId, room) -> {
            room.save();
        });
        this.userLocationMap.forEach((userId, userLocation) -> {
            userLocation.save();
        });
    }

    private void initCreatingRoomIds() {
        this.creatingRoomIds.addAll(this.roomMap.keySet());
    }

    private String generateRoomId() {
        String roomId = "";
        for (int i = 0; i < 6; i++) {
            roomId += Math.round(Math.random() * 9);
        }
        return roomId;
    }

    public Room constructRoomFromDb(RoomInfo roomInfo) {
        Room room = this.initRoom(roomInfo);
        this.addRoom(room);
        return room;
    }

    private Room initRoom(RoomInfo roomInfo) {
        Room room = new Room(roomInfo);
        this.initUserLocation(room.allRoomPlayer(), room.getId());
        return room;
    }

    /**
     * 初始化玩家所在位置信息
     */
    private void initUserLocation(List<RoomPlayer> roomPlayers, String roomId) {
        roomPlayers.forEach(roomPlayer -> {
            UserLocation userLocation = new UserLocation();
            userLocation.setRoomId(roomId);
            long userId = roomPlayer.getUserId();
            userLocation.setUserId(userId);
            userLocation.setSeatIndex(roomPlayer.getSeatIndex());
            this.userLocationMap.put(userId, userLocation);
        });
    }

    private void addRoom(Room room) {
        String roomId = room.getId();
        this.roomMap.put(roomId, room);
        this.creatingRoomIds.add(roomId);
    }

    public Room createRoom(RoomConfig roomConfig, int gems, String ip, int port) {
        if (roomConfig == null) {
            return null;
        }

        if (roomConfig.getDifen() < 0 || roomConfig.getDifen() > DI_FEN.length) {
            return null;
        }

        if (roomConfig.getZimo() < 0 || roomConfig.getZimo() > 2) {
            return null;
        }

        if (roomConfig.getZuidafanshu() < 0 || roomConfig.getZuidafanshu() > MAX_FAN.length) {
            return null;
        }

        if (roomConfig.getJushuxuanze() < 0 || roomConfig.getJushuxuanze() > JU_SHU.length) {
            return null;
        }

        int cost = JU_SHU_COST[roomConfig.getJushuxuanze()];
        if (cost > gems) {
            return null;
        }
        return this.fnCreate(ip, port, roomConfig);
    }

    private Room fnCreate(String ip, int port, RoomConfig roomConfig) {
        String roomId = generateRoomId();
        if (this.creatingRoomIds.contains(roomId)) {
            this.fnCreate(ip, port, roomConfig);
        }
        RoomInfo roomInfo = new RoomInfo(roomId, ip, port, roomConfig);
        Room room = this.initRoom(roomInfo);
        this.addRoom(room);
        return room;
    }

    public void destroy(String roomId) {
        Room room = this.getRoom(roomId);
        if (room == null) {
            return;
        }
        List<RoomPlayer> roomPlayers = room.allRoomPlayer();
        roomPlayers.forEach(roomPlayer -> {
            this.userLocationMap.remove(roomPlayer.getUserId());
        });
        this.roomMap.remove(roomId);
        this.creatingRoomIds.remove(roomId);
    }

    public int getTotalRooms() {
        return this.roomMap.size();
    }

    public Room getRoom(String roomId) {
        return this.roomMap.get(roomId);
    }

    public boolean isCreator(String roomId, long userId) {
        Room room = this.getRoom(roomId);
        if (room == null) {
            return false;
        }
        return room.isCreator(userId);
    }

    public void enterRoom(String roomId, long userId, String userName) {
        Room room = this.getRoom(roomId);
        RoomPlayer roomPlayer = new RoomPlayer(userId, userName, 100);
        room.enterRoom(roomPlayer);
    }

    public void setReady(long userId, boolean ready) {
        Room room = this.getRoomByUserId(userId);
        Seat seat = room.getSeatByUserId(userId);
        if (seat == null) {
            return;
        }
        seat.setReady(ready);
    }

    public boolean isReady(long userId) {
        Room room = this.getRoomByUserId(userId);
        Seat seat = room.getSeatByUserId(userId);
        if (seat == null) {
            return false;
        }
        return seat.isReady();
    }

    public String getUserRoom(long userId) {
        UserLocation userLocation = this.userLocationMap.get(userId);
        if (userLocation == null) {
            return null;
        }
        return userLocation.getRoomId();
    }

    public int getUserSeat(long userId) {
        UserLocation userLocation = this.userLocationMap.get(userId);
        if (userLocation == null) {
            return 0;
        }
        return userLocation.getSeatIndex();
    }

    public List<UserLocation> getUserLocations() {
        return Lists.newArrayList(this.userLocationMap.values());
    }

    public void exitRoom(long userId) {
        Room room = this.getRoomByUserId(userId);
        if (room == null) {
            return;
        }
        Seat seat = room.getSeatByUserId(userId);
        if (seat == null) {
            return;
        }
        room.exitRoom(userId);
        this.userLocationMap.remove(userId);
    }

    private Room getRoomByUserId(long userId) {
        UserLocation userLocation = this.userLocationMap.get(userId);
        if (userLocation == null) {
            return null;
        }
        String roomId = userLocation.getRoomId();
        return this.getRoom(roomId);
    }
}
