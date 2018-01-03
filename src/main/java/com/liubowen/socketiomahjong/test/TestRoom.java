package com.liubowen.socketiomahjong.test;

import java.util.List;

/**
 * @author liubowen
 * @date 2018/1/3 23:27
 * @description
 */
public class TestRoom {

    private String id = "111111";

    private TestGame testGame;

    private TestGameConfig testGameConfig;

    public TestRoom(TestGameConfig testGameConfig) {
        this.testGameConfig = testGameConfig;
        this.testGame = new TestGame(testGameConfig);
    }

    /** 解散房间 */
    public void disband() {
        this.testGame = new TestGame(testGameConfig);
    }

    public TestGame getTestGame() {
        return this.testGame;
    }

    public void addPlayer(TestSeatPlayer testSeatPlayer) {
        this.testGame.addPlayer(testSeatPlayer);
    }

    public List<TestRoomSeatData> allTestRoomSeatDatas() {
        return this.testGame.allTestRoomSeatDatas();
    }

    public String getId() {
        return id;
    }

    public String getConf() {
        return "";
    }

    public int getNumOfGames() {
        return 0;
    }

    public void setReady(long userId, boolean ready) {
        this.testGame.setReady(userId, ready);
    }
}
