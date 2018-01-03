package com.liubowen.socketiomahjong.test;

import org.springframework.stereotype.Component;

/**
 * @author liubowen
 * @date 2018/1/3 23:28
 * @description
 */
@Component("testRoomContext")
public class TestRoomContext {

    private TestRoom room = new TestRoom(new TestGameConfig());

    public TestRoom createRoom() {
        return this.room;
    }

    public void disbandRoom() {
        this.room.disband();
    }

    public TestRoom getTestRoom() {
        return this.room;
    }

    public TestGame getTestGame() {
        return this.room.getTestGame();
    }

    public void addPlayer(TestSeatPlayer testSeatPlayer) {
        this.room.addPlayer(testSeatPlayer);
    }

}
